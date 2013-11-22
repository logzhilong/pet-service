package com.momoplan.pet.framework.petservice.bbs.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.bbs.po.NoteSub;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.commons.http.PostRequest;
import com.momoplan.pet.commons.repository.bbs.NoteSubRepository;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.petservice.bbs.controller.NoteAction;
import com.momoplan.pet.framework.petservice.bbs.vo.NoteSubVo;

@Service
public class ReplyService {

	private static Logger logger = LoggerFactory.getLogger(NoteAction.class);

	@Autowired
	private NoteSubRepository noteSubRepository = null;
	@Autowired
	private CommonConfig commonConfig = null;
	@Autowired
	private MapperOnCache mapperOnCache = null;

	public List<NoteSubVo> getReplyList(String noteId) throws Exception {
		List<NoteSub> list = noteSubRepository.getReplyListByNoteId(noteId, Integer.MAX_VALUE, 0);
		List<NoteSubVo> vos = new ArrayList<NoteSubVo>();
		for(NoteSub ns : list){
			String nsid = ns.getId();
			NoteSub noteSub = mapperOnCache.selectByPrimaryKey(NoteSub.class, nsid);//这么做是为了取最新的状态,都是缓存取值
			NoteSubVo vo = new NoteSubVo();
			BeanUtils.copyProperties(noteSub, vo);
			String uid = noteSub.getUserId();
			if(StringUtils.isNotEmpty(uid)){
				SsoUser user = mapperOnCache.selectByPrimaryKey(SsoUser.class, uid);// 在缓存中获取用户
				logger.debug("---------------");
				logger.debug("userId="+uid);
				logger.debug("user="+user);
				logger.debug("noteSub="+noteSub);
				logger.debug("vo="+vo);
				logger.debug("---------------");
				vo.setNickname(user.getNickname());
				String img = user.getImg();
				if(img!=null){
					String fs = commonConfig.get("pet_file_server");
					vo.setUserIcon(fs+"/get/"+img.split("_")[0]);
				}
			}else{
				logger.debug("ERR : 用户ID为空 : note_sub_id="+noteSub.getId());
			}
			vos.add(vo);
		}
		return vos;
	}

	//{"userId":"用户id","noteId":"帖子id","content":"内容","pid":"父回帖id"}
	public void saveReply(NoteSub vo)throws Exception {
		logger.debug("save : "+vo.toString());
		String url = commonConfig.get("service.uri.pet_bbs");
		HashMap<String,Object> params = new HashMap<String,Object>();
		params.put("userId", vo.getUserId());
		params.put("noteId", vo.getNoteId());
		params.put("content", vo.getContent());
		if(StringUtils.isNotEmpty(vo.getPid()))
			params.put("pid", vo.getPid());
		ClientRequest request = new ClientRequest();
		request.setMethod("replyNote");
		request.setParams(params);
		String body = MyGson.getInstance().toJson(request);
		logger.debug("saveReply input="+body);
		String response = PostRequest.postText(url, "body",body);
		logger.info("saveReply output="+response);
		JSONObject success = new JSONObject(response);
		if(!success.getBoolean("success")){
			throw new Exception(success.getString("entity"));
		}
	}
	
}
