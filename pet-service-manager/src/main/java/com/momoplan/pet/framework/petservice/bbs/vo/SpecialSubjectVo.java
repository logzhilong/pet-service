package com.momoplan.pet.framework.petservice.bbs.vo;

import com.momoplan.pet.commons.domain.bbs.po.SpecialSubject;

public class SpecialSubjectVo extends SpecialSubject {
	
	private static final long serialVersionUID = 1L;

	private NoteVo note = new NoteVo();
	
	private String navTabId = null;
	
	public String getNavTabId() {
		return navTabId;
	}

	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}

	public NoteVo getNote() {
		return note;
	}

	public void setNote(NoteVo note) {
		this.note = note;
	}
	
}
