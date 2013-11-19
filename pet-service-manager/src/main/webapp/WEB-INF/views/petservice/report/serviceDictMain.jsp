<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<div class="pageContent" selector="h1" layoutH="2">
<div id="serviceDictList">

<div class="panel" defH="600" id="serviceDictList_panel">
	
	<h1>服务字典</h1>
	
	<div>
		<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a class="add" href="${ctx }/petservice/report/serviceDictAddOrEdit.html" target="dialog" mask="true" title="添加" width="500" height="300" rel="serviceDictList"><span>添加</span></a>				
				</li>
				<li class="line">line</li>
				<li>
					<a class="edit" href="${ctx }/petservice/report/serviceDictAddOrEdit.html?id={id}" target="dialog" warn="请选择" width="500" height="300" rel="serviceDictList"><span>修改</span></a>
				</li>
				<li class="line">line</li>
				<li>
					<a class="delete" href="${ctx }/petservice/report/serviceDictDel.html?id={id}" target="ajaxTodo" title="确定要删除吗?" rel="serviceDictList"><span>删除</span></a>
				</li>
			</ul>
		</div>
		<div>
			<table width="100%" class="list" border="0" style="background-color: white;">
				<thead>
					<tr>
						<th width="20" ></th>
						<th >别名</th>
						<th width="150" >服务注册码</th>
						<th width="150" >方法</th>
						<th width="100" >创建人</th>
						<th width="150" >创建日期</th>
						<th width="100" >修改人</th>
						<th width="150" >修改日期</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${list }" var="itm" varStatus="idx">
					<tr height="30" align="left" target="id" rel="${itm.id }" >
						<td align="center">${idx.index+1 }</td>
						<td>
							<a class="edit" href="${ctx }/petservice/report/serviceDictAddOrEdit.html?id=${itm.id}" target="dialog" width="500" height="300" rel="serviceDictList">
								${itm.alias }
							</a>
						</td>
						<td>${itm.service }</td>
						<td>${itm.method }</td>
						<td>${itm.cb }</td>
						<td align="center">
							<fmt:formatDate value="${itm.ct }" type="both" />
						</td>
						<td>${itm.eb }</td>
						<td align="center">
							<fmt:formatDate value="${itm.et }" type="both" />
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>	
	</div>
</div>



</div>
</div>

