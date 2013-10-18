<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<form method="post" id="areaCodeAddForm"
	action="${ctx }/manager/notemanager/NoteAddOrUpdate.html"
	onsubmit="return validateCallback(this,dialogAjaxDone);">

	<div class="pageContent">
		<div class="pageFormContent" layoutH="80">
			<fieldset>
				<input type="hidden"  name="id" value="${note2.id }" />
				<legend>创建帖子信息</legend>
				<dl>
					<dt>帖子名字：</dt>
					<dd>
						<input type="text" name="name" value="${note2.name }" />
					</dd>
				</dl>
				<dl>
					<dt>帖子内容：</dt>
					<dd>
						<input type="text" readonly="readonly" name="content" value="${note2.content }" />
					</dd>
				</dl>
				<dl>
					<dt>点击数量:</dt>
					<dd>
						<input type="text"  readonly="readonly" name="clientCount" value="${note2.clientCount }" />
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
