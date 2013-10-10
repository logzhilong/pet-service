package com.momoplan.pet.framework.manager.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.domain.bbs.mapper.ForumMapper;
import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.commons.domain.bbs.po.ForumCriteria;
import com.momoplan.pet.framework.manager.service.BBSManagerService;
import com.momoplan.pet.framework.manager.vo.PageBean;


@Service
public class BBSManagerServiceImpl implements BBSManagerService {
	private Logger logger = LoggerFactory.getLogger(CommonDataManagerServiceImpl.class);

	@Autowired
	private ForumMapper forumMapper = null;
	
	@Override
	public PageBean<Forum> listForum(PageBean<Forum> pageBean , Forum vo) throws Exception {
		String id = vo.getId();
		ForumCriteria forumCriteria = new ForumCriteria();
		ForumCriteria.Criteria criteria = forumCriteria.createCriteria();
		if(StringUtils.isEmpty(id)){
			criteria.andPidIsNull();
		}else{
			criteria.andPidEqualTo(id);
		}
		int totalCount = forumMapper.countByExample(forumCriteria);
		forumCriteria.setMysqlOffset((pageBean.getPageNo()-1)*pageBean.getPageSize());
		forumCriteria.setMysqlLength(pageBean.getPageSize());
		List<Forum> list = forumMapper.selectByExample(forumCriteria);
		pageBean.setData(list);
		pageBean.setTotalRecorde(totalCount);
		return pageBean;
	}
	/**
	 * 增加圈子或者修改圈子
	 * @param forum
	 * @throws Exception
	 */
	public int addOrUpdateForum(Forum forum)throws Exception{
		String id = forum.getId();
		Forum fo = forumMapper.selectByPrimaryKey(id);
		if(fo!=null&&!"".equals(fo.getId())){
			logger.debug("selectByPK.po="+fo.toString());
			return forumMapper.updateByPrimaryKeySelective(forum);
		}else{
			forum.setId(IDCreater.uuid());
			forum.setCt(new Date());
			if("all".equals(forum.getPid())){
				forum.setPid(null);
			}
			return forumMapper.insertSelective(forum);
		}
	}
	/**
	 * 根据id删除圈子
	 * @param forum
	 * @throws Exception
	 */
	public void DelForum(Forum forum)throws Exception{
		try {
			ForumCriteria forumCriteria=new ForumCriteria();
			ForumCriteria.Criteria criteria=forumCriteria.createCriteria();
			criteria.andPidEqualTo(forum.getId());
			List<Forum> forums=forumMapper.selectByExample(forumCriteria);
			if(forums.size()>0){
				for(Forum fo:forums){
					forumMapper.deleteByPrimaryKey(fo.getId());
				}
			}
			forumMapper.deleteByPrimaryKey(forum.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据id获取圈子
	 * @param forum
	 * @throws Exception
	 */
	public Forum getForumbyid(Forum forum)throws Exception{
		try {
				
			return forumMapper.selectByPrimaryKey(forum.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 获取圈子集合(做级联)
	 * @param forum
	 * @return
	 * @throws Exception
	 */
	public List<Forum> getForumlist()throws Exception{
		try {
			ForumCriteria forumCriteria=new ForumCriteria();
			ForumCriteria.Criteria criteria=forumCriteria.createCriteria();
			criteria.andPidIsNull();
			List<Forum> forums=forumMapper.selectByExample(forumCriteria);
			return forums;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
