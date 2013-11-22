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

import com.momoplan.pet.commons.domain.bbs.mapper.ForumMapper;
import com.momoplan.pet.commons.domain.bbs.mapper.UserForumConditionMapper;
import com.momoplan.pet.commons.domain.bbs.mapper.UserForumRelMapper;
import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.commons.domain.bbs.po.ForumCriteria;
import com.momoplan.pet.commons.domain.bbs.po.UserForumCondition;
import com.momoplan.pet.commons.domain.bbs.po.UserForumConditionCriteria;
import com.momoplan.pet.commons.domain.bbs.po.UserForumRel;
import com.momoplan.pet.commons.domain.bbs.po.UserForumRelCriteria;
import com.momoplan.pet.commons.repository.bbs.NoteRepository;
import com.momoplan.pet.commons.repository.bbs.NoteSubRepository;
import com.momoplan.pet.framework.bbs.service.ForumService;
import com.momoplan.pet.framework.bbs.vo.ForumNode;

@Service
public class ForumServiceImpl implements ForumService {

	private static Logger logger = LoggerFactory.getLogger(ForumServiceImpl.class);

	private UserForumRelMapper userForumRelMapper = null;
	private ForumMapper forumMapper = null;
	private NoteRepository noteRepository = null;
	private NoteSubRepository noteSubRepository = null;
	private UserForumConditionMapper userForumConditionMapper = null;
	
	@Autowired
	public ForumServiceImpl(UserForumRelMapper userForumRelMapper,
			ForumMapper forumMapper, NoteRepository noteRepository,
			NoteSubRepository noteSubRepository,
			UserForumConditionMapper userForumConditionMapper) {
		super();
		this.userForumRelMapper = userForumRelMapper;
		this.forumMapper = forumMapper;
		this.noteRepository = noteRepository;
		this.noteSubRepository = noteSubRepository;
		this.userForumConditionMapper = userForumConditionMapper;
	}
	
	
	
	/**
	 * add by liangc 构建一颗栏目树
	 * 
	 * @param rootList
	 * @param pMap
	 * @param userForumRelMap
	 * @return
	 */
	private ForumNode buildTree(List<Forum> rootList, Map<String, List<Forum>> pMap, Map<String, String> userForumRelMap) throws Exception{
		ForumNode tree = new ForumNode();
		List<ForumNode> child = new ArrayList<ForumNode>();
		if (rootList != null && rootList.size() > 0)
			for (Forum pnode : rootList) {
				String pid = pnode.getId();// 树根
				tree.setId(pnode.getId());
				tree.setName(pnode.getName());
				List<Forum> nList = pMap.get(pid);
				if (nList != null && nList.size() > 0) {
					List<ForumNode> no = new ArrayList<ForumNode>();
					for (Forum n : nList) {
						ForumNode f = new ForumNode();
						f.setId(n.getId());
						f.setName(n.getName());// 名字
						/**
						 * 是否关注：在字典里找不到则没有关注
						 */
						String userId = userForumRelMap.get(n.getId());
						if (StringUtils.isEmpty(userId)) {
							f.setAtte(false);// 未关注
						} else {
							f.setAtte(true);// 关注
						}
						f.setLogoImg(n.getLogoImg());// 图标

						String forumId = n.getId();
						// 当天总数
						Long totalToday = noteRepository.totalToday(forumId);
						// 总帖子数
						Long totalCount = noteRepository.totalCount(forumId);
						// 总回复数
						Long totalReply = noteSubRepository.totalCount(forumId);
						//被关注数
						Long totalAtte = getTotalAtte(forumId);
						f.setTotalAtte(totalAtte);
						f.setTotalToday(totalToday);
						f.setTotalCount(totalCount);
						f.setTotalReply(totalReply);
						no.add(f);
					}
					tree.setChild(no);
				}
				pMap.remove(pid);
				child.add(buildTree(nList, pMap, userForumRelMap));
			}
		return tree;
	}

	public Map<String, String> getUserForumMap(String userId){
		UserForumRelCriteria userForumRelCriteria = new UserForumRelCriteria();
		userForumRelCriteria.createCriteria().andUserIdEqualTo(userId);
		List<UserForumRel> userForumRelList = userForumRelMapper.selectByExample(userForumRelCriteria);
		logger.debug("转换成 hash 结构，当作字典用");
		Map<String, String> userForumRelMap = new HashMap<String, String>();
		if (userForumRelList != null && userForumRelList.size() > 0){
			for (UserForumRel userForumRel : userForumRelList) {
				userForumRelMap.put(userForumRel.getForumId(), userForumRel.getUserId());
			}
			return userForumRelMap;
		}
		return null;
	}
	
