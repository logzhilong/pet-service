package com.momoplan.pet.framework.bbs.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.domain.bbs.mapper.UserForumRelMapper;
import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.commons.domain.bbs.po.UserForumRel;
import com.momoplan.pet.commons.domain.bbs.po.UserForumRelCriteria;
import com.momoplan.pet.commons.repository.bbs.NoteRepository;
import com.momoplan.pet.commons.repository.bbs.NoteSubRepository;
import com.momoplan.pet.framework.bbs.service.ForumService;
import com.momoplan.pet.framework.bbs.service.UserForumRelService;
import com.momoplan.pet.framework.bbs.vo.BbsNoteCount;
@Service
public class UserForumRelServiceImpl implements UserForumRelService {
	@Resource
	private UserForumRelMapper userForumRelMapper=null;
	@Resource
	private ForumService forumService=null;
	@Autowired
	private NoteRepository noteRepository = null;
	@Autowired
	private NoteSubRepository noteSubRepository = null;
	/**
	 * 
	 * 退出圈子
	 * @return
	 */
	@Override
	public Object quitForum(ClientRequest ClientRequest){
		try {
			UserForumRelCriteria userForumRelCriteria=new UserForumRelCriteria();
			UserForumRelCriteria.Criteria criteria=userForumRelCriteria.createCriteria();
			String forumid=PetUtil.getParameter(ClientRequest, "forumid");
			criteria.andIdEqualTo(forumid);
			userForumRelMapper.deleteByExample(userForumRelCriteria);
			return "quitForumSuccss";
		} catch (Exception e) {
			e.printStackTrace();
			return "quitForumFail";
		}
	}
	
	/**
	 * 关注圈子
	 * @return
	 */
	@Override
	public Object attentionForum(ClientRequest ClientRequest){
		try {
			UserForumRel userForumRel=new UserForumRel();
			userForumRel.setId(IDCreater.uuid());
			userForumRel.setUserId(PetUtil.getParameter(ClientRequest, "userId"));
			userForumRel.setForumId(PetUtil.getParameter(ClientRequest, "forumid"));
			userForumRelMapper.insertSelective(userForumRel);
			return "attentionForumSuccess";
		} catch (Exception e) {
			e.printStackTrace();
			return "attentionForumFail";
		}
	}
	
	/**
	 *我关注的圈子
	 * @param ClientRequest
	 * @return
	 */
	public Object getUserForumListbyUserid(ClientRequest ClientRequest){
		try {
			UserForumRelCriteria userForumRelCriteria=new UserForumRelCriteria();
			
			int pageNo=PetUtil.getParameterInteger(ClientRequest, "pageNo");
			int pageSize=PetUtil.getParameterInteger(ClientRequest, "pageSize");
			userForumRelCriteria.setMysqlOffset((pageNo-1)*pageSize);
			userForumRelCriteria.setMysqlLength(pageSize);
			
			UserForumRelCriteria.Criteria criteria=userForumRelCriteria.createCriteria();
			criteria.andUserIdEqualTo(PetUtil.getParameter(ClientRequest,"userId"));
			List<UserForumRel> userForumRels=userForumRelMapper.selectByExample(userForumRelCriteria);
			List<Forum> forums=new ArrayList<Forum>();
			for(UserForumRel forumRel:userForumRels){
				Forum forum=new Forum();
				forum.setId(forumRel.getForumId());
				Forum forum2=forumService.getForumByid(forum);
				forums.add(forum2);
			}
			
			
			List<BbsNoteCount> noteCounts=new ArrayList<BbsNoteCount>();
			for(Forum forum:forums){
				BbsNoteCount noteCount=new BbsNoteCount();
				noteCount.setId(forum.getId());
				
				
				String forumId = forum.getId();
				//圈子当天总帖数
				Long totalToday = noteRepository.totalToday(forumId);
				//圈子的总帖子数
				Long totalCount = noteRepository.totalCount(forumId);
				//圈子的总回复数
				Long totalReply = noteSubRepository.totalCount(forumId);
				
				noteCount.setTotalToday(totalToday);
				noteCount.setTotalCount(totalCount);
				noteCount.setTotalReply(totalReply);
				
				noteCount.setName(forum.getName());
				noteCount.setImgId(forum.getLogoImg());
				noteCounts.add(noteCount);
			}
			
			return noteCounts;
		} catch (Exception e) {
			e.printStackTrace();
			return "getUserForumListbyUseridFail";
		}
		
	}
	/**
	 * 根据用户id和圈子id查看是否关注该圈子
	 * @param ClientRequest
	 * @return
	 */
	public int isAttentionForum(String uid,String fid){
		UserForumRelCriteria userForumRelCriteria=new UserForumRelCriteria();
		UserForumRelCriteria.Criteria criteria=userForumRelCriteria.createCriteria();
		criteria.andUserIdEqualTo(uid);
		criteria.andForumIdEqualTo(fid);
		List<UserForumRel> forumRels=userForumRelMapper.selectByExample(userForumRelCriteria);
		
		return forumRels.size();
	}
}
