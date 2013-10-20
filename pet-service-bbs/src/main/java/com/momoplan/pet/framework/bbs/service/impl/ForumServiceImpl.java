package com.momoplan.pet.framework.bbs.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.bbs.mapper.ForumMapper;
import com.momoplan.pet.commons.domain.bbs.mapper.UserForumRelMapper;
import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.commons.domain.bbs.po.ForumCriteria;
import com.momoplan.pet.commons.domain.bbs.po.UserForumRel;
import com.momoplan.pet.commons.domain.bbs.po.UserForumRelCriteria;
import com.momoplan.pet.commons.repository.bbs.NoteRepository;
import com.momoplan.pet.commons.repository.bbs.NoteSubRepository;
import com.momoplan.pet.framework.bbs.service.ForumService;
import com.momoplan.pet.framework.bbs.service.UserForumRelService;
import com.momoplan.pet.framework.bbs.vo.BbsNoteCount;
import com.momoplan.pet.framework.bbs.vo.ForumNode;
import com.momoplan.pet.framework.bbs.vo.ForumsNote;
@Service
public class ForumServiceImpl implements ForumService {

	private UserForumRelMapper userForumRelMapper=null;
	private ForumMapper forumMapper=null;
	private UserForumRelService userForumRelService=null;
	private NoteRepository noteRepository = null;
	private NoteSubRepository noteSubRepository = null;
	private MapperOnCache mapperOnCache = null;
	
	@Autowired
	public ForumServiceImpl(UserForumRelMapper userForumRelMapper, ForumMapper forumMapper, UserForumRelService userForumRelService,
			NoteRepository noteRepository, NoteSubRepository noteSubRepository, MapperOnCache mapperOnCache) {
		super();
		this.userForumRelMapper = userForumRelMapper;
		this.forumMapper = forumMapper;
		this.userForumRelService = userForumRelService;
		this.noteRepository = noteRepository;
		this.noteSubRepository = noteSubRepository;
		this.mapperOnCache = mapperOnCache;
	}

