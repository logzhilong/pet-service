package com.momoplan.pet.framework.ssoserver.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;

import com.google.gson.Gson;
import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.cache.pool.RedisPool;
import com.momoplan.pet.commons.domain.user.dto.SsoAuthenticationToken;
import com.momoplan.pet.commons.domain.user.mapper.SsoChatServerMapper;
import com.momoplan.pet.commons.domain.user.mapper.SsoVersionMapper;
import com.momoplan.pet.commons.domain.user.po.SsoChatServer;
import com.momoplan.pet.commons.domain.user.po.SsoChatServerCriteria;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.commons.domain.user.po.SsoVersion;
import com.momoplan.pet.commons.domain.user.po.SsoVersionCriteria;
import com.momoplan.pet.commons.repository.user.SsoUserRepository;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.ssoserver.service.SsoService;
import com.momoplan.pet.framework.ssoserver.vo.LoginResponse;
/**
 * TODO 关于 token 相关的操作，应该在 redis 中完成，此处留一个作业
 * @author liangc
 */
@Service
public class SsoServiceImpl implements SsoService {

	private static Logger logger = LoggerFactory.getLogger(SsoServiceImpl.class);
	
	private SsoChatServerMapper ssoChatServerMapper = null;
	private MapperOnCache mapperOnCache = null;
	private RedisPool redisPool = null;
	private SsoUserRepository ssoUserRepository = null;
	private SsoVersionMapper ssoVersionMapper = null;
	
	private CommonConfig commonConfig = null;
	private Gson gson = MyGson.getInstance();
	
	@Autowired
	public SsoServiceImpl(SsoChatServerMapper ssoChatServerMapper, MapperOnCache mapperOnCache, RedisPool redisPool, SsoUserRepository ssoUserRepository, SsoVersionMapper ssoVersionMapper,
			CommonConfig commonConfig) {
		super();
		this.ssoChatServerMapper = ssoChatServerMapper;
		this.mapperOnCache = mapperOnCache;
		this.redisPool = redisPool;
		this.ssoUserRepository = ssoUserRepository;
		this.ssoVersionMapper = ssoVersionMapper;
		this.commonConfig = commonConfig;
	}

	@Override
	public SsoAuthenticationToken register(SsoUser user) throws Exception {
		if(getSsoUserByName(user.getUsername())!=null){
			throw new Exception("用户名 "+user.getUsername()+" 已存在");
		}
		user.setId(IDCreater.uuid());
		ssoUserRepository.insertUser(user);
		user = getSsoUserByName(user.getUsername());
		logger.debug("register : "+user.toString());
		SsoAuthenticationToken token = ssoUserRepository.createToken(user.getId());
		logger.debug("token : "+gson.toJson(token));
		return token;
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
			SsoAuthenticationToken token = ssoUserRepository.createToken(u.getId());
			LoginResponse response = new LoginResponse(token,getSsoChatServer());
			return response;
		}catch(Exception e){
			throw e;
		}
	}
	
	public SsoUser getSsoUserByName(String username) {
		return ssoUserRepository.getSsoUserByName(username);
	}

	@Override
	public LoginResponse getToken(String token) throws Exception {
		SsoAuthenticationToken tk = ssoUserRepository.getToken(token);
		LoginResponse response = new LoginResponse(tk,getSsoChatServer());
		return response;
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
		ssoUserRepository.delToken(token);
	}

	@Override
	public SsoVersion getVersion(String phoneType) throws Exception {
		SsoVersionCriteria ssoVersionCriteria = new SsoVersionCriteria();
		ssoVersionCriteria.createCriteria().andPhoneTypeEqualTo(phoneType);
		ssoVersionCriteria.setOrderByClause("create_date desc");
		return ssoVersionMapper.selectByExample(ssoVersionCriteria).get(0);
	}

	@Override
	public String getFirstImage() {
		String firstImage = commonConfig.get("app.first.image", null);
		logger.debug("开机图片[firstImage]"+firstImage);
		return firstImage;
	}
	
}
