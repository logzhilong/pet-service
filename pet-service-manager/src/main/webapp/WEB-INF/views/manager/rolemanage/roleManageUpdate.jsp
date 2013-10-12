<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post"  action="${ctx }/manager/mgrrolemanage/rolemanageSaveOrUpdate.html" onsubmit="return validateCallback(this,dialogAjaxDone);">

	<div class="pageContent">
		<div class="pageFormContent" layoutH="60">
			<fieldset>
				<legend>修改角色信息</legend>
			
				
			
					<input type="hidden" name="id" value="${mgrRole.id }" />
				
			<dl>
				<dt>角色名称：</dt>
				<dd>
					<input type="text" name="name" value="${mgrRole.name }" />
				</dd>
			</dl>
			<dl>
				<dt>角色描述：</dt>
				<dd>
					<input type="text" name="desct" value="${mgrRole.desct }" />
				</dd>
			</dl>
			<dl>
				<dt>Code：</dt>
				<dd>
					<input type="text" name="code" value="${mgrRole.code }" />
				</dd>
			</dl>
			<dl>
				<dt>是否可用：</dt>
				<dd><select name="enable">
				<c:choose>  
	 				  <c:when test="${mgrRole.enable }"> 
	 				    <option value="true">true</option>
						<option value="false">false</option>
	  				  </c:when>  
	  				 <c:otherwise> 
						<option value="false">false</option>
	   					 <option value="true">true</option>
	 				 </c:otherwise>  
				</c:choose>  
				</select>
				</dd>
			</dl>
			</fieldset>
		</div>

		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">修改</button>
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
