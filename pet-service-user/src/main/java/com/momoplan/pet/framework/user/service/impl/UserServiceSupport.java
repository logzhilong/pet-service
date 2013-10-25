package com.momoplan.pet.framework.user.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.ShardedJedis;

import com.google.gson.Gson;
import com.momoplan.pet.commons.GeoHash;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.cache.pool.RedisPool;
import com.momoplan.pet.commons.cache.pool.StorePool;
import com.momoplan.pet.commons.domain.user.mapper.PetInfoMapper;
import com.momoplan.pet.commons.domain.user.mapper.UserFriendshipMapper;
import com.momoplan.pet.commons.domain.user.po.PetInfo;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.commons.repository.user.SsoUserRepository;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.user.enums.GenderType;
import com.momoplan.pet.framework.user.service.UserService;
import com.momoplan.pet.framework.user.vo.NearPerson;
import com.momoplan.pet.framework.user.vo.PetVo;
import com.momoplan.pet.framework.user.vo.UserVo;

public class UserServiceSupport {

	private static Logger logger = LoggerFactory.getLogger(UserServiceSupport.class);

	protected CommonConfig commonConfig = null;
	protected MapperOnCache mapperOnCache = null;
	protected PetInfoMapper petInfoMapper = null; 
	protected RedisPool redisPool = null;
	protected UserFriendshipMapper userFriendshipMapper = null;
	protected SsoUserRepository ssoUserRepository = null;
	protected GeoHash geoHash = new GeoHash();
	protected StorePool storePool = null;
	protected Gson gson = MyGson.getInstance();
	
	/**
	 * 在单点存储中对 用户和宠物类型，建立一个联合索引
	 * @param petInfo
	 * @throws Exception
	 */
	protected void updateUserPetTypeIndex(PetInfo petInfo,boolean del)throws Exception{
		String uid = petInfo.getUserid();
		long type = petInfo.getType();
		String petId = petInfo.getId();

		String indexKey = UserService.USERID_PETTYPE_INDEX+uid+":"+type+":"+petId;
		if(del){
			storePool.del(indexKey);
			logger.debug("人与宠物类型索引[删除]:key="+indexKey);
		}else{
			String indexValue = gson.toJson(petInfo);
			storePool.set(indexKey, indexValue);
			logger.debug("人与宠物类型索引[更新]:key="+indexKey+" ; value="+indexValue);
		}
		
	}
	
