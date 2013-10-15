package com.momoplan.pet.framework.bbs.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.domain.bbs.mapper.ForumMapper;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteMapper;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteSubMapper;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.bbs.po.NoteCriteria;
import com.momoplan.pet.commons.domain.bbs.po.NoteSub;
import com.momoplan.pet.commons.domain.bbs.po.NoteSubCriteria;
import com.momoplan.pet.framework.bbs.service.BbsNoteCountService;

@Service
public class BbsNoteCountServiceImpl implements BbsNoteCountService {
	@Resource
	ForumMapper forumMapper = null;
	@Resource
	NoteMapper noteMapper = null;
	@Resource
	NoteSubMapper noteSubMapper=null;
	/**
	 * 某圈子今日新增帖子数
	 */
	public String getNewNoteNumByForumid(String fid) {
		NoteCriteria noteCriteria=new NoteCriteria();
		NoteCriteria.Criteria criteria=noteCriteria.createCriteria();
		criteria.andForumIdEqualTo(fid);

		Calendar currentDate = new GregorianCalendar();   
		currentDate.set(Calendar.HOUR_OF_DAY, 0);  
		currentDate.set(Calendar.MINUTE, 0);  
		currentDate.set(Calendar.SECOND, 0);  
		criteria.andCtGreaterThanOrEqualTo(((Date)currentDate.getTime().clone()));
		
		criteria.andIsDelEqualTo(false);
		criteria.andTypeEqualTo("0");
		List<Note> notes=noteMapper.selectByExample(noteCriteria);
		return String.valueOf(notes.size());
	}

	/**
	 * 某圈子所有帖子数
	 */
	public String getNoteNumByForumid(String fid) {
		NoteCriteria noteCriteria=new NoteCriteria();
		NoteCriteria.Criteria criteria=noteCriteria.createCriteria();
		criteria.andForumIdEqualTo(fid);
		criteria.andIsDelEqualTo(false);
		criteria.andTypeEqualTo("0");
		List<Note> notes=noteMapper.selectByExample(noteCriteria);
		return String.valueOf(notes.size());
	}

	/**
	 * 某圈子所有回复数
	 */
	public String getNoteRelNumByForumid(String fid) {
		int count=0;
		NoteCriteria noteCriteria=new NoteCriteria();
		NoteCriteria.Criteria criteria=noteCriteria.createCriteria();
		criteria.andForumIdEqualTo(fid);
		List<Note> notelist = noteMapper.selectByExample(noteCriteria);
		for(Note note:notelist){
			NoteSubCriteria noteSubCriteria=new NoteSubCriteria();
			NoteSubCriteria.Criteria criteriaw=noteSubCriteria.createCriteria();
			criteriaw.andNoteIdEqualTo(note.getId());
			List<NoteSub> noteSubs=noteSubMapper.selectByExample(noteSubCriteria);
			count=noteSubs.size()+count;
		}
		return String.valueOf(count);
	}

}
