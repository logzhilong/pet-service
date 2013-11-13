<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post"  action="${ctx }/manager/userforumcondition/AddOrUpdateuserforum.html" onsubmit="return validateCallback(this,dialogAjaxDone);">

	<div class="pageContent">
		<div class="pageFormContent" layoutH="60">
			<fieldset>
				<legend>修该默认关注圈子信息</legend>
				<input type="hidden" name="id" value="${userForum.id }" />
			<dl>
				<dt>关注圈子：</dt>
				<dd>
					<select name="forumId" style="width: 130px;">
						<option value="all">${userForum.forumId }</option>
						<c:forEach var="itr" items="${forums }">
							<option value="${itr.id }">${itr.name }</option>
						</c:forEach>
					</select>
			</dl>
			<dl>
				<dt>value：</dt>
				<dd>
					<input type="text" name="conditionValue" value="${userForum.conditionValue }" />
				</dd>
			</dl>
			<dl>
				<dt>类型：</dt>
				<dd>
					<input type="text" name="conditionType" value="${userForum.conditionType }" />
				</dd>
			</dl>
			
			
		
			</fieldset>
		</div>

		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">修改</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</div>
</form>
