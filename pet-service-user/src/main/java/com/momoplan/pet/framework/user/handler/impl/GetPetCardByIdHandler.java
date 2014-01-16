package com.momoplan.pet.framework.user.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.user.po.PetCard;
import com.momoplan.pet.framework.user.handler.AbstractHandler;
/**
 * 获取宠物名片信息
 * 
 * 宠物品种，宠物昵称，宠物主人，主人电话，主人寄语
 * @author liangc
 */
@Component("getPetCardById")
public class GetPetCardByIdHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(GetPetCardByIdHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try{
			String id = PetUtil.getParameter(clientRequest, "id");
			PetCard p = mapperOnCache.selectByPrimaryKey(PetCard.class, id);
			rtn = new Success(sn,true,p).toString();
		}catch(Exception e){
			logger.debug("getPetCard 失败 body="+gson.toJson(clientRequest));
			logger.error(e.getMessage(),e);
			rtn = new Success(sn,false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}

}
