package com.momoplan.pet.framework.manager.vo;

import java.util.List;

public class PageBean<T> {
	
	private List<T> data;
	
	private int pageSize,pageNo,totalCount;
	
	public PageBean() {
		super();
	}

	public PageBean(int pageSize, int pageNo, int totalCount) {
		super();
		this.pageSize = pageSize;
		this.pageNo = pageNo;
		this.totalCount = totalCount;
	}

	public PageBean(List<T> data, int pageSize, int pageNo, int totalCount) {
		super();
		this.data = data;
		this.pageSize = pageSize;
		this.pageNo = pageNo;
		this.totalCount = totalCount;
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

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
}
