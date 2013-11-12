package com.momoplan.pet.framework.manager.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.domain.bbs.mapper.ForumMapper;
import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.commons.domain.bbs.po.ForumCriteria;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.http.PostRequest;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.manager.security.SessionManager;
import com.momoplan.pet.framework.manager.service.BBSManagerService;
import com.momoplan.pet.framework.manager.service.CommonDataManagerService;
import com.momoplan.pet.framework.manager.vo.PageBean;
import com.momoplan.pet.framework.manager.vo.WebUser;

@Service
public class BBSManagerServiceImpl implements BBSManagerService {
	private Logger logger = LoggerFactory.getLogger(BBSManagerServiceImpl.class);
	@Autowired
	private ForumMapper forumMapper = null;
	@Autowired
	CommonDataManagerService commonDataManagerService;
	private CommonConfig commonConfig = new CommonConfig();
	@Override
	public PageBean<Forum> listForum(PageBean<Forum> pageBean, Forum vo)
			throws Exception {
		logger.debug("welcome to listForum.....................");
		String id = vo.getId();
		ForumCriteria forumCriteria = new ForumCriteria();
		forumCriteria.setOrderByClause("seq");
		ForumCriteria.Criteria criteria = forumCriteria.createCriteria();
		if (StringUtils.isEmpty(id)) {
			criteria.andPidIsNull();
		} else {
			criteria.andPidEqualTo(id);
		}
//		if (StringUtils.isEmpty(vo.getName())) {
//			if ("all".equals(vo.getAreaCode())
//					&& "all".equals(vo.getAreaCode())) {
//			} else if ("" != vo.getAreaDesc() && null != vo.getAreaDesc()) {
//				criteria.andAreaCodeLike("%" + vo.getAreaDesc() + "%");
//			} else if ("" != vo.getAreaCode() && null != vo.getAreaCode()) {
//				criteria.andAreaCodeLike("%" + vo.getAreaCode() + "%");
//			}
//		} else {
//			criteria.andNameLike("%" + vo.getName() + "%");
//		}
		int totalCount = forumMapper.countByExample(forumCriteria);
		forumCriteria.setMysqlOffset((pageBean.getPageNo() - 1)* pageBean.getPageSize());
		forumCriteria.setMysqlLength(pageBean.getPageSize());
		List<Forum> list = forumMapper.selectByExample(forumCriteria);
		pageBean.setData(list);
		pageBean.setTotalRecorde(totalCount);
		// 做为地区显示,暂时不用
		// for (Forum forum : pageBean.getData()) {
		// int i = forum.getAreaCode().indexOf("-");
		// if (i != -1) {
		// CommonAreaCode code2 = new CommonAreaCode();
		// String sheng =
		// forum.getAreaCode().substring(0,forum.getAreaCode().indexOf("-"));
		// code2.setId(sheng);
		// String sheng1 =
		// commonDataManagerService.getCommonAreaCodeByid(code2).getName();
		//
		// if (forum.getAreaCode().indexOf("-") !=
		// forum.getAreaCode().lastIndexOf("-")) {
		// String shi =
		// forum.getAreaCode().substring(forum.getAreaCode().indexOf("-") +
		// 1,forum.getAreaCode().lastIndexOf("-"));
		// code2.setId(shi);
		// String sheng2 =
		// commonDataManagerService.getCommonAreaCodeByid(code2).getName();
		//
		// String qu =
		// forum.getAreaCode().substring(forum.getAreaCode().lastIndexOf("-") +
		// 1);
		// code2.setId(qu);
		// String sheng3 =
		// commonDataManagerService.getCommonAreaCodeByid(code2).getName();
		// forum.setAreaCode(sheng1 + "-" + sheng2 + "-" + sheng3);
		// } else {
		// String qu =
		// forum.getAreaCode().substring(forum.getAreaCode().lastIndexOf("-") +
		// 1);
		// code2.setId(qu);
		// String sheng3 =
		// commonDataManagerService.getCommonAreaCodeByid(code2).getName();
		// forum.setAreaCode(sheng1 + "-" + sheng3);
		// }
		// } else {
		// CommonAreaCode code2 = new CommonAreaCode();
		// code2.setId(forum.getAreaCode());
		// String ee =
		// commonDataManagerService.getCommonAreaCodeByid(code2).getName();
		// forum.setAreaCode(ee);
		// }
		// }
		return pageBean;
	}

