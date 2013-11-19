<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageContent">

	<form method="post" action="${ctx }/petservice/report/serviceDictSave.html" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">

		<input type="hidden" name="id" value="${myForm.id }" />
		
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>别名：</label>
				<input type="text" name="alias" size="30" value="${myForm.alias }" class="required" />
			</div>	
			<div class="unit">
				<label>服务注册名：</label>
				<input type="text" name="service" size="30" value="${myForm.service }" class="required"/>
			</div>
			
			<div class="unit">
				<label>方法名：</label>
				<input type="text" name="methodValue" size="30" value="${myForm.method }" class="required"/>
			</div>
			<div class="unit">
				<label>提示：</label>
				<a target="_blank" href="https://github.com/cc14514/pet-service/wiki/_pages">服务注册名/方法名 详见wiki(点击打开)</a>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>
