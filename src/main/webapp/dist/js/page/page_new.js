	//bootstrap验证控件		
	    $('#formpage').bootstrapValidator({
	        message: 'This value is not valid',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	        	pageTitle: {
	                validators: {
	                    notEmpty: {
	                        message: '页面标题不能为空'
	                    }
	                }
	            },
	            pageBody: {
	                validators: {
	                    notEmpty: {
	                        message: '页面内容不能为空'
	                    }
	                }
	            },
	            pageTicker: {
	                validators: {
	                    notEmpty: {
	                        message: '页面缩略不能为空'
	                    }
	                }
	            }
	         }
	    });
	    
	//实例化编辑器
	var um = UM.getEditor('myEditor');
		    
	function save(){
		$("[name=pageHtml]").val(getContent());

		/*手动验证表单，当是普通按钮时。*/
		$('#formpage').data('bootstrapValidator').validate();
		if(!$('#formpage').data('bootstrapValidator').isValid()){
			return ;
		}
		
		var options ={   
	            url:'../web/page/savePage',   
	            type:'post',                    
	            dataType:'text',
	            success:function(data){
	            	$("#main").html(data);
	            	$("#modal-table").modal("show");
	            },error:function(XMLHttpRequest, textStatus, errorThrown) {
	            	
	 	       }
		}
					
		$("#formpage").ajaxSubmit(options);
	}
	
	function init(){
		loadPage('#main', '../webpage/poms/page/page_new.jsp');
	}

	function returnpage(){
		loadPage('#main', '../web/page/pageList');
	}
	
	function getContent() {
        return UM.getEditor('myEditor').getContent();
    }
	
	function openSpecfiyWindown(windowName){
	    window.open('about:blank',windowName,'width=700,height=400,menubar=no,scrollbars=no');   
	} 
	
	function preview(obj){
		$("#pageHtml").val(UM.getEditor('myEditor').getContent());
		$("#previewForm").submit();
	}
	
	function hideInnerModel(){
		$("#innerModel").modal('hide').removeClass('in');
		$("body").removeClass('modal-open').removeAttr('style');
		$(".modal-backdrop").remove();
		$("#tmpclass").remove();
	}
		