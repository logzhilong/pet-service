package com.momoplan.pet.framework.bbs.handler.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.bbs.mapper.ForumAssortRelMapper;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteMapper;
import com.momoplan.pet.commons.domain.bbs.po.ForumAssortRel;
import com.momoplan.pet.commons.domain.bbs.po.ForumAssortRelCriteria;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.bbs.po.NoteCriteria;
import com.momoplan.pet.framework.bbs.handler.AbstractHandler;

@Component("adminUpdateNoteAssortId")
public class AdminUpdateNoteAssortIdHandler extends AbstractHandler {
	
	private static Logger logger = LoggerFactory.getLogger(AdminUpdateNoteAssortIdHandler.class);
	@Autowired
	private NoteMapper noteMapper = null;
	@Autowired
	private ForumAssortRelMapper forumAssortRelMapper = null;
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String sn = clientRequest.getSn();
		String rtn = null;
		try{
			List<ForumAssortRel> farl = forumAssortRelMapper.selectByExample(new ForumAssortRelCriteria());
			Map<String,List<String>> map = new HashMap<String,List<String>>();
			for(ForumAssortRel far : farl){
				String assortId = far.getAssortId();
				String forumId = far.getForumId();
				List<String> forumIds = map.get(assortId);
				if(forumIds == null){
					forumIds = new ArrayList<String>();
					map.put(assortId, forumIds);
				}
				forumIds.add(forumId);
			}
			Set<String> assortIds = map.keySet();
			int i = 0;
			for(String assortId : assortIds){
				NoteCriteria noteCriteria = new NoteCriteria();
				noteCriteria.createCriteria().andForumIdIn(map.get(assortId));
				List<Note> noteList = noteMapper.selectByExample(noteCriteria);
				for(Note n : noteList){
					n.setAssortId(assortId);
					mapperOnCache.updateByPrimaryKeySelective(n, n.getId());
					i++;
					logger.debug("update["+i+"]::::> note.id="+n.getId());
				}
			}
			rtn = new Success(sn,true,"OK").toString();
		}catch(Exception e){
			logger.error("updateNoteRel : ",e);
			rtn = new Success(sn,false,e.toString()).toString();
		}finally{
			writeStringToResponse(rtn,response);
		}
	}
}
