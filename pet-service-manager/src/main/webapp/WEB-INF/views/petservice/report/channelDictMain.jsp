<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<div class="pageContent" selector="h1" layoutH="2">
<div id="channelDictList">

<div class="panel" defH="600" id="channelDictList_panel">
	
	<h1>渠道字典</h1>
	
	<div>
		<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a class="add" href="${ctx }/petservice/report/channelDictAddOrEdit.html" target="dialog" mask="true" title="添加" width="500" height="300" rel="channelDictList"><span>添加</span></a>				
				</li>
				<li class="line">line</li>
				<li>
					<a class="edit" href="${ctx }/petservice/report/channelDictAddOrEdit.html?id={id}" target="dialog" warn="请选择" width="500" height="300" rel="channelDictList"><span>修改</span></a>
				</li>
				<li class="line">line</li>
				<li>
					<a class="delete" href="${ctx }/petservice/report/channelDictDel.html?id={id}" target="ajaxTodo" title="确定要删除吗?" rel="channelDictList"><span>删除</span></a>
				</li>
			</ul>
		</div>
		<div>
			<table width="100%" class="list" border="0" style="background-color: white;">
				<thead>
					<tr>
						<th width="20" ></th>
						<th >渠道名称</th>
						<th width="150" >渠道编码</th>
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
							<a class="edit" href="${ctx }/petservice/report/channelDictAddOrEdit.html?id=${itm.id}" target="dialog" width="500" height="300" rel="channelDictList">
								${itm.alias }
							</a>
						</td>
						<td>${itm.code }</td>
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

