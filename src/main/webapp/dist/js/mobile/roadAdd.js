

	    $('#roadform').bootstrapValidator({
	        message: 'This value is not valid',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	conditionType: {
	                validators: {
	                    notEmpty: {
	                        message: '路况类型不能为空'
	                    }
	                }
	            },
	            captureTime_str: {
	                validators: {
	                    notEmpty: {
	                        message: '拍照时间不能为空'
	                    }
	                }
	            },
	            startTime_str: {
	                validators: {
	                    notEmpty: {
	                        message: '拍照时间不能为空'
	                    }
	                }
	            },
	            longitude: {
	                validators: {
	                    notEmpty: {
	                        message: '坐标不能为空'
	                    }
	                }
	            },
	            longitude: {
	                validators: {
	                    notEmpty: {
	                        message: '坐标不能为空'
	                    }
	                }
	            },
	            captureLongitude: {
	                validators: {
	                    notEmpty: {
	                        message: '拍照时坐标不能为空'
	                    }
	                }
	            },
	            captureTime: {
	                validators: {
	                    notEmpty: {
	                        message: '拍照时时间不能为空'
	                    }
	                }
	            },
	            conditionMsg: {
	                validators: {
	                    notEmpty: {
	                        message: '路况说明不能为空'
	                    }
	                }
	            },
	            startTime: {
	                validators: {
	                    notEmpty: {
	                        message: '开始时间不能为空'
	                    }
	                }
	            },
	            address: {
	                validators: {
	                    notEmpty: {
	                        message: '详细地址不能为空'
	                    }
	                }
	            }
	         }
	    });
	  

	    $('.timebox').datetimepicker({
			 icons: {
				time: 'fa fa-clock-o',
				date: 'fa fa-calendar',
				up: 'fa fa-chevron-up',
				down: 'fa fa-chevron-down',
				previous: 'fa fa-chevron-left',
				next: 'fa fa-chevron-right',
				today: 'fa fa-arrows ',
				clear: 'fa fa-trash',
				close: 'fa fa-times'
			 }
			}).next().on(ace.click_event, function(){
				$(this).prev().focus();
			});
	  var imgupload=0;
	function saveRoad(){
		/*手动验证表单，当是普通按钮时。*/
		if(imgupload==0){
			bootbox.alert("请上传图片");
			return ;
		}
		if($("#conditionType").val()*1>5){
			if($("#endTime_str").val()==""){
				bootbox.alert("请选择结束时间");
			}
			
		}
		$('#roadform').data('bootstrapValidator').validate();
		if(!$('#roadform').data('bootstrapValidator').isValid()){
			return ;
		}
		
		var options ={   
	            url:'../web/mobile/road/saveRoad',   
	            type:'post',
	            dataType:'text',
	            success:function(data){
	            	$("#main").html(data);
	            	$("#modal-table").modal("show");
	            },error:function(XMLHttpRequest, textStatus, errorThrown) {
	            	
	 	       }
		}
					
		$("#roadform").ajaxSubmit(options);
	}
	 $("#end").hide();
	function changeType(obj){
		if($('#'+obj).val()=='01'){
			 $("#end").hide();
		}else if($('#'+obj).val()=='02'){
			$("#end").hide();
		}else if($('#'+obj).val()=='05'){
			$("#end").hide();
		}else{
			 $("#end").show();
		}
		
	}
	function init(){
		loadPage('#main', '../webpage/poms/mobile/roadAdd.jsp');
		 imgupload=0;
	}
	var device_token = '';
	var driver_name = '';
	function addUser(){
		
	
//		
//		 var listOptions ={
//		    		url:'../web/message/driverList',
//		    		type:'post',
//		    		dataType:'html',
//		    		success:function(data){
//		    			//console.log("这里分页之后");
//		    			$("#content").html(data);
//		    			
//		    			$("#editModel").modal('show');
//		    			if($("#retCode").val() != "100"){
//
//		    			}
//		    			$('[data-rel="tooltip"]').tooltip();
//		    		},error:function(XMLHttpRequest, textStatus, errorThrown) {
//
//		    		}
//		    	}
//			 $("#formgastation").ajaxSubmit(listOptions);
		 loadPage('#content', '../web/message/driverList');
			$("#editModel").modal('show');
	}
	function closeDialog(obj){
		$("#" + obj).modal('hide').removeClass('in');
		$("body").removeClass('modal-open').removeAttr('style');
		$(".modal-backdrop").remove();
//		init();
		
	}
	var projectfileoptions = {
			showUpload : false,
			showRemove : false,
			language : 'zh',
			allowedPreviewTypes : [ 'image' ],
			allowedFileExtensions : [ 'jpg', 'png', 'gif', 'jepg' ],
			maxFileSize : 10000,
		}
		// 文件上传框
		$('input[class=projectfile]').each(function() {
			var imageurl = $(this).attr("value");

			if (imageurl) {
				var op = $.extend({
					initialPreview : [ // 预览图片的设置
					"<img src='" + imageurl + "' class='file-preview-image'>", ]
				}, projectfileoptions);

				$(this).fileinput(op);
			} else {
				$(this).fileinput(projectfileoptions);
			}
		});
	function savePhoto(fileobj, obj, obj1, obj2) {

		$(fileobj).parents("div").find("input[name=uploadfile]").each(
				function() {
					$(this).attr("name", "");
				});
		$(fileobj).parent("div").find("input:first").attr("name", "uploadfile");
		if ($(obj).val() == null || $(obj).val() == "") {
			bootbox.alert("请先上传文件");
			return;
		}
		
		
		var stationId = "mobile";
		var multipartOptions = {
			url : '../crmInterface/crmBaseService/web/upload?stationid='
					+ stationId,
			type : 'post',
			dataType : 'text',
			enctype : "multipart/form-data",
			success : function(data) {
				var s = JSON.parse(data);
				if (s.success == true) {
					var a=s.obj.substring(s.obj.indexOf('/')+1,s.obj.length);
					
					bootbox.alert("上传成功");
					$(obj1).val(a.substring(a.indexOf('/'),a.length));
					imgupload=1;
				}

			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				bootbox.alert("上传成功");
			}
		}
		$("#roadform").ajaxSubmit(multipartOptions);

	}


	
	function returnpage(){
		loadPage('#main', '../web/mobile/road/roadList');
	}
		