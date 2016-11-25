/**
 * 页面DIV布局加载
 */
function loadPage(obj,url){
	jQuery(obj).load(url,
		function(response,status,xhr){
			if(xhr.status == 911){
				bootbox.confirm("当前会话已超时，请重新登录",function (result) {
					if(result){
						/*window.location.href = '../login.jsp';*/
						window.location.href = '../'
					}
				});
			}
			$('[data-rel="tooltip"]').tooltip();
		}
	)
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
			$('[data-rel="tooltip"]').tooltip();
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			if(XMLHttpRequest.status == 911){
				bootbox.confirm("当前会话已超时，请重新登录",function (result) {
					if(result){
						/*window.location.href = '../login.jsp';*/
						window.location.href = '../';
					}
				});
			}
			$('[data-rel="tooltip"]').tooltip();
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

//Add by xiukun
var sjny = sjny || {};
sjny.admin = sjny.admin || {};
sjny.admin.comm = {
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
	},
	globalBootstrapValidator: function(){
		//添加全局校验，方案来源http://www.jb51.net/article/75996.htm
		/*var overwrite = $.fn.bootstrapValidator;
		$.fn.bootstrapValidator = function(options){
			$.fn.bootstrapValidator = overwrite;
			var validtor = overwrite.apply(this,arguments);
			if($.type(arguments[0]) == "object"){
				var vtor = this.data("bootstrapValidator"),
					fileds = this.find("input[name][type='hidden'],input[name][type='password'],input[name][type='text'],textarea[name]").not(":disabled,[type='hidden']");
				fileds.each(function(){
					if(!vtor.getOptions($(this).attr('name'),'regexp','regexp')){
						vtor.addField($(this).attr('name'),
						{
							validators: {
								regexp: {
									regexp: /^[^&]*$/,
									message: "不能包含&字符"
								}
							}
						})
					}
				})
			}
			return validtor;
		}*/
	}
};

$(document).ready(function() {
	sjny.admin.comm.selectedSubMenuItem();
	sjny.admin.comm.globalBootstrapValidator();
});

function compareDate(d1,d2){
	return Date.parse(new Date(d1)) > Date.parse(new Date(d2));
}

//比较日期是否大于31天
var compareDateMonth=function(d1,d2){
	if(d1=='' || d2==''){return 0;}
	var d1arry=new Array();
	var d2arry=new Array();
	d1arry =d1.split(' ');
	d2arry =d2.split(' ');
	var a = new Date(d1arry[0]).getTime();
	var b= new Date(d2arry[0]).getTime();
	var c = 60*60*24*1000;
	var res = (b - a)/c;
	if(parseInt(res)>31){
		return 0;
	}
	return 1;
};
//console.log(compareDateMonth('2016-08-24 10:51:18','2016-09-24 10:51:18'));
