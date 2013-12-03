<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageContent">

	<form method="post" action="${ctx }/petservice/notice/noticeSave.html" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
	
		<input type="hidden" name="id" value="${myForm.id }" />
	
		<div class="pageFormContent" layoutH="58">
			
			<div class="unit">
				<label>标题：</label>
				<input type="text" name="name" size="30" value="${myForm.name }" class="required"/>
			</div>

			<div class="unit">
				<label>内容：</label>
				<textarea class="editor required" name="info" rows="25" cols="60"
					upImgUrl="${ctx }/petservice/bbs/uploadFile.html" 
					upImgExt="jpg,jpeg,gif,png" 
					tools="simple" class="required" >
					${myForm.info }
				</textarea>
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
