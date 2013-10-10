<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id="areaCodeAddForm" action="${ctx }/manager/bbs/addOrUpdateForum.html" onsubmit="return validateCallback(this,dialogAjaxDone);">

	<div class="pageContent">
		<div class="pageFormContent" layoutH="60">
			<fieldset>
				<legend>修改圈子信息</legend>
			
				
				<dl>
					<dt>id：</dt>
					<dd>
						<input readonly="readonly" type="text" name="id" value="${fos.id }" />
					</dd>
				</dl>
				<dl>
					<dt>圈子名字：</dt>
					<dd>
						<input type="text" name="name" value="${fos.name }" />
					</dd>
				</dl>
				<dl>
					<dt>圈子描述：</dt>
					<dd>
						<input type="text" name="descript" value="${fos.descript }" />
					</dd>
				</dl>
				<dl>
					<dt>点击数量:</dt>
					<dd>
						<input type="text" name="clientCount" value="${fos.clientCount }" />
					</dd>
				</dl>
				<dl>
					<dt>圈子名字：</dt>
					<dd>
						<input type="text" name="replyCount" value="${fos.replyCount }" />
					</dd>
				</dl>
				<dl>
					<dt>圈子头像：</dt>
					<dd>
						<input type="text" name="logoImg" value="${fos.logoImg }" />
					</dd>
				</dl>
				<dl><dt>选择地区:</dt>
						<select class="combox" name="fatherid" ref="w_comboxcity" refUrl="${ctx }/manager/commons/getConmonArealistBypid.html?pid={value}">
							<option value="all">--请选择国家--</option>
							<c:forEach var="itr" items="${codes }">
		  							<option value="${itr.id }">${itr.name }</option>
		  					</c:forEach>
						</select> 
						<select class="combox" name="sunid" ref="xcity" id="w_comboxcity" refUrl="${ctx }/manager/commons/getConmonArealistBypid.html?pid={value}">
							<option value="all">--请选择省份(市)--</option>
						</select> 
						<select class="combox" name="grandsunid" id="xcity" >
							<option value="all">--请选择区(县)--</option>
						</select> 
				</dl>
				<dl>
					<dt>街道(小区)：</dt>
					<dd>
						<input type="text" name="areaDesc" value="${fos.areaDesc }" />
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
