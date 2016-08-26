	$('#j-input-daterange-top').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});
	
	var listOptions ={   
            url:'../web/gastationMap/gastationMapList',
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
	
		$('.gastation-log-colorbox').colorbox(colorbox_params);
		$("#cboxLoadingGraphic").html("<i class='ace-icon fa fa-spinner orange fa-spin'></i>");//let's add a custom loading icon
		
		
		$(document).one('ajaxloadstart.page', function(e) {
			$('#colorbox, #cboxOverlay').remove();
	   });
	})
	
	function preUpdate(obj){
		var stationid = $(obj).parents("tr").find("td:first").find("input").val();
		loadPage('#main', '../web/gastation/preUpdate?gastationid='+stationid);
	}
	
	function preDeposit(obj){
		var stationid = $(obj).parents("tr").find("td:first").find("input").val();
		var stationame = $(obj).parents("tr").children("td").eq(2).text();
		var stationdeposit = $(obj).parents("tr").children("td").eq(10).text();
		var acconutid = $(obj).parents("tr").children("td").eq(11).text();
		
		loadPage('#main', '../webpage/poms/gastation/gastation_deposit.jsp?acconutid='+acconutid+'&gastationame='+stationame+'&gastationdeposit='+stationdeposit+'&stationid='+stationid);
	}
	
	function deleteGasMap(id){
//		console.log(id);
		bootbox.setLocale("zh_CN");
		bootbox.confirm("确认要删除数据吗？", function(result) {
			if (result) {
				var deleteOptions = {
					url : '../web/gastationMap/delete',
					data : {
						id : id
					},
					type : 'post',
					dataType : 'text',
					success : function(data) {
						$("#main").html(data);
						$("#modal-table").modal("show");
						$('[data-rel="tooltip"]').tooltip();
					}
				}
				$("#formgastation").ajaxSubmit(deleteOptions);
			}
		})
	}
	function openImportDiv(){
	    $("#importDivModel").modal("show");
	}
	
	/**
	 *导入文件
	 */
	function saveTemplate(){
	    var multipartOptions ={
	        url:'../web/gastationMap/file',
	        type:'post',
	        dataType:'json',
	        enctype:"multipart/form-data",
	        success:function(data){
	            /*bootbox.alert("操作成功！");*/
//	            $("#main").html(data);
//	            $("#modal-table").modal("show");
//	            alert($("#retCode").val());
	            if (data.SUCCESS == true) {
	            	bootbox.alert(data.datas);
	            	init();
	    			//$("#modal-table").modal("show");
	    		}
	        },error:function(XMLHttpRequest, textStatus, errorThrown) {
	        	bootbox.alert("操作失败！");
	        }
	    }
	    $("#importForm").ajaxSubmit(multipartOptions);
	    $("#importDivModel").modal('hide').removeClass('in');
	    $("body").removeClass('modal-open').removeAttr('style');
	    $(".modal-backdrop").remove();
	}
	
	/*取消弹层方法*/
	function closeDialog(divId){
	    jQuery('#editForm').validationEngine('hide');//隐藏验证弹窗
	    $("#editForm :input").each(function () {
	        $(this).val("");
	    });
	    $("#avatar_b").empty();
	    $("#"+divId).modal('hide');
	}
	function clearDiv(){
	    $("#editForm :input").each(function () {
	        $(this).val("");
	    });
	    $("#avatar_b").empty();
	}

	
	function commitForm(obj){
		//设置当前页的值
		if(typeof obj == "undefined") {
			$("#pageNum").val("1");
		}else{
			$("#pageNum").val($(obj).text());
		}
		
		var options ={   
	            url:'../web/gastationMap/gastationMapList',
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
		
		$("#formgastation").ajaxSubmit(options);
	}
	
	function del(obj){
		var cardid = $(obj).parents('tr').find("td:first").find("input").val();
		
		var deloptions ={   
	            url:'../web/gastation/deleteGastation?gastationid='+cardid,   
	            type:'post',                    
	            dataType:'text',
	            success:function(data){
		             $("#main").html(data);
		             $("#modal-table").modal("show");
	            },
	            error:function(XMLHttpRequest, textStatus, errorThrown) {
	            	
	            }
		}
		
		$("#formgastation").ajaxSubmit(deloptions);
	}
	
	
	function init(){
		loadPage('#main', '../web/gastationMap/gastationMapList');
	}
	
	function showInnerModel(obj1,obj2,obj3,obj4){
		$("#innerimg1").attr("src",obj1);
		$("#innerimg1").parent("a").attr("href",obj1);
		$("#innerimg2").attr("src",obj2);
		$("#innerimg2").parent("a").attr("href",obj2);
		$("#innerimg3").attr("src",obj3);
		$("#innerimg3").parent("a").attr("href",obj3);
		$("#innerimg4").attr("src",obj4);
		$("#innerimg4").parent("a").attr("href",obj4);
		
		$("#innerModel").modal('show');
	}