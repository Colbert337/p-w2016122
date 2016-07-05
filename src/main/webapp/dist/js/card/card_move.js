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
		            workstation: {
		                validators: {
		                    notEmpty: {
		                        message: '调拨工作站不能为空'
		                    }
		                }
		            },
		            workstation_type: {
		                validators: {
		                    notEmpty: {
		                        message: '工作站类型不能为空'
		                    }
		                }
		            },
		            workstation_resp: {
		                validators: {
		                    notEmpty: {
		                        message: '工作站领取人不能为空'
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
			/*手动验证表单，当是普通按钮时。*/
			$('#newcardform').data('bootstrapValidator').validate();
			if(!$('#newcardform').data('bootstrapValidator').isValid()){
				return ;
			}
			
			var options ={   
		            url:'../web/card/moveCard',   
		            type:'post',                    
		            dataType:'html',
		            success:function(data){
			              $("#main").html(data);
			              $("#modal-table").modal("show");
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
			
			if(end - start >=2000){
				bootbox.alert("单批次操作卡数量最大值为2000");
				return;
			}
			
			$.ajax({
				   type: "POST",
				   url:'../web/card/checkMoveCardMultiple?cardidStart='+start+'&cardidEnd='+end,   
		           contentType:"application/x-www-form-urlencoded; charset=UTF-8",
		           dataType:'text',
		           beforeSend: function () {
					   $('body').addClass('modal-open').css('padding-right','17px')
					   $('body').append('<div class="loading-warp"><div class="loading"><i class="ace-icon fa fa-spinner fa-spin"></i></div><div class="modal-backdrop fade in"></div></div>')
		           },
		           success:function(data){
		        	   var s = JSON.parse(data);
		        	   for(var i=0;i<s.length;i++){
		        		   if(s[i].exist == "0"){
			        		   	contral = "1";
								$("#dynamic-table").find("tbody").append("<tr class='success'><td>"+s[i].card_no+"</td><td>"+$('#workstation').find("option:selected").text()+"</td><td>"+$('#workstation_resp').val()+"</td><td>已入库</td><td>"+$("#operator").val()+"</td></tr>");
			           			$("#card_no_arr").val($("#card_no_arr").val()+s[i].card_no+",");
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
				var tr = $("#dynamic-table").find("tbody").find("tr");
				if(tr.length==0){
					bootbox.alert("此号段没有可出库的用户卡");
					return;
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
				var myTable = $('#dynamic-table')
				.DataTable( {bAutoWidth: false,"aoColumns": [null, null,null, null, null],"aaSorting": [],
					"oLanguage" :lang, //提示信息
					select: {
						style: 'multi'
					}
				} );
			}
		}
		
		function init(){
			loadPage('#main', '../webpage/poms/card/card_move.jsp');
		}
		
	 	function init_workstation(obj){
			$.ajax({
				   type: "POST",
				   url:'../web/usysparam/query?gcode='+$(obj).val(),  
		           dataType:'text',
		           async:false,
		           success:function(data){
		           		if(data != ""){
				        	   $("#workstation").empty();
				        	   var s = JSON.parse(data);
				        	   for(var i=0;i<s.length;i++){
				        		   $("#workstation").append("<option value='"+s[i].mcode+"''>"+s[i].mname+"</option>");
				        	   }
		           		}
		            }
				});
		} 