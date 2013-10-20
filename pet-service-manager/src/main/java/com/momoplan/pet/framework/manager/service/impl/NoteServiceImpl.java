package com.momoplan.pet.framework.manager.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteMapper;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.framework.manager.service.NoteService;

@Service
public class NoteServiceImpl implements NoteService {

	@Resource
	private NoteMapper noteMapper = null;

	@Override
	public Note getNotebyid(String id) throws Exception {
		try {
			if ("" != id && null != id) {
				Note note = noteMapper.selectByPrimaryKey(id);
				return note;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void NoteAddOrUpdate(Note note) throws Exception {
		try {
			String id = note.getId();
			Note note2 = noteMapper.selectByPrimaryKey(id);
			if (note2 != null && !"".equals(note2.getId())) {
				note2.setEt(new Date());
				noteMapper.updateByPrimaryKeySelective(note2);
			} else {
				note.setId(IDCreater.uuid());
				note.setUserId("703");
				note.setCt(new Date());
				note.setEt(new Date());
				note.setForumId("92DE9E82807142A293107DFFC4368177");
				note.setIsDel(false);
				note.setIsEute(false);
				note.setIsTop(false);
				note.setState("0");
				note.setType("0");
				noteMapper.insertSelective(note);
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
	public void NoteDel(Note note) throws Exception{
		try {
			note.setIsDel(true);
			noteMapper.updateByPrimaryKeySelective(note);
		} catch (Exception e) {
		
			e.printStackTrace();
			
		}
	}

}
