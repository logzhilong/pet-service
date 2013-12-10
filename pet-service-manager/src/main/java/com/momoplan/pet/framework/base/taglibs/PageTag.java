package com.momoplan.pet.framework.base.taglibs;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.momoplan.pet.commons.FreeMarkerUtils;
import com.momoplan.pet.framework.base.vo.Page;


public class PageTag extends TagSupport {
	
	private static final long serialVersionUID = 1L;
	
	private String id=null;
	private String form=null;
	private int pageSize=-1;
	private Page<?> pageBean = null;
	private String styleNum = null;
	
	public String pageResCode(){
		if(pageSize==-1){
			pageSize = pageBean.getPageSize();
		}
		int pageNo = pageBean.getPageNo();
		int totalCount = pageBean.getTotalCount();
		int totalPages = totalCount/pageSize;
		if(totalCount%pageSize!=0){
			totalPages+=1;
		}
		pageBean.setPageSize(pageSize);
		Map<String,Object> root = new HashMap<String,Object>();
		Map<String,Object> pageData = new HashMap<String,Object>();
		pageData.put("idx", getId());
		pageData.put("pageNoIf", pageNo);
		pageData.put("pageNo", pageNo);
		pageData.put("pageSize", pageSize);
		pageData.put("pageList", pageBean.getData());
		pageData.put("nextPage", pageNo+1);
		pageData.put("prePage", pageNo-1);
		pageData.put("totalCount", totalCount);
		pageData.put("totalPagesIf", totalPages);
		pageData.put("totalPages", totalPages);
		pageData.put("form", getForm());
		root.put("pageData", pageData);
		FreeMarkerUtils fu = new FreeMarkerUtils("/template/taglibs/pageTag.ftl",root);
		String res = fu.getText();
		return res;
	}
	
	@Override
	public int doEndTag() throws JspException {
		try {
			String res = pageResCode();
			pageContext.getOut().write(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.doEndTag();
	}
	
	public String getId() {
		return id==null?UUID.randomUUID().toString().replaceAll("-", "").toUpperCase():id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}

	public Page<?> getPageBean() {
		return pageBean;
	}

	public void setPageBean(Page<?> pageBean) {
		this.pageBean = pageBean;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getStyleNum() {
		return styleNum;
	}

	public void setStyleNum(String styleNum) {
		this.styleNum = styleNum;
	}
	
}
