<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageContent">

	<form method="post" action="${ctx }/petservice/exper/experSave.html" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
	
		<input type="hidden" name="id" value="${myForm.id }" />
		<input type="hidden" name="pid" value="${myForm.pid }" />
	
		<div class="pageFormContent" layoutH="58">
			
			<div class="unit">
				<label>序号：</label>
				<input type="text" name="seq" size="30" value="${myForm.seq }" />
			</div>
			
			<div class="unit">
				<label>名称：</label>
				<input type="text" name="name" size="30" value="${myForm.name }" class="required"/>
			</div>

			<c:if test="${not empty myForm.pid}">
				<div class="unit">
					<label>父圈：</label>
					<input type="text" name="pname" size="30" value="${pf.name }" readonly="readonly" />
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
			</c:if>
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>
