package com.momoplan.pet.framework.bbs.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import vo.BbsNoteCount;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.domain.bbs.mapper.ForumMapper;
import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.commons.domain.bbs.po.ForumCriteria;
import com.momoplan.pet.framework.bbs.controller.BbSClientRequest;
import com.momoplan.pet.framework.bbs.service.BbsNoteCountService;
import com.momoplan.pet.framework.bbs.service.ForumService;
import com.momoplan.pet.framework.bbs.service.UserForumRelService;
@Service
public class ForumServiceImpl implements ForumService {

	@Resource
	ForumMapper forumMapper=null;
	@Resource
	BbsNoteCountService bbsNoteCountService=null;
	@Resource
	UserForumRelService userForumRelService=null;
	private static Logger logger = LoggerFactory.getLogger(ForumServiceImpl.class);
	/**
	 * 获取所有父级圈子
	 * @param bbsClientRequest
	 * @return
	 */
	public Object getForumList(BbSClientRequest bbsClientRequest){
		try {
				ForumCriteria  forumCriteria=new ForumCriteria();
				ForumCriteria.Criteria criteria=forumCriteria.createCriteria();
				criteria.andPidIsNull();
				int pageNo=PetUtil.getParameterInteger(bbsClientRequest, "pageNo");
				int pageSize=PetUtil.getParameterInteger(bbsClientRequest, "pageSize");
				forumCriteria.setMysqlOffset((pageNo-1)*pageSize);
				forumCriteria.setMysqlLength(pageSize);
				forumCriteria.setOrderByClause("ct desc");
				List<Forum> forumlist=forumMapper.selectByExample(forumCriteria);
				logger.debug(""+forumlist.toString());
				return forumlist;
		} catch (Exception e) {
			e.printStackTrace();
		return "getForumListFail";
		}
	}
	
	/**
	 * 根据父级id获取子集圈子
	 * @param bbsClientRequest
	 * @return
	 */
	public Object getSunForumListByForumid(BbSClientRequest bbsClientRequest){
		try {
			ForumCriteria forumCriteria=new ForumCriteria();
			ForumCriteria.Criteria criteria=forumCriteria.createCriteria();
			criteria.andPidEqualTo(PetUtil.getParameter(bbsClientRequest, "forumPid"));
			int pageNo=PetUtil.getParameterInteger(bbsClientRequest, "pageNo");
			int pageSize=PetUtil.getParameterInteger(bbsClientRequest, "pageSize");
			forumCriteria.setMysqlOffset((pageNo-1)*pageSize);
			forumCriteria.setMysqlLength(pageSize);
			String userid=PetUtil.getParameter(bbsClientRequest, "userId");
			
			List<Forum> forums=forumMapper.selectByExample(forumCriteria);
			List<BbsNoteCount> noteCounts=new ArrayList<BbsNoteCount>();
			for(Forum forum:forums){
				BbsNoteCount noteCount=new BbsNoteCount();
				noteCount.setId(forum.getId());
				//判断用户是否关注该圈子
				if(userForumRelService.isAttentionForum(userid, forum.getId())>0){
					noteCount.setIsattention("1");
				}else{
					noteCount.setIsattention("0");
				}
				String NewNoteNum=bbsNoteCountService.getNewNoteNumByForumid(forum.getId());
				String NoteNum=bbsNoteCountService.getNoteNumByForumid(forum.getId());
				String NoteRelNum=bbsNoteCountService.getNoteRelNumByForumid(forum.getId());
				noteCount.setNoteCount(NoteNum);
				noteCount.setNoteRelCount(NoteRelNum);
				noteCount.setTodayNewNoteCount(NewNoteNum);
				noteCount.setName(forum.getName());
				noteCount.setImgId(forum.getLogoImg());
				noteCounts.add(noteCount);
			}
			return noteCounts;
		} catch (Exception e) {
			e.printStackTrace();
			return "getSunForumListByForumidFail";
		}
	}
	
	/**
	 * 根据id获取圈子
	 * @param bbsClientRequest
	 * @return
	 */
	public Forum getForumByid(Forum forum){
		try {
			if("" != forum.getId() && null != forum.getId()){
				Forum forum2=forumMapper.selectByPrimaryKey(forum.getId());
				return forum2;
			}else {
				return null; 
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
