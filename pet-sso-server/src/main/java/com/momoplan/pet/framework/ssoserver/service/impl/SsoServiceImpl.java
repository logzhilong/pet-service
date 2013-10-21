package com.momoplan.pet.framework.ssoserver.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;

import com.google.gson.Gson;
import com.momoplan.pet.commons.DateUtils;
import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.cache.pool.RedisPool;
import com.momoplan.pet.commons.domain.ssoserver.mapper.SsoChatServerMapper;
import com.momoplan.pet.commons.domain.ssoserver.mapper.SsoUserMapper;
import com.momoplan.pet.commons.domain.ssoserver.po.SsoAuthenticationToken;
import com.momoplan.pet.commons.domain.ssoserver.po.SsoChatServer;
import com.momoplan.pet.commons.domain.ssoserver.po.SsoChatServerCriteria;
import com.momoplan.pet.commons.domain.ssoserver.po.SsoUser;
import com.momoplan.pet.commons.domain.ssoserver.po.SsoUserCriteria;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.ssoserver.service.SsoService;
import com.momoplan.pet.framework.ssoserver.vo.LoginResponse;
/**
 * TODO 关于 token 相关的操作，应该在 redis 中完成，此处留一个作业
 * @author liangc
 */
@Service
public class SsoServiceImpl implements SsoService {

	private Logger logger = LoggerFactory.getLogger(SsoServiceImpl.class);
	
	private SsoUserMapper ssoUserMapper = null;
	private SsoChatServerMapper ssoChatServerMapper = null;
	private MapperOnCache mapperOnCache = null;
	private RedisPool redisPool = null;

	private CommonConfig commonConfig = null;
	private Gson gson = MyGson.getInstance();
	
	@Autowired
	public SsoServiceImpl(SsoUserMapper ssoUserMapper, SsoChatServerMapper ssoChatServerMapper, CommonConfig commonConfig,
			MapperOnCache mapperOnCache, RedisPool redisPool) {
		super();
		this.ssoUserMapper = ssoUserMapper;
		this.ssoChatServerMapper = ssoChatServerMapper;
		this.commonConfig = commonConfig;
		this.mapperOnCache = mapperOnCache;
		this.redisPool = redisPool;
	}

	@Override
	public SsoAuthenticationToken register(SsoUser user) throws Exception {
		if(getSsoUserByName(user.getUsername())!=null){
			throw new Exception("用户名 "+user.getUsername()+" 已存在");
		}
		user.setVersion(0);
		ssoUserMapper.insertSelective(user);
		user = getSsoUserByName(user.getUsername());
		logger.debug("register : "+user.toString());
		SsoAuthenticationToken token = createToken(user.getId());
		logger.debug("token : "+gson.toJson(token));
		return token;
	}
	/**
	 * 产生一个 token
	 * TODO 每个 TOKEN 都是临时的，要找到一个对策来清理过期不用的 TOKEN，等解耦以后再处理
	 * @param userId
	 * @return
	 */
	private SsoAuthenticationToken createToken(Long userId) {
		SsoAuthenticationToken authenticationToken = new SsoAuthenticationToken();
		authenticationToken.setExpire(-1L);
		authenticationToken.setToken(IDCreater.uuid());
		authenticationToken.setUserid(userId);
		authenticationToken.setCreateDate(DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
		String json = gson.toJson(authenticationToken);
		ShardedJedis jedis = null;
		try{
			jedis = redisPool.getConn();
			jedis.hset(CF_TOKEN, authenticationToken.getToken(), json);
			logger.debug("createToken 成功 : "+json);
		}catch(Exception e){
			logger.error("createToken 异常",e);
		}finally{
			redisPool.closeConn(jedis);
		}
		return authenticationToken;
	}

	private SsoChatServer getSsoChatServer(){
		String xmppDomain = commonConfig.get("xmpp.domain");
		ShardedJedis jedis = null;
		String key = "chatserver."+xmppDomain;
		int sec = 60*60*4;//4小时更新一次
		try{
			jedis = redisPool.getConn();
			String json = jedis.get(key);
			if(StringUtils.isEmpty(json)){
				SsoChatServerCriteria ssoChatServerCriteria = new SsoChatServerCriteria();
				ssoChatServerCriteria.createCriteria().andNameEqualTo(xmppDomain);
				List<SsoChatServer> ssoChatServerList = ssoChatServerMapper.selectByExample(ssoChatServerCriteria);
				SsoChatServer ssoChatServer = ssoChatServerList.get(0);
				json = gson.toJson(ssoChatServer);
				jedis.setex(key, sec, json);
				logger.debug("set json to cache : "+json);
			}
			logger.debug("getSsoChatServer on cache : "+json);
			SsoChatServer ssoChatServer = gson.fromJson(json, SsoChatServer.class);
			return ssoChatServer;
		}catch(Exception e){
			logger.error("getSsoChatServer 异常",e);
		}finally{
			redisPool.closeConn(jedis);
		}
		return null;
	}
	
	@Override
	public LoginResponse login(SsoUser user) throws Exception {
		try{
			SsoUser u = getSsoUserByName(user.getUsername());
			if(u==null){
				throw new Exception("用户不存在");
			}
			if(user.getPassword()==null||!user.getPassword().equals(u.getPassword())){
				throw new Exception("密码错误");
			}
			u.setDeviceToken(user.getDeviceToken());
			mapperOnCache.updateByPrimaryKeySelective(u, u.getId());
			SsoAuthenticationToken token = createToken(u.getId());
			LoginResponse response = new LoginResponse(token,getSsoChatServer());
			return response;
		}catch(Exception e){
			throw new Exception("login error :",e);
		}
	}
	
	private SsoUser getSsoUserByName(String username) {
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
			return mapperOnCache.selectByPrimaryKey(SsoUser.class, Long.parseLong(useridStr));
		}catch(Exception e){
			logger.error("getSsoUserByName 异常",e);
		}finally{
			redisPool.closeConn(jedis);
		}
		return null;
	}

	@Override
	public String getToken(String token) throws Exception {
		ShardedJedis jedis = null;
		try{
			jedis = redisPool.getConn();
			String json = null;
			try{
				json = jedis.hget(CF_TOKEN, token);//SsoAuthenticationToken
			}catch(Exception e){
				throw new Exception("TOKEN 无效或已过期");
			}
			if(StringUtils.isEmpty(json))
				throw new Exception("TOKEN 无效或已过期");
			logger.debug("getToken 成功 : "+token);
			return json;
		}catch(Exception e){
			logger.debug("error : "+e.getMessage());
			throw e;
		}finally{
			redisPool.closeConn(jedis);
		}
	}

	@Override
	public void updatePassword(SsoUser user) throws Exception {
		SsoUser ssoUser = getSsoUserByName(user.getUsername());
		ssoUser.setPassword(user.getPassword());
		mapperOnCache.updateByPrimaryKey(ssoUser, ssoUser.getId());
	}
	/**
	 * TODO 交给缓存处理
	 * @param token
	 * @throws Exception
	 */
	@Override
	public void logout(String token) throws Exception {
		ShardedJedis jedis = null;
		try{
			jedis = redisPool.getConn();
			jedis.hdel(CF_TOKEN, token);
			logger.debug("logout 成功 : "+token);
		}catch(Exception e){
			logger.error("logout 失败",e);
			throw e;
		}finally{
			redisPool.closeConn(jedis);
		}
	}
	
}