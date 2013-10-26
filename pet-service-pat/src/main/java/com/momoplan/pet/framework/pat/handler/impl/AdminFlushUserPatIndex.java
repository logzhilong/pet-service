package com.momoplan.pet.framework.pat.handler.impl;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.cache.pool.StorePool;
import com.momoplan.pet.commons.domain.pat.mapper.PatUserPatMapper;
import com.momoplan.pet.commons.domain.pat.po.PatUserPat;
import com.momoplan.pet.commons.domain.pat.po.PatUserPatCriteria;
import com.momoplan.pet.commons.repository.pat.CacheKeysConstance;
import com.momoplan.pet.commons.repository.pat.PatUserPatRepository;
import com.momoplan.pet.framework.pat.handler.AbstractHandler;

/**
 * {"method":"adminFlushUserPetTypeIndex",params:{"pwd":"abc123"}}
 * 更新赞的索引，慎用
 * @author liangc
 */
@Component("adminFlushUserPatIndex")
public class AdminFlushUserPatIndex extends AbstractHandler {
	
	private static Logger logger = LoggerFactory.getLogger(AdminFlushUserPatIndex.class);
	
	private PatUserPatMapper patUserPatMapper = null;
	private PatUserPatRepository patUserPatRepository = null;
	private StorePool storePool = null;

	@Autowired
	public AdminFlushUserPatIndex(PatUserPatMapper patUserPatMapper, PatUserPatRepository patUserPatRepository, StorePool storePool) {
		super();
		this.patUserPatMapper = patUserPatMapper;
		this.patUserPatRepository = patUserPatRepository;
		this.storePool = storePool;
	}

	private static String PWD = "abc123";
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			String _pwd = getParameter(clientRequest, "pwd");
			if(!PWD.equals(_pwd)){
				logger.debug("口令错误，暂时不校验口令");
			}
			
			PatUserPatCriteria patUserPatCriteria = new PatUserPatCriteria();
			List<PatUserPat> list = patUserPatMapper.selectByExample(patUserPatCriteria);
			String keys = CacheKeysConstance.USER_PAT;
			Set<String> keyset = storePool.keys(keys+"*");
			if(keyset!=null&&keyset.size()>0){
				String[] ka = keyset.toArray(new String[keyset.size()]);
				storePool.del(ka);
			}
			
			for(PatUserPat po:list){
				String k = patUserPatRepository.getCacheKey(po);
				String v = gson.toJson(po);
				storePool.set(k, v);
				logger.debug("赞索引[更新]:key="+k+" ; value="+v);
			}
			rtn = new Success(true,"OK").toString();
			logger.debug("刷新索引 成功 ");
		}catch(Exception e){
			logger.debug("刷新索引 失败 ");
			logger.debug(e.getMessage());
			rtn = new Success(false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}

}
