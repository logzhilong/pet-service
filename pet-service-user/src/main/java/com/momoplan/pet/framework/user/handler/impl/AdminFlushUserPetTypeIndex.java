package com.momoplan.pet.framework.user.handler.impl;

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
import com.momoplan.pet.commons.domain.user.mapper.PetInfoMapper;
import com.momoplan.pet.commons.domain.user.po.PetInfo;
import com.momoplan.pet.commons.domain.user.po.PetInfoCriteria;
import com.momoplan.pet.framework.user.handler.AbstractHandler;
import com.momoplan.pet.framework.user.service.UserService;

/**
 * {"method":"adminFlushUserPetTypeIndex",params:{"uid":"all","pwd":"abc123"}}
 * 更新用户与宠物的索引
 * @author liangc
 */
@Component("adminFlushUserPetTypeIndex")
public class AdminFlushUserPetTypeIndex extends AbstractHandler {
	
	private static Logger logger = LoggerFactory.getLogger(AdminFlushUserPetTypeIndex.class);
	
	@Autowired
	private PetInfoMapper petInfoMapper = null;
	@Autowired
	private StorePool storePool = null;
	private static String PWD = "abc123";
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			String _uid = getParameter(clientRequest, "uid");
			String _pwd = getParameter(clientRequest, "pwd");
			String keys = UserService.USERID_PETTYPE_INDEX;
			if(!PWD.equals(_pwd)){
				logger.debug("口令错误，暂时不校验口令");
			}
			
			PetInfoCriteria petInfoCriteria = new PetInfoCriteria();
			if(_uid!=null&&!"all".equalsIgnoreCase(_uid)){
				petInfoCriteria.createCriteria().andUseridEqualTo(_uid);
				keys = keys+_uid;
			}
			
			List<PetInfo> petInfoList = petInfoMapper.selectByExample(petInfoCriteria);
			Set<String> keyset = storePool.keys(keys+"*");
			if(keyset!=null&&keyset.size()>0){
				String[] ka = keyset.toArray(new String[keyset.size()]);
				storePool.del(ka);
			}
			
			for(PetInfo petInfo:petInfoList){
				String uid = petInfo.getUserid();
				long type = petInfo.getType();
				String petId = petInfo.getId();
				String indexKey = UserService.USERID_PETTYPE_INDEX+uid+":"+type+":"+petId;
				String indexValue = gson.toJson(petInfo);
				storePool.set(indexKey, indexValue);
				logger.debug("人与宠物类型索引[更新]:key="+indexKey+" ; value="+indexValue);
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