	/**
	 * 增加圈子或者修改圈子
	 * 
	 * @param forum
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	@Override
	public int addOrUpdateForum(Forum forum, HttpServletRequest request)
		throws Exception {
		logger.debug("welcome to addOrUpdateForum.....................");
		String id = forum.getId();
		Forum fo = forumMapper.selectByPrimaryKey(id);
		if (fo != null && !"".equals(fo.getId())) {
			logger.debug("selectByPK.po=" + fo);
			String img = forum.getLogoImg();
			if (null != img && "" != img) {
				forum.setLogoImg(img.substring(img.indexOf("get") + 4, 83));
			}
			return forumMapper.updateByPrimaryKeySelective(forum);
		} else {
			forum.setId(IDCreater.uuid());
			forum.setCt(new Date());
			SessionManager manager = null;
			WebUser user1 = manager.getCurrentUser(request);

			forum.setCb(user1.getName());
			if ("all".equals(forum.getPid())) {
				forum.setPid(null);
			}
			String img = forum.getLogoImg();
			if (null != img && "" != img) {
				forum.setLogoImg(img.substring(img.indexOf("get") + 4, 83));
			}
			return forumMapper.insertSelective(forum);
		}
	}

	/**
	 * 根据id删除圈子
	 * 
	 * @param forum
	 * @throws Exception
	 */
	@Override
	public void DelForum(Forum forum) throws Exception {
		logger.debug("welcome to DelForum.....................");
		ForumCriteria forumCriteria = new ForumCriteria();
		ForumCriteria.Criteria criteria = forumCriteria.createCriteria();
		criteria.andPidEqualTo(forum.getId());
		List<Forum> forums = forumMapper.selectByExample(forumCriteria);
		if (forums.size() > 0) {
			for (Forum fo : forums) {
				forumMapper.deleteByPrimaryKey(fo.getId());
			}
		}
		forumMapper.deleteByPrimaryKey(forum.getId());
	}

	/**
	 * 根据id获取圈子
	 * 
	 * @param forum
	 * @throws Exception
	 */
	@Override
	public Forum getForumbyid(Forum forum) throws Exception {
		logger.debug("welcome to getForumbyid.....................");
		return forumMapper.selectByPrimaryKey(forum.getId());
	}

	/**
	 * 获取圈子集合(做级联)
	 * 
	 * @param forum
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Forum> getForumlist() throws Exception {
		logger.debug("welcome to getForumlist.....................");
		ForumCriteria forumCriteria = new ForumCriteria();
		ForumCriteria.Criteria criteria = forumCriteria.createCriteria();
		criteria.andPidIsNull();
		List<Forum> forums = forumMapper.selectByExample(forumCriteria);
		return forums;
	}

	/**
	 * 根据父id获取子级圈子集合
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Forum> getSunForumListbyPid(Forum forum) throws Exception {
		logger.debug("welcome to getSunForumList.....................");
		ForumCriteria forumCriteria = new ForumCriteria();
		forumCriteria.setOrderByClause("seq");
		ForumCriteria.Criteria criteria = forumCriteria.createCriteria();
		criteria.andPidEqualTo(forum.getId());
		List<Forum> forums = forumMapper.selectByExample(forumCriteria);
		return forums;
	}

	/**
	 * 根据圈子id获取帖子集合
	 * 
	 * @param forum
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Note> getAllNotesByForumId(Forum forum) throws Exception {
		logger.debug("welcome to getAllNotesByForumId.....................");
		String url = commonConfig.get("service.uri.pet_bbs", null);
		String body = null;
		if (forum.getDescript() != null && "" != forum.getDescript()) {
			body = "{\"method\":\"getNoteList\",\"params\":{\"condition\":\""+ forum.getDescript() + "\",\"forumId\":\"" + forum.getId()+ "\",\"action\":\"" + "SEARCH" + "\",\"withTop\":\""+ "true" + "\",\"pageNo\":\"" + "0" + "\",\"pageSize\":\""+ "500" + "\"}}";
		} else {
			body = "{\"method\":\"getNoteList\",\"params\":{\"forumId\":\""+ forum.getId() + "\",\"action\":\"" + "ALL"+ "\",\"withTop\":\"" + "true" + "\",\"pageNo\":\"" + "0"+ "\",\"pageSize\":\"" + "500" + "\"}}";
		}
		String res = PostRequest.postText(url, "body", body.toString());
		JSONObject object = new JSONObject(res);
		if (object.getBoolean("success")) {
			if (res.indexOf("entity") >= 0) {
				JSONArray ja = object.getJSONArray("entity");
				List<Note> notes = new ArrayList<Note>();
				for (int i = 0; i < ja.length(); i++) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
					JSONObject jo = (JSONObject) ja.get(i);
					logger.debug(jo.toString());
					Note note = new Note();
					note.setId(jo.getString("id"));
					note.setForumId(jo.getString("forumId"));
					note.setName(jo.getString("name"));
					note.setUserId(jo.getString("userId"));
					note.setClientCount(jo.getLong("clientCount"));
					note.setIsDel(jo.getBoolean("isDel"));
					note.setIsEute(jo.getBoolean("isEute"));
					note.setState(jo.getString("state"));
					note.setType(jo.getString("type"));
					note.setIsTop(jo.getBoolean("isTop"));
					note.setCt(sdf.parse(jo.getString("ct")));
					note.setEt(sdf.parse(jo.getString("et")));
					note.setRt(sdf.parse(jo.getString("rt")));
					notes.add(note);
				}
				return notes;
			}
		}
		return null;
	}
}
