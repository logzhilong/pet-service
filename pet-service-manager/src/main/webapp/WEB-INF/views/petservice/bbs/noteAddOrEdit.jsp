<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>


<div class="pageContent">

	<form method="post" action="${ctx }/petservice/bbs/noteSave.html" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
		<input type="hidden" name="id" value="${myForm.id }" />
		<input type="hidden" name="forumId" value="${myForm.forumId }" />
	
		<div class="pageFormContent" layoutH="58">

			<div class="unit">
				<label>发帖人：</label>
				<c:choose>
					<c:when test="${ empty myForm.id }">
						<select name="userId" class="required" >
							<c:forEach items="${tuList }" var="itm" >
								<option value="${itm.userId }" >${itm.nickname }</option>
							</c:forEach>
						</select>
					</c:when>
					<c:otherwise>
						<table>
							<tr>
								<td align="left">用户名</td><td>:${myForm.userName }</td>
								<td>&nbsp;&nbsp;|&nbsp;&nbsp;</td>
								<td align="left">昵称</td><td>:${myForm.nickname }</td>
							</tr>
						</table>
					</c:otherwise>
				</c:choose>
				${errorMsg }
			</div>
			
			<c:if test="${not empty myForm.id }">
				<div class="unit">
					<label>状态：</label>
					<select name="state" class="required" >
						<option value="ACTIVE" <c:if test="${ myForm.state eq 'ACTIVE' }">selected="selected"</c:if> >ACTIVE--正常</option>
						<option value="AUDIT" <c:if test="${ myForm.state eq 'AUDIT' }">selected="selected"</c:if> >AUDIT--审核中</option>
						<option value="PASS" <c:if test="${ myForm.state eq 'PASS' }">selected="selected"</c:if> >PASS--审核通过</option>
						<option value="REJECT" <c:if test="${ myForm.state eq 'REJECT' }">selected="selected"</c:if> >REJECT--审核拒绝</option>
						<option value="REPORT" <c:if test="${ myForm.state eq 'REPORT' }">selected="selected"</c:if> >REPORT--被举报</option>
					</select>
				</div>
			</c:if>
			
			<div class="unit">
				<label>置顶：</label>
				<select name="isTop" >
					<option value="false" <c:if test="${ myForm.isTop == false }">selected="selected"</c:if> >否</option>
					<option value="true" <c:if test="${ myForm.isTop == true }">selected="selected"</c:if> >是</option>
				</select>
			</div>

			<div class="unit">
				<label>精华：</label>
				<select name="isEute" >
					<option value="false" <c:if test="${ myForm.isEute == false }">selected="selected"</c:if> >否</option>
					<option value="true" <c:if test="${ myForm.isEute == true }">selected="selected"</c:if> >是</option>
				</select>
			</div>

			<div class="unit">
				<label>标题：</label>
				<input type="text" name="name" size="80" value="${myForm.name }" class="required"/>
			</div>
			
			<div class="unit">
				<label>内容：</label>
				<textarea class="editor required" name="content" rows="25" cols="60"
					upImgUrl="${ctx }/petservice/bbs/uploadFile.html" 
					upImgExt="jpg,jpeg,gif,png" 
					tools="simple" >
					${myForm.content }
				</textarea>
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
