<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<form method="post" id="areaCodeAddForm"
	action="${ctx }/manager/trustuser/userAddOrUpdate.html"
	onsubmit="return validateCallback(this,dialogAjaxDone);">

	<div class="pageContent">
		<div class="pageFormContent" layoutH="80">
			<fieldset>
				<legend>创建托管用户信息</legend>
				<dl>
					<dt>昵称：</dt>
					<dd>
						<input type="text" name="nickname" />
					</dd>
				</dl>
				<dl>
					<dt>密码：</dt>
					<dd>
						<input type="password" name="password"/>
					</dd>
				</dl>
				<dl>
					<dt>手机:</dt>
					<dd>
						<input type="text" name="phonenumber"/>
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
