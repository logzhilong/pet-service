package com.momoplan.pet.framework.user.handler.impl;

import java.util.Date;

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
 * 解除绑定宠物卡片
 * 
 * @author liangc
 */
@Component("unboundPetCard")
public class UnboundPetCardHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(UnboundPetCardHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try{
			String userid = getUseridFParamSToken(clientRequest);
			String id = PetUtil.getParameter(clientRequest, "id");
			PetCard po = mapperOnCache.selectByPrimaryKey(PetCard.class, id);
			if(!userid.equals(po.getUserId())){
				throw new Exception("您不是名片的所有者，不能解除绑定。");
			}
			PetCard p = new PetCard();
			p.setId(po.getId());
			p.setNum(po.getNum());
			p.setEt(new Date());
			p.setEb(userid);
			mapperOnCache.updateByPrimaryKey(p,p.getId());
			logger.debug("unboundPetCard 成功 body="+gson.toJson(clientRequest));
			rtn = new Success(sn,true,"OK").toString();
		}catch(Exception e){
			logger.debug("unboundPetCard 失败 body="+gson.toJson(clientRequest));
			logger.error(e.getMessage(),e);
			rtn = new Success(sn,false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}

}