<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<div class="pageContent" selector="h1" layoutH="2">
<div id="adsList">

<div class="panel" defH="600" id="adsList_panel">
	
	<h1>系统广告</h1>
	
	<div>
		<div class="panelBar">
			<ul class="toolBar">
				
				<li>
					<a class="add" href="${ctx }/petservice/ads/adsAddOrEdit.html" target="dialog" mask="true" title="添加" width="700" height="750" ><span>添加</span></a>				
				</li>
				<li class="line">line</li>
				
				<li>
					<a class="edit" href="${ctx }/petservice/ads/adsAddOrEdit.html?id={id}" target="dialog" warn="请选择" width="700" height="750" ><span>修改</span></a>
				</li>
				<li class="line">line</li>
				
			</ul>
		</div>
		<div>
			<table width="100%" class="list" border="0" style="background-color: white;">
				<thead>
					<tr>
						<th width="25"></th>
						<th width="640">广告图</th>
						<th>状态</th>
						<th>创建人</th>
						<th>创建日期</th>
						<th>修改人</th>
						<th>修改日期</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.data }" var="itm" varStatus="idx">
					<tr height="30" align="left" target="id" rel="${itm.id }" >
						<td>${idx.index+1 }</td>
						<td>
							<a class="edit" href="${ctx }/petservice/ads/adsAddOrEdit.html?id=${itm.id}" target="dialog" title="修改" warn="请选择" width="700" height="750" >
								<img src="${pet_file_server }/get/${itm.img }" width="640" height="120" />
							</a>
						</td>
						<td>${itm.state }</td>
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
<%-- "adsList_panel" --%>

</div>
<%-- "adsList" --%>

