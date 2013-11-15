package com.momoplan.pet.framework.petservice.bbs.vo;

import com.momoplan.pet.commons.domain.bbs.po.Forum;

public class ForumVo extends Forum {

	private static final long serialVersionUID = 1L;
	
	private String pname = "无";//父节点名

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

}
