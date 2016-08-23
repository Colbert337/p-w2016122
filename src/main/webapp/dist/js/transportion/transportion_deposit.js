	
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
		    $('#transportionform').bootstrapValidator({
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
		                        message: '运输公司保证金不能为空'
		                    },
		                    regexp: {
		                        regexp: '^[-+]?[0-9]+(\.[0-9]+)?$',
		                        message: '运输公司保证必须是数字'
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
			    
		function save(){			
			/*手动验证表单，当是普通按钮时。*/
			$('#transportionform').data('bootstrapValidator').validate();
			if(!$('#transportionform').data('bootstrapValidator').isValid()){
				return ;
			}
			
			bootbox.setLocale("zh_CN");
			bootbox.confirm("是否确认对 [" + $("#stationame").text() + "] 充值" + $("[name=deposit]").val() + "元", function (result) {
				if (result) {
					var options ={   
				            url:'../web/transportion/deposiTransportion',   
				            type:'post',                    
				            dataType:'text',
				            success:function(data){
				            	$("#main").html(data);
				            	$("#modal-table").modal("show");
				            	if($("#retCode").val() != 100){
				            		
				            	}
				            },error:function(XMLHttpRequest, textStatus, errorThrown) {
				            	
				 	       }
					}

					$("#transportionform").ajaxSubmit(options);
				}
			})
		}

		
		function returnpage(){
			loadPage('#main', '../web/transportion/transportionList');
		}
		
		function returnpage2(){
			loadPage('#main', '../web/transportion/transportionList2');
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
			$("#transportionform").ajaxSubmit(multipartOptions);
		}