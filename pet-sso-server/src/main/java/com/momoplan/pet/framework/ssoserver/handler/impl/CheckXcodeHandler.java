package com.momoplan.pet.framework.ssoserver.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.ShardedJedis;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.cache.pool.RedisPool;
import com.momoplan.pet.framework.ssoserver.CacheKeysConstance;
import com.momoplan.pet.framework.ssoserver.handler.AbstractHandler;

/**
 * 校验验证码
 * @author liangc
 */
@Component("verifyCode")
public class CheckXcodeHandler extends AbstractHandler implements CacheKeysConstance {
	
	private Logger logger = LoggerFactory.getLogger(CheckXcodeHandler.class);
	@Autowired
	private RedisPool redisPool = null;

	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			String phoneNumber = PetUtil.getParameter(clientRequest, "phoneNum");
			String xcode = PetUtil.getParameter(clientRequest, "verificationCode");
			String _xcode = getXcode(phoneNumber);
			if(!xcode.equals(_xcode)){
				logger.debug("revice xcode="+xcode);
				logger.debug("get xcode="+_xcode);
				throw new Exception("随机无效");
			}
			logger.debug("XCODE 校验成功 body="+gson.toJson(clientRequest));
			rtn = new Success(true,xcode).toString();
		}catch(Exception e){
			logger.debug("XCODE 校验失败 body="+gson.toJson(clientRequest));
			logger.error("login : ",e);
			rtn = new Success(false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}

	private String getXcode(String phoneNumber){
		ShardedJedis jedis = null;
		String key = CF_XCODE+phoneNumber;
		try{
			jedis = redisPool.getConn();
			String xcode = jedis.get(key);
			logger.debug(key+" = "+xcode);
			return xcode;
		}catch(Exception e){
			logger.error("",e);
		}finally{
			redisPool.closeConn(jedis);
		}
		return null;
	}
}
