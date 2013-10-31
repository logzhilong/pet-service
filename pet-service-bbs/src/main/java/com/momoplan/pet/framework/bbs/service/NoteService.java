package com.momoplan.pet.framework.bbs.service;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.domain.bbs.po.Note;

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
	  * 根据帖子name搜索
	  * @param note
	  * forumid圈子id(如果为0标识全站搜索否则为某圈子内搜索),notename帖子名称
	  * @param pageNo
	  * @param pageSize
	  * @return
	  * @throws Exception
	  */
	public Object searchNote(Note note,int pageNo,int pageSize) throws Exception;

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
	 * 某圈子今日新增帖子列表
	 * @param fid圈子id(fid为0则是全站搜索,否则某圈子内部搜索)
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Object getTodayNewNoteListByFid(String fid,int pageNo,int pageSize)throws Exception;
	/**
	 * 某圈子最新帖子
	 * @param forumid圈子id(如果为0 则全站搜索否则圈子内部搜索)
	 * @param pageno
	 * @param pagesize
	 * @return
	 * @throws Exception
	 */
	public Object newNoteByFid(String forumid,int pageno,int pagesize) throws Exception;
	/**
	 * 获取精华
	 * @param fid圈子id(为0则全站搜索否则某圈子内搜索)
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Object getEuteNoteList(String fid,int pageNo,int pageSize) throws Exception;
	
	/**
	 * 获取最新回复
	 * @param fid圈子id(为0则全站否走某圈子内搜索)
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Object getNewReplysByReplyct(String fid,int pageNo,int pageSize) throws Exception;
	
}
