// 获取当前网址，如： http://localhost:8080/Tmall/index.jsp
var curWwwPath = window.document.location.href;
// 获取主机地址之后的目录如：/Tmall/index.jsp
var pathName = window.document.location.pathname;
var pos = curWwwPath.indexOf(pathName);
// 获取主机地址，如： http://localhost:8080
var localhostPaht = curWwwPath.substring(0, pos);
// 获取带"/"的项目名，如：/Tmall
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);


$('#j-input-daterange-top').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});
	
	var listOptions ={   
            url:'../web/mobile/road/roadList',
            type:'post',                    
            dataType:'html',
            success:function(data){
	              $("#main").html(data);
	              if($("#retCode").val() != "100"){
	            	  $("#modal-table").modal("show");
		          }
            },error:function(XMLHttpRequest, textStatus, errorThrown) {
            
	       }
	}
	
	window.onload = setCurrentPage();
	
	function del(obj){
		
		bootbox.setLocale("zh_CN");
		bootbox.confirm("是否删除该条数据?", function(result) {
			if(result){
				var messageid = $(obj).parents("tr").find("td:first").find("input").val();
				loadPage('#main', '../web/message/deleteMessage?messageid='+messageid);
			}
			
		})
		
	
	}
	
	function commitForm(obj){
		//设置当前页的值
		if(typeof obj == "undefined") {
			$("#pageNum").val("1");
		}else{
			$("#pageNum").val($(obj).text());
		}
		
		$("#formessage").ajaxSubmit(listOptions);
	}
	function closeDialog(obj){
		$("#" + obj).modal('hide').removeClass('in');
		$("body").removeClass('modal-open').removeAttr('style');
		$(".modal-backdrop").remove();
//		init();
		
	}
	function init(){
		loadPage('#main', '../web/mobile/road/roadList');
	}
	function updateCheck(obj1,tr) {
		console.log('111');
		var show = $("div[name='show']");
		for (var i = 0; i < show.length; i++) {
			show[i].innerHTML = tr.children('td').eq(i).text().replace(/(.{28})/g,
				'$1\n');
		}
		$("#innerimg1").attr("src", projectName+obj1);
		$("#innerimg1").parent("a").attr("href",projectName+ obj1);
		$("#innerModel").modal('show');
		
	}
	
	function showUser(id){
 
		 loadPage('#content', '../web/message/showUser?id='+id);
			$("#editModel").modal('show');
	}