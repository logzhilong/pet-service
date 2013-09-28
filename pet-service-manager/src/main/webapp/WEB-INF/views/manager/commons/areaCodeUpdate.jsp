<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id="areaCodeAddForm" action="${ctx }/alerts/areaCodeSaveOrUpdate.html" onsubmit="return validateCallback(this,dialogAjaxDone);">

	<div class="pageContent">
		<div class="pageFormContent" layoutH="60">
			<fieldset>
				<legend>修改地区信息</legend>

				
			<dl>
				<dt>编码：</dt>
				<dd>
					<input type="text" name="id" value="${code.id }" />
				</dd>
			</dl>
			
			<dl>
				<dt>父级编码：</dt>
				<dd>
					<input type="text" name="pid" value="${code.pid }" />
				</dd>
			</dl>
			
			<dl>
				<dt>父级名称：</dt>
				<dd>
					<input type="text" name="pname" value="${code.pname }" />
				</dd>
			</dl>
			
			<dl>
				<dt>本区域名称：</dt>
				<dd>
					<input type="text" name="name" value="${code.name }" />
				</dd>
			</dl>
			

			</fieldset>
		</div>

		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button"
								onclick="areaCode.addSubmit('${ctx}','areaCodeAddForm');">保存</button>
						</div>
					</div>
<!-- 					<div class="buttonActive"> -->
<!-- 						<div class="buttonContent"> -->
<!-- 							<button type="submit">submit</button> -->
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
