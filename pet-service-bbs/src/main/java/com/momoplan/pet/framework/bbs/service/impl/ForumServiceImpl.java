package com.momoplan.pet.framework.bbs.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import vo.BbsNoteCount;
import vo.ForumsNote;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.domain.bbs.mapper.ForumMapper;
import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.commons.domain.bbs.po.ForumCriteria;
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
	 * @param ClientRequest
	 * @return
	 */
	public Object getForumList(ClientRequest ClientRequest){
		try {
				ForumCriteria  forumCriteria=new ForumCriteria();
				ForumCriteria.Criteria criteria=forumCriteria.createCriteria();
				criteria.andPidIsNull();
				int pageNo=PetUtil.getParameterInteger(ClientRequest, "pageNo");
				int pageSize=PetUtil.getParameterInteger(ClientRequest, "pageSize");
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
	 * @param ClientRequest
	 * @return
	 */
	public Object getSunForumListByForumid(ClientRequest ClientRequest){
		try {
			ForumCriteria forumCriteria=new ForumCriteria();
			ForumCriteria.Criteria criteria=forumCriteria.createCriteria();
			criteria.andPidEqualTo(PetUtil.getParameter(ClientRequest, "forumPid"));
			int pageNo=PetUtil.getParameterInteger(ClientRequest, "pageNo");
			int pageSize=PetUtil.getParameterInteger(ClientRequest, "pageSize");
			forumCriteria.setMysqlOffset((pageNo-1)*pageSize);
			forumCriteria.setMysqlLength(pageSize);
			String userid=PetUtil.getParameter(ClientRequest, "userId");
			
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
	 * @param ClientRequest
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
	
	

	/**
	 * 获取所有圈子(父级和子集)
	 * @param ClientRequest
	 * @return
	 */
	public Object getAllForum(ClientRequest ClientRequest){
		try {
			//获取所有父级
			ForumCriteria  forumCriteria=new ForumCriteria();
			ForumCriteria.Criteria criteria=forumCriteria.createCriteria();
			criteria.andPidIsNull();
			int pageNo=PetUtil.getParameterInteger(ClientRequest, "pageNo");
			int pageSize=PetUtil.getParameterInteger(ClientRequest, "pageSize");
			String userid=PetUtil.getParameter(ClientRequest, "userId");
			forumCriteria.setMysqlOffset((pageNo-1)*pageSize);
			forumCriteria.setMysqlLength(pageSize);
			forumCriteria.setOrderByClause("ct desc");
			List<Forum> forumlist=forumMapper.selectByExample(forumCriteria);
			
			//获取所有子集
			List<ForumsNote> notes=new ArrayList<ForumsNote>();
			for(Forum forum:forumlist ){
				
				String fforumid=forum.getId();
				ForumCriteria sfforumCriteria=new ForumCriteria();
				ForumCriteria.Criteria sfcriteria=sfforumCriteria.createCriteria();
				sfcriteria.andPidEqualTo(fforumid);
				List<Forum> forumstt=forumMapper.selectByExample(sfforumCriteria);
				for(Forum forum2:forumstt){
					ForumsNote forumsNote=new ForumsNote();
					forumsNote.setfFid(forum.getPid());
					forumsNote.setfForumName(forum.getName());
					forumsNote.setfDescript(forum.getDescript());
					forumsNote.setfDescript(forum.getDescript());
					forumsNote.setfLogoimg(forum.getLogoImg());
					
					
					forumsNote.setSid(forum2.getId());
					forumsNote.setsLogoimg(forum2.getLogoImg());
					forumsNote.setsDescript(forum2.getDescript());
					forumsNote.setsForumName(forum2.getName());
					//是否关注此圈子
					if(userForumRelService.isAttentionForum(userid, forum2.getId())>0){
						forumsNote.setIsAtt("1");
					}else{
						forumsNote.setIsAtt("0");
					}
					String NewNoteNum=bbsNoteCountService.getNewNoteNumByForumid(forum.getId());
					String NoteNum=bbsNoteCountService.getNoteNumByForumid(forum.getId());
					String NoteRelNum=bbsNoteCountService.getNoteRelNumByForumid(forum.getId());
					forumsNote.setsAllnoteCount(NoteNum);
					forumsNote.setsTodayNewNoteCount(NewNoteNum);
					forumsNote.setsAllnoteRelCount(NoteRelNum);
					notes.add(forumsNote);
				}
			}
			return notes;
		} catch (Exception e) {
			e.printStackTrace();
			return "getAllForumFail";
		}
		
	}
}
