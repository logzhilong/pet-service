<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<form method="post" id="areaCodeAddForm"
	action="${ctx }/manager/bbs/addOrUpdateForum.html"
	onsubmit="return validateCallback(this,dialogAjaxDone);">

	<div class="pageContent">
		<div class="pageFormContent" layoutH="80">
			<fieldset>
				<legend>创建圈子信息</legend>
				<dl>
					<select class="combox" name="pid" >
						<option value="all">--请选择父级圈子--</option>
						<c:forEach var="itr" items="${forums }">
	  							<option value="${itr.id }">${itr.name }</option>
	  					</c:forEach>
					</select> 
						
				</dl>
				<dl>
					<dt>圈子名字：</dt>
					<dd>
						<input type="text" name="name" value="${forum.name }" />
					</dd>
				</dl>
				<dl>
					<select class="combox" name="fatherid" ref="w_comboxcity" refUrl="${ctx }/manager/commons/getConmonArealistBypid.html?pid={value}">
						<option value="all">--请选择国家--</option>
						<c:forEach var="itr" items="${codes }">
	  							<option value="${itr.id }">${itr.name }</option>
	  					</c:forEach>
					</select> 
					<select class="combox" name="sdd" ref="xcity" id="w_comboxcity" refUrl="${ctx }/manager/commons/getConmonArealistBypid.html?pid={value}">
						<option value="all">--请选择省份(市)--</option>
					</select> 
					<select class="combox" name="zid" id="xcity" refUrl="${ctx }/manager/commons/getConmonArealistBypid.html?pid={value}">
						<option value="all">--请选择区(县)--</option>
					</select> 
				</dl>
			</fieldset>
		</div>

		<div class="formBar">
			<ul>
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
