/**
 * 页面DIV布局加载
 */
function loadPage(obj,url){
	jQuery(obj).load(url);
}

/**
 * ajaxDIV跳转，POST
 */
function ajaxQueryData(obj,url,datatype){
	$.ajax({
		   type: "POST",
		   url: url,
		   dataType: datatype, 
		   //cache:false,
		   success: function(msg){
			   $(obj).html(msg);
		   },
			error:function(){
				alert('加载页面' + url + '时出错！');
			}
		});
}

/**
 * 选中表格中所有checkbox
 * 
 * pram obj(表格行头checkbox对象)
 */
function checkedAllRows(obj){
	if($(obj).is(":checked")){
		$(obj).parents('table').find("tbody").find('tr').each(function (){
			$(this).children("td:first").find("input[type='checkbox']").prop('checked',true);
		});
	}else{console.debug("2");
		$(obj).parents('table').find("tbody").find('tr').each(function (){
			$(this).children("td:first").find("input[type='checkbox']").attr('checked',false);
		});
	}
}

/**
 * 选中表格中所有checkbox
 * 
 * pram obj(表格排序字段)
 */
function orderBy(obj,order){
	if($(obj).find('i').attr("class") == 'glyphicon glyphicon-chevron-up'){
		$(obj).find('i').attr("class","glyphicon glyphicon-chevron-down");
	}else{
		$(obj).find('i').attr("class","glyphicon glyphicon-chevron-up");
	}
	
}