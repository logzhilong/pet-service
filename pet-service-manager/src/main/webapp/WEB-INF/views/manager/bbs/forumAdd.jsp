<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<form method="post" id="areaCodeAddForm"
	action="${ctx }/manager/bbs/addOrUpdateForum.html"
	onsubmit="return validateCallback(this,dialogAjaxDone);">

	<div class="pageContent">
		<div class="pageFormContent" layoutH="50">
			<fieldset style="height: 280px;">
				<legend>创建圈子信息</legend>
				<dl>
					<dt>父级圈子:</dt>
					<select class="combox" name="pid">
						<option value="all">--请选择父级圈子--</option>
						<c:forEach var="itr" items="${forums }">
							<option value="${itr.id }">${itr.name }</option>
						</c:forEach>
					</select>
				</dl>
				<dl>
					<dt>(子)圈子类型:</dt>*父圈不用选择
					<select class="combox"  ref="fforum" refUrl="${ctx }/manager/bbs/Toaddforum.html?fid={value}">
						<option value="all">--请选择--</option>
						<c:forEach var="itr" items="${xmllist }">
	  							<option value="${itr.id }">${itr.name }</option>
	  					</c:forEach>
					</select> 
					<select class="combox" name="type" id="fforum" ref="fd">
						<option value="">--请选择--</option>
					</select> 
				</dl>
				
				<dl>
					<dt>圈子名字：</dt>
					<dd>
						<input type="text" name="name" value="${forum.name }" />
					</dd>
				</dl>
<!-- 				<dl> -->
<!-- 					<dt>圈子描述：</dt> -->
<!-- 					<dd> -->
<%-- 						<input type="text" name="descript" value="${forum.descript }" /> --%>
<!-- 					</dd> -->
<!-- 				</dl> -->
<!-- 				<dl> -->
<!-- 					<dt>点击数量:</dt> -->
<!-- 					<dd> -->
<!-- 						<input type="text" name="clientCount" -->
<%-- 							value="${forum.clientCount }" /> --%>
<!-- 					</dd> -->
<!-- 				</dl> -->
<!-- 				<dl> -->
<!-- 					<dt>回复数量：</dt> -->
<!-- 					<dd> -->
<%-- 						<input type="text" name="replyCount" value="${forum.replyCount }" /> --%>
<!-- 					</dd> -->
<!-- 				</dl> -->
				<dl>
					<dt>圈子顺序：</dt>
					<dd>
						<input type="text" name="seq" value="${forum.seq }" />
					</dd>
				</dl>
<!-- 				<dl> -->
<!-- 					<dt>圈子头像：</dt> -->
<!-- 					<dd > -->
<!-- 						<textarea style="width:130px;"  class="editor" -->
<!-- 							tools="Img" name="logoImg" cols="45" rows="2"  -->
<!-- 							enctype="multipart/form-data" alt="" uplinkext="zip,rar,txt" -->
<!-- 							upimgext="jpg,jpeg,gif,png" upflashext="swf" -->
<%-- 							upimgurl="${ctx }/manager/forummamage/upimgforforum.html" skin="vista"> --%>
<%-- 							${forum.logoImg } --%>
<!-- 						</textarea> -->
<!-- 					</dd> -->
<!-- 				</dl> -->
				
				<!-- 			
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
						<input type="text" name="areaDesc" value="${forum.areaDesc }" />
					</dd>
				</dl>
			 -->
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
