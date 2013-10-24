package com.momoplan.pet.framework.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;

import com.momoplan.pet.commons.GeoHash;
import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.PushApn;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.cache.pool.RedisPool;
import com.momoplan.pet.commons.cache.pool.StorePool;
import com.momoplan.pet.commons.domain.user.dto.SsoAuthenticationToken;
import com.momoplan.pet.commons.domain.user.dto.UserLocation;
import com.momoplan.pet.commons.domain.user.mapper.PetInfoMapper;
import com.momoplan.pet.commons.domain.user.mapper.UserFriendshipMapper;
import com.momoplan.pet.commons.domain.user.po.PetInfo;
import com.momoplan.pet.commons.domain.user.po.PetInfoCriteria;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.commons.domain.user.po.UserFriendship;
import com.momoplan.pet.commons.domain.user.po.UserFriendshipCriteria;
import com.momoplan.pet.commons.repository.user.SsoUserRepository;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.user.enums.SubscriptionType;
import com.momoplan.pet.framework.user.service.UserService;
import com.momoplan.pet.framework.user.vo.UserVo;
/**
 * @author liangc
 */
@Service
public class UserServiceImpl implements UserService {

	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private CommonConfig commonConfig = null;
	private MapperOnCache mapperOnCache = null;
	private PetInfoMapper petInfoMapper = null; 
	private RedisPool redisPool = null;
	private UserFriendshipMapper userFriendshipMapper = null;
	private SsoUserRepository ssoUserRepository = null;
	private GeoHash geoHash = new GeoHash();
	private StorePool storePool = null;
	
	
	@Autowired
	public UserServiceImpl(CommonConfig commonConfig, MapperOnCache mapperOnCache, PetInfoMapper petInfoMapper, RedisPool redisPool, UserFriendshipMapper userFriendshipMapper,
			SsoUserRepository ssoUserRepository, StorePool storePool) {
		super();
		this.commonConfig = commonConfig;
		this.mapperOnCache = mapperOnCache;
		this.petInfoMapper = petInfoMapper;
		this.redisPool = redisPool;
		this.userFriendshipMapper = userFriendshipMapper;
		this.ssoUserRepository = ssoUserRepository;
		this.storePool = storePool;
	}

	@Override
	public void updateUser(SsoUser user) throws Exception {
		mapperOnCache.updateByPrimaryKeySelective(user, user.getId());
	}
	
	/**
	 * 获取附近的人，两个查询条件，分别是性别、宠物类型
	 * @param userId
	 * @param gender 性别
	 * @param petType 宠物类型
	 * @param longitude
	 * @param latitude
	 * @return
	 * @throws Exception
	 */
	public List<UserVo> getNearPerson(String userId,String gender,String petType, double longitude, double latitude) throws Exception {
		String hash = geoHash.encode(latitude, longitude);
		String subHash = hash.substring(0,6);
		logger.debug("区域编码 "+subHash);
		String key = USER_LOCATION_GEOHASH+"*"+subHash+"*";
		Set<String> uids = storePool.keys(key);
		List<UserVo> uvs = new ArrayList<UserVo>();
		for(String uk : uids){
			String uid = storePool.get(uk);
			if(!userId.equals(uid)){//附近的人里排除自己
				UserLocation ul = getUserLocation(uid);
				SsoUser user = mapperOnCache.selectByPrimaryKey(SsoUser.class, uid);
				user.setPassword(null);
				UserVo uv = new UserVo(user,ul.getLongitude()+"",ul.getLatitude()+"");
				//TODO 通过查询条件过滤
				logger.debug("//TODO 通过查询条件过滤");
				uvs.add(uv);
				logger.debug(userId+" 附近 "+uv.toString());
			}
		}
		//TODO 排序结果集，按照距离排序
		logger.debug("//TODO 排序结果集，按照距离排序");
		return uvs;
	}
	
	/**
	 * TODO 这个地方，如果压力过大，可以调整成云存储方式，但思路是一样的
	 * @param userId
	 * @param longitude
	 * @param latitude
	 * @throws Exception
	 */
	private void updateUserGeohash(String userId, double longitude, double latitude)throws Exception {
		try{
			String hash = geoHash.encode(latitude, longitude);
			String hashKey = USER_LOCATION_GEOHASH+userId+":"+hash;
			logger.debug("hash="+hash+" ; lat="+latitude+" ; lng="+longitude); 
			Set<String> oldKeys = storePool.keys(USER_LOCATION_GEOHASH+userId+"*");
			if(oldKeys!=null){
				String[] oks = oldKeys.toArray(new String[oldKeys.size()]);
				logger.debug("删除旧的坐标 del_geohash_keys_size="+oks.length);
				storePool.del(oks);
			}
			logger.debug("记录新的坐标 key="+hashKey+" ; value="+userId);
			storePool.set(hashKey, userId);
		}catch(Exception e){
			logger.error("坐标hash存储异常",e);
		}
	}
	
