package com.momoplan.pet.framework.bbs.handler.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.framework.bbs.handler.AbstractHandler;
import com.momoplan.pet.framework.bbs.vo.NoteSubVo;
import com.momoplan.pet.framework.bbs.vo.PageBean;

/**
 * 根据帖子id获取所有回复
 */
@Component("getReplyList")
public class GetReplyListHandler extends AbstractHandler {

	private Logger logger = LoggerFactory.getLogger(GetReplyListHandler.class);
	public static void main(String[] args) throws JSONException {
		PageBean<NoteSubVo> o = new PageBean<NoteSubVo>();
		List<NoteSubVo> l = new ArrayList<NoteSubVo>();
		NoteSubVo vo = new NoteSubVo();
		vo.setId("aa");
		l.add(vo);
//		o.setData(l);
		String j = MyGson.getInstance().toJson(o.getData());
		JSONArray arr = new JSONArray(j);
		System.out.println(arr);
	}
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try {
			int pageNo = PetUtil.getParameterInteger(clientRequest, "pageNo");
			int pageSize = PetUtil.getParameterInteger(clientRequest, "pageSize");
			String noteid = PetUtil.getParameter(clientRequest, "noteId");
			String userId = PetUtil.getParameter(clientRequest, "userId");
			PageBean<NoteSubVo> vo = noteSubService.getReplyByNoteId(noteid,userId, pageNo, pageSize);
			String jsonArr = null;
			if(vo.getData()!=null&&vo.getData().size()>0)
				jsonArr = gson.toJson(vo.getData());
			JSONObject success = new JSONObject();
			success.put("success", true);
			JSONObject entity = new JSONObject();
			entity.put("pageSize", pageSize);
			entity.put("pageNo", pageNo);
			entity.put("totalCount", vo.getTotalCount());
			if(jsonArr!=null){
				entity.put("data", new JSONArray(jsonArr));
			}
			success.put("entity", entity);
			logger.debug("根据帖子id获取所有回复成功 body=" + gson.toJson(clientRequest));
			rtn = success.toString();
		} catch (Exception e) {
			logger.error("根据帖子id获取所有回复失败 body=" + gson.toJson(clientRequest));
			logger.error("login : ", e);
			rtn = new Success(false, e.toString()).toString();
		} finally {
			logger.debug(rtn);
			writeStringToResponse(rtn, response);
		}
	}
}
