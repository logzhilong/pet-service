<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post"
	action="${ctx }/manager/mgrrolemanage/rolemanageSaveOrUpdate.html"
	onsubmit="return validateCallback(this,dialogAjaxDone);">

	<div class="pageContent">
		<div class="pageFormContent" layoutH="60">
			<fieldset>
				<legend>当前角色用户列表</legend>
				<table class="table" width="100%" layoutH="130">
					<thead>
						<tr>
							<th width="30" align="left">编号</th>
							<th width="100" align="left">用户名称</th>
						</tr>
					</thead>
					<tbody >
						<c:forEach items="${mgrUsers }" var="ite" varStatus="idx">
							<tr target="id" rel="${ite.id }">
								<td align="left">${idx.index+1 }</td>
								<td align="left">${ite.name }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</fieldset>
		</div>

		<div class="formBar">
			<ul>
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
