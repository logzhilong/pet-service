<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>



<form method="post" id="areaCodeAddForm"
	action="${ctx }/manager/notemanager/NoteAddOrUpdate.html?forumId=${forumId}"
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
					<dt>选择用户:</dt>
					<select name="userId" style="width: 130px;">
						<option value="all">--请选择托管用户--</option>
						<c:forEach var="itr" items="${trustUserlist }">
							<option value="${itr.userId }">${itr.nrootId }</option>
						</c:forEach>
					</select>
				</dl>
				<dl>

				</dl>
				<dl style="margin-left: -380px; margin-top: 30px;">
					<dt>帖子内容:</dt>
					<textarea style="width: 200%; height: 200%;" class="editor"
						tools="simple" name="content" cols="45" rows="5" 
						enctype="multipart/form-data" alt="" uplinkext="zip,rar,txt"
						upimgext="jpg,jpeg,gif,png" upflashext="swf"
						upimgurl="${ctx }/manager/notemanage/upimg.html" skin="vista">
						${note2.content }
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
