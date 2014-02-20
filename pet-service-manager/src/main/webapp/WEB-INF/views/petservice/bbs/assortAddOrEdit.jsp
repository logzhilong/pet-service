<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageContent">

	<form method="post" action="${ctx }/petservice/bbs/assortSave.html" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
	
		<input type="hidden" name="id" value="${myForm.id }" />
	
		<div class="pageFormContent" layoutH="58">
			
			<div class="unit">
				<label>序号：</label>
				<input type="text" name="seq" size="30" value="${myForm.seq }" class="required" />
			</div>
			<div class="unit">
				<label>名称：</label>
				<input type="text" name="name" size="30" value="${myForm.name }" class="required" />
			</div>
			<div class="unit">
				<label>映射：</label>
				<table>
					<c:forEach items="${forumList }" var="itm"> 
						<tr>
							<td>
								<c:set var="checked" value=" " />
								<c:if test="${ not empty forumChecked and not empty forumChecked[itm.id] }">
									<c:set var="checked" value="checked='checked'" />
								</c:if>
								<input type="checkbox" name="forumIds" value="${itm.id}" ${checked } />
							</td>
							<td>${itm.name }</td>
						</tr>
					</c:forEach>
				</table>
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
