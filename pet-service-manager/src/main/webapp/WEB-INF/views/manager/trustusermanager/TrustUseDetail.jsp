<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id="areaCodeAddForm"
	action="${ctx }/manager/trustuser/userAddOrUpdate.html"
	onsubmit="return validateCallback(this,dialogAjaxDone);">
	<div class="pageContent">
		<div class="pageFormContent" layoutH="50">
			<fieldset style="height: 280px;">
				<tr>
					<td><input name="id" type="hidden" value="${petuser.id }"></td>
				</tr>
				<dl>
					<dt>名字(昵称):</dt>
					<dd>
						<input name="nickname" type="text" value="${petuser.nickname }">
					</dd>
				</dl>
				<dl>
					<dt>电话:</dt>
					<dd>
						<input name="phonenumber" type="text" value="${petuser.phonenumber }">
					</dd>
				</dl>
				<dl>
					<dt>爱好:</dt>
					<dd>
						<input name="hobby" type="text" value="${petuser.hobby }">
					</dd>
				</dl>
				<dl>
					<dt>性别:</dt>
					<dd>
						<select name="gender">
						<option value="male">男</option>
						<option value="female">女</option>
						</select>
					</dd>
				</dl>
				<dl>
					<dt>人物头像：</dt>
					<dd>
						<textarea style="width: 130px;" class="editor" tools="Img" 
							 name="img" cols="45" rows="2" enctype="multipart/form-data"
							alt="" uplinkext="zip,rar,txt" upimgext="jpg,jpeg,gif,png"
							upflashext="swf"
							upimgurl="${ctx }/manager/forummamage/upimgforforum.html"
							skin="vista">
						</textarea>
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
