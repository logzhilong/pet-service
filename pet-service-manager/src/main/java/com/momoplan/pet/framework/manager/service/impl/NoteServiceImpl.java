package com.momoplan.pet.framework.manager.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteMapper;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.manager.mapper.MgrTrustUserMapper;
import com.momoplan.pet.commons.domain.manager.po.MgrTrustUser;
import com.momoplan.pet.commons.domain.manager.po.MgrTrustUserCriteria;
import com.momoplan.pet.commons.http.PostRequest;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.manager.security.SessionManager;
import com.momoplan.pet.framework.manager.service.NoteService;
import com.momoplan.pet.framework.manager.vo.WebUser;

@Service
public class NoteServiceImpl implements NoteService {
	Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);
	@Resource
	private NoteMapper noteMapper = null;
	@Resource
	private MgrTrustUserMapper trustUserMapper = null;
	private CommonConfig commonConfig=new CommonConfig();
	/**
	 * 根据id获取帖子
	 */
	@Override
	public Note getNotebyid(String id) throws Exception {
		try {
			if ("" != id && null != id) {
				Note note = noteMapper.selectByPrimaryKey(id);
				logger.debug("跟据id为"+id.toString()+"获取实体为"+note.toString());
				return note;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 增加或者修改帖子
	 */
	@Override
	public void NoteAddOrUpdate(Note note) throws Exception {
		try {
			String id = note.getId();
			Note note2 = noteMapper.selectByPrimaryKey(id);
			if (note2 != null && !"".equals(note2.getId())) {
				note2.setEt(new Date());
				logger.debug("修该帖子"+note2.toString());
				noteMapper.updateByPrimaryKeySelective(note);
			} else {
				note.setId(IDCreater.uuid());
				note.setCt(new Date());
				note.setEt(new Date());
				note.setIsDel(false);
				note.setIsEute(false);
				note.setIsTop(false);
				note.setState("0");
				note.setType("0");
				noteMapper.insertSelective(note);
				logger.debug("增加帖子"+note.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除帖子
	 * @param note
	 * @throws Exception
	 */
	@Override
	public void NoteDel(Note note) throws Exception{
		try {
			note.setIsDel(true);
			noteMapper.updateByPrimaryKeySelective(note);
			logger.debug("删除帖子"+note.toString());
		} catch (Exception e) {
			logger.error("NoteDelError"+e);
			e.printStackTrace();
		}
	}

	/**
	 * 更新帖子点击数
	 * @param ClientRequest
	 * @return
	 */
	public void updateClickCount(String noteid){
		try {
			Note note=noteMapper.selectByPrimaryKey(noteid);
				if(note.equals(null)){
				}else{
					note.setClientCount(note.getClientCount()+1);
					note.setEt(new Date());
					logger.debug("更新帖子:"+note.toString()+"点击数:");
					noteMapper.updateByPrimaryKey(note);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取托管用户列表
	 */
	@SuppressWarnings("static-access")
	@Override
	public List<MgrTrustUser> trustUserslist(HttpServletRequest request) throws Exception {
		MgrTrustUserCriteria trustUserCriteria = new MgrTrustUserCriteria();
		MgrTrustUserCriteria.Criteria criteria=trustUserCriteria.createCriteria();
		SessionManager manager = null;
		WebUser user1 = manager.getCurrentUser(request);
		criteria.andNrootIdEqualTo(user1.getId());
		List<MgrTrustUser> trustUserlist = trustUserMapper.selectByExample(trustUserCriteria);
		String url = commonConfig.get("service.uri.pet_user", null);
		for (MgrTrustUser user : trustUserlist) {
			String uid = user.getUserId();
			String body = "{\"method\":\"getUserinfo\",\"params\":{\"userid\":\""+ uid + "\"}}";
			String res = PostRequest.postText(url, "body", body.toString());
			JSONObject object = new JSONObject(res);
			JSONObject object1 = new JSONObject(object.getString("entity"));
			user.setNrootId(object1.getString("nickname"));
		}
		return trustUserlist;
	}
}