	@Override
	public void updateUserLocation(String userId, double longitude, double latitude) throws Exception {
		ShardedJedis jedis = null;
		updateUserGeohash(userId ,longitude ,latitude);
		try{
			jedis = redisPool.getConn();
			UserLocation ul = new UserLocation();
			ul.setCreateDate(new Date());
			ul.setLatitude(latitude);
			ul.setLongitude(longitude);
			ul.setUserid(userId);
			String json = MyGson.getInstance().toJson(ul);
			logger.debug("更新坐标,每个人一个列表 key="+LIST_USER_LOCATION+userId+" ; value="+json);
			jedis.lpush(LIST_USER_LOCATION+userId, json);
		}catch(Exception e){
			logger.error("更新坐标异常",e);
		}finally{
			redisPool.closeConn(jedis);
		}
	}
	private UserLocation getUserLocation(String userId){
		ShardedJedis jedis = null;
		try{
			jedis = redisPool.getConn();
			List<String> list = jedis.lrange(LIST_USER_LOCATION+userId, 0, 0);
			String json = list.get(0);
			logger.debug("获取坐标 : "+json);
			UserLocation ul = MyGson.getInstance().fromJson(json, UserLocation.class);
			return ul;
		}catch(Exception e){
			logger.error("获取坐标异常",e);
		}finally{
			redisPool.closeConn(jedis);
		}
		return null;
	}
	@Override
	public UserVo getUser(SsoAuthenticationToken tokenObj) throws Exception {
		String userid = tokenObj.getUserid();
		SsoUser user = mapperOnCache.selectByPrimaryKey(SsoUser.class, userid);
		UserLocation userLocation = getUserLocation(userid+"");
		UserVo userVo = null;
		user.setPassword(null);
		if(userLocation!=null){
			userVo = new UserVo(user,userLocation.getLongitude()+"",userLocation.getLatitude()+"");
		}else{
			userVo = new UserVo(user,"0","0");
		}
		return userVo;
	}


	@Override
	public List<PetInfo> getPetInfo(String userid) throws Exception {
		PetInfoCriteria petInfoCriteria = new PetInfoCriteria();
		PetInfoCriteria.Criteria criter =  petInfoCriteria.createCriteria();
		criter.andUseridEqualTo(userid);
		List<PetInfo> list = petInfoMapper.selectByExample(petInfoCriteria);
		return list;
	}

	@Override
	public void updatePetInfo(PetInfo petInfo) throws Exception {
		mapperOnCache.updateByPrimaryKeySelective(petInfo, petInfo.getId());
	}
	
	@Override
	public void savePetInfo(PetInfo petInfo) throws Exception {
		petInfo.setId(IDCreater.uuid());
		mapperOnCache.insertSelective(petInfo, petInfo.getId());
	}

	@Override
	public void addOrRemoveFriend(String st, String aid, String bid) throws Exception {
		String ua = ssoUserRepository.getSsoUserByName(aid).getId();
		String ub = ssoUserRepository.getSsoUserByName(bid).getId();
		if("subscribed".equalsIgnoreCase(st)){
			//目前，其实没有状态，冗余一个状态吧先
			String type = SubscriptionType.SUB.toString();//TODO 这种写法不好，以后要合并到 Po 中去，让 Po 认识自己的 枚举
			logger.debug("添加好友 aid="+aid+" ; bid="+bid);
			UserFriendship userFriendship = new UserFriendship();
			userFriendship.setId(IDCreater.uuid());
			userFriendship.setaId(ua);
			userFriendship.setbId(ub);
			userFriendship.setVerified(type);
			mapperOnCache.insertSelective(userFriendship, userFriendship.getId());
		}else if("unsubscribed".equalsIgnoreCase(st)){
			logger.debug("删除好友 aid="+aid+" ; bid="+bid);
			//这里应该是删除数据的操作吧？
			UserFriendshipCriteria userFriendshipCriteria = new UserFriendshipCriteria();
			UserFriendshipCriteria.Criteria criteria =  userFriendshipCriteria.createCriteria();
			criteria.andAIdEqualTo(ua);
			criteria.andBIdEqualTo(ub);
			//按照条件删除的方法，好像不太好使，单元测试没通过，查出来再删吧
			List<UserFriendship> list = userFriendshipMapper.selectByExample(userFriendshipCriteria);
			if(list!=null)
				for(UserFriendship uf:list){
					mapperOnCache.deleteByPrimaryKey(uf.getClass(), uf.getId());
				}
		}else{
			throw new Exception("无法识别 SubscriptionType="+st);
		}
	}

	@Override
	public void pushMsgApn(String fromname, String toname, String msg) throws Exception {
		String pwd = commonConfig.get("ios.push.pwd","110110");
		SsoUser from = ssoUserRepository.getSsoUserByName(fromname);
		SsoUser to = ssoUserRepository.getSsoUserByName(toname);
		String deviceToken = to.getDeviceToken();
		if(StringUtils.isNotEmpty(deviceToken)){
			//TODO 暂时先用用户名或昵称吧
			String name = from.getNickname();
			if(StringUtils.isEmpty(name)){
				name = fromname;
			}
			PushApn.sendMsgApn(deviceToken, name+":"+msg, pwd, false);
		}
	}

	@Override
	public List<UserVo> getFirendList(String userid) throws Exception {
		UserFriendshipCriteria userFriendshipCriteria = new UserFriendshipCriteria();
		UserFriendshipCriteria.Criteria criteriaA = userFriendshipCriteria.createCriteria();
		criteriaA.andAIdEqualTo(userid);
		UserFriendshipCriteria.Criteria criteriaB = userFriendshipCriteria.createCriteria();
		criteriaB.andBIdEqualTo(userid);
		userFriendshipCriteria.or(criteriaB);
		List<UserFriendship> list = userFriendshipMapper.selectByExample(userFriendshipCriteria);
		List<UserVo> userList = new ArrayList<UserVo>();
		//缓存取值
		for(UserFriendship u : list){
			String aid = u.getaId();
			String bid = u.getbId();
			String uid = userid.equals(aid)?bid:aid;
			String alias = userid.equals(aid)?u.getAliasb():u.getAliasa();//别名
			SsoUser user = mapperOnCache.selectByPrimaryKey(SsoUser.class, uid);
			user.setPassword(null);
			user.setEmail(null);
			UserVo uv = new UserVo();
			org.springframework.beans.BeanUtils.copyProperties(user, uv);
			uv.setAlias(alias);
			userList.add(uv);
		}
		return userList;
	}
	
	
	
}