	/**
	 * add by liangc 获取所有栏目，以树的集合形式
	 */
	public List<ForumNode> getAllForumAsTree(String userId) throws Exception{
		ForumCriteria forumCriteria = new ForumCriteria();
		forumCriteria.setOrderByClause("seq asc");
		logger.debug(" 所有的圈子，因为圈子不会经常被创建，所以这些圈子可以放入缓存，并随着创建和删除圈子，进行更新");
		List<Forum> forumlist = forumMapper.selectByExample(forumCriteria);
		
		logger.debug("我关注的圈子");
		Map<String, String> userForumRelMap = getUserForumMap(userId); 
		// 1、获取所有树根节点
		// else
		// 2、以父节点为索引，分组所有节点
		List<Forum> rootList = new ArrayList<Forum>();
		Map<String, List<Forum>> pMap = new HashMap<String, List<Forum>>();
		logger.debug("构建基础数据结构");
		for (Forum forum : forumlist) {
			// 1
			if (StringUtils.isEmpty(forum.getPid())) {
				rootList.add(forum);
			} else {
				// 2
				List<Forum> group = pMap.get(forum.getPid());
				if (group == null)
					group = new ArrayList<Forum>();
				group.add(forum);
				pMap.put(forum.getPid(), group);
			}
		}
		
		List<ForumNode> treeList = new ArrayList<ForumNode>();
		logger.debug("//把我关注的圈子，放在最前面的一个元素里");
		ForumNode node0 = getAtteForum(forumlist, userForumRelMap);
		treeList.add(node0);
		for (Forum root : rootList) {
			List<Forum> r = new ArrayList<Forum>();
			r.add(root);
			ForumNode tree = buildTree(r, pMap, userForumRelMap);
			treeList.add(tree);
		}
		if(treeList!=null)
			logger.debug("getAllForumAsTree : " + treeList.size());
		return treeList;
	}

	/**
	 * 我关注的圈子作为圈子树的第一个节点，只有一级关系
	 * 
	 * @param forumlist
	 * @param userForumRelMap
	 * @return
	 */
	private ForumNode getAtteForum(List<Forum> forumlist, Map<String, String> userForumRelMap) throws Exception{
		ForumNode node0 = new ForumNode();
		node0.setAtte(true);
		node0.setId("0");
		node0.setName("我关注的圈子");
		List<ForumNode> no = new ArrayList<ForumNode>();
		for (Forum n : forumlist) {
			String forumId = n.getId();
			String userId = userForumRelMap.get(forumId);
			if (StringUtils.isNotEmpty(userId)) {
				ForumNode f = new ForumNode();
				f.setId(n.getId());
				f.setName(n.getName());// 名字
				f.setAtte(true);// 关注
				f.setLogoImg(n.getLogoImg());// 图标
				// TODO : 叶子节点 当天总数
				Long totalToday = noteRepository.totalToday(forumId);
				// TODO : 叶子节点 总帖子数
				Long totalCount = noteRepository.totalCount(forumId);
				// TODO : 叶子节点 总回复数
				Long totalReply = noteSubRepository.totalCount(forumId);
				//被关注数
				Long totalAtte = getTotalAtte(forumId);
				f.setTotalAtte(totalAtte);
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
	 * 获取被关注总数
	 * @param forumId
	 * @return
	 */
	private Long getTotalAtte(String forumId){
		//TODO 稍后挪到缓存中实现
		UserForumRelCriteria userForumRelCriteria = new UserForumRelCriteria();
		userForumRelCriteria.createCriteria().andForumIdEqualTo(forumId);
		int count = userForumRelMapper.countByExample(userForumRelCriteria);
		logger.debug(forumId+" 被关注数 : "+count);
		return Long.valueOf(count);
	}

	@Override
	public List<UserForumCondition> getUserForumCondition() throws Exception {
		UserForumConditionCriteria userForumConditionCriteria = new UserForumConditionCriteria();
		List<UserForumCondition> list = userForumConditionMapper.selectByExample(userForumConditionCriteria);
		logger.debug("获取被推荐的圈子列表，供新注册用户关注 list="+list);
		return list;
	}

	@Override
	public Forum getForum(String petType) throws Exception {
		ForumCriteria forumCriteria = new ForumCriteria();
		forumCriteria.createCriteria().andTypeEqualTo(petType);
		List<Forum> list = forumMapper.selectByExample(forumCriteria);
		if(list!=null&&list.size()>0)
			return list.get(0);
		return null;
	}
	
}
