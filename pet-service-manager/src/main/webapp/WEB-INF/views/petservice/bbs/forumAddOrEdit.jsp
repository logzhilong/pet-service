<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<div class="pageContent">

	<form method="post" action="${ctx }/petservice/bbs/forumSave.html" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
	
		<input type="hidden" name="id" value="${myForm.id }" />
		<input type="hidden" name="pid" value="${myForm.pid }" />
		<input type="hidden" id="img" name="logoImg" value="${myForm.logoImg }"/>
	
		<div class="pageFormContent" layoutH="58">
			
			<c:if test="${not empty myForm.pid}">
				<div class="unit">
					<label>父圈：</label>
					<input type="text" name="pname" size="30" value="${myForm.pname }" readonly="readonly" />
				</div>
			</c:if>
			
			<div class="unit">
				<label>序号：</label>
				<input type="text" name="seq" size="30" value="${myForm.seq }" />
			</div>
			<div class="unit">
				<label>名称：</label>
				<input type="text" name="name" size="30" value="${myForm.name }" class="required"/>
			</div>
			<c:if test="${not empty myForm.pid}">
				<div class="unit">
					<label>类型：</label>
					<select class="combox"  ref="fforum" refUrl="${ctx }/manager/bbs/Toaddforum.html?fid={value}">
						<option value="all">--请选择--</option>
						<c:forEach var="itr" items="${xmllist }">
	  							<option value="${itr.id }">${itr.name }</option>
	  					</c:forEach>
					</select>
					<select class="combox" name="type" id="fforum" ref="fd">
						<option value="">--请选择--</option>
					</select>
				</div>
				
				<div class="unit">
					<input id="testFileInput" type="file" name="image"
							uploaderOption="{
								swf:'${ctx }/static/dwz1.4.5/uploadify/scripts/uploadify.swf',
								uploader:'${ctx }/petservice/albums/uploadFile.html',
								buttonText:'请选择图片',
								fileSizeLimit:'512KB',
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
								$('#forumIcon').attr('src',j['imgUrl']);
							}else{
								alert(j['error']);
							}
						}
					</script>
				</div>
				<div>
					<img alt="" src="${pet_file_server }/get/${myForm.logoImg }" id="forumIcon" width="100" >
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