	private static Logger logger = LoggerFactory.getLogger(ForumServiceImpl.class);
	/**
	 * 获取所有父级圈子
	 * @param ClientRequest
	 * @return
	 */
	public Object getForumList(ClientRequest ClientRequest) throws Exception{
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
			throw e;
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
			return "getSunForumListByForumidFail";
		}
	}
	
	/**
	 * 根据id获取圈子
	 * @param ClientRequest
	 * @return
	 */
	public Forum getForumByid(Forum forum) throws Exception{
		try {
			if(StringUtils.isNotEmpty(forum.getId())){
				Forum res = mapperOnCache.selectByPrimaryKey(forum.getClass(), forum.getId());
				return res;
			}else{
				new Exception("参数不合法, forumId 为空");
			}
		} catch (Exception e) {
			logger.error("getForumByid",e);
			throw e;
		}
		return null;
	}
	
	/**
	 * add by liangc
	 * 构建一颗栏目树
	 * @param rootList
	 * @param pMap
	 * @param userForumRelMap
	 * @return
	 */
	public ForumNode buildTree(List<Forum> rootList,Map<String,List<Forum>> pMap,Map<String,String> userForumRelMap){
		ForumNode tree = new ForumNode();
		List<ForumNode> child = new ArrayList<ForumNode>();
		if(rootList!=null&&rootList.size()>0)
			for(Forum pnode : rootList){
				String pid = pnode.getId();//树根
				tree.setId(pnode.getId());
				tree.setName(pnode.getName());
				List<Forum> nList = pMap.get(pid);
				if(nList!=null&&nList.size()>0){
					List<ForumNode> no = new ArrayList<ForumNode>();
					for(Forum n : nList){
						ForumNode f = new ForumNode();
						f.setId(n.getId());
						f.setName(n.getName());//名字
						/**
						 * 是否关注：在字典里找不到则没有关注
						 */
						String userId = userForumRelMap.get(n.getId());
						if(StringUtils.isEmpty(userId)){
							f.setAtte(false);//未关注
						}else{
							f.setAtte(true);//关注
						}
						f.setLogoImg(n.getLogoImg());//图标
						
						String forumId = n.getId();
						//TODO : 叶子节点 当天总数
						Long totalToday = noteRepository.totalToday(forumId);
						//TODO : 叶子节点 总帖子数
						Long totalCount = noteRepository.totalCount(forumId);
						//TODO : 叶子节点 总回复数
						Long totalReply = noteSubRepository.totalCount(forumId);
						f.setTotalToday(totalToday);
						f.setTotalCount(totalCount);
						f.setTotalReply(totalReply);
						no.add(f);
					}
					tree.setChild(no);
				}
				pMap.remove(pid);
				child.add(buildTree(nList,pMap,userForumRelMap));
			}
		return tree;
	}
	
	/**
	 * add by liangc
	 * 获取所有栏目，以树的集合形式
	 */
	public List<ForumNode> getAllForumAsTree(String userId){
		ForumCriteria  forumCriteria=new ForumCriteria();
		//所有的圈子
		List<Forum> forumlist=forumMapper.selectByExample(forumCriteria);
		//我关注的圈子
		UserForumRelCriteria userForumRelCriteria = new UserForumRelCriteria();
		userForumRelCriteria.createCriteria().andUserIdEqualTo(userId);
		List<UserForumRel> userForumRelList = userForumRelMapper.selectByExample(userForumRelCriteria);
		//转换成 hash 结构，当作字典用
		Map<String,String> userForumRelMap = new HashMap<String,String>();
		if(userForumRelList!=null&&userForumRelList.size()>0)
		for(UserForumRel userForumRel : userForumRelList){
			userForumRelMap.put(userForumRel.getForumId(), userForumRel.getUserId());
		}
		//1、获取所有树根节点
		//else
		//2、以父节点为索引，分组所有节点
		List<Forum> rootList = new ArrayList<Forum>();
		Map<String,List<Forum>> pMap = new HashMap<String,List<Forum>>();
		for(Forum forum : forumlist){//构建基础数据结构
			//1
			if(StringUtils.isEmpty(forum.getPid())){
				rootList.add(forum);
			}else{
				//2
				List<Forum> group = pMap.get(forum.getPid());
				if(group==null)
					group = new ArrayList<Forum>();
				group.add(forum);
				pMap.put(forum.getPid(), group);
			}
		}
		logger.debug("ROOT: "+MyGson.getInstance().toJson(rootList));
		logger.debug("GROUP : "+MyGson.getInstance().toJson(pMap));
		List<ForumNode> treeList = new ArrayList<ForumNode>();
		//TODO 把我关注的圈子，放在最前面的一个元素里
		ForumNode node0 = getAtteForum(forumlist,userForumRelMap);
		treeList.add(node0);
		for(Forum root : rootList){
			List<Forum> r = new ArrayList<Forum>();
			r.add(root);
			ForumNode tree = buildTree(r,pMap,userForumRelMap);
			treeList.add(tree);
		}
		logger.debug("getAllForumAsTree : "+MyGson.getInstance().toJson(treeList));
		return treeList;
	}
	/**
	 * 我关注的圈子作为圈子树的第一个节点，只有一级关系
	 * @param forumlist
	 * @param userForumRelMap
	 * @return
	 */
	private ForumNode getAtteForum(List<Forum> forumlist,Map<String,String> userForumRelMap){
		ForumNode node0 = new ForumNode();
		node0.setAtte(true);
		node0.setId("0");
		node0.setName("我关注的圈子");
		List<ForumNode> no = new ArrayList<ForumNode>();
		for(Forum n : forumlist){
			String forumId = n.getId();
			String userId = userForumRelMap.get(forumId);
			if(StringUtils.isNotEmpty(userId)){
				ForumNode f = new ForumNode();
				f.setId(n.getId());
				f.setName(n.getName());//名字
				f.setAtte(true);//关注
				f.setLogoImg(n.getLogoImg());//图标
				//TODO : 叶子节点 当天总数
				Long totalToday = noteRepository.totalToday(forumId);
				//TODO : 叶子节点 总帖子数
				Long totalCount = noteRepository.totalCount(forumId);
				//TODO : 叶子节点 总回复数
				Long totalReply = noteSubRepository.totalCount(forumId);
				f.setTotalToday(totalToday);
				f.setTotalCount(totalCount);
				f.setTotalReply(totalReply);
				no.add(f);
			}
		}
		node0.setChild(no);
		return node0;
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
					String forumId = forum.getId();
					//圈子当天总帖数
					Long totalToday = noteRepository.totalToday(forumId);
					//圈子的总帖子数
					Long totalCount = noteRepository.totalCount(forumId);
					//圈子的总回复数
					Long totalReply = noteSubRepository.totalCount(forumId);
					
					forumsNote.setTotalToday(totalToday);
					forumsNote.setTotalCount(totalCount);
					forumsNote.setTotalReply(totalReply);
					
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
