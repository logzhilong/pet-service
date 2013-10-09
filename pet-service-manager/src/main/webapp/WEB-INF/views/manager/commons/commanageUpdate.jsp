<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id="areaCodeAddForm" action="${ctx }/manager/commons/commanageSaveOrUpdate.html" onsubmit="return validateCallback(this,dialogAjaxDone);">

	<div class="pageContent">
		<div class="pageFormContent" layoutH="60">
			<fieldset>
				<legend>修改配置信息</legend>
			
				
			<dl>
				<dt>key：</dt>
				<dd>
					<input readonly="true" type="text" name="key" value="${key }" />
				</dd>
			</dl>
			<dl>
				<dt>Value：</dt>
				<dd>
					<input type="text" name="pvalue" value="${valu }" />
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
