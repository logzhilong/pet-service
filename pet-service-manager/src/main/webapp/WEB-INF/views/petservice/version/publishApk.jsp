<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageContent">

	<form method="post" action="${ctx }/petservice/version/publishApkSave.html" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
		
		<input type="hidden" id="fileId" name="fileId" size="30" value="" />
		
		<div class="pageFormContent" layoutH="58">

			<div class="unit">
				<label>版本号：</label>
				<input type="text" name="version" size="30" value="" class="required"/>
			</div>
			<div class="unit">
				<label>渠道号：</label>
				<input type="text" name="channel" size="30" value="" class="required"/>
			</div>
			<div class="unit">
				<label>上传状态：</label>
				<input type="text" id="msg" size="30" value="待上传" readonly="readonly" class="required" />
			</div>
			<div class="unit">
				<input id="testFileInput" type="file" name="image"
						uploaderOption="{
							swf:'${ctx }/static/dwz1.4.5/uploadify/scripts/uploadify.swf',
							uploader:'${ctx }/petservice/version/uploadFile.html',
							buttonText:'请选择文件',
							fileSizeLimit:'40000KB',
							fileTypeDesc:'*.apk;',
							fileTypeExts:'*.apk;',
							auto:true,
							multi:true,
							onUploadSuccess:uploadifySuccess
						}"
				/>
				<script type="text/javascript">
					function uploadifySuccess( file, data, response ){
						var j = eval("("+data+")");
						if( !j['error'] ){
							$('#fileId').val(j['fileId']);
							$('#msg').val('上传成功');
						}else{
							alert(j['error']);
						}
					}
				</script>
			</div>
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>
