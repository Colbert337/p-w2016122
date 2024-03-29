$('#j-input-daterange-top').datepicker({autoclose:true, format: 'yyyy/mm/dd', language: 'cn'});

	var listOptions ={   
            url:'../web/transportion/transportionList',
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
	
	function preUpdate(obj){
		var stationid = $(obj).parents("tr").find("td:first").find("input").val();
		loadPage('#main', '../web/transportion/preUpdate?transportionid='+stationid);
	}
	
	function commitForm(obj){
		//设置当前页的值
		if(typeof obj == "undefined") {
			$("#pageNum").val("1");
		}else{
			$("#pageNum").val($(obj).text());
		}
		
		$("#formtransportion").ajaxSubmit(listOptions);
	}
	
	function del(obj){
		var cardid = $(obj).parents('tr').find("td:first").find("input").val();
		
		var deloptions ={   
	            url:'../web/transportion/deletetransportion?transportionid='+cardid,   
	            type:'post',                    
	            dataType:'text',
	            success:function(data){
		             $("#main").html(data);
		             if($("#retCode").val() != "100"){
		            	 $("#modal-table").modal("show");
		             }
	            },
	            error:function(XMLHttpRequest, textStatus, errorThrown) {
					
	            }
		}
		
		$("#formtransportion").ajaxSubmit(deloptions);
	}
	
	function preDeposit(obj){
		var stationid = $(obj).parents("tr").find("td:first").find("input").val();
		var stationame = $(obj).parents("tr").children("td").eq(2).text();
		var stationdeposit = $(obj).parents("tr").children("td").eq(10).text();
		var acconutid = $(obj).parents("tr").children("td").eq(11).text();
		
		loadPage('#main', '../webpage/poms/transportion/transportion_deposit.jsp?acconutid='+acconutid+'&stationame='+stationame+'&stationdeposit='+stationdeposit+'&stationid='+stationid);
	}
	
	function init(){
		loadPage('#main', '../web/transportion/transportionList');
	}
	
	function resetPassword(obj){
		var userName = $(obj).parents("tr").children("td").eq(5).text();
		var station = $(obj).parents("tr").children("td").eq(1).text();
		
		bootbox.setLocale("zh_CN");
		bootbox.confirm("将重置该运输公司的管理员密码，是否确认?", function (result) {
			if (!result) {
				$('[data-rel=tooltip]').tooltip('hide');
				return;
			}else{
				$.ajax({
					   type: "POST",
					   url:'../web/transportion/resetPassword?gastationid='+station+'&username='+userName,
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
	
	function showInnerModel(obj1,obj2,tr){
		var show=$("div[name='show']");
		for(var i=0;i<show.length;i++){
			show[i].innerHTML=tr.children('td').eq(i+1).text();
		}
		$("#innerimg1").attr("src",obj1);
		$("#innerimg1").parent("a").attr("href",obj1);
		$("#innerimg2").attr("src",obj2);
		$("#innerimg2").parent("a").attr("href",obj2);
		
		
		$("#innerModel").modal('show');
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