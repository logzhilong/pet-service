<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id="areaCodeAddForm" action="${ctx }/manager/bbs/addOrUpdateForum.html" onsubmit="return validateCallback(this,dialogAjaxDone);">

	<div class="pageContent">
		<div class="pageFormContent" layoutH="60">
			<fieldset>
				<legend>修改圈子信息</legend>
			
				
			<dl>
				<dt>id：</dt>
				<dd>
					<input readonly="readonly" type="text" name="id" value="${fos.id }" />
				</dd>
			</dl>
			<dl>
				<dt>name：</dt>
				<dd>
					<input type="text" name="name" value="${fos.name }" />
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
