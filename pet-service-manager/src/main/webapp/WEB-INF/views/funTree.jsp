<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<%--循环遍历平台树--%>
<script>
    jQuery(function($) {
    	var ctx = "${ctx}";
        //var data = [{"id":1,"pid":0,"name":"aaa"},{"id":2,"pid":0,"name":"bbb"},{"id":3,"pid":2,"name":"ccc"},{"id":4,"pid":3,"name":"ddd"},{"id":5,"pid":2,"name":"eee"},{"id":6,"pid":5,"name":"fff"}] 
        var data = eval('${funTree}');
        $("#left_1").html(getTree(data,'0',ctx).replaceAll("<ul></ul>",""));
    }); 
</script>