<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>


<div class="tabsContent">

	<div style="float: left; display: block; overflow: auto; width: 240px;height: 359px; border: 1px solid rgb(204, 204, 204); line-height: 21px; background: none repeat scroll 0% 0% rgb(255, 255, 255);" layouth="30">
		<tbody>
			<ul class="tree treeFolder">
				<div class="selected">
					<div class="end_collapsable"></div>
					<div class="folder_collapsable"></div>
					<a href="javascript">圈子</a> 
				</div>
				<c:forEach items="${forumss }" var="itm" varStatus="idx">
					<tr target="id" rel="${itm.id }">
						<li><a title="${itm.name }"
							href="${ctx }/manager/bbs/forumrightmanagelist.html?id={333}" target="ajax" rel="jbsxBoxmm" param="${itm.id }">${itm.name}
							</a>
						</li>
					</tr>
				</c:forEach>
			</ul>
		</tbody>
	</div>
</div>

