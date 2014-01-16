package com.momoplan.pet.framework.base.taglibs;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


public class Output extends TagSupport {
	
	private static final long serialVersionUID = 1L;
	
	private String value=null;
	private Integer len=50;
	
	@Override
	public int doEndTag() throws JspException {
		try {
			int l = value.length();
			if(len!=null&&(l+3)>len){
				value = value.substring(0, len)+"...";
			}
			pageContext.getOut().write(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getLen() {
		return len;
	}

	public void setLen(Integer len) {
		this.len = len;
	}
}
