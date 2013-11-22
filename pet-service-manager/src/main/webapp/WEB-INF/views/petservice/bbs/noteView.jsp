<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>

<h2 class="contentTitle">${myForm.name }</h2>

<div class="pageContent" layoutH="42">
	<div style="border:1px solid #B8D0D6;padding:5px;margin:5;width: 400px;" >
		<table style="width: 100%;font-size: 15pt;background-color:#E6E6FA;" >
				<tr>
					<td align="left">
						<img alt="" src="${myForm.icon }" width="25" height="25" />
						${myForm.nickname }
					</td>
					<td align="right">
						<a class="add" href="${ctx }/petservice/bbs/reply.html?noteId=${myForm.id}" target="dialog" mask="true" title="回复帖子" width="650" height="570" >
							<button>回复</button>
						</a>
						楼主
					</td>
				</tr>
		</table>
	</div>
	<div style="border:1px solid #B8D0D6;padding:5px;margin:5;width: 400px;">
		<c:out value="${myForm.content }" escapeXml="false" />
	</div>
	<hr />
	<c:forEach items="${reply }" var="itm" >
		<div style="border:1px solid #B8D0D6;padding:5px;margin:5;width: 400px;" >
			<table style="width: 100%;font-size: 15pt;background-color:#E6E6FA;" >
				<tr>
					<td align="left">
						<img alt="" src="${itm.userIcon }" width="25" height="25" />
						${itm.nickname }
					</td>
					<td align="right">
						<a class="add" href="${ctx }/petservice/bbs/reply.html?noteId=${myForm.id}&pid=${itm.id}" target="dialog" mask="true" title="回复帖子" width="650" height="570" >
							<button>回复</button>
						</a>
						楼层：${itm.seq+1 }
					</td>
				</tr>
			</table>
		</div>
		<div style="border:1px solid #B8D0D6;padding:5px;margin:5;width: 400px">
			<c:out value="${itm.content }" escapeXml="false" />
		</div>
	</c:forEach>

</div>