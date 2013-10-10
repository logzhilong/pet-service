<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<form method="post" id="cfgMgrAddForm"
	action="${ctx }/manager/configmanager/save.html"
	onsubmit="return validateCallback(this,dialogAjaxDone);">

	<div class="pageContent">
		<div class="pageFormContent" layoutH="60">
			<fieldset>
				<legend>公共配置信息</legend>
				<dl>
					<dt>key：</dt>
					<dd>
						<input type="text" name="key" value="${myForm.key }" />
					</dd>
				</dl>
				<dl>
					<dt>value：</dt>
					<dd>
						<input type="text" name="value" value="${myForm.value }" />
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
