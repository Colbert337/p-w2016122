$('#j-input-daterange-top').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});

jQuery(function($) {
	var $overflow = '';
	var colorbox_params = {
		rel: 'colorbox',
		reposition:true,
		scalePhotos:true,
		scrolling:false,
		previous:'<i class="ace-icon fa fa-arrow-left"></i>',
		next:'<i class="ace-icon fa fa-arrow-right"></i>',
		close:'&times;',
		current:'{current} of {total}',
		maxWidth:'100%',
		maxHeight:'100%',
		onOpen:function(){
			$overflow = document.body.style.overflow;
			document.body.style.overflow = 'hidden';
		},
		onClosed:function(){
			document.body.style.overflow = $overflow;
		},
		onComplete:function(){
			$.colorbox.resize();
		}
	};

	$('.ace-thumbnails [data-rel="colorbox"]').colorbox(colorbox_params);
	$("#cboxLoadingGraphic").html("<i class='ace-icon fa fa-spinner orange fa-spin'></i>");//let's add a custom loading icon
	
	
	$(document).one('ajaxloadstart.page', function(e) {
		$('#colorbox, #cboxOverlay').remove();
   });
})

var listOptions ={   
            url:'../web/driver/driverList',
            type:'post',                    
            dataType:'html',
            success:function(data){
	              $("#main").html(data);
	              if($("#retCode").val() != "100"){
		            	 //$("#modal-table").modal("show");
		          }
            },error:function(XMLHttpRequest, textStatus, errorThrown) {
            
	       }
	}
	
	window.onload = setCurrentPage();
	
	function commitForm(obj){
		//设置当前页的值
		if(typeof obj == "undefined") {
			$("#pageNum").val("1");
		}else{
			$("#pageNum").val($(obj).text());
		}
		
		$("#formdriver").ajaxSubmit(listOptions);
	}
	
	function review(driverid,type,memo){
				
		var deloptions ={   
	            url:'../web/driver/review?driverid='+driverid+'&type='+type+'&memo='+memo,   
	            type:'post',                    
	            dataType:'text',
	            success:function(data){
		            $("#main").html(data);
		            $("#modal-table").modal("show");
	            },
	            error:function(XMLHttpRequest, textStatus, errorThrown) {
	            	
	            }
		}
		$("#gridSystemModalLabel").text("输入审核备注");
		$("#formdriver").ajaxSubmit(deloptions);
	}
	
	function init(){
		loadPage('#main', '../web/driver/driverList');
	}
	
	function showInnerModel(obj,type){
		
		if(type==null ||type==''){
			$("#optionbutton").css('display','none');
		}else{
			$("#optionbutton").css('display','block');
		}
		
		var driverid = $(obj).parents('tr').find("td:first").find("input").val();
		$("#objid").val(driverid);
		$("#objval").val(type);
		$("[name=remark]").val($(obj).parents('tr').children("td").eq(10).text());
		$("#driving_lice").attr("src",$(obj).parents('tr').children("td").eq(11).text());
		$("#driving_lice").parent("a").attr("href",$(obj).parents('tr').children("td").eq(11).text());
		$("#vehicle_lice").attr("src",$(obj).parents('tr').children("td").eq(12).text());
		$("#vehicle_lice").parent("a").attr("href",$(obj).parents('tr').children("td").eq(12).text());
		$("#innerModel").modal('show');
	}
	
	function hideInnerModel(){
		$("#innerModel").modal('hide').removeClass('in');
		$("body").removeClass('modal-open').removeAttr('style');
		$(".modal-backdrop").remove();
	}
	
	function addMemo(){
		review($("#objid").val(),$("#objval").val(),$("[name=remark]").val());
		hideInnerModel();
	}