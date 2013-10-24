package com.momoplan.pet.framework.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;

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
import com.momoplan.pet.framework.user.service.UserService;
import com.momoplan.pet.framework.user.vo.UserVo;
/**
 * @author liangc
 */
@Service
public class UserServiceImpl extends UserServiceSupport implements UserService {

	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
	@Override
	public JSONArray getNearPerson(String pageIndex,String userId,String gender,String petType, double longitude, double latitude) throws Exception {
		int index = Integer.parseInt(pageIndex);
		if(index==0){
			logger.debug("构建缓冲区 index="+index+" ; userid="+userId);
			buildNearPersonBuffer(userId,gender,petType,longitude,latitude);
		}
		logger.debug("在缓冲区读取 index="+index+" ; userid="+userId);
		return readNearPersionBuffer(index,userId);
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
	
	@Override
	public UserVo getUser(SsoAuthenticationToken tokenObj) throws Exception {
		String userid = tokenObj.getUserid();
		SsoUser user = mapperOnCache.selectByPrimaryKey(SsoUser.class, userid);
		UserVo userVo = null;
		user.setPassword(null);
		JSONObject ul = getUserLocation(userid);
		if(ul!=null){
			String lat = ul.getString("latitude");
			String lng = ul.getString("longitude");
			userVo = new UserVo(user,lng,lat);
		}else{
			userVo = new UserVo(user,"0","0");
		}
		return userVo;
	}


	@Override
	public List<PetInfo> getPetInfo(String userid) throws Exception {
		//TODO 这里可以在单点存储里取值了
		PetInfoCriteria petInfoCriteria = new PetInfoCriteria();
		PetInfoCriteria.Criteria criter =  petInfoCriteria.createCriteria();
		criter.andUseridEqualTo(userid);
		List<PetInfo> list = petInfoMapper.selectByExample(petInfoCriteria);
		return list;
	}
	
	@Override
	public void updatePetInfo(PetInfo petInfo) throws Exception {
		mapperOnCache.updateByPrimaryKeySelective(petInfo, petInfo.getId());
		updateUserPetTypeIndex(petInfo,false);
	}
	
	@Override
	public void savePetInfo(PetInfo petInfo) throws Exception {
		petInfo.setId(IDCreater.uuid());
		//放进数据库、缓存
		mapperOnCache.insertSelective(petInfo, petInfo.getId());
		updateUserPetTypeIndex(petInfo,false);
	}

	@Override
	public void addOrRemoveFriend(String st, String aid, String bid) throws Exception {
		String ua = ssoUserRepository.getSsoUserByName(aid).getId();
		String ub = ssoUserRepository.getSsoUserByName(bid).getId();
		if("subscribed".equalsIgnoreCase(st)){
			//目前，其实没有状态，冗余一个状态吧先
			String type = "subscribed";
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

	@Override
	public void delPetInfo(String id) throws Exception {
		PetInfo petInfo = mapperOnCache.selectByPrimaryKey(PetInfo.class, id);
		updateUserPetTypeIndex(petInfo, true);
		mapperOnCache.deleteByPrimaryKey(PetInfo.class, id);
	}
	
}