	var contral = "0";
		
			//bootstrap验证控件
		    $('#newcardform').bootstrapValidator({
		        message: 'This value is not valid',
		        feedbackIcons: {
		            valid: 'glyphicon glyphicon-ok',
		            invalid: 'glyphicon glyphicon-remove',
		            validating: 'glyphicon glyphicon-refresh'
		        },
		        fields: {
		        	card_no_start: {
		                message: 'The cardno is not valid',
		                validators: {
		                    notEmpty: {
		                        message: '用户卡号不能为空'
		                    },
		                    regexp: {
		                        regexp: /^1\d{8}$/,
		                        message: '用户卡号格式必须是1开头的9位数字'
		                    }
		                }
		            },
		            card_no_end: {
		                message: 'The cardno is not valid',
		                validators: { 
		                    notEmpty: {
		                        message: '用户卡号不能为空'
		                    },
		                    regexp: {
		                        regexp: /^1\d{8}$/,
		                        message: '用户卡号格式必须是1开头的9位数字'
		                    },
		                    callback: {
		                    	message: '用户卡结束编号必须不小于起始编号',
		                    	callback: function (value, validator, $field) {
	                                var start = $('#card_no_1').val();
	                                 if(parseFloat($('#card_no_1').val()) > value){
	                                	 return false;
	                                 }
	                                 return true;
	                            }
		                    }
		                }
		            },
		            card_type: {
		                validators: {
		                    notEmpty: {
		                        message: '用户卡类型不能为空'
		                    }
		                }
		            },
		            card_property: {
		                validators: {
		                    notEmpty: {
		                        message: '用户卡属性不能为空'
		                    }
		                }
		            },
		            operator: {
		                validators: {
		                    notEmpty: {
		                        message: '操作员工号不能为空'
		                    }
		                }
		            }
		        }
		    });
			
			
		    
		function save(){
			if(contral == "0"){
				alert("列表中没有需要入库的卡");
				return;
			}
			/*手动验证表单，当是普通按钮时。*/
			$('#newcardform').data('bootstrapValidator').validate();
			if(!$('#newcardform').data('bootstrapValidator').isValid()){
				return ;
			}
			
			var options ={   
		            url:'../web/card/saveCard',   
		            type:'post',                    
		            dataType:'text',
		            success:function(data){
		            	$("#main").html(data);
						$("#modal-table").modal("show");
		            },error:function(XMLHttpRequest, textStatus, errorThrown) {

		 	       }
			}
			
			$("#storage_data").attr("disabled","disabled");
			
			$("#newcardform").ajaxSubmit(options);
		}
		
		function returnpage(){
			loadPage('#main', '../web/card/cardList');
		}
		
		function dynamicSheet(){
			
			/*手动验证表单，当是普通按钮时。*/
			$('#newcardform').data('bootstrapValidator').validate();
			if(!$('#newcardform').data('bootstrapValidator').isValid()){
				return ;
			}
			
			var start = parseFloat($("#card_no_1").val());
			var end = parseFloat($("#card_no_2").val());
			var num = end - start;
			
			if(end - start >=2000){
				alert("单批次操作卡数量最大值为2000");
				return;
			}
			
			$("#dynamic-table").find("tbody").find("tr").remove();
			
			$.ajax({
				   type: "POST",
				   url:'../web/card/checkCardMultiple?cardidStart='+start+'&cardidEnd='+end,   
		           type:'post',                    
		           dataType:'text',
		           //async:false,
					beforeSend: function () {
						$('body').addClass('modal-open').css('padding-right','17px')
						$('body').append('<div class="loading-warp"><div class="loading"><i class="ace-icon fa fa-spinner fa-spin"></i></div><div class="modal-backdrop fade in"></div></div>')
					},
		           success:function(data){
		        	   var s = JSON.parse(data);
		        	   for(var i=0;i<s.length;i++){
		        		   if(s[i].exist == "0"){
			        		   	contral = "1";
								$("#dynamic-table").find("tbody").append("<tr class='success'><td>"+s[i].card_no+"</td><td>"+$('#card_type').find("option:selected").text()+"</td><td>"+$('#card_property').find("option:selected").text()+"</td><td>未入库</td><td>"+$('#operator').val()+"</td></tr>");
			           			$("#card_no_arr").val($("#card_no_arr").val()+s[i].card_no+",");
			        	   }else{
								$("#dynamic-table").find("tbody").append("<tr class='danger'><td>"+s[i].card_no+"</td><td>"+$('#card_type').find("option:selected").text()+"</td><td>"+$('#card_property').find("option:selected").text()+"</td><td>已入库</td><td>"+$('#operator').val()+"</td></tr>");
			        	   }
		        	   }
					   tableList();
		            },
					complete: function () {
						$("body").removeClass('modal-open').removeAttr('style');
						$(".loading-warp").remove();
					}
				});

			function tableList() {
				if(contral == "0"){
					alert("该批次中没有需要入库的卡");
				}
				$("#init_dynamic_data").attr("disabled","disabled");
				initTable();
				$("#storage_data").removeAttr("disabled");
			}
				
			//动态初始化详细列表
			function initTable() {
				//提示信息
				var lang = {
					"sProcessing": "处理中...",
					"sLengthMenu": "显示 _MENU_ 项结果",
					"sZeroRecords": "没有匹配结果",
					"sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
					"sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
					"sInfoFiltered": "(由 _MAX_ 项结果过滤)",
					"sInfoPostFix": "",
					"sSearch": "搜索:",
					"sUrl": "",
					"sEmptyTable": "表中数据为空",
					"sLoadingRecords": "载入中...",
					"sInfoThousands": ",",
					"oPaginate": {
						"sFirst": "首页",
						"sPrevious": "上页",
						"sNext": "下页",
						"sLast": "末页"
					},
					"oAria": {
						"sSortAscending": ": 以升序排列此列",
						"sSortDescending": ": 以降序排列此列"
					}
				};

				//initiate dataTables plugin
				$('#dynamic-table').DataTable( {bAutoWidth: false,"aoColumns": [null, null,null, null, null],"aaSorting": [],
					"oLanguage" :lang, //提示信息
					select: {
						style: 'multi'
					}
				} );
			}
		}

		function init(){
			loadPage('#main', '../webpage/poms/card/card_new.jsp');
		}