package com.momoplan.pet.commons.repository.user;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;

import com.google.gson.Gson;
import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.cache.pool.StorePool;
import com.momoplan.pet.commons.domain.user.dto.SsoAuthenticationToken;
import com.momoplan.pet.commons.domain.user.mapper.SsoUserMapper;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.commons.domain.user.po.SsoUserCriteria;
import com.momoplan.pet.commons.repository.CacheKeysConstance;

public class SsoUserRepository implements CacheKeysConstance {

	private static Logger logger = LoggerFactory.getLogger(SsoUserRepository.class);
	private static Gson gson = MyGson.getInstance();

	private MapperOnCache mapperOnCache = null;
	private SsoUserMapper ssoUserMapper = null;
	private StorePool storePool = null;
	
	@Autowired
	public SsoUserRepository(MapperOnCache mapperOnCache,
			SsoUserMapper ssoUserMapper, StorePool storePool) {
		super();
		this.mapperOnCache = mapperOnCache;
		this.ssoUserMapper = ssoUserMapper;
		this.storePool = storePool;
	}

	public SsoUser getSsoUserByName(String username) {
		Jedis jedis = null;
		try{
			jedis = storePool.getConn();
			String index = SEARCH_USER_INDEX+"*:"+username+"*";
			logger.debug("index="+index);
			Set<String> set = jedis.keys(index);
			String userid = "";
			if(set!=null&&set.size()>0){
				String k = set.iterator().next();
				userid = jedis.get(k);
				logger.debug("用户名索引 k="+k+" ; v="+userid); 
			}else{
				SsoUserCriteria ssoUserCriteria = new SsoUserCriteria();
				ssoUserCriteria.createCriteria().andUsernameEqualTo(username);
				List<SsoUser> ssoUserList = ssoUserMapper.selectByExample(ssoUserCriteria);
				if(ssoUserList!=null&&ssoUserList.size()>0){
					SsoUser user = ssoUserList.get(0);
					updateUserIndex(user);
				}
			}
			return mapperOnCache.selectByPrimaryKey(SsoUser.class, userid);
		}catch(Exception e){
			logger.error("getSsoUserByName 异常",e);
		}finally{
			storePool.closeConn(jedis);
		}
		return null;
	}
	
	/**
	 * 产生一个 token
	 * TODO 每个 TOKEN 都是临时的，要找到一个对策来清理过期不用的 TOKEN，等解耦以后再处理
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	public SsoAuthenticationToken createToken(String userId)throws Exception {
		Jedis jedis = null;
		SsoAuthenticationToken authenticationToken = new SsoAuthenticationToken();
		try{
			SsoUser user = mapperOnCache.selectByPrimaryKey(SsoUser.class, userId);
			authenticationToken.setExpire(-1L);
			authenticationToken.setToken(IDCreater.uuid());
			authenticationToken.setUserid(userId);
			authenticationToken.setCreateDate(new Date());
			authenticationToken.setUsername(user.getUsername());
			String json = gson.toJson(authenticationToken);
			jedis = storePool.getConn();
			jedis.hset(CF_TOKEN, authenticationToken.getToken(), json);
			logger.debug("createToken 成功 : "+json);
		}catch(Exception e){
			logger.error("createToken 异常",e);
			throw e;
		}finally{
			storePool.closeConn(jedis);
		}
		return authenticationToken;
	}
	
	public SsoAuthenticationToken getToken(String token) throws Exception {
		Jedis jedis = null;
		try{
			jedis = storePool.getConn();
			String json = null;
			try{
				json = jedis.hget(CF_TOKEN, token);//SsoAuthenticationToken
			}catch(Exception e){
				logger.debug("gettoken_error : "+e.getMessage());
				throw new Exception("TOKEN 无效或已过期");
			}
			if(StringUtils.isEmpty(json))
				throw new Exception("TOKEN 无效或已过期");
			logger.debug("getToken 成功 : "+token);
			
			SsoAuthenticationToken tk = gson.fromJson(json, SsoAuthenticationToken.class);
			return tk;
		}catch(Exception e){
			logger.debug("error : "+e.getMessage());
			throw e;
		}finally{
			storePool.closeConn(jedis);
		}
	}
	
	public void delToken(String token) throws Exception {
		Jedis jedis = null;
		try{
			jedis = storePool.getConn();
			jedis.hdel(CF_TOKEN, token);
			logger.debug("logout 成功 : "+token);
		}catch(Exception e){
			logger.error("logout 失败",e);
			throw e;
		}finally{
			storePool.closeConn(jedis);
		}
	}
	
	/**
	 * 更新用户
	 * @param user
	 * @throws Exception
	 */
	public void updateUser(SsoUser user) throws Exception {
		//更新数据
		mapperOnCache.updateByPrimaryKeySelective(user, user.getId());
		updateUserIndex(user);
	}

	/**
	 * 创建用户
	 * @param user
	 * @throws Exception
	 */
	public void insertUser(SsoUser user) throws Exception {
		mapperOnCache.insertSelective(user, user.getId());
		updateUserIndex(user);
	}

	/**
	 * 更新用户索引
	 * @param user
	 */
	public void updateUserIndex(SsoUser user){
		//更新索引
		String key = SEARCH_USER_INDEX+user.getId();//这个用户的索引key
		logger.debug("更新用户索引 k="+key);
		Set<String> keys = storePool.keys(key+":*");
		if(keys!=null&&keys.size()>0){
			//如果存在这个索引，就把它删除掉
			for(Iterator<String> it = keys.iterator();it.hasNext();){
				String k = it.next();
				logger.debug("删除用户索引 k="+k);
				storePool.del(k);
			}
		}
		//重新创建
		String indexKey = key+":"+user.getUsername()+":"+user.getNickname();
		logger.debug("创建用户索引 k="+indexKey);
		storePool.set(indexKey, user.getId());
	}
	
}
