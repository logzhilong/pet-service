/**
 * 圈子用到的脚本
 */
var forum = (function(){

	var datas = {};
	
	function initDatas( eid ){
		datas.eid = eid;
	}
	
	function alertDefineTbodyAppend( adv ){
		var active = '否';
		if( adv.active ){
			active = '是'
		}
		var row = 	'<tr target="aid" rel="'+adv.id+'">'+
		'	<td>'+adv.name+'</td>'+
		'	<td>'+adv.description+'</td>'+
		'	<td align="center" >'+adv.ctime+'</td>'+
		'	<td align="center" >'+adv.mtime+'</td>'+
		'	<td align="center" >'+active+'</td>'+
		'</tr>';
		$("#alertDefineTbody").append( row );
	}

	function reload(dialogId,queryParam){
		var dialog = $("body").data(dialogId);
        if(dialog){
            var u = dialog.data("url")+"&"+queryParam;
            $.pdialog.reload( u , { dialogId: dialogId } );
        }
	}
	
	return {
		refresh : function( eid ) {//作为关闭dialog的回调
			return true;
		},
		queryAlertsByDate : function(queryDate){//第一个 TAB 的检索
			reload("alert_","cindex=0&queryDate="+queryDate);//刷新dialog
		},
		gotoEdit : function() {
			reload("alert_dialog","action=edit");//刷新dialog
		},
		checkRow : function(id) {
			if( $("#"+id).attr('checked')=='checked' ){
				$("#"+id).attr('checked',false)
			}else{
				$("#"+id).attr('checked',true)
			}
		},
		checkAll : function(id,name) {
			if( $("#"+id).attr('checked')=='checked' ){
				$("input[name='"+name+"']").attr("checked",true);  
			}else{
				$("input[name='"+name+"']").attr("checked",false);
			}
		},
		deleteAlert : function( name ){
			alertMsg.confirm("您确认删除么？", {
				okCall: function(){
					var arrChk=$("input[name='"+name+"']:checked");
					//var aids = new Array();
				    var aids = '';
				    $(arrChk).each(function(){
					    //aids.push(this.value);
						aids = aids + this.value + ','
					});
				    if( aids.length>0 ){
						$.ajax({
							url : "${ctx }/alerts/delete.html",
							data : {aids: aids , eid:'${eid}' },
							type : "POST",
							dataType : "json",
							success : function(result) {
								if(result.rtn){
									alertMsg.correct(result.msg);
									reload("alert_","cindex=1");//刷新dialog 第1个 tab
								}else{
									alertMsg.error(result.msg);
								}
							}
						});
					}
				}
			});
		},
		addSubmit : function() {
			//提交
			$.ajax({
				url : "${ctx }/alerts/saveOrUpdate.html",
				data : $('#addAlertForm').serialize(),
				type : "POST",
				dataType : "json",
				success : function(result) {
					if(result.rtn){
						alertMsg.correct(result.msg);
						$.pdialog.close("alert_dialog");//关闭新增页面
						reload("panel0101","cindex=1");//刷新dialog
					}else{
						alertMsg.error(result.msg);
					}
				}
			});
		}
	}
})();

/**
 * 没特么用
 */
var areaCode = (function(){
	var datas = {
	};
	function reload(dialogId,queryParam){
		var dialog = $("body").data(dialogId);
        if(dialog){
            var u = dialog.data("url")+"&"+queryParam;
            $.pdialog.reload( u , { dialogId: dialogId } );
        }
	}	
	return {
		addSubmit : function(ctx,formId) {
			$.ajax({
				url : ctx+"/alerts/areaCodeSaveOrUpdate.html",
				data : $('#'+formId).serialize(),
				type : "POST",
				dataType : "json",
				success : function(result) {
					if(result.rtn){
						alertMsg.correct(result.msg);
						$.pdialog.close("areaCode_add_dialog");//关闭新增页面
					}else{
						alertMsg.error(result.msg);
					}
				}
			});
		}		
	}
})();

/**
 * 这会根据 data 展开一颗树，具体渲染到哪，需要自己指定，例如 
 * 
 * $("#left_1").html(getTree(data,'0').replaceAll("<ul></ul>",""));
 * 
 * @param data
 * @param pid
 * @returns {String}
 */
function getTree(data,pid,ctx){ 
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
				tree += "<li><a title='"+data[i].name+"' href='"+ctx+data[i].href+"' target='navTab' rel='panel"+data[i].node+"'>"+data[i].name+"</a>"; 
			}
            tree += getTree(data,data[i].node,ctx);
            tree += "</li>";
        }
    }
    return tree+"</ul>"; 
}