<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
		
<form method="post" id="areaCodeAddForm"
	action="${ctx }/manager/pwdmanager/pwdmanageedit.html"
	onsubmit="return validateCallback(this,dialogAjaxDone);">

	<div class="pageContent">
		<div class="pageFormContent" layoutH="80">
			<fieldset>
				<legend>修改个人密码</legend>
				<dl>
					<dt>旧密码：</dt>
					<dd>
						<input type="password" name="opassword" />
					</dd>
				</dl>

				<dl>
					<dt>新密码:</dt>
					<dd>
						<input type="password" name="npassword"/>
					</dd>
				</dl>
				<dl>
					<dt>重复新密码:</dt>
					<dd>
						<input type="password" name="rnpassword"/>
					</dd>
				</dl>
				
			</fieldset>
		</div>

		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">修改密码</button>
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
		