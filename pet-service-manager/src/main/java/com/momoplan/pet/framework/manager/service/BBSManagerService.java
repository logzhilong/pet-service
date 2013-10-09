package com.momoplan.pet.framework.manager.service;

import java.util.List;

import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.framework.manager.vo.PageBean;


public interface BBSManagerService {
	
	/**
	 * 圈子列表
	 * @return
	 * @throws Exception
	 */
	public PageBean<Forum> listForum(PageBean<Forum> pageBean , Forum vo) throws Exception;
	/**
	 * 增加圈子或者修改圈子
	 * @param forum
	 * @throws Exception
	 */
	public int addOrUpdateForum(Forum forum)throws Exception;
	/**
	 * 根据id删除圈子
	 * @param forum
	 * @throws Exception
	 */
	public void DelForum(Forum forum)throws Exception;
	/**
	 * 根据id获取圈子
	 * @param forum
	 * @throws Exception
	 */
	public Forum getForumbyid(Forum forum)throws Exception;
	/**
	 * 获取所有圈子(做级联)
	 * @param forum
	 * @return
	 * @throws Exception
	 */
	public List<Forum> getForumlist()throws Exception;
	
}
