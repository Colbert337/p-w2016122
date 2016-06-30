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
				alert("单批次操作卡数量最大值为2000");
				return;
			}
			
			$.ajax({
				   type: "POST",
				   url:'../web/card/checkMoveCardMultiple?cardidStart='+start+'&cardidEnd='+end,   
		           contentType:"application/x-www-form-urlencoded; charset=UTF-8",
		           dataType:'text',
		           async:false,
		           beforeSend: function () {
		        	   $("#dynamic-table").append('<tfoot><tr><td colspan="6" class="text-center">出库中...</td></tr></tfoot>');
		           },
		           success:function(data){
		        	   var s = JSON.parse(data);
		        	   for(var i=0;i<s.length;i++){
		        		   if(s[i].exist == "0"){
			        		   	contral = "1";
								$("#dynamic-table").find("tbody").append("<tr class='success'><td class='center'><label class='pos-rel'><input type='checkbox' class='ace' id='pks'/><span class='lbl'></span></label></td><td>"+s[i].card_no+"</td><td>"+$('#workstation').find("option:selected").text()+"</td><td>"+$('#workstation_resp').val()+"</td><td>已入库</td><td>"+$("#operator").val()+"</td></tr>");
			           			$("#card_no_arr").val($("#card_no_arr").val()+s[i].card_no+",");
			        	   }
		        	   }
		            },
					complete: function () {
						$("#dynamic-table").find("tfoot").remove();
					}
				});
			
			/*for(var i=start; i<=end; i++){
				$.ajax({
					   type: "POST",
					   url:'../web/card/checkMoveCard?cardid='+i,   
			           contentType:"application/x-www-form-urlencoded; charset=UTF-8",
			           dataType:'text',
			           async:false,
						beforeSend: function () {
							$("#dynamic-table").append('<tfoot><tr><td colspan="6" class="text-center">出库中...</td></tr></tfoot>');
						},
			           success:function(data){
			           		if(data != ""){
								$("#dynamic-table").find("tbody").append("<tr class='success'><td class='center'><label class='pos-rel'><input type='checkbox' class='ace' id='pks'/><span class='lbl'></span></label></td><td>"+i+"</td><td>"+$('#workstation').find("option:selected").text()+"</td><td>"+$('#workstation_resp').val()+"</td><td>"+data+"</td><td>"+$("#operator").val()+"</td></tr>");
			           			$("#card_no_arr").val($("#card_no_arr").val()+i+",");
			           		}
			            },
						complete: function () {
							$("#dynamic-table").find("tfoot").remove();
						}
					});
			}*/
			
			var tr = $("#dynamic-table").find("tbody").find("tr");
		 	if(tr.length==0){
				alert("此号段没有可出库的用户卡");
				return;
			}
		 	
		 	$("#init_dynamic_data").attr("disabled","disabled");
				
			//动态初始化详细列表
			jQuery(function($) {
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
				//.wrap("<div class='dataTables_borderWrap' />")   //if you are applying horizontal scrolling (sScrollX)
				.DataTable( {bAutoWidth: false,"aoColumns": [{ "bSortable": false },null, null,null, null, null],"aaSorting": [],

					"oLanguage" :lang, //提示信息
			
					select: {
						style: 'multi'
					}
			    } );
				
				$.fn.dataTable.Buttons.swfPath = "../assets/js/dataTables/extensions/buttons/swf/flashExport.swf"; //in Ace demo ../assets will be replaced by correct assets path
				$.fn.dataTable.Buttons.defaults.dom.container.className = 'dt-buttons btn-overlap btn-group btn-overlap';
				
				new $.fn.dataTable.Buttons( myTable, {
					buttons: [
					  {
						"extend": "colvis",
						"text": "<i class='fa fa-search bigger-110 blue'></i> <span class='hidden'>Show/hide columns</span>",
						"className": "btn btn-white btn-primary btn-bold",
						columns: ':not(:first):not(:last)'
					  },
					  {
						"extend": "copy",
						"text": "<i class='fa fa-copy bigger-110 pink'></i> <span class='hidden'>Copy to clipboard</span>",
						"className": "btn btn-white btn-primary btn-bold"
					  },
					  {
						"extend": "csv",
						"text": "<i class='fa fa-database bigger-110 orange'></i> <span class='hidden'>Export to CSV</span>",
						"className": "btn btn-white btn-primary btn-bold"
					  },
					  {
						"extend": "excel",
						"text": "<i class='fa fa-file-excel-o bigger-110 green'></i> <span class='hidden'>Export to Excel</span>",
						"className": "btn btn-white btn-primary btn-bold"
					  },
					  {
						"extend": "pdf",
						"text": "<i class='fa fa-file-pdf-o bigger-110 red'></i> <span class='hidden'>Export to PDF</span>",
						"className": "btn btn-white btn-primary btn-bold"
					  },
					  {
						"extend": "print",
						"text": "<i class='fa fa-print bigger-110 grey'></i> <span class='hidden'>Print</span>",
						"className": "btn btn-white btn-primary btn-bold",
						autoPrint: false,
						message: 'This print was produced using the Print button for DataTables'
					  }		  
					]
				} );
				myTable.buttons().container().appendTo( $('.tableTools-container') );
				
				//style the message box
				var defaultCopyAction = myTable.button(1).action();
				myTable.button(1).action(function (e, dt, button, config) {
					defaultCopyAction(e, dt, button, config);
					$('.dt-button-info').addClass('gritter-item-wrapper gritter-info gritter-center white');
				});			
				
				var defaultColvisAction = myTable.button(0).action();
				myTable.button(0).action(function (e, dt, button, config) {
					
					defaultColvisAction(e, dt, button, config);	
					
					if($('.dt-button-collection > .dropdown-menu').length == 0) {
						$('.dt-button-collection')
						.wrapInner('<ul class="dropdown-menu dropdown-light dropdown-caret dropdown-caret" />')
						.find('a').attr('href', '#').wrap("<li />")
					}
					$('.dt-button-collection').appendTo('.tableTools-container .dt-buttons')
				});
			
				////
			
				setTimeout(function() {
					$($('.tableTools-container')).find('a.dt-button').each(function() {
						var div = $(this).find(' > div').first();
						if(div.length == 1) div.tooltip({container: 'body', title: div.parent().text()});
						else $(this).tooltip({container: 'body', title: $(this).text()});
					});
				}, 500);
				
				myTable.on( 'select', function ( e, dt, type, index ) {
					if ( type === 'row' ) {
						$( myTable.row( index ).node() ).find('input:checkbox').prop('checked', true);
					}
				} );
				myTable.on( 'deselect', function ( e, dt, type, index ) {
					if ( type === 'row' ) {
						$( myTable.row( index ).node() ).find('input:checkbox').prop('checked', false);
					}
				} );
			
				/////////////////////////////////
				//table checkboxes
				$('th input[type=checkbox], td input[type=checkbox]').prop('checked', false);
				
				//select/deselect all rows according to table header checkbox
				$('#dynamic-table > thead > tr > th input[type=checkbox], #dynamic-table_wrapper input[type=checkbox]').eq(0).on('click', function(){
					var th_checked = this.checked;//checkbox inside "TH" table header
					
					$('#dynamic-table').find('tbody > tr').each(function(){
						var row = this;
						if(th_checked) myTable.row(row).select();
						else  myTable.row(row).deselect();
					});
				});
				
				//select/deselect a row when the checkbox is checked/unchecked
				$('#dynamic-table').on('click', 'td input[type=checkbox]' , function(){
					var row = $(this).closest('tr').get(0);
					if(!this.checked) myTable.row(row).deselect();
					else myTable.row(row).select();
				});

				$(document).on('click', '#dynamic-table .dropdown-toggle', function(e) {
					e.stopImmediatePropagation();
					e.stopPropagation();
					e.preventDefault();
				});
			
				/********************************/
				//add tooltip for small view action buttons in dropdown menu
				$('[data-rel="tooltip"]').tooltip({placement: tooltip_placement});
				
				//tooltip placement on right or left
				function tooltip_placement(context, source) {
					var $source = $(source);
					var $parent = $source.closest('table')
					var off1 = $parent.offset();
					var w1 = $parent.width();
			
					var off2 = $source.offset();
					//var w2 = $source.width();
			
					if( parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2) ) return 'right';
					return 'left';
				}
			})
			
			$("#storage_data").removeAttr("disabled");
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