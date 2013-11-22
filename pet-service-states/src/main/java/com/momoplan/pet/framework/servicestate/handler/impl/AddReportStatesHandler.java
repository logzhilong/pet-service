package com.momoplan.pet.framework.servicestate.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.ShardedJedis;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.cache.pool.RedisPool;
import com.momoplan.pet.commons.domain.states.po.StatesUserStates;
import com.momoplan.pet.framework.servicestate.common.Constants;
import com.momoplan.pet.framework.servicestate.handler.AbstractHandler;
import com.momoplan.pet.framework.servicestate.service.StateService;
@Component("addReport")
public class AddReportStatesHandler extends AbstractHandler{
	
	private static Logger logger = LoggerFactory.getLogger(AddReportStatesHandler.class);
	
	@Autowired
	private RedisPool redisPool = null;
	@Autowired
	private MapperOnCache mapperOnCache = null;
	
	@Override
	public void process(ClientRequest clientRequest,HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try{
			String userid = getUseridFParamSToken(clientRequest);
			String stateid = PetUtil.getParameter(clientRequest, "stateid");
			report(userid,stateid);
			logger.debug("举报 成功 body="+gson.toJson(clientRequest));
			rtn = new Success(sn,true,"OK").toString();
		}catch(Exception e){
			logger.debug("举报 失败 body="+gson.toJson(clientRequest));
			logger.error("addReport : ",e);
			rtn = new Success(sn,false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}
	
	private void report(String userid,String stateid)throws Exception{
		String threshold = commonConfig.get(Constants.REPORT_THRESHOLD, "20");
		logger.debug("//举报的阀值 "+threshold);
		int t = Integer.parseInt(threshold);
		ShardedJedis jedis = null;
		try{
			String key = StateService.HASH_STATES_REPORT+stateid;
			jedis = redisPool.getConn();
			if(jedis.exists(key)&&StringUtils.isNotEmpty(jedis.hget(key, userid))){
				logger.debug("重复举报");
				throw new Exception("不能重复举报.");
			}
			logger.debug("//添加举报信息");
			jedis.hset(key, userid, stateid);
			Long count = jedis.hlen(key);
			logger.debug("//达到阀值以后，更新状态，并清空举信息");
			if(count>=t){
				StatesUserStates state = mapperOnCache.selectByPrimaryKey(StatesUserStates.class, stateid);
				state.setState("5");
				logger.debug("更改动态状态为 5 ; stateid="+stateid); 
				mapperOnCache.updateByPrimaryKeySelective(state, stateid);
				jedis.del(key);
			}
		}catch(Exception e){
			logger.error("report",e);
			throw e;
		}finally{
			redisPool.closeConn(jedis);
		}
	}
	
}
