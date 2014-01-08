<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<script type="text/javascript">
$(function () {

	$("#specialGetNoteFormReturn").click(function () {
		var noteId = $("#specialGetNoteId").val();
		$.ajax({url: '${ctx }/petservice/bbs/getNoteById.html', 
			type: 'POST', 
			data:{id:noteId}, 
			dataType: 'text', 
			timeout: 1000,
			success: function(result){
				var j = JSON.parse(result);
				$.bringBack(j);
			}
		});
    });
	
});

</script>

<div class="pageHeader" id="specialGetNote" />
	
	<input type="hidden" id="specialGetNoteId" />

	<div class="searchBar">
		<form method="post" action="${ctx }/petservice/bbs/specialGetNote.html" onsubmit="return dwzSearch(this, 'dialog');" id="specialGetNoteForm">
			圈子：
			<select class="combox"  ref="specialGetNoteForum" refUrl="${ctx }/petservice/bbs/forumMain.html?rtnType=js_arr&pid={value}">
				<option value="all">--请选择--</option>
				<c:forEach var="itm" items="${forumList }">
						<option value="${itm.id }">${itm.name }</option>
					</c:forEach>
			</select>
			<select class="combox" name="forumId" id="specialGetNoteForum">
				<option value="">--请选择--</option>
			</select>
		
			<button type="submit">查询</button>
			<button type="button" class="close">关闭</button>
		</form>
	</div>
</div>
<div class="pageContent" id="specialGetNoteList">
	<table class="table" width="100%" layoutH="115">
		<thead>
			<tr>
				<th width="20"></th>
				<th>帖子名称</th>
				<th width="150">圈子名称</th>
				<th width="150">创建人</th>
				<th width="150">最后修改时间</th>
				<th width="150">创建时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.data }" var="itm" varStatus="idx">
			<tr height="35" align="left" onclick="$('#specialGetNoteId').val('${itm.id}')" >
				<td>${idx.index+1 }</td>
				<td>${itm.name }</td>
				<td>${itm.forumName }</td>
				<td>${itm.nickname }</td>
				<td align="center">
					<fmt:formatDate value="${itm.et }" type="both" />
				</td>
				<td align="center">
					<fmt:formatDate value="${itm.ct }" type="both" />
				</td>
			</tr>
		</c:forEach>
		</tbody>
		
	</table>
	<pet:page form="specialGetNoteForm" pageBean="${page }" pageSize="${page.pageSize }" />
</div>
<div class="formBar">
	<ul>
		<c:if test="${empty errorMsg}">
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="specialGetNoteFormReturn">确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
		</c:if>
	</ul>
</div>