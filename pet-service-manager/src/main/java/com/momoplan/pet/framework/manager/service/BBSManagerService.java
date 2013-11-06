package com.momoplan.pet.framework.manager.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.framework.manager.vo.PageBean;


public interface BBSManagerService {
	/**
	 * 获取圈子列表
	 * @param pageBean将数据传入pb,做分页
	 * @param vo圈子实体
	 * @return
	 * @throws Exception
	 */
	public PageBean<Forum> listForum(PageBean<Forum> pageBean , Forum vo) throws Exception;
	/**
	 * 增加圈子 或  修改圈子
	 * @param forum
	 * @throws Exception
	 */
	public int addOrUpdateForum(Forum forum,HttpServletRequest request)throws Exception;
	/**
	 * 根据id删除圈子
	 * @param forum圈子对象
	 * @throws Exception
	 */
	public void DelForum(Forum forum)throws Exception;
	/**
	 * 根据id获取圈子
	 * @param forum圈子对象
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
	/**
	 * 根据父圈子获取子圈集合
	 * @param forum圈子对象
	 * @return
	 * @throws Exception
	 */
	public List<Forum> getSunForumListbyPid(Forum forum)throws Exception;
	/**
	 * 根据子集圈子id获取子集帖子集合(父级圈子没有帖子)
	 * @param forum
	 * @return
	 * @throws Exception
	 */
	public List<Note> getAllNotesByForumId(Forum forum)throws Exception;
	
}
