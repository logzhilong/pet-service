package com.momoplan.pet.framework.bbs.service;

import java.util.List;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.framework.bbs.vo.Action;
import com.momoplan.pet.framework.bbs.vo.NoteVo;

public interface NoteService {

	/**
	 * 发送帖子
	 * @param note
	 * userId用户id,forumId圈子id,name帖子名称,content帖子内容
	 * @return
	 * @throws Exception
	 */
	public Object sendNote(Note note) throws Exception;
 
	/**
	 * 查看帖子详情
	 * @param 帖子id
	 * @return
	 * @throws Exception
	 */
	public Object detailNote(String id) throws Exception;

	/**
	 * 删除帖子
	 * 
	 * @param ClientRequest
	 * @return
	 */
	public Object delNote(ClientRequest ClientRequest) throws Exception ;


	/**
	 * 根据id举报帖子
	 * @param noteid帖子id
	 * @return
	 * @throws Exception
	 */
	public Object reportNote(String noteid) throws Exception;
	
	
	
	/**
	 * 更新帖子点击数
	 * @param ClientRequest
	 * @return
	 */
	public Object updateClickCount(ClientRequest ClientRequest) throws Exception;
	
	/**
	 * 我发表过的帖子列表
	 * 
	 */
	public Object getMyNotedListByuserid(ClientRequest ClientRequest) throws Exception;
	
	/**
	 * 获取帖子列表
	 * @param forumid 圈子ID
	 * @param action 动作 "action":"ALL 全部；EUTE 精华；NEW_ET 最新回复；NEW_CT 最新发布；SEARCH 查询"
	 * @param condition 查询条件
	 * @param withTop 是否带置顶
	 * @param pageno 
	 * @param pagesize
	 * @return
	 * @throws Exception
	 */
	public List<NoteVo> getNoteList(String forumid,Action action,String condition,boolean withTop,int pageno,int pagesize) throws Exception;
	
}
