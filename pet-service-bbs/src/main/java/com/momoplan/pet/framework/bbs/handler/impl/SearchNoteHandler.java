package com.momoplan.pet.framework.bbs.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.framework.bbs.handler.AbstractHandler;

/**
 *搜索帖子
 * @author  qiyongc
 */
@Component("searchNote")
public class SearchNoteHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(SearchNoteHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try{Note note=new Note();
			note.setForumId(PetUtil.getParameter(clientRequest, "forumid"));
			note.setName(PetUtil.getParameter(clientRequest, "notename"));
			int pageNo=PetUtil.getParameterInteger(clientRequest, "pageNo");
			int pageSize=PetUtil.getParameterInteger(clientRequest, "pageSize");
		Object object=noteService.searchNote(note,pageNo,pageSize);
			logger.debug("搜索帖子成功 body="+gson.toJson(clientRequest));
			rtn = new Success(true,object).toString();
		}catch(Exception e){
			logger.error("搜索帖子失败 body="+gson.toJson(clientRequest));
			logger.error("login : ",e);
			rtn = new Success(false,e.toString()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}
}
