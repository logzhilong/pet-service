<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<form method="post" id="areaCodeAddForm"
	action="${ctx }/alerts/areaCodeSaveOrUpdate.html"
	onsubmit="return validateCallback(this,dialogAjaxDone);">

	<div class="pageContent">

		<div class="pageFormContent" layoutH="60">
			<fieldset>
				<legend>创建地区信息</legend>
				<dl>
					<select class="combox" name="father" ref="w_combox_city" refUrl="${ctx }/manager/commons/getConmonArealistBypid.html?pid={value}">
						<option value="all">--请选择国家--</option>
						<c:forEach var="itr" items="${codes }">
	  							<option value="${itr.id }">${itr.name }</option>
	  					</c:forEach>
					</select> 
					<select class="combox" name="pid" id="w_combox_city">
						<option value="all">--请选择省份(市)--</option>
					</select> 
					
				</dl>


				<dl>
					<dt>编码：</dt>
					<dd>
						<input type="text" name="id" value="${myForm.id }" />
					</dd>
				</dl>

				<dl>
					<dt>名称：</dt>
					<dd>
						<input type="text" name="name" value="${myForm.name }" />
					</dd>
				</dl>

			</fieldset>
		</div>

		<div class="formBar">
			<ul>
				<li>
					 
				<div class="buttonActive"><div class="buttonContent"><button type="button" onclick="areaCode.addSubmit('${ctx}','areaCodeAddForm');" >保存</button></div></div>
				
<!-- 					<div class="buttonActive"> -->
<!-- 						<div class="buttonContent"> -->
<!-- 							<button type="submit">保存</button> -->
<!-- 						</div> -->
<!-- 					</div> -->
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
