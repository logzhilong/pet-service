package com.momoplan.pet.framework.manager.vo;

import java.util.List;

public class PageBean<T> {
	private int pageSize = 10;// 每页显示条数
	private int pageNo = 1; // 当前页
	private int totalPage; // 总页面
	private int totalRecorde; // 总记录数
	private int prep; // 当前页上一页
	private int nextp; // 当前页下一页
	private List<T> data;

	public PageBean() {
		super();
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getTotalPage() {
		if (this.getTotalRecorde() % this.getPageSize() == 0)
			this.totalPage = totalRecorde / pageSize;
		else
			this.totalPage = totalRecorde / pageSize + 1;
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalRecorde() {
		return totalRecorde;
	}

	public void setTotalRecorde(int totalRecorde) {
		this.totalRecorde = totalRecorde;
	}

	public int getPrep() {
		if (this.getPageNo() <= 1)
			this.setPrep(1);
		else
			this.setPrep(pageNo - 1);
		return prep;
	}

	public void setPrep(int prep) {
		this.prep = prep;
	}

	public int getNextp() {
		if (this.getPageNo() >= this.getTotalPage())
			this.setNextp(totalPage);
		else
			this.setNextp(pageNo + 1);
		return nextp;
	}

	public void setNextp(int nextp) {
		this.nextp = nextp;
	}

}
