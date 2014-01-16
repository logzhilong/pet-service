package com.momoplan.pet.framework.petservice.customer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteMapper;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteSubMapper;
import com.momoplan.pet.commons.domain.bbs.po.NoteCriteria;
import com.momoplan.pet.commons.domain.bbs.po.NoteSubCriteria;
import com.momoplan.pet.commons.domain.manager.mapper.MgrTrustUserMapper;
import com.momoplan.pet.commons.domain.manager.po.MgrTrustUser;
import com.momoplan.pet.commons.domain.manager.po.MgrTrustUserCriteria;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.commons.http.PostRequest;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.base.vo.MgrTrustUserVo;

@Service
public class GhostService {
	private static Logger logger = LoggerFactory.getLogger(GhostService.class);
	
	protected static Gson gson = MyGson.getInstance();
	@Autowired
	private MgrTrustUserMapper mgrTrustUserMapper = null;
	@Autowired
	private MapperOnCache mapperOnCache = null;
	@Autowired
	private CommonConfig commonConfig = null;
	@Autowired
	private NoteMapper noteMapper = null;
	@Autowired
	private NoteSubMapper noteSubMapper = null;
	
	/**
	 * 我的幽灵用户列表
	 * 
	 * @param currentUser
	 * @return
	 * @throws Exception
	 */
	public List<MgrTrustUserVo> getGhostList(String currentUser) throws Exception {
		String pet_file_server = commonConfig.get("pet_file_server");
		String imgUrl = pet_file_server + "/get";
		logger.debug("getGhostList : currentUser=" + currentUser);
		MgrTrustUserCriteria mgrTrustUserCriteria = new MgrTrustUserCriteria();
		MgrTrustUserCriteria.Criteria criteria = mgrTrustUserCriteria.createCriteria();
		criteria.andNrootIdEqualTo(currentUser);
		List<MgrTrustUser> list = mgrTrustUserMapper.selectByExample(mgrTrustUserCriteria);
		if (list == null)
			return null;
		List<MgrTrustUserVo> vl = new ArrayList<MgrTrustUserVo>();
		for (MgrTrustUser m : list) {
			MgrTrustUserVo vo = new MgrTrustUserVo();
			BeanUtils.copyProperties(m, vo);
			vo.setTotalNote(getTotalNote(m.getUserId()));
			vo.setTotalReply(getTotalReply(m.getUserId()));
			String uid = m.getUserId();
			if (StringUtils.isNotEmpty(uid)) {
				SsoUser user = mapperOnCache.selectByPrimaryKey(SsoUser.class,uid);
				vo.setNickname(user.getNickname());
				vo.setUserName(user.getUsername());
				String img = user.getImg();
				if (img != null) {
					String icon = imgUrl + "/" + img.split("_")[0];
					logger.debug("ICON :: " + icon);
					vo.setImg(icon);
				}
				vo.setCt(user.getCreateTime());
			}
			vl.add(vo);
		}
		return vl;
	}
	
	private Integer getTotalNote(String userid){
		NoteCriteria noteCriteria = new NoteCriteria();
		noteCriteria.createCriteria().andUserIdEqualTo(userid);
		int count = noteMapper.countByExample(noteCriteria);
		return count;
	}
	private Integer getTotalReply(String userid){
		NoteSubCriteria noteSubCriteria = new NoteSubCriteria();
		noteSubCriteria.createCriteria().andUserIdEqualTo(userid);
		int count = noteSubMapper.countByExample(noteSubCriteria);
		return count;
	}

	public void saveGhost(String currentUser, SsoUser user) throws Exception {
		String id = user.getId();
		if(StringUtils.isNotEmpty(id)){
			logger.debug("更新幽灵用户:"+gson.toJson(user));
			String url = commonConfig.get("service.uri.pet_user");
			ClientRequest request = new ClientRequest();
			request.setMethod("updateUser");
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("userid", 		user.getId());
			params.put("nickname", 		user.getNickname());
			params.put("phonenumber", 	user.getPhoneNumber());
			params.put("password", 		"pet-service-manager");
			params.put("birthdate", 	user.getBirthdate());
			params.put("gender", 		user.getGender());
			params.put("city", 			user.getCity());
			params.put("signature", 	user.getSignature());
			params.put("img", 			user.getImg());
			params.put("hobby", 		user.getHobby());
			request.setParams(params);
			String body = gson.toJson(request);
			logger.debug("url=" + url);
			logger.debug("body=" + body);
			String res = PostRequest.postText(url, "body", body);
			logger.debug("response=" + res);
			JSONObject success = new JSONObject(res);
			String entity = success.getString("entity");
			if (!success.getBoolean("success")) {
				throw new Exception(entity);
			}
		}else{
			logger.debug("新建幽灵用户:"+gson.toJson(user));
			String url = commonConfig.get("service.uri.pet_sso");
			ClientRequest request = new ClientRequest();
			request.setMethod("register");
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("nickname", 		user.getNickname());
			params.put("phonenumber", 	user.getPhoneNumber());
			params.put("password", 		"pet-service-manager");
			params.put("birthdate", 	user.getBirthdate());
			params.put("gender", 		user.getGender());
			params.put("city", 			user.getCity());
			params.put("signature", 	user.getSignature());
			params.put("img", 			user.getImg());
			params.put("hobby", 		user.getHobby());
			request.setParams(params);
			String body = gson.toJson(request);
			logger.debug("url=" + url);
			logger.debug("body=" + body);
			String res = PostRequest.postText(url, "body", body);
			logger.debug("response=" + res);
			JSONObject success = new JSONObject(res);
			String entity = success.getString("entity");
			if (!success.getBoolean("success")) {
				throw new Exception(entity);
			}
			JSONObject entityObj = new JSONObject(entity);
			MgrTrustUser mtu = new MgrTrustUser();
			mtu.setNrootId(currentUser);
			mtu.setUserId(entityObj.getString("userid"));
			mtu.setId(IDCreater.uuid());
			logger.debug("mtu="+gson.toJson(mtu));
			mgrTrustUserMapper.insertSelective(mtu);
		}
	}

}
