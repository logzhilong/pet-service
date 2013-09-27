<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<div>
	<div style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
	    <jsp:include page="/manager/bbs/forumManagerMain.html" >
	    	<jsp:param name="target" value="left" />
	    </jsp:include>
	</div>
	
	<div id="jbsxBox" class="unitBox" style="margin-left:246px;">
		<%-- ctype 和 eid 在打开这个页面的URL里传递过了，这里不必再传 
	    <jsp:include page="/manager/bbs/forumManagerMain.html">
  	    	<jsp:param name="target" value="right" />
	    </jsp:include>
		--%>
	</div>
</div>