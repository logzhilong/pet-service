package com.momoplan.pet.framework.user.handler.impl;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.user.po.PetCard;
import com.momoplan.pet.framework.user.handler.AbstractHandler;
/**
 * 设置宠物名片信息
 * 
 * 宠物品种，宠物昵称，宠物主人，主人电话，主人寄语
 * @author liangc
 */
@Component("setPetCard")
public class SetPetCardHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(SetPetCardHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try{
			String userid = getUseridFParamSToken(clientRequest);
			PetCard vo = revicePetCard(clientRequest);
			vo.setEt(new Date());
			vo.setEb(userid);
			PetCard po = mapperOnCache.selectByPrimaryKey(PetCard.class, vo.getId());
			if(StringUtils.isEmpty(po.getUserId())){
				vo.setUserId(userid);
			}else if(!userid.equalsIgnoreCase(po.getUserId())){
				throw new Exception("您不是名片的所有者，不能编辑");
			}
			mapperOnCache.updateByPrimaryKeySelective(vo, vo.getId());
			logger.debug("setPetCard 成功 body="+gson.toJson(clientRequest));
			rtn = new Success(sn,true,vo.getId()).toString();
		}catch(Exception e){
			logger.debug("setPetCard 失败 body="+gson.toJson(clientRequest));
			logger.error(e.getMessage(),e);
			rtn = new Success(sn,false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}

}