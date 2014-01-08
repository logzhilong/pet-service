<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<script type="text/javascript">
$(function () {

	$("#specialGetForumFormReturn").click(function () {
		var id = $("#specialGetForumForum").attr('value');
		var text = $("#specialGetForumForum").find("option:selected").text();
		$.bringBack({forumId:id,forumName:text});
	});
	
});

</script>
<div layoutH="35">
	<select class="combox"  ref="specialGetForumForum" refUrl="${ctx }/petservice/bbs/forumMain.html?rtnType=js_arr&pid={value}">
		<option value="all">--请选择--</option>
		<c:forEach var="itm" items="${forumList }">
			<option value="${itm.id }" >${itm.name }</option>
		</c:forEach>
	</select>
	<select class="combox" name="forumId" id="specialGetForumForum">
		<option value="">--请选择--</option>
	</select>
</div>
<div class="formBar">
	<ul>
		<c:if test="${empty errorMsg}">
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="specialGetForumFormReturn">提交</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
		</c:if>
	</ul>
</div>