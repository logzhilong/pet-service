package com.momoplan.pet.framework.bbs.vo;

import java.io.Serializable;
import java.util.List;

public class PageBean <T> implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private long totalCount = 0;
	private int pageSize = 0;
	private int pageNo = 0;
	private List<T> data = null;
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
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
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
}
