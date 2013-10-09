<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<form method="post" id="areaCodeAddForm" onsubmit="return validateCallback(this,dialogAjaxDone);" action="${ctx }/manager/commons/commanageSaveOrUpdate.html">

	<div class="pageContent">
		<div class="pageFormContent" layoutH="60">
			<fieldset>
				<legend>创建配置信息</legend>
				<dl>
					<dt>key：</dt>
					<dd>
						<input type="text" name="key"/>
					</dd>
				</dl>

				<dl>
					<dt>value：</dt>
					<dd>
						<input type="text" name="pvalue" />
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
