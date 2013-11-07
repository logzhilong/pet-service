<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id="areaCodeAddForm"
	action="${ctx }/manager/trustuser/userAddOrUpdate.html"
	onsubmit="return validateCallback(this,dialogAjaxDone);">
<div class="pageContent">
	<table class="table" width="100%" layoutH="40">
		<tbody id="trustuserDetail">
			<tr>
				<td><input name="id" type="hidden" value="${petuser.id }"></td>
			</tr>
			<tr>
				<td>名字:<input name="nickname" type="text" value="${petuser.nickname }"></td>
			</tr>
			<tr>
				<td>电话:<input name="phonenumber" type="text" value="${petuser.phonenumber }"></td>
			</tr>
			<tr>
				<td>创建日期:${petuser.createtime }</td>
			</tr>
		</tbody>
	</table>
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
