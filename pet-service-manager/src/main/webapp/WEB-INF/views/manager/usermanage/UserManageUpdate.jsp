<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post"  action="${ctx }/manager/mgrusermanager/usermanageSaveOrUpdate.html" onsubmit="return validateCallback(this,dialogAjaxDone);">

	<div class="pageContent">
		<div class="pageFormContent" layoutH="60">
			<fieldset>
				<legend>修改用户信息</legend>
					<input type="hidden" name="id" value="${mgrUser2.id }" />
			<dl>
				<dt>用户名称：</dt>
				<dd>
					<input type="text" name="name" value="${mgrUser2.name }" />
				</dd>
			</dl>
			
			
			<dl>
				<dt>是否可用：</dt>
				<dd><select name="enable">
				<c:choose>  
	 				  <c:when test="${mgrUser2.enable }"> 
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
			<dl>
				<dt>选择角色：</dt>
				<dd>
					</label><input type="checkbox" class="checkboxCtrl" group="roletype" />全选</label>
						
						<c:forEach var="itrr" items="${list }">
						<label><input type="checkbox" name="roletype"  <c:if test="${itrr.rcheck }">checked="checked" </c:if> value="${itrr.rid }" />${itrr.rname }</label>
						
						</c:forEach>
					</dd>
				</dl>
			</fieldset>
		</div>

		<div class="formBar">
			<ul>
				<li>
					<div class="buttonContent"><button type="button" class="checkboxCtrl" group="roletype" selectType="invert">反选</button></div>
				</li>
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
