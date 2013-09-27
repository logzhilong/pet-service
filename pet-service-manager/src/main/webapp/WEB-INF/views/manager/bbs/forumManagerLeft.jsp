<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<div id="forumManagerLeft" layoutH="20" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
<script>
    jQuery(function($) {
    	var ctx = "${ctx}";
        //var data = [{"id":1,"pid":0,"name":"aaa"},{"id":2,"pid":0,"name":"bbb"},{"id":3,"pid":2,"name":"ccc"},{"id":4,"pid":3,"name":"ddd"},{"id":5,"pid":2,"name":"eee"},{"id":6,"pid":5,"name":"fff"}] 
        var data = eval('${tree}');
        $("#forumManagerLeft").html(getTree(data,'0',ctx).replaceAll("<ul></ul>",""));
    });
</script>
</div>
