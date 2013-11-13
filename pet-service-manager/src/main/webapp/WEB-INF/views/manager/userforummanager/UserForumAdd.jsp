<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<form method="post"  onsubmit="return validateCallback(this,dialogAjaxDone);" action="${ctx }/manager/userforumcondition/AddOrUpdateuserforum.html">

	<div class="pageContent">
		<div class="pageFormContent" layoutH="60">
			<fieldset>
				<legend>创建默认关注圈子信息</legend>
				<dl>
					<dt>圈子：</dt>
					<dd>
						<select name="forumId" style="width: 130px;">
						<option value="all">--请选圈子--</option>
						<c:forEach var="itr" items="${forums }">
							<option value="${itr.id }">${itr.name }</option>
						</c:forEach>
					</select>
					</dd>
				</dl>
				<dl>
					<dt>vlaue：</dt>
					<dd>
						<input type="text" name="conditionValue" />
					</dd>
				</dl>
				<dl>
					<dt>type：</dt>
					<dd>
						<input type="text" name="conditionType" />
					</dd>
				</dl>

			</fieldset>
		</div>

		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
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
