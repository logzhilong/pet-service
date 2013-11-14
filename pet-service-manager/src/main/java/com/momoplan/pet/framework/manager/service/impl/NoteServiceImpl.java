package com.momoplan.pet.framework.manager.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteMapper;
import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.bbs.po.NoteCriteria;
import com.momoplan.pet.commons.domain.manager.mapper.MgrTrustUserMapper;
import com.momoplan.pet.commons.domain.manager.po.MgrTrustUser;
import com.momoplan.pet.commons.domain.manager.po.MgrTrustUserCriteria;
import com.momoplan.pet.commons.http.PostRequest;
import com.momoplan.pet.commons.repository.bbs.NoteRepository;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.manager.security.SessionManager;
import com.momoplan.pet.framework.manager.service.NoteService;
import com.momoplan.pet.framework.manager.vo.WebUser;

@Service
public class NoteServiceImpl implements NoteService {
	Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);
	@Autowired
	private NoteMapper noteMapper = null;
	@Autowired
	private MgrTrustUserMapper trustUserMapper = null;
	private CommonConfig commonConfig = new CommonConfig();
	@Autowired
	private MapperOnCache onCache =null;
	@Autowired
	private NoteRepository noteRepository = null;
	/**
	 * 根据id获取帖子
	 */
	@Override
	public Note getNotebyid(String id) throws Exception {
		logger.debug("welcome to getNotebyid.....................");
		if ("" != id && null != id) {
			Note note = noteMapper.selectByPrimaryKey(id);
			logger.debug("跟据id为" + id.toString() + "获取实体为" + note.toString());
			return note;
		} else {
			return null;
		}
	}

	/**
	 * 增加或者修改帖子
	 */
	@Override
	public void NoteAddOrUpdate(Note note) throws Exception {
		String id = note.getId();
		Note note2 = noteMapper.selectByPrimaryKey(id);
		if (note2 != null && !"".equals(note2.getId())) {
			note.setCt(note2.getCt());
			logger.debug("修该帖子" + note2.toString());
			boolean boo=note.getIsTop();
			//如果指定帖子数量>5则按照创建时间选择后5条,保证指定仅有5条
			if(boo){
				NoteCriteria noteCriteria=new NoteCriteria();
				noteCriteria.setOrderByClause("ct");
				NoteCriteria.Criteria criteria=noteCriteria.createCriteria();
				criteria.andForumIdEqualTo(note2.getForumId());
				criteria.andIsTopEqualTo(true);
				criteria.andIsDelEqualTo(false);
				List<Note> notelist=noteMapper.selectByExample(noteCriteria);
				if((notelist.size()-5)>=0){
					for(int i=0;i<=(notelist.size()-5);i++){
					Note nn =notelist.get(i);
					nn.setIsTop(false);
					onCache.updateByPrimaryKeySelective(nn, nn.getId());
					}
				}
			}
			noteRepository.flushTopNoteByFid(note2.getForumId());
			onCache.updateByPrimaryKeySelective(note, note.getId());
		} else {
			logger.debug(note.toString());
			String con = note.getContent();
			//为图片增加超链接
			Matcher matcher = Pattern.compile("(<img.*?src=\")(.+?)(\".*?/>)").matcher(con);
			Map<String,String> map = new HashMap<String,String>();
			while(matcher.find()){
				String s1 = matcher.group(1);
				String s2 = matcher.group(2);
				String s3 = matcher.group(3);
				String s4 = "<a href=\""+s2+"\">";
				String s5 = "</a>";
				String str = s1+s2+s3;
				String target = s4+s1+s2+s3+s5;
				map.put(str, target);
			}
			
			for(String k :map.keySet()){
				String target = map.get(k);
				con = con.replaceAll(k, target);
			}
			
			//转义
			con = con.replace("\"", "\\\"");
			String url = commonConfig.get("service.uri.pet_bbs", null);
			String body = "{\"method\":\"sendNote\",\"params\":{\"userId\":\""+ note.getUserId() + "\",\"forumId\":\""+ note.getForumId() + "\",\"name\":\"" + note.getName()+ "\",\"content\":\"" + con + "\"}}";
			String res = PostRequest.postText(url, "body", body.toString());
			logger.debug("增加帖子" + res.toString());
		}
	}

	/**
	 * 删除帖子
	 * 
	 * @param note
	 * @throws Exception
	 */
	@Override
	public void NoteDel(Note note) throws Exception {
		logger.debug("welcome to NoteDel.....................");
		note.setIsDel(true);
		onCache.updateByPrimaryKeySelective(note,note.getId());
		logger.debug("删除帖子" + note.toString());
	}

	/**
	 * 更新帖子点击数
	 * 
	 * @param ClientRequest
	 * @return
	 */
	public void updateClickCount(String noteid) throws Exception {
		logger.debug("welcome to updateClickCount.....................");
		Note note = noteMapper.selectByPrimaryKey(noteid);
		if (note.equals(null)) {
		} else {
			note.setClientCount(note.getClientCount() + 1);
			note.setEt(new Date());
			logger.debug("更新帖子:" + note.toString() + "点击数:");
			noteMapper.updateByPrimaryKey(note);
		}
	}

	/**
	 * 获取托管用户列表
	 */
	@SuppressWarnings("static-access")
	@Override
	public List<MgrTrustUser> trustUserslist(HttpServletRequest request)
			throws Exception {
		logger.debug("welcome to trustUserslist.....................");
		MgrTrustUserCriteria trustUserCriteria = new MgrTrustUserCriteria();
		MgrTrustUserCriteria.Criteria criteria = trustUserCriteria.createCriteria();
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
			if (object.getBoolean("success")) {
				if (res.indexOf("entity") >= 0) {
					JSONObject object1 = new JSONObject(object.getString("entity"));
					try {
						user.setNrootId(object1.getString("nickname"));
					} catch (Exception e) {
						user.setNrootId(null);
					}
				}
			}
		}

		return trustUserlist;
	}

	@Override
	public List<Note> getDeledNotes(Forum forum) throws Exception {
		if(null != forum.getId() && "" != forum.getId()){
			NoteCriteria noteCriteria=new NoteCriteria();
			NoteCriteria.Criteria criteria=noteCriteria.createCriteria();
			criteria.andIsDelEqualTo(true);
			criteria.andForumIdEqualTo(forum.getId());
			List<Note> notes=noteMapper.selectByExample(noteCriteria);
			return notes;
		}else{
			return null;
		}
	}
}
