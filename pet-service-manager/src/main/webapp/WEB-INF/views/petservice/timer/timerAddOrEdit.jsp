<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageContent">

	<form method="post" action="${ctx }/petservice/timer/timerSave.html" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
		<input type="hidden" name="id" value="${myForm.id }" />
		<input type="hidden" name="state" value="${myForm.state }" />
	
		<div class="pageFormContent" layoutH="58">

			<div class="unit">
				<label>来源：</label>
				<input type="text" size="50" value="${myForm.src }" readonly="readonly"/>
			</div>
			<div class="unit">
				<label>标题：</label>
				<input type="text" size="50" value="${myForm.name }" readonly="readonly"/>
			</div>
			<div class="unit">
				<label>计划日期：</label>
				<input type="text" name="at_str" class="date textInput readonly valid" value="<fmt:formatDate value="${myForm.at }" type="both" />" datefmt="yyyy-MM-dd HH:mm:ss" readonly="true">
				<a class="inputDateButton" href="javascript:;">选择</a>
			</div>
			
		</div>
		<div class="formBar">
			<ul>
				<c:if test="${empty errorMsg}">
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
				</c:if>
			</ul>
		</div>
	</form>
	
</div>
