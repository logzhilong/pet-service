<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageContent">
<form method="post" action="${ctx }/petservice/petcard/petcardSave.html" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
	
	<div class="pageFormContent" layoutH="58">
		
		<div class="unit">
			<label>数量：</label>
			<input type="text" name="number" size="15" value="${myForm.name }" class="required number"/>
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
