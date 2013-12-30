<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageContent">

	<form method="post" action="${ctx }/petservice/ads/adsSave.html" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
	
		<input type="hidden" name="id" value="${myForm.id }" />
	
		<div class="pageFormContent" layoutH="58">
		
		
			<div class="unit">
				<input id="testFileInput" type="file" name="image"
						uploaderOption="{
							swf:'${ctx }/static/dwz1.4.5/uploadify/scripts/uploadify.swf',
							uploader:'${ctx }/petservice/ads/uploadFile.html',
							buttonText:'请选择广告图',
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
			</div>
			<div>
				<img alt="" src="${imgUrl }" id="icon" width="640" height="120" />
			</div>		
			
			<div class="unit">
				<textarea class="editor required" name="info" rows="25" cols="89"
					upImgUrl="${ctx }/petservice/bbs/uploadFile.html" 
					upImgExt="jpg,jpeg,gif,png" 
					tools="simple" class="required" >${myForm.info }</textarea>
			</div>
			<div class="unit">
				<label>内容类型：</label>
				<select name="adType">
					<option value="text" <c:if test="${'text' eq myForm.adType}">selected="selected"</c:if> >文本</option>
					<option value="url" <c:if test="${'url' eq myForm.adType}">selected="selected"</c:if> >链接</option>
				</select>
			</div>
			
			<c:if test="${not empty myForm.id }">
				<div class="unit">
					<label>广告状态：</label>
					<select name="state">
						<option value="active" <c:if test="${'active' eq myForm.state}">selected="selected"</c:if> >active-有效</option>
						<option value="invalid" <c:if test="${'invalid' eq myForm.state}">selected="selected"</c:if> >invalid-无效</option>
					</select>
				</div>
			</c:if>
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>
