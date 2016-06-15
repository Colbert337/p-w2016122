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
	}else{
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
function orderBy(obj, order){

	if($("#orderby").val() == "" || $("#orderby").val() == null){
		$("#orderby").val(order+" desc");
		$(obj).append("<i id='card_status' class='glyphicon glyphicon-chevron-down'>");
	}else{
		var tmp = $("#orderby").val().split(" ");
		if(tmp.length ==2){
			if(tmp[1] == "asc"){
				tmp[1] = "desc";
			}else{
				tmp[1] = "asc";
			}

			$("#orderby").val(order+" "+tmp[1]);
		}
	}
}

/**
 * 设置当前页分页控件样式，请注意页面DOM对象的ID与此公用方法的ID需要一致
 */
function setCurrentPage(){
	var pagenum = parseInt($("#pageNum").val());
	var pageNumMax = parseInt($("#pageNumMax").val());

	if(pagenum == 1){
		$("#previous").attr("class","disabled");
	}else if(pagenum == pageNumMax){
		$("#next").attr("class","disabled");
	}else{
		$("#previous").removeClass("disabled");
		$("#next").removeClass("disabled");
	}
	//动态加载分页按钮并设定页数
	for(var i=5;i>0;i--){
		var num = pagenum%5==0?pagenum-5+i:pagenum-(pagenum%5)+i;
		$("li[id=previous]").after("<li id='navigator'><a href='javascript:void(0);' onclick='commitForm(this)'>"+num+"</a></li>");
	}
	//设置当前页按钮样式
	$("li[id=navigator]").removeClass("active");
	$("li[id=navigator]").each(function(){
		if($("#pageNum").val() == $(this).find("a").html()){
			$(this).attr("class","active");
		}

		if(parseInt($(this).find("a").text())>pageNumMax){
			$(this).find("a").attr("style","display:none");
		}
	});

	//设置orderby箭头样式
	if($("#orderby").val() !="" && $("#orderby").val() != null){
		var tmp = $("#orderby").val().split(" ");
		if(tmp.length == 2){
			if(tmp[1] == "asc"){
				$("#"+tmp[0]+"_order").append("<i id='card_status' class='glyphicon glyphicon-chevron-up'>");
			}else{
				$("#"+tmp[0]+"_order").append("<i id='card_status' class='glyphicon glyphicon-chevron-down'>");
			}
		}

	}
}

function prepage(formid){
	//如果是第一页
	if(parseInt($("#pageNum").val()) <= 1){
		return ;
	}

	//设置当前页-1
	$("#pageNum").val(parseInt($("#pageNum").val())-1);

	$(formid).ajaxSubmit(listOptions);
}

function nextpage(formid){
	//如果是最后一页
	if(parseInt($("#pageNum").val()) >= parseInt($("#pageNumMax").val())){
		return ;
	}
	//设置当前页+1
	$("#pageNum").val(parseInt($("#pageNum").val())+1);
	$(formid).ajaxSubmit(listOptions);
}

function compareDate(d1,d2){
	return Date.parse(new Date(d1)) > Date.parse(new Date(d2));
}

//Add by xiukun
var sjny = sjny || {};
sjny.admin = sjny.admin || {};
sjny.admin.comm = {
	clickShowModal: function() {
		$('#testModal').on('click', function(){
			$('#myModal').modal({
				backdrop: 'static',
				keyboard: false
			});
		});
	},
	selectedSubMenuItem: function() {
		$('.nav-list > li').on('click', '.submenu > li', function(){
			var $this = $(this),
				$parent = $this.parents('li');
			function isActive(){
				$this.addClass('active').siblings().removeClass('active');
				$parent.addClass('active').siblings().removeClass('active');
				$parent.addClass('open').siblings().removeClass('open');
				$parent.siblings().find('.submenu').removeClass('nav-show').hide();
				$parent.siblings().find('li').removeClass('active');
			}
			setTimeout(isActive,10);
		});
	}
};

$(document).ready(function() {
	sjny.admin.comm.clickShowModal();
	sjny.admin.comm.selectedSubMenuItem();
});