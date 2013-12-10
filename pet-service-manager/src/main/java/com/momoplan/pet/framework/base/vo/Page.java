package com.momoplan.pet.framework.base.vo;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {
	private int pageSize = 20;// 每页显示条数
	private int pageNo = 1; // 当前页
	private int totalCount = 0;
	private List<T> data = new ArrayList<T>();

	
	
	public Page(int pageSize, int pageNo) {
		super();
		this.pageSize = pageSize;
		this.pageNo = pageNo;
	}

	public Page() {
		super();
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

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
