package com.momoplan.pet.framework.petservice.bbs.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteMapper;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.bbs.po.NoteCriteria;
import com.momoplan.pet.commons.domain.manager.mapper.MgrTrustUserMapper;
import com.momoplan.pet.commons.domain.manager.po.MgrTrustUser;
import com.momoplan.pet.commons.domain.manager.po.MgrTrustUserCriteria;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.commons.http.PostRequest;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.petservice.bbs.controller.NoteAction;
import com.momoplan.pet.framework.petservice.bbs.vo.MgrTrustUserVo;
import com.momoplan.pet.framework.petservice.bbs.vo.NoteVo;
import com.momoplan.pet.framework.petservice.bbs.vo.Page;

@Service
public class NoteService {

	private static Logger logger = LoggerFactory.getLogger(NoteAction.class);
	
	private static Gson gson = MyGson.getInstance();
	
	@Autowired
	private CommonConfig commonConfig = null;
	@Autowired
	private MapperOnCache mapperOnCache = null;
	@Autowired
	private NoteMapper noteMapper = null;
	@Autowired
	private MgrTrustUserMapper mgrTrustUserMapper = null;
	
	public List<MgrTrustUserVo> getMgrTrustUserList(String currentUserid) throws Exception {
		logger.debug("currentUserid="+currentUserid);
		MgrTrustUserCriteria mgrTrustUserCriteria = new MgrTrustUserCriteria();
		mgrTrustUserCriteria.createCriteria().andNrootIdEqualTo(currentUserid);
		List<MgrTrustUser> list = mgrTrustUserMapper.selectByExample(mgrTrustUserCriteria);
		List<MgrTrustUserVo> l = new ArrayList<MgrTrustUserVo>();
		if(list!=null&&list.size()>0){
			logger.debug("我管理的用户 >>>>>>>>");
			for(MgrTrustUser tu : list){
				SsoUser u = mapperOnCache.selectByPrimaryKey(SsoUser.class, tu.getUserId());
				MgrTrustUserVo v = new MgrTrustUserVo();
				BeanUtils.copyProperties(tu, v);
				v.setUserName(u.getUsername());
				v.setNickname(u.getNickname());
				l.add(v);
			}
			logger.debug("我管理的用户 <<<<<<<<<");
			return l;
		}else
			throw new Exception("无托管用户，请先创建托管用户。");
	}
	
	public Page<Note> getNoteList(Page<Note> page,NoteVo vo) throws Exception {
		logger.debug("getNoteList: "+gson.toJson(vo));
		NoteCriteria noteCriteria = new NoteCriteria();
		noteCriteria.createCriteria().andForumIdEqualTo(vo.getForumId());
		
		int totalCount = noteMapper.countByExample(noteCriteria);
		noteCriteria.setMysqlOffset(page.getPageNo());
		noteCriteria.setMysqlLength((page.getPageNo()+1)*page.getPageSize());
		noteCriteria.setOrderByClause("et desc");
		List<Note> list = noteMapper.selectByExample(noteCriteria);
		
		page.setTotalCount(totalCount);
		page.setData(list);
		
		return page;
		
	}

	public void saveNote(Note vo) throws Exception {
		if(vo.getId()!=null&&!"".equals(vo.getId())){
			logger.debug("更新帖子 "+gson.toJson(vo));
			vo.setEt(new Date());
			mapperOnCache.updateByPrimaryKeySelective(vo, vo.getId());
		}else{
			logger.debug("新增帖子需要调接口的 "+gson.toJson(vo));
			String url = commonConfig.get("service.uri.pet_bbs");
			ClientRequest request = new ClientRequest();
			request.setMethod("sendNote");
			HashMap<String,Object> params = new HashMap<String,Object>();
			params.put("userId", vo.getUserId());
			params.put("forumId", vo.getForumId());
			params.put("name", vo.getName());
			params.put("content", vo.getContent());
			request.setParams(params);
			String body = gson.toJson(request);
			logger.debug("url="+url);
			logger.debug("body="+body);
			String res = PostRequest.postText(url, "body", body);
			logger.debug("response="+res);
			JSONObject success = new JSONObject(res);
			if(!success.getBoolean("success")){
				throw new Exception(success.getString("entity"));
			}
		}
	}
	
}
