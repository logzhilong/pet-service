<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<%--循环遍历平台树--%>
<script> 
    function getTree(data,pid){ 
        var tree; 
        if(pid == '0'){ 
            tree = '<ul class="tree treeFolder">';
        }else{
            tree = '<ul>'; 
        }
        for(var i in data){
            if(data[i].pnode == pid){
				if(data[i].href=='#' || data[i].href==null ){
                	tree += "<li><a title='"+data[i].name+"' href='#' >"+data[i].name+"</a>"; 
				}else{
					tree += "<li><a title='"+data[i].name+"' href='${ctx}"+data[i].href+"' target='navTab' rel='panel"+data[i].name+"'>"+data[i].name+"</a>"; 
				}
                tree += getTree(data,data[i].node);
                tree += "</li>";
            }
        }
        return tree+"</ul>"; 
    }
    jQuery(function($) {
        //var data = [{"id":1,"pid":0,"name":"aaa"},{"id":2,"pid":0,"name":"bbb"},{"id":3,"pid":2,"name":"ccc"},{"id":4,"pid":3,"name":"ddd"},{"id":5,"pid":2,"name":"eee"},{"id":6,"pid":5,"name":"fff"}] 
        var data = eval('${funTree}');
        $("#left_1").html(getTree(data,'0').replaceAll("<ul></ul>",""));
    }); 
</script>