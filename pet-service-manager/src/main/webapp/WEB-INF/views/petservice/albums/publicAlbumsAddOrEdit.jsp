<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageContent">

	<form id="albumsForm" method="post" action="${ctx }/petservice/albums/publicAlbumsSave.html" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
		
		<input type="hidden" id="width" name="width" value="${myForm.width }" />
		<input type="hidden" id="height" name="height" value="${myForm.height }" />
		<input type="hidden" id="img" name="id" value="${myForm.id }"/>
		
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>标题：</label>
				<input type="text" name="title" size="30" value="${myForm.title }" />
			</div>	
			<div class="unit">
				<label>描述：</label>
				<input type="text" name="descript" size="30" value="${myForm.descript }" />
			</div>
			<br>
			<c:if test="${not empty myForm.id }">
				<div class="unit">
					<label></label>
					<script type="text/javascript">
						function del(){
							if(confirm('确认彻底删除此图片？')){
								var action = '${ctx}/petservice/albums/publicAlbumsDelete.html';
								$('#albumsForm').attr('action',action);
								$('#albumsForm').submit();
							}
						}
					</script>
					<button type="button" onclick="del()" >
						&nbsp;&nbsp;删&nbsp;除&nbsp;此&nbsp;图&nbsp;&nbsp;
					</button>
				</div>
			</c:if>
			<br>
			
			<c:if test="${empty myForm.id }">
			
				<div class="unit">
					<input id="testFileInput" type="file" name="image"
							uploaderOption="{
								swf:'${ctx }/static/dwz1.4.5/uploadify/scripts/uploadify.swf',
								uploader:'${ctx }/petservice/albums/uploadFile.html',
								buttonText:'请选择图片',
								fileSizeLimit:'2048KB',
								fileTypeDesc:'*.jpg;*.jpeg;*.gif;*.png;',
								fileTypeExts:'*.jpg;*.jpeg;*.gif;*.png;',
								auto:true,
								multi:true,
								onUploadSuccess:uploadifySuccess
							}"
					/>
					<script type="text/javascript">
						function uploadifySuccess( file, data, response ){
							var j = eval("("+data+")");
							if( !j['error'] ){
								$('#img').val(j['img']);
								$('#width').val(j['width']);
								$('#height').val(j['height']);
								$('#icon').attr('src',j['imgUrl']);
							}else{
								alert(j['error']);
							}
						}
					</script>
				</div>
			</c:if>
			
			<div>
				<img alt="" src="${pet_file_server }/get/${myForm.id }" id="icon" width="512" >
			</div>
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存图片</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>
