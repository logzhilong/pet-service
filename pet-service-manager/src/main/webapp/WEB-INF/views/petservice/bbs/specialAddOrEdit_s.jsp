<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<h2 class="contentTitle">添加专题-单</h2>

<form method="post" action="${ctx }/petservice/bbs/specialSave_s.html" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">

<input type="hidden" name="id" value="${myForm.id }" />
<input type="hidden" name="gid" value="${myForm.gid }" />
<input type="hidden" name="navTabId" value="${myForm.navTabId }" />

<div class="pageFormContent" layoutH="97">

	<fieldset>
		<legend>专题信息</legend>
		<dl class="nowrap">
			<dt>
				<input id="testFileInput" type="file" name="image"
						uploaderOption="{
							swf:'${ctx }/static/dwz1.4.5/uploadify/scripts/uploadify.swf',
							uploader:'${ctx }/petservice/ads/uploadFile.html',
							buttonText:'选择图片',
							fileSizeLimit:'900KB',
							fileTypeDesc:'*.jpg;*.jpeg;*.gif;*.png;',
							fileTypeExts:'*.jpg;*.jpeg;*.gif;*.png;',
							auto:true,
							multi:true,
							onUploadSuccess:uploadifySuccess
						}"
				/>
				<input type="hidden" id="img" name="img" value="${myForm.img }"/>
				<script type="text/javascript">
					function uploadifySuccess( file, data, response ){
						var j = eval("("+data+")");
						if( !j['error'] ){
							$('#img').val(j['img']);
							$('#icon').attr('src',j['imgUrl']);
						}else{
							alert(j['error']);
						}
					}
				</script>	
			</dt>
			<dd>
				<img alt="" src="${imgUrl }" id="icon" width="360" height="200" />
			</dd>
		</dl>

		<c:if test="${not empty myForm.gid }">
			<dl class="nowrap">
				<dt>排序</dt>
				<dd>
					<input type="text" name="seq" size="10" value="${myForm.seq }" class="required number" />
				</dd>
			</dl>
		</c:if>
		
		<dl class="nowrap">
			<dt>标题</dt>
			<dd>
				<input type="text" name="name" size="80" value="${myForm.name }"/>
			</dd>
		</dl>
		
		<dl class="nowrap">
			<dt>摘要</dt>
			<dd>
				<textarea rows="4" cols="60" name="summary">${myForm.summary }</textarea>
			</dd>
		</dl>
	</fieldset>
		
	<fieldset>
		<legend>帖子信息</legend>
		<dl class="nowrap">
			<dt>选择现有帖子</dt>
			<dd>
				<a class="btnLook" href="${ctx }/petservice/bbs/specialGetNote.html" lookupGroup="note" width="1000" height="550">选择现有帖子</a>
			</dd>
		</dl>
		<dl class="nowrap">
			<dt>圈子：</dt>
			<dd>
				<input type="hidden" name="note.id" value="${note.id }" />
				<input type="hidden" name="note.forumId" value="${note.forumId } "/>
				<input type="text" name="note.forumName" readonly="readonly" value="${note.forumName }" />
				<a class="btnLook" href="${ctx }/petservice/bbs/specialGetForum.html" lookupGroup="note" width="260" height="150">选择圈子</a>
			</dd>
		</dl>
		<dl class="nowrap">
			<dt>发帖人：</dt>
			<dd>
				<input type="hidden" name="note.userId" value="${note.userId } "/>
				<input type="text" name="note.nickname" readonly="readonly" value="${note.nickname }" />
				<a class="btnLook" href="${ctx }/petservice/bbs/specialGetGhost.html" lookupGroup="note" width="250" height="300">选择发帖人</a>
				
				${errorMsg }
			</dd>
		</dl>

		<dl class="nowrap">
			<dt>标题：</dt>
			<dd>
				<input type="text" name="note.name" size="80" value="${note.name }" class="required"/>
			</dd>
		</dl>

		<dl class="nowrap">
			<dt>内容：</dt>
			<dd>
				<textarea class="editor required" name="note.content" rows="25" cols="60"
					upImgUrl="${ctx }/petservice/bbs/uploadFile.html" 
					upImgExt="jpg,jpeg,gif,png" 
					tools="simple" >${note.content }</textarea>
			</dd>
		</dl>
		
	</fieldset>
</div>

<div class="formBar">
	<ul>
		<c:if test="${empty errorMsg}">
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
		</c:if>
	</ul>
</div>
</form>
