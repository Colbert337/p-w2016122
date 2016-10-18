	$('#j-input-daterange-top').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});
	
	var listOptions ={   
            url:'../web/gastation/gastationList',
            type:'post',                    
            dataType:'html',
            success:function(data){
	              $("#main").html(data);
	              if($("#retCode").val() != "100"){
		            	 //$("#modal-table").modal("show");
		          }
            },error:function(XMLHttpRequest, textStatus, errorThrown) {
            	 /*if (XMLHttpRequest.status == 911) {  
            		 bootbox.confirm("当前会话已超时，请重新登录6",function (result) {
     					if(result){
     						window.location.href = '../login.jsp';
     					}
     				});
                 }*/
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
	
	function commitForm(obj){
		//设置当前页的值
		if(typeof obj == "undefined") {
			$("#pageNum").val("1");
		}else{
			$("#pageNum").val($(obj).text());
		}
		
		$("#formgastation").ajaxSubmit(listOptions);
	}
	
	function commitForm2(obj){
		//设置当前页的值
		if(typeof obj == "undefined") {
			$("#pageNum").val("1");
		}else{
			$("#pageNum").val($(obj).text());
		}
		
		var options ={   
	            url:'../web/gastation/gastationList2',
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
		loadPage('#main', '../web/gastation/gastationList');
	}
	
	function init2(){
		loadPage('#main', '../web/gastation/gastationList2');
	}
	
	function showInnerModel(obj1,obj2,obj3,obj4,tr){
		var show=$("div[name='show']");
		for(var i=0;i<show.length;i++){
			show[i].innerHTML=tr.children('td').eq(i+1).text();
		}
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
	
	function resetPassword(obj){
		var userName = $(obj).parents("tr").children("td").eq(6).text();
		var station = $(obj).parents("tr").children("td").eq(1).text();
		
		bootbox.setLocale("zh_CN");
		bootbox.confirm("将重置该加注站的管理员密码，是否确认?", function (result) {
			if (!result) {
				$('[data-rel=tooltip]').tooltip('hide');
				return;
			}else{
				$.ajax({
					   type: "POST",
					   url:'../web/gastation/resetPassword?gastationid='+station+'&username='+userName,
			           dataType:'text',
			           async:false,
			           success:function(data){
			        	   $("#main").html(data);
			        	   $("#modal-table").modal("show");
			           }
				});
			}
		})
	}
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
				"'--"
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