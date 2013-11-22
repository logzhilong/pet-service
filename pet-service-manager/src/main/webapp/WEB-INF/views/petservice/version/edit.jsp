<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageContent">

	<form method="post" action="${ctx }/petservice/version/save.html" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">

		<input type="hidden" name="id" value="${myForm.id }" />
		
		<div class="pageFormContent" layoutH="58">

			<div class="unit">
				<label>终端类型：</label>
				<input type="text" size="30" value="${myForm.phoneType }" readonly="readonly" />
			</div>	

			<div class="unit">
				<label>版本：</label>
				<input type="text" name="petVersion" size="30" value="${myForm.petVersion }" class="required"/>
			</div>
			
			<div class="unit">
				<label>IOSURL：</label>
				<input type="text" name="iosurl" size="30" value="${myForm.iosurl }" />
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
