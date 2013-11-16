<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageContent">

	<form method="post" action="${ctx }/petservice/customer/ghostSave.html" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">

		<input type="hidden" name="id" value="${myForm.id }" />
	
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>手机号(用户名)：</label>
				<c:choose>
					<c:when test="${not empty myForm.id }">
						<input type="text" name="phoneNumber" size="30" value="${myForm.phoneNumber }" readonly="readonly"/>
					</c:when>
					<c:otherwise>
						<input type="text" name="phoneNumber" size="30" value="${myForm.phoneNumber }" class="required" />
					</c:otherwise>
				</c:choose>
			</div>
				
			<div class="unit">
				<label>昵称：</label>
				<input type="text" name="nickname" size="30" value="${myForm.nickname }" class="required" />
			</div>	
			<div class="unit">
				<label>年龄：</label>
				<input type="text" name="birthdate" size="30" value="${myForm.birthdate }" class="required" />
			</div>
			<div class="unit">
				<label>性别：</label>
				<select name="gender" >
					<option value="male" <c:if test="${ 'male' eq myForm.gender }">selected="selected"</c:if> >男</option>
					<option value="female" <c:if test="${ 'female' eq myForm.gender }">selected="selected"</c:if> >女</option>
				</select>
			</div>
			
			<div class="unit">
				<label>地址：</label>
				<input type="text" name="city" size="30" value="${myForm.city }" class="required"/>
			</div>
			
			<div class="unit">
				<label>签名：</label>
				<input type="text" name="signature" size="30" value="${myForm.signature }" class="required"/>
			</div>
			
			<div class="unit">
				<label>爱好：</label>
				<input type="text" name="hobby" size="30" value="${myForm.hobby }" class="required"/>
			</div>
			
			<div class="unit">
				<input id="testFileInput" type="file" name="image"
						uploaderOption="{
							swf:'${ctx }/static/dwz1.4.5/uploadify/scripts/uploadify.swf',
							uploader:'${ctx }/petservice/customer/uploadFile.html',
							buttonText:'请选择头像',
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
							$('#img').val(j['img']+"_"+j['img']+",");
							$('#icon').attr('src',j['imgUrl']);
						}else{
							alert(j['error']);
						}
					}
				</script>
			</div>
			<div>
				<img alt="" src="${myForm.imgUrl }" id="icon" width="200" >
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
