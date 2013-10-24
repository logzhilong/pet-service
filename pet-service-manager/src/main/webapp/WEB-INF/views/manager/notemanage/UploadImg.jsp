<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<h2 class="contentTitle">多文件上传</h2>

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

<div class="pageContent" style="margin: 0 10px" layoutH="50">


	<input id="testFileInput" type="file" name="image"
		uploaderOption="{
			swf:'static/dwz1.4.5/uploadify/scripts/uploadify.swf',
			uploader:'${ctx }/manager/notemanage/upimg.html?up=11&operationID=1205',
			formData:{PHPSESSID:'xxx', ajax:1},
			buttonText:'请选择文件',
			fileSizeLimit:'70000KB',
			fileTypeDesc:'*.jpg;*.jpeg;*.gif;*.png;',
			fileTypeExts:'*.jpg;*.jpeg;*.gif;*.png;',
			auto:true,
			multi:true,
		onUploadSuccess:uploadifySuccess,
			onQueueComplete:uploadifyQueueComplete
		}" />

	<div class="divider"></div>

	<input id="testFileInput2" type="file" name="image2" 
		uploaderOption="{
			swf:'static/dwz1.4.5/uploadify/scripts/uploadify.swf',
			uploader:'${ctx }/manager/notemanage/upimg.html',
			formData:{},
			queueID:'fileQueue',
			buttonImage:'static/dwz1.4.5/uploadify/img/add.jpg',
			buttonClass:'my-uploadify-button',
			width:102,
			auto:false,
			onQueueComplete:uploadifyQueueComplete
		}"
	/>
	
	<div id="fileQueue" class="fileQueue"></div>
	
	<input type="image" src="static/dwz1.4.5/uploadify/img/upload.jpg" onclick="$('#testFileInput2').uploadify('upload', '*');"/>
	<input type="image" src="static/dwz1.4.5/uploadify/img/cancel.jpg" onclick="$('#testFileInput2').uploadify('cancel', '*');"/>


</div>