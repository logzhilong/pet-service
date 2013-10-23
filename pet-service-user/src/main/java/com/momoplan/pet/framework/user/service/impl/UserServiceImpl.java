package com.momoplan.pet.framework.user.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.cache.pool.RedisPool;
import com.momoplan.pet.commons.domain.user.dto.SsoAuthenticationToken;
import com.momoplan.pet.commons.domain.user.dto.UserLocation;
import com.momoplan.pet.commons.domain.user.mapper.PetInfoMapper;
import com.momoplan.pet.commons.domain.user.mapper.SsoUserMapper;
import com.momoplan.pet.commons.domain.user.mapper.UserFriendshipMapper;
import com.momoplan.pet.commons.domain.user.po.PetInfo;
import com.momoplan.pet.commons.domain.user.po.PetInfoCriteria;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.commons.domain.user.po.UserFriendship;
import com.momoplan.pet.commons.domain.user.po.UserFriendshipCriteria;
import com.momoplan.pet.commons.repository.user.SsoUserRepository;
import com.momoplan.pet.framework.user.enums.SubscriptionType;
import com.momoplan.pet.framework.user.service.UserService;
import com.momoplan.pet.framework.user.vo.UserVo;
/**
 * TODO 关于 token 相关的操作，应该在 redis 中完成，此处留一个作业
 * @author liangc
 */
@Service
public class UserServiceImpl implements UserService {

	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private SsoUserMapper ssoUserMapper = null;
	private MapperOnCache mapperOnCache = null;
	private PetInfoMapper petInfoMapper = null; 
	private RedisPool redisPool = null;
	private UserFriendshipMapper userFriendshipMapper = null;
	private SsoUserRepository ssoUserRepository = null;
	
	@Autowired
	public UserServiceImpl(SsoUserMapper ssoUserMapper, MapperOnCache mapperOnCache, PetInfoMapper petInfoMapper, RedisPool redisPool, UserFriendshipMapper userFriendshipMapper,
			SsoUserRepository ssoUserRepository) {
		super();
		this.ssoUserMapper = ssoUserMapper;
		this.mapperOnCache = mapperOnCache;
		this.petInfoMapper = petInfoMapper;
		this.redisPool = redisPool;
		this.userFriendshipMapper = userFriendshipMapper;
		this.ssoUserRepository = ssoUserRepository;
	}

	@Override
	public void updateUser(SsoUser user) throws Exception {
		mapperOnCache.updateByPrimaryKeySelective(user, user.getId());
	}

	@Override
	public void updateUserLocation(String userId, double longitude, double latitude) throws Exception {
		ShardedJedis jedis = null;
		try{
			jedis = redisPool.getConn();
			UserLocation ul = new UserLocation();
			ul.setCreateDate(new Date());
			ul.setLatitude(latitude);
			ul.setLongitude(longitude);
			ul.setUserid(userId);
			String json = MyGson.getInstance().toJson(ul);
			logger.debug("更新坐标 : "+json);
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
			userVo = new UserVo(user,userLocation.getLongitude(),userLocation.getLatitude());
		}else{
			userVo = new UserVo(user,0,0);
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
			userFriendship.setaId(IDCreater.uuid());
			userFriendship.setaId(ua);
			userFriendship.setbId(ub);
			userFriendship.setVerified(type);
			userFriendshipMapper.insertSelective(userFriendship);
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
					userFriendshipMapper.deleteByPrimaryKey(uf.getId());
				}
		}else{
			throw new Exception("无法识别 SubscriptionType="+st);
		}
	}
	
}