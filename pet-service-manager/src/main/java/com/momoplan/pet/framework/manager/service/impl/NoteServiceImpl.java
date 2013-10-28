package com.momoplan.pet.framework.manager.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteMapper;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.framework.manager.service.NoteService;

@Service
public class NoteServiceImpl implements NoteService {
	Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);
	@Resource
	private NoteMapper noteMapper = null;
	/**
	 * 根据id获取帖子
	 */
	@Override
	public Note getNotebyid(String id) throws Exception {
		try {
			if ("" != id && null != id) {
				Note note = noteMapper.selectByPrimaryKey(id);
				logger.debug("跟据id为"+id.toString()+"获取实体为"+note.toString());
				return note;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 增加或者修改帖子
	 */
	@Override
	public void NoteAddOrUpdate(Note note) throws Exception {
		try {
			String id = note.getId();
			Note note2 = noteMapper.selectByPrimaryKey(id);
			if (note2 != null && !"".equals(note2.getId())) {
				note2.setEt(new Date());
				logger.debug("修该帖子"+note2.toString());
				noteMapper.updateByPrimaryKeySelective(note);
			} else {
				note.setId(IDCreater.uuid());
				note.setUserId("703");
				note.setCt(new Date());
				note.setEt(new Date());
				note.setIsDel(false);
				note.setIsEute(false);
				note.setIsTop(false);
				note.setState("0");
				note.setType("0");
				noteMapper.insertSelective(note);
				logger.debug("增加帖子"+note.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除帖子
	 * @param note
	 * @throws Exception
	 */
	@Override
	public void NoteDel(Note note) throws Exception{
		try {
			note.setIsDel(true);
			noteMapper.updateByPrimaryKeySelective(note);
			logger.debug("删除帖子"+note.toString());
		} catch (Exception e) {
		
			e.printStackTrace();
			
		}
	}

	/**
	 * 更新帖子点击数
	 * @param ClientRequest
	 * @return
	 */
	public void updateClickCount(String noteid){
		try {
			Note note=noteMapper.selectByPrimaryKey(noteid);
			
				if(note.equals(null)){
				}else{
					note.setClientCount(note.getClientCount()+1);
					note.setEt(new Date());
					logger.debug("更新帖子:"+note.toString()+"点击数:");
					noteMapper.updateByPrimaryKey(note);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
