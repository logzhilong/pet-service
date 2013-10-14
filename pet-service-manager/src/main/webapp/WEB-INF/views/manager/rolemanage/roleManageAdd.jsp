<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<form method="post"  onsubmit="return validateCallback(this,dialogAjaxDone);" action="${ctx }/manager/mgrrolemanage/rolemanageSaveOrUpdate.html">

	<div class="pageContent">
		<div class="pageFormContent" layoutH="60">
			<fieldset>
				<legend>创建角色信息</legend>
				<dl>
					<dt>角色名字：</dt>
					<dd>
						<input type="text" name="name"/>
					</dd>
				</dl>

				<dl>
					<dt>角色描述：</dt>
					<dd>
						<input type="text" name="desct" />
					</dd>
				</dl>
				<dl>
					<dt>Code：</dt>
					<dd>
						<input type="text" name="code" />
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
