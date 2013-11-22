package com.momoplan.pet.framework.bbs.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.framework.bbs.handler.AbstractHandler;
import com.momoplan.pet.framework.bbs.vo.NoteVo;

/**
 * 根据帖子id查看帖子详情
 * @author  qiyongc
 */
@Component("getNoteById")
public class GetNoteByIdHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(GetNoteByIdHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			String noteId=PetUtil.getParameter(clientRequest, "noteId");
			//TODO更新帖子点击数   暂时这样写,
			noteService.updateClickCount(noteId);
			NoteVo vo = noteService.getNoteById(noteId);
			logger.debug("根据id获取帖子详情 body="+gson.toJson(clientRequest));
			rtn = new Success(true,vo).toString();
		}catch(Exception e){
			logger.debug("根据id获取帖子详情 body="+gson.toJson(clientRequest));
			logger.error("getNoteById : ",e);
			rtn = new Success(false,e.toString()).toString();
		}finally{
			//logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}
}
