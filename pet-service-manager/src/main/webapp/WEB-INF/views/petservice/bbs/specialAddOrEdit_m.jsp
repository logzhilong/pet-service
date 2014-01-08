<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<h2 class="contentTitle">添加专题-组的</h2>

<div class="panelBar">
	<ul class="toolBar">
		<li>
			<a class="add" href="${ctx }/petservice/bbs/specialAddOrEdit_s.html?gid=${myForm.gid}&navTabId=speciaAddOrEdit_m" title="添加专题-组" target="navTab" rel="speciaAddOrEdit_s" >
				<span>添加</span>
			</a>
		</li>
		<li class="line">line</li>
		<li>
			<a class="delete" href="${ctx }/petservice/bbs/specialDelete.html?id={id}&m=m&navTabId=speciaAddOrEdit_m" target="ajaxTodo" title="确定删除？" warn="请选择"><span>删除</span></a>
		</li>
		<li class="line">line</li>
	</ul>
</div>


<div class="pageContent" id="noteList" layoutH="105">


<div class="panel" defH="500"><h1>已添加</h1><div>

	<table class="list" width="100%">
		<thead>
			<tr>
				<th width="30">顺序</th>
				<th width="166">图片</th>
				<th>标题</th>
				<th>摘要</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list }" var="itm" varStatus="idx">
			<tr height="35" align="left" target="id" rel="${itm.id }" >
				<td>${itm.seq }</td>
				<td>
					<img alt="" src="${pet_file_server }/get/${itm.img }" width="166" height="90"  />
				</td>
				<td>
					<a href="${ctx }/petservice/bbs/specialAddOrEdit_s.html?id=${itm.id}&navTabId=speciaAddOrEdit_m" target="navTab" rel="speciaAddOrEdit_s" title="修改">
						${itm.name }
					</a>
				</td>
				<td>${itm.summary }</td>
			</tr>
		</c:forEach>
		</tbody>
		
	</table>

	
	
</div></div>


</div>

<form method="post" action="${ctx }/petservice/bbs/specialSave_m.html" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
<input type="hidden" name="navTabId" value="${myForm.navTabId }" />
<div class="formBar">
	<ul>
		<c:if test="${empty errorMsg}">
			<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
		</c:if>
	</ul>
</div>
</form>