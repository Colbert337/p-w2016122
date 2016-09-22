	
	//datepicker plugin
	$('.date-picker').datepicker({
		autoclose: true,
		todayHighlight: true,
		language: 'cn'
	}).next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
	
	
	var projectfileoptions = {
	        showUpload : false,
	        showRemove : false,
	        language : 'zh',
	        allowedPreviewTypes : [ 'image' ],
	        allowedFileExtensions : [ 'jpg', 'png', 'gif', 'jepg' ],
	        maxFileSize : 1000,
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

			//bootstrap验证控件		
		    $('#gastationform').bootstrapValidator({
		        message: 'This value is not valid',
		        feedbackIcons: {
		            valid: 'glyphicon glyphicon-ok',
		            invalid: 'glyphicon glyphicon-remove',
		            validating: 'glyphicon glyphicon-refresh'
		        },
		        fields: {
		        	deposit: {
		                validators: {
		                    notEmpty: {
		                        message: '加注站保证金不能为空'
		                    },
							callback: {
								message: '加注站保证金必须是正数',
								callback: function(value, validator) {
									if (value<=0||isNaN(value)) {
										return false;
									}
									return true;
								}
							}
		                }
		            },
		            /*company: {
		                validators: {
		                    notEmpty: {
		                        message: '企业名称不能为空'
		                    }
		                }
		            },*/
		            depositType: {
		                validators: {
		                    notEmpty: {
		                        message: '转账方式不能为空'
		                    }
		                }
		            }
		         }
		    });
		    
		function save() {
			/*手动验证表单，当是普通按钮时。*/
			$('#gastationform').data('bootstrapValidator').validate();
			if (!$('#gastationform').data('bootstrapValidator').isValid()) {
				return;
			}

			bootbox.setLocale("zh_CN");
			bootbox.confirm("是否确认将 [" + $("#gastationame").text() + "] 预付款额度增加" + $("[name=deposit]").val() + "元", function (result) {
				if (result) {
					var options = {
						url: '../web/gastation/depositGastation',
						type: 'post',
						dataType: 'text',
						success: function (data) {
							$("#main").html(data);
							$("#modal-table").modal("show");
							if ($("#retCode").val() != 100) {
							}
						}, error: function (XMLHttpRequest, textStatus, errorThrown) {

						}
					}
					$("#gastationform").ajaxSubmit(options);
				}
			})
		}

		function returnpage(){
			loadPage('#main', '../web/gastation/gastationList');
		}
		
		function returnpage2(){
			loadPage('#main', '../web/gastation/gastationList2');
		}
		
		function save_photo(fileobj,obj,obj1){
			
			$(fileobj).parents("div").find("input[name=uploadfile]").each(function(){
				$(this).attr("name","");
			});
			
			$(fileobj).parent("div").find("input:first").attr("name","uploadfile");
			
			if($(obj).val()==null || $(obj).val()==""){
				bootbox.alert("请先上传文件");	
				return;
			}
			
			var multipartOptions ={   
		            url:'../crmInterface/crmBaseService/web/upload?stationid='+$("[name=stationId]").val(),
		            type:'post',                    
		            dataType:'text',
		            enctype:"multipart/form-data",
		            success:function(data){
		            	var s = JSON.parse(data);
		            	if(s.success == true){
		            		bootbox.alert("上传成功");
		            		$(obj1).val(s.obj);
		            	}
		            	
		            },error:function(XMLHttpRequest, textStatus, errorThrown) {

		 	       }
			}
			$("#gastationform").ajaxSubmit(multipartOptions);
		}