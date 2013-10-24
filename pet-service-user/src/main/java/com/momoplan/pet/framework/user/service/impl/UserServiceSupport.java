package com.momoplan.pet.framework.user.service.impl;

import java.util.ArrayList;
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
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.cache.pool.RedisPool;
import com.momoplan.pet.commons.cache.pool.StorePool;
import com.momoplan.pet.commons.domain.user.dto.UserLocation;
import com.momoplan.pet.commons.domain.user.mapper.PetInfoMapper;
import com.momoplan.pet.commons.domain.user.mapper.UserFriendshipMapper;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.commons.repository.user.SsoUserRepository;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.user.enums.GenderType;
import com.momoplan.pet.framework.user.service.UserService;
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
		List<String> list = storePool.lrange(bufferKey, pageNo-1, (pageNo)*pageSize);
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
		String subHash = hash.substring(0,6);
		logger.debug("区域编码 "+subHash);
		String key = UserService.USER_LOCATION_GEOHASH+"*"+subHash+"*";
		Set<String> uids = storePool.keys(key);
		List<String> uvs = new ArrayList<String>();
		for(String uk : uids){
			String uid = storePool.get(uk);
			if(!userId.equals(uid)){//附近的人里排除自己
				UserLocation ul = getUserLocation(uid);
				SsoUser user = mapperOnCache.selectByPrimaryKey(SsoUser.class, uid);
				user.setPassword(null);
				UserVo uv = new UserVo(user,ul.getLongitude()+"",ul.getLatitude()+"");
				//TODO 通过查询条件过滤
				logger.debug("//TODO 通过查询条件过滤");
				//TODO 第一个条件：性别
				if(StringUtils.isNotEmpty(gender)){//带了性别这个条件
					String condition = GenderType.FEMALE.getCode();
					if(GenderType.MALE.getCode().equals(gender))
						condition = GenderType.MALE.getCode();
					String _gender = uv.getGender();
					if(!condition.equalsIgnoreCase(_gender)){
						logger.debug(condition+" 条件不符，跳过 "+uv);
						break;
					}
				}
				//TODO 第二个条件：宠物类型
				uvs.add(gson.toJson(uv));
			}
		}
		//TODO 排序结果集，按照距离排序
		String[] buff = new String[uvs.size()];
		uvs.toArray(buff);
		logger.debug("//TODO 排序 buff 结果集，按照距离排序");
		
		String bufferKey = UserService.LIST_USER_NEAR_BUFFER+userId;
		logger.debug("插入缓冲区 buff.size="+buff.length+" bufferKey="+bufferKey);
		storePool.del(bufferKey);
		if(buff!=null&&buff.length>0){
			storePool.lpush(bufferKey, buff);
		}
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
	
	protected UserLocation getUserLocation(String userId){
		ShardedJedis jedis = null;
		try{
			jedis = redisPool.getConn();
			List<String> list = jedis.lrange(UserService.LIST_USER_LOCATION+userId, 0, 0);
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
}
