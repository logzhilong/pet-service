<div class="pate">
	<a
		<#if (pageData.totalPagesIf > 0) >
			href="javascript:pageFun${pageData.idx}.gotoPage(1);"
		<#else>
			href="javascript:void(0);"
		</#if>
	>首页</a>
		
	<a
		<#if (pageData.pageNoIf > 1) >
			href="javascript:pageFun${pageData.idx}.gotoPage(${pageData.pageNo-1 });"
		<#else>
			href="javascript:void(0);"
		</#if>
	>上一页 </a>

	<a
		<#if (pageData.pageNoIf < pageData.totalPagesIf ) >
			href="javascript:pageFun${pageData.idx}.gotoPage(${(pageData.pageNo?int)+(1?int) });"
		<#else>
			href="javascript:void(0);"
		</#if>
	>下一页</a>
   
	<a
		<#if ( pageData.totalPagesIf > 0 ) >
			href="javascript:pageFun${pageData.idx}.gotoPage(${pageData.totalPages });"
		<#else>
			href="javascript:void(0);"
		</#if>
	>尾页</a>
	<span class="shop_page_total">
		共<b>[${pageData.totalCount }]</b>条记录
			<b>[${pageData.totalPages }]</b>页
	</span>
	&nbsp;当前显示第<b> [${ pageData.pageNo}]</b>页&nbsp;
	到
	<input type="text" id="jumpto${pageData.idx}" size="4" maxlength="9" class="pa" value="" />
	页
	<input type="button" onclick="pageFun${pageData.idx}.jump(document.getElementById('jumpto${pageData.idx}').value);" value="确定" />
</div>

<script type="text/javascript">

var formObj${pageData.idx} = null;

function pageInit${pageData.idx}(){
	
}

var pageFun${pageData.idx} = {
	gotoPage:function(num){
		pageInit${pageData.idx}();
		//$('#pageNo').val(num);
		//$('#pageSize').val(${pageData.pageSize});
		var action = $('#${pageData.form}').attr('action');
		action=action+"?pageNo="+num+"&pageSize="+${pageData.pageSize};
		$('#${pageData.form}').attr('action',action);
		$('#${pageData.form}').submit();
	},
	jump:function(num){
		pageInit${pageData.idx}();
		var minPage = 1;
		//modify by jin
		var maxPage = '${pageData.totalPages}';
		if (/[^\d]/.test(num)){
			alert("请输入数字");
			return false;
		}
		if (Number(num) > Number(maxPage)) {
			alert("不能超过最大页数["+ maxPage+ "].");
			return false;
		}
		if (Number(num) < Number(minPage)) {
			alert("不能小于最小页数["+ minPage+ "].");
			return false;
		}
		//$('#pageNo').val(num);
		//$('#pageSize').val(${pageData.pageSize});
		var action = $('#${pageData.form}').attr('action');
		action=action+"?pageNo="+num+"&pageSize="+${pageData.pageSize};
		$('#${pageData.form}').attr('action',action);
		$('#${pageData.form}').submit();
	}
}
</script>
