<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<form method="post"  onsubmit="return validateCallback(this,dialogAjaxDone);" action="${ctx }/manager/mgrusermanager/usermanageSaveOrUpdate.html">

	<div class="pageContent">
		<div class="pageFormContent" layoutH="60">
			<fieldset>
				<legend>创建用户信息</legend>
				<dl>
					<dt>用户名称：</dt>
					<dd>
						<input type="text" name="name"/>
					</dd>
				</dl>

				<dl>
					<dt>密码：</dt>
					<dd>
						<input type="text" name="password" />
					</dd>
				</dl>
				<dl>
					<dt>选择角色：</dt>
					<dd>
						</label><input type="checkbox" class="checkboxCtrl" group="roletype" />全选</label>
						<c:forEach var="itr" items="${roles }">
							<label><input type="checkbox" name="roletype" value="${itr.id }" />${itr.name }</label>
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
							<button type="submit">保存</button>
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
