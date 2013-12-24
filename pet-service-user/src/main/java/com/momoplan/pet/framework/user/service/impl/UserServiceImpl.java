package com.momoplan.pet.framework.user.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.PushApn;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.cache.pool.RedisPool;
import com.momoplan.pet.commons.cache.pool.StorePool;
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
		ssoUserRepository.updateUser(user);
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
	public JSONArray getNearPerson(String pageIndex,String userId,String gender,String petType, double longitude, double latitude,String personOrPet) throws Exception {
		int index = Integer.parseInt(pageIndex);
		if(index==0){
			logger.debug("构建缓冲区 index="+index+" ; userid="+userId);
			buildNearPersonBuffer(userId,gender,petType,longitude,latitude,personOrPet);
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
	public UserVo getUser(String userid,String username) throws Exception {
		SsoUser user = null;
		if(username!=null&&!"".equals(username)){
			user = ssoUserRepository.getSsoUserByName(username);
		}else{
			user = mapperOnCache.selectByPrimaryKey(SsoUser.class, userid);
		}
		UserVo userVo = null;
		user.setPassword(null);
		JSONObject ul = getUserLocation(user.getId());
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
		petInfo = mapperOnCache.selectByPrimaryKey(petInfo.getClass(), petInfo.getId());
		logger.debug("修改宠物:"+petInfo.toString());
		updateUserPetTypeIndex(petInfo,false);
	}
	
	@Override
	public String savePetInfo(PetInfo petInfo) throws Exception {
		petInfo.setId(IDCreater.uuid());
		//放进数据库、缓存
		mapperOnCache.insertSelective(petInfo, petInfo.getId());
		petInfo = mapperOnCache.selectByPrimaryKey(petInfo.getClass(), petInfo.getId());
		logger.debug("保存宠物:"+petInfo.toString());
		updateUserPetTypeIndex(petInfo,false);
		addUserForumRel(petInfo.getUserid(),petInfo.getType()+"");//添加宠物时关注相关圈子
		return petInfo.getId();
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
			JSONObject json = new JSONObject();
			json.put("aId", ua);
			json.put("bId", ub);
			//TODO 修改时，记得连缓存里的别名一起改了
			json.put("aliasA", "");
			json.put("aliasB", "");
			storePool.set(FRIEND_KEY+userFriendship.getId()+":"+ua+ub,json.toString());
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
					String id = uf.getaId();
					mapperOnCache.deleteByPrimaryKey(uf.getClass(), id);
					Set<String> set = storePool.keys(FRIEND_KEY+id+":*");
					if(set!=null&&set.size()>0){
						storePool.del(set.toArray(new String[set.size()]));
					}
				}
		}else{
			throw new Exception("无法识别 SubscriptionType="+st);
		}
	}

	@Override
	public void pushMsgApn(String fromname, String toname, String msg,String type) throws Exception {
		String pwd = commonConfig.get("ios.push.pwd","110110");
		String test = commonConfig.get("iphone.push");
		boolean t = false;
		if("test".equalsIgnoreCase(test)){
			logger.debug("测试证书");
			t = true;
		}
		SsoUser from = ssoUserRepository.getSsoUserByName(fromname);
		SsoUser to = ssoUserRepository.getSsoUserByName(toname);
		String deviceToken = to.getDeviceToken();
		if(StringUtils.isNotEmpty(deviceToken)){
			//TODO 暂时先用用户名或昵称吧
			String name = from.getNickname();
			if(StringUtils.isEmpty(name)){
				name = fromname;
			}
			/*
			 img:发的消息是，用户名：发来一张图片
			 audio:用户名：发来一段语音 
			 text:或没有（因为早期版本没有fileType字段）就是以前的格式，用户名：消息内容
			 */
			String pushMsg = name+":"+msg;
			if("img".equalsIgnoreCase(type)){
				pushMsg = "发来一张图片";
			}else if("audio".equalsIgnoreCase(type)){
				pushMsg = "发来一段语音";
			}
			logger.debug("type="+type+" ; "+pushMsg);
			PushApn.sendMsgApn(deviceToken, pushMsg, pwd, t);
		}
	}
	
	@Override
	public List<UserVo> getFirendList(String userid) throws Exception {
		Set<String> set = storePool.keys(FRIEND_KEY+"*:*"+userid+"*");
		if(set!=null&&set.size()>0){
			List<UserVo> userList = new ArrayList<UserVo>();
			List<String> list = storePool.get(set.toArray(new String[set.size()]));
			Map<String,String> uidAliasMap = new HashMap<String,String>();
			//存储中是 AB BA 的关系，所以单提出 uid 会有重复，放入 map 去除重复
			for(String jsonStr : list){
				JSONObject jsonObj = new JSONObject(jsonStr);
				String aid = jsonObj.getString("aId");
				String bid = jsonObj.getString("bId");
				String uid = userid.equals(aid)?bid:aid;
				String alias = get(jsonObj,"aliasA");//别名
				if(userid.equals(aid)){
					alias = get(jsonObj,"aliasB");
				}
				uidAliasMap.put(uid, alias);
			}
			for(String key:uidAliasMap.keySet()){
				String uid = key;
				String alias = uidAliasMap.get(key);
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
		return null;
	}

	@Override
	public void delPetInfo(String id) throws Exception {
		PetInfo petInfo = mapperOnCache.selectByPrimaryKey(PetInfo.class, id);
		updateUserPetTypeIndex(petInfo, true);
		mapperOnCache.deleteByPrimaryKey(PetInfo.class, id);
	}

	@Override
	public List<UserVo> searchUser(String userid,String condition,String conditionType) throws Exception {
		String index = SEARCH_USER_INDEX+"*:";
		if("username".equalsIgnoreCase(conditionType)){
			index = SEARCH_USER_INDEX+"*:"+condition+"*";
		}else{
			index = SEARCH_USER_INDEX+"*:*:"+condition+"*";
		}
		Set<String> set = storePool.keys(index);
		if(set!=null&&set.size()>0){
			logger.debug("搜索用户 key="+index+" ; size="+set.size());
			JSONObject ul0 = getUserLocation(userid);
			double latitude = 0;
			double longitude = 0;
			if(ul0!=null){
				String lat = ul0.getString("latitude");
				String lng = ul0.getString("longitude");
				latitude = StringUtils.isNotEmpty(lat)?Double.parseDouble(lat):0;
				longitude = StringUtils.isNotEmpty(lng)?Double.parseDouble(lng):0;
			}
			List<UserVo> uvl = new ArrayList<UserVo>();
			int i=0;
			for(Iterator<String> iterator = set.iterator();iterator.hasNext();){
				String key = iterator.next();
				String uid = key.split(":")[1];
				SsoUser user = mapperOnCache.selectByPrimaryKey(SsoUser.class, uid);
				UserVo userVo = null;
				JSONObject ul = getUserLocation(uid);
				if(ul!=null){
					String lat = ul.getString("latitude");
					String lng = ul.getString("longitude");
					userVo = new UserVo(user,lng,lat);
					double distance = PetUtil.getDistance(latitude, longitude, Double.parseDouble(lat), Double.parseDouble(lng));
					userVo.setDistance(distance+"");
				}else{
					userVo = new UserVo(user,"0","0");
				}
				uvl.add(userVo);
				if(i++>100){
					logger.debug("//截断结果集 i="+i);
					break;
				}
			}
			logger.debug("//TODO 排序结果集，按照距离排序");
			Collections.sort(uvl, new Comparator<UserVo>(){
				@Override
				public int compare(UserVo o1, UserVo o2) {
					try{
						Double d1 = Double.parseDouble(o1.getDistance());
						Double d2 = Double.parseDouble(o2.getDistance());
						return d2.compareTo(d1);
					}catch(Exception e){
						return 0;
					}
				}
			});
			return uvl;
		}
		return null;
	}
	
}