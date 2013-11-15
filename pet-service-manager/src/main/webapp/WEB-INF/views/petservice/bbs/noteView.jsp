<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageContent">

	<form method="post" action="${ctx }/petservice/bbs/noteSave.html" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
		<input type="hidden" name="id" value="${myForm.id }" />
		<input type="hidden" name="forumId" value="${myForm.forumId }" />
	
		<div class="pageFormContent" layoutH="58">

			<div class="unit">
				<label>发帖人：</label>
				<input type="text" name="name" size="80" value="${myForm.nickname }" readonly="readonly"/>
			</div>

			<div class="unit">
				<label>标题：</label>
				<input type="text" name="name" size="80" value="${myForm.name }" readonly="readonly"/>
			</div>
			
			<div class="unit">
				<textarea class="editor" rows="30" cols="70" tools="mini" readonly="readonly" >
					<c:out value="${myForm.content }" />
				</textarea>	
			</div>
			
		</div>
		<div class="formBar">
<%--
			<ul>
				<c:if test="${empty errorMsg}">
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
				</c:if>
			</ul>
--%>
		</div>
	</form>
	
</div>
