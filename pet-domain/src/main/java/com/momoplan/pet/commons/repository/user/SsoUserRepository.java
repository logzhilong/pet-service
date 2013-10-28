package com.momoplan.pet.commons.repository.user;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

import com.google.gson.Gson;
import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.cache.pool.RedisPool;
import com.momoplan.pet.commons.cache.pool.StorePool;
import com.momoplan.pet.commons.domain.user.dto.SsoAuthenticationToken;
import com.momoplan.pet.commons.domain.user.mapper.SsoUserMapper;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.commons.domain.user.po.SsoUserCriteria;

public class SsoUserRepository implements CacheKeysConstance {

	private static Logger logger = LoggerFactory.getLogger(SsoUserRepository.class);

	@Autowired
	private RedisPool redisPool= null;
	@Autowired
	private MapperOnCache mapperOnCache = null;
	@Autowired
	private SsoUserMapper ssoUserMapper = null;
	private Gson gson = MyGson.getInstance();
	@Autowired
	private StorePool storePool = null;

	public SsoUser getSsoUserByName(String username) {
		ShardedJedis jedis = null;
		try{
			jedis = redisPool.getConn();
			String useridStr = null;
			try{
				useridStr = jedis.hget(CF_INDEX_USER_USERNAME, username);
			}catch(Exception e){
				logger.debug(username+" login error [hget]: "+e.getMessage());
			}
			if(StringUtils.isEmpty(useridStr)){
				SsoUserCriteria ssoUserCriteria = new SsoUserCriteria();
				ssoUserCriteria.createCriteria().andUsernameEqualTo(username);
				List<SsoUser> ssoUserList = ssoUserMapper.selectByExample(ssoUserCriteria);
				if(ssoUserList!=null&&ssoUserList.size()>0){
					SsoUser user = ssoUserList.get(0);
					useridStr = user.getId()+"";
					try{
						jedis.hset(CF_INDEX_USER_USERNAME, username, useridStr);
					}catch(Exception e){
						logger.debug(username+" login error [hset] : "+e.getMessage());
					}
				}
			}
			return mapperOnCache.selectByPrimaryKey(SsoUser.class, useridStr);
		}catch(Exception e){
			logger.error("getSsoUserByName 异常",e);
		}finally{
			redisPool.closeConn(jedis);
		}
		return null;
	}
	
	/**
	 * 产生一个 token
	 * TODO 每个 TOKEN 都是临时的，要找到一个对策来清理过期不用的 TOKEN，等解耦以后再处理
	 * @param userId
	 * @return
	 */
	public SsoAuthenticationToken createToken(String userId) {
		SsoAuthenticationToken authenticationToken = new SsoAuthenticationToken();
		authenticationToken.setExpire(-1L);
		authenticationToken.setToken(IDCreater.uuid());
		authenticationToken.setUserid(userId);
		authenticationToken.setCreateDate(new Date());
		String json = gson.toJson(authenticationToken);

		Jedis jedis = null;
		try{
			jedis = storePool.getConn();
			jedis.hset(CF_TOKEN, authenticationToken.getToken(), json);
			logger.debug("createToken 成功 : "+json);
		}catch(Exception e){
			logger.error("createToken 异常",e);
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
}
