package com.momoplan.pet.framework.bbs.service;

import java.util.List;

import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.framework.bbs.vo.Action;
import com.momoplan.pet.framework.bbs.vo.ConditionType;
import com.momoplan.pet.framework.bbs.vo.NoteVo;

public interface NoteService {

	/**
	 * 查看帖子详情
	 * @param 帖子id
	 * @return
	 * @throws Exception
	 */
	public NoteVo getNoteById(String id) throws Exception;

	/**
	 * 更新帖子点击数
	 * @param ClientRequest
	 * @return
	 */
	public void updateClickCount(String noteId) throws Exception;
	
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
	public List<NoteVo> getNoteList(String forumid,Action action,String condition,ConditionType conditionType,boolean withTop,int pageno,int pagesize) throws Exception;

	/**
	 * 发送帖子
	 * @param note
	 * userId用户id,forumId圈子id,name帖子名称,content帖子内容
	 * @return
	 * @throws Exception
	 */
	public String sendNote(Note note) throws Exception;

}
