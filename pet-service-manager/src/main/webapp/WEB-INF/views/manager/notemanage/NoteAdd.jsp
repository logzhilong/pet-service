<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<style type="text/css" media="screen">
.my-uploadify-button {
	background: none;
	border: none;
	text-shadow: none;
	border-radius: 0;
}

.uploadify:hover .my-uploadify-button {
	background: none;
	border: none;
}

.fileQueue {
	width: 400px;
	height: 150px;
	overflow: auto;
	border: 1px solid #E5E5E5;
	margin-bottom: 10px;
}
</style>
<form method="post" id="areaCodeAddForm"
	action="${ctx }/manager/notemanager/NoteAddOrUpdate.html"
	onsubmit="return validateCallback(this,dialogAjaxDone);">

	<div class="pageContent">
		<div class="pageFormContent" layoutH="80">
			<fieldset>
				<legend>创建帖子信息</legend>
				<dl>
					<dt>帖子名字：</dt>
					<dd>
						<input type="text" name="name" value="${note.name }" />
					</dd>
				</dl>

				<dl>
					<dt>点击数量:</dt>
					<dd>
						<input type="text" name="clientCount" value="${note.clientCount }" />
					</dd>
				</dl>
				<dl>
					<dt>图片上传文件:</dt>
					<li>
					
					<a rel="w_uploadify" target="dialog"  width="650" height="650" href="${ctx }/manager/notemanage/upimg.html" class="button"><span>+点此上传</span></a>
						
					</li>
				</dl>
				<dl style="margin-left: -380px; margin-top: 30px;">
					<dt>帖子内容:</dt>
					<textarea style="width: 200%; height: 200%;" class="editor"
						name="content" cols="100">${note2.content }
					</textarea>
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
