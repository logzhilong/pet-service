<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>


<h2 class="contentTitle">多文件上传</h2>

<style type="text/css" media="screen">
.my-uploadify-button {
	background:none;
	border: none;
	text-shadow: none;
	border-radius:0;
}

.uploadify:hover .my-uploadify-button {
	background:none;
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
			swf:'uploadify/scripts/uploadify.swf',
			uploader:'demo/common/ajaxDone.html',
			formData:{PHPSESSID:'xxx', ajax:1},
			buttonText:'请选择文件',
			fileSizeLimit:'200KB',
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
			swf:'uploadify/scripts/uploadify.swf',
			uploader:'demo/common/ajaxDone.html',
			formData:{PHPSESSID:'xxx', ajax:1},
			queueID:'fileQueue',
			buttonImage:'uploadify/img/add.jpg',
			buttonClass:'my-uploadify-button',
			width:102,
			auto:false
		}"
	/>
	
	<div id="fileQueue" class="fileQueue"></div>
	
	<input type="image" src="uploadify/img/upload.jpg" onclick="$('#testFileInput2').uploadify('upload', '*');"/>
	<input type="image" src="uploadify/img/cancel.jpg" onclick="$('#testFileInput2').uploadify('cancel', '*');"/>


	<div class="divider"></div>
	<p style="margin:10px"><a href="http://www.uploadify.com/documentation/" target="_blank">Uploadify 在线文档</a></p>
	
<textarea cols="160" rows="10">
uploaderOption: http://www.uploadify.com/documentation/
	auto            : true,               // 上传文件时,自动添加到队列
	buttonClass     : '',                 // 一个类名称添加到浏览按钮DOM对象


	buttonCursor    : 'hand',             // 光标到使用browse按钮


	buttonImage     : null,               // (String或null)到一个图像的路径使用Flash browse按钮如果不使用CSS样式按钮
	buttonText      : 'SELECT FILES',     // 文本使用browse按钮


	checkExisting   : false,              // 通往一个服务器端脚本,检查现有的文件在服务器上


	debug           : false,              // 打开swfUpload调试模式


	fileObjName     : 'Filedata',         // 文件对象的名称使用在你的服务器端脚本


	fileSizeLimit   : 0,                  // 最大大小上载文件在KB(接受单位B KB MB GB如果字符串,0(无极限


	fileTypeDesc    : 'All Files',        // 文件类型的描述在浏览对话框


	fileTypeExts    : '*.*',              //允许扩展在浏览对话框(服务器端验证也应该被使用


	height          : 30,                 // 浏览按钮的高度


	itemTemplate    : false,              // The template for the file item in the queue
	method          : 'post',             // The method to use when sending files to the server-side upload script
	multi           : true,               // Allow multiple file selection in the browse dialog
	formData        : {},                 // An object with additional data to send to the server-side upload script with every file upload
	preventCaching  : true,               // Adds a random value to the Flash URL to prevent caching of it (conflicts with existing parameters)
	progressData    : 'percentage',       // ('percentage' or 'speed') Data to show in the queue item during a file upload
	queueID         : false,              // The ID of the DOM object to use as a file queue (without the #)
	queueSizeLimit  : 999,                // The maximum number of files that can be in the queue at one time
	removeCompleted : true,               // Remove queue items from the queue when they are done uploading
	removeTimeout   : 3,                  // The delay in seconds before removing a queue item if removeCompleted is set to true
	requeueErrors   : false,              // Keep errored files in the queue and keep trying to upload them
	successTimeout  : 30,                 // The number of seconds to wait for Flash to detect the server's response after the file has finished uploading
	uploadLimit     : 0,                  // The maximum number of files you can upload
	width           : 120,                // The width of the browse button
</textarea>

</div>