	/**
	 * 读取缓冲区
	 * @param index
	 * @param userId
	 * @return
	 * @throws JSONException 
	 */
	protected JSONArray readNearPersionBuffer(int index,String userId) throws JSONException{
		int pageSize = 20;
		int pageNo = index;//从1开始
		String bufferKey = UserService.LIST_USER_NEAR_BUFFER+userId;
		List<String> list = storePool.lrange(bufferKey, pageNo*pageSize, (pageNo+1)*pageSize);
		JSONArray jsonArray = new JSONArray();
		for(String json : list){
			JSONObject jsonObj = new JSONObject(json);
			jsonArray.put(jsonObj);
		}
		return jsonArray;
	}
	/**
	 * 构建附近的人，缓冲区
	 * @param userId
	 * @param gender
	 * @param petType
	 * @param longitude
	 * @param latitude
	 * @return
	 * @throws Exception
	 */
	protected void buildNearPersonBuffer(String userId,String gender,String petType, double longitude, double latitude) throws Exception {
		String hash = geoHash.encode(latitude, longitude);
		logger.debug("userid="+userId+" ; longitude="+longitude+" ; latitude="+latitude);
		String scope = commonConfig.get("geohash.scope");//截取 geohash 范围
		String subHash = hash.substring(0,Integer.parseInt(scope));
		logger.debug("区域编码 "+subHash);
		String key = UserService.USER_LOCATION_GEOHASH+"*"+subHash+"*";
		Set<String> uids = storePool.keys(key);
		List<NearPerson> uvs = new ArrayList<NearPerson>();
		for(String uk : uids){
			NearPerson nearPerson = new NearPerson();
			String uid = storePool.get(uk);
			if(!userId.equals(uid)){//附近的人里排除自己
				//这里的 ul 对象必须必须必须不能是空的，如果是空也不可能绝对不可能会走到这个分支
				JSONObject ul = getUserLocation(uid);
				String lat = ul.getString("latitude");//wd
				String lng = ul.getString("longitude");//jd
				logger.debug("p0 lat="+latitude+" ; lng="+longitude);
				logger.debug("p1 lat="+lat+" ; lng="+lng);
				//距离
				double distance = PetUtil.getDistance(latitude, longitude, Double.parseDouble(lat), Double.parseDouble(lng));
				logger.debug("p0 -> p1 distance="+distance);
				SsoUser user = mapperOnCache.selectByPrimaryKey(SsoUser.class, uid);
				user.setPassword(null);
				UserVo uv = new UserVo(user,lng,lat);
				uv.setDistance(distance+"");
				//TODO 通过查询条件过滤
				logger.debug("//TODO 通过查询条件过滤");
				//TODO 第一个条件：性别
				if(StringUtils.isNotEmpty(gender)){//带了性别这个条件
					String condition = GenderType.FEMALE.getCode();
					if(GenderType.MALE.getCode().equals(gender))
						condition = GenderType.MALE.getCode();
					String _gender = uv.getGender();
					if(!condition.equalsIgnoreCase(_gender)){
						logger.debug(condition+" 条件不符[用户性别]，跳过 "+uv);
						continue;
					}
				}
				List<PetVo> petList = null;
				//TODO 第二个条件：宠物类型
				if(StringUtils.isNotEmpty(petType)){//按条件过滤类型
					String userPetTypeIndexKey = UserService.USERID_PETTYPE_INDEX+uid+":"+petType+"*";
					Set<String> petSet = storePool.keys(userPetTypeIndexKey);
					if(petSet==null||petSet.size()<1){
						logger.debug(petType+" 条件不符[宠物类型]，跳过 ");
						continue;
					}
					List<String> petJsonList = storePool.get(petSet.toArray(new String[petSet.size()]));
					petList = petJsonList2PetVoList(petJsonList);
				}else{//全部类型
					String userPetTypeIndexKey = UserService.USERID_PETTYPE_INDEX+uid+":*";
					Set<String> petSet = storePool.keys(userPetTypeIndexKey);
					if( petSet!=null && petSet.size()>0 ){
						List<String> petJsonList = storePool.get(petSet.toArray(new String[petSet.size()]));
						petList = petJsonList2PetVoList(petJsonList);
					}	
				}
				nearPerson.setUser(uv);
				nearPerson.setPetList(petList);
				uvs.add(nearPerson);
			}
		}
		
		logger.debug("//TODO 排序结果集，按照距离排序");
		Collections.sort(uvs, new Comparator<NearPerson>(){
			@Override
			public int compare(NearPerson o1, NearPerson o2) {
				String d1 = o1.getUser().getDistance();
				String d2 = o2.getUser().getDistance();
				return d1.compareTo(d2);
			}
		});
		
		String[] buff = new String[uvs.size()];
		int i=0;
		logger.debug("//TODO 装载排序结果到 buff");
		for(NearPerson nearPerson : uvs){
			buff[i++] = gson.toJson(nearPerson);
		}
		
		String bufferKey = UserService.LIST_USER_NEAR_BUFFER+userId;
		storePool.del(bufferKey);
		logger.debug("准备插入缓冲区");
		if(buff!=null&&buff.length>0){
			logger.debug("插入缓冲区 buff.size="+buff.length+" bufferKey="+bufferKey);
			storePool.lpush(bufferKey, buff);
		}
		
	}
	
	private List<PetVo> petJsonList2PetVoList(List<String> jsonList){
		if(jsonList==null||jsonList.size()==0)
			return null;
		List<PetVo> objList = new ArrayList<PetVo>(jsonList.size());
		for(String json : jsonList){
			PetVo pv = gson.fromJson(json, PetVo.class);
			objList.add(pv);
		}
		return objList;
	}
	
	/**
	 * TODO 这个地方，如果压力过大，可以调整成云存储方式，但思路是一样的
	 * @param userId
	 * @param longitude
	 * @param latitude
	 * @throws Exception
	 */
	protected void updateUserGeohash(String userId, double longitude, double latitude)throws Exception {
		try{
			String hash = geoHash.encode(latitude, longitude);
			String hashKey = UserService.USER_LOCATION_GEOHASH+userId+":"+hash;
			logger.debug("hash="+hash+" ; lat="+latitude+" ; lng="+longitude); 
			Set<String> oldKeys = storePool.keys(UserService.USER_LOCATION_GEOHASH+userId+"*");
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
	
	protected JSONObject getUserLocation(String userId){
		ShardedJedis jedis = null;
		try{
			jedis = redisPool.getConn();
			List<String> list = jedis.lrange(UserService.LIST_USER_LOCATION+userId, 0, 0);
			String json = list.get(0);
			logger.debug("获取坐标 : "+json);
			JSONObject jsonObj = new JSONObject(json);
			return jsonObj;
		}catch(Exception e){
			logger.error("获取坐标异常",e);
		}finally{
			redisPool.closeConn(jedis);
		}
		return null;
	}
}
