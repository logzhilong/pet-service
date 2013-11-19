package com.momoplan.pet.framework.petservice.report.vo;

import com.momoplan.pet.commons.domain.manager.po.MgrServiceDict;

public class MgrServiceDictVo extends MgrServiceDict {

	private static final long serialVersionUID = 1L;
	/**
	 * method 这个属性在 html 里是关键字，所以这里要换一个名字接收
	 */
	private String methodValue = null;

	public String getMethodValue() {
		return methodValue;
	}
	public void setMethodValue(String methodValue) {
		this.methodValue = methodValue;
	}

}