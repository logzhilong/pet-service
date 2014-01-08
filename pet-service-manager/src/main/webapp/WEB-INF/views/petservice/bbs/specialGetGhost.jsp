<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<script type="text/javascript">
$(function () {
	$("#specialGetGhostFormReturn").click(function () {
		var userId = $("#specialGetGhostId").val();
		var nickname = $("#specialGetGhostName").val();
		$.bringBack({userId:userId,nickname:nickname});
    });
});
</script>
<input type="hidden" id="specialGetGhostId" />
<input type="hidden" id="specialGetGhostName" />
<div class="pageContent" id="specialGetGhostList">
	<table class="table" width="100%" layoutH="55">
		<thead>
			<tr>
				<th width="20"></th>
				<th>昵称</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list }" var="itm" varStatus="idx">
			<tr height="35" align="left" onclick="$('#specialGetGhostId').val('${itm.userId}');$('#specialGetGhostName').val('${itm.nickname}')" >
				<td>${idx.index+1 }</td>
				<td>${itm.nickname }</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
<div class="formBar">
	<ul>
		<c:if test="${empty errorMsg}">
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="specialGetGhostFormReturn">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
		</c:if>
	</ul>
</div>