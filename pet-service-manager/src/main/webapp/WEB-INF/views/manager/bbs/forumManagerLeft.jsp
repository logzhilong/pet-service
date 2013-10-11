<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>


<div class="tabsContent">

<div style="text-align:left; display: block; overflow: auto; width: 150spx; border: 1px solid rgb(204, 204, 204); line-height: 21px; background: none repeat scroll 0% 0% rgb(255, 255, 255); height: auto;" layouth="10">
	<tbody id="alertListTbody" >
			<c:forEach items="${forumss }" var="itm" varStatus="idx">
				<tr target="id" rel="${itm.id }">
					<td>${idx.index+1 }</td>
					<li>
						<a title="${itm.name }" href="${ctx }/manager/bbs/forumrightmanagelist.html?id={333}" target="ajax" rel="jbsxBoxmm" param="${itm.id }" >${itm.name }</a>								
					</li>
				</tr>
			</c:forEach>
		</tbody>
	</div>
</div>

