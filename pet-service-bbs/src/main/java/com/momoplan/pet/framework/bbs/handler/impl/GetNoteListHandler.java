package com.momoplan.pet.framework.bbs.handler.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.framework.bbs.handler.AbstractHandler;
import com.momoplan.pet.framework.bbs.vo.Action;
import com.momoplan.pet.framework.bbs.vo.ConditionType;
import com.momoplan.pet.framework.bbs.vo.NoteVo;

/**
 * 获取帖子列表
 * 
 * 全部：所有帖子的列表，置顶帖子在前，其他帖子按照最新更新时间排序
 * 精华：全部精华帖
 * 最新回复：按照帖子中最新回复的时间排序，无回复的帖子不显示，置顶的帖子按照普通帖子格式筛选显示，精华帖子也在筛选范围
 * 最新发表：按照帖子最新发表的时间排序，置顶的帖子按照普通帖子的格式筛选显示，精华帖子也在筛选范围
//			"forumId":"圈子id",
//			"withTop":"是否带着置顶帖(boolean),true 是 ,false 否"
//			"action":"ALL 全部；EUTE 精华；NEW_ET 最新回复；NEW_CT 最新发布；SEARCH 查询"
//			"condition":"查询条件，当 action=SEARCH 时，此值必填"
 */
@Component("getNoteList")
public class GetNoteListHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(GetNoteListHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String sn = clientRequest.getSn();
		String rtn = null;
		try{
			String userid = getUseridFParamSToken(clientRequest);
			String fid=PetUtil.getParameter(clientRequest, "forumId");
			boolean withTop=Boolean.valueOf(PetUtil.getParameter(clientRequest, "withTop"));
			String action=PetUtil.getParameter(clientRequest, "action");
			String condition=PetUtil.getParameter(clientRequest, "condition");
			String conditionType=PetUtil.getParameter(clientRequest, "conditionType");
			if(StringUtils.isEmpty(conditionType))
				conditionType = ConditionType.NOTE_NAME.getCode();
			int pageNo=PetUtil.getParameterInteger(clientRequest, "pageNo");
			int pageSize=PetUtil.getParameterInteger(clientRequest, "pageSize");
			List<NoteVo> list = noteService.getNoteList(userid,fid,Action.valueOf(action),condition,ConditionType.valueOf(conditionType),withTop,pageNo,pageSize);
			logger.debug("某圈子最新帖子成功 body="+gson.toJson(clientRequest));
			rtn = new Success(sn,true,list).toString();
		}catch(Exception e){
			logger.debug("某圈子最新帖子失败 body="+gson.toJson(clientRequest));
			logger.error("getNoteList : ",e);
			rtn = new Success(sn,false,e.toString()).toString();
		}finally{
			writeStringToResponse(rtn,response);
		}
	}
}
