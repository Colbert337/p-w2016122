<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
	<script type="text/javascript" src="<%=basePath %>/dist/js/bootstrapValidator.js"></script>

			<!-- /section:basics/sidebar -->
			<div class="main-content">
				<div class="main-content-inner">
					<!-- #section:basics/content.breadcrumbs -->
					<div class="breadcrumbs" id="breadcrumbs">
						<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>

						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								<a href="#">主页</a>
							</li>

							<li>
								<a href="#">资源管理</a>
							</li>
							<li class="active">用户卡管理</li>
						</ul><!-- /.breadcrumb -->

						<!-- #section:basics/content.searchbox -->
						<div class="nav-search" id="nav-search">
							<form class="form-search" >
							
								<input id="retCode" type="text" value=" ${ret.retCode}" />
								<input id="retMsg" type="text" value=" ${ret.retMsg}" />
								
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
									<i class="ace-icon fa fa-search nav-search-icon"></i>
								</span>
							</form>
						</div><!-- /.nav-search -->

						<!-- /section:basics/content.searchbox -->
					</div>

					<!-- /section:basics/content.breadcrumbs -->
					<div class="page-content">
						<!-- /section:settings.box -->
						<div class="page-header">
							<h1>
								新建用户卡
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<form class="form-horizontal"  id="newcardform">
									<!-- #section:elements.form -->
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 用户卡起始编号： </label>

										<div class="col-sm-3">
											<input type="text" id="card_no_1"  name="card_no_start" placeholder="卡起始编号" class="col-xs-10 col-sm-5" maxlength="9"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 用户卡结束编号： </label>

										<div class="col-sm-3">
											<input type="text" id="card_no_2"  name="card_no_end" placeholder="卡结束编号" class="col-xs-10 col-sm-5" maxlength="9"/>
											<input type="hidden" name="card_no_arr" id="card_no_arr"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 用户卡类型： </label>
										<div class="col-sm-2">
												<select class="form-control" id="card_type" name="card_type" multiple="multiple">
														<s:option flag="true" gcode="CARDTYPE" link="true" />
												</select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 用户卡属性： </label>
										<div class="col-sm-2">
												<select class="form-control" id="card_property" name="card_property" multiple="multiple">
														<s:option flag="true" gcode="CARDPROPERTY" link="true" />
												</select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 操作人工号： </label>

										<div class="col-sm-4">
											<input type="text"  id="operator" name="operator" class="col-xs-10 col-sm-5" readonly="readonly" value="GZCYKFA001"/>
										</div>
									</div>
						
									<div class="form-group">
											<label class="col-sm-3 control-label no-padding-right" for="form-field-1" id="dynamic-table_after_handler"> 明细列表： </label>
											<div class="col-sm-7" id="dynamic-table_div">
											<div class="table-header">用户卡列表</div>
												<table id="dynamic-table" class="table table-striped table-bordered table-hover">
													<thead>
														<tr>
															<th class="center">
																<label class="pos-rel"> 
																	<input type="checkbox" class="ace" onclick="checkedAllRows(this);" /> 
																	<span class="lbl"></span>
																</label>
															</th>
															<th id="card_no_order">用户卡号</th>
															<th id="card_type_order">用户卡类型</th>
															<th id="card_name_order">用户卡属性</th> 
															<th id="card_status_order">用户卡状态</th>
															<th id="operator_order">操作人工号</th> 
														</tr>
													</thead>
													<tbody>
													</tbody>
												</table>
										</div>
									</div>
									
									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											<button class="btn btn-info" type="button" onclick="dynamicSheet();" id="init_dynamic_data">
												<i class="ace-icon fa fa-check bigger-110"></i>
												生成卡列表
											</button>
											
											<button class="btn btn-info" type="button" onclick="save();" disabled="disabled" id="storage_data">
												<i class="ace-icon fa fa-check bigger-110"></i>
												用户卡入库
											</button>

											&nbsp; &nbsp; &nbsp;
											<button class="btn" type="reset" onclick="init();">
												<i class="ace-icon fa fa-repeat bigger-110"></i>
												重置
											</button>
											
											&nbsp; &nbsp; &nbsp;
											<button class="btn btn-success" type="buttom" onclick="returnpage();">
												<i class="ace-icon fa fa-undo bigger-110"></i>
												返回
											</button>
										</div>
									</div>
									
										<jsp:include page="../message.jsp"></jsp:include>
										
								</form>						
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div>


		<!-- inline scripts related to this page -->
	<script type="text/javascript">
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
		                    },
		                    stringLength: {
		                        min: 10,
		                        max: 10,
		                        message: '操作人工号只能是10位'
		                    }
		                }
		            },
		            memo: {
		                validators: {
		                    notEmpty: {
		                        message: 'The email operator is required and can\'t be empty'
		                    },
		                    stringLength: {
		                        min: 6,
		                        max: 30,
		                        message: 'The operator must be more than 6 and less than 30 characters long'
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
			
			$("#init_dynamic_data").attr("disabled","disabled");
			
			var start = parseFloat($("#card_no_1").val());
			var end = parseFloat($("#card_no_2").val());
			
			if(end - start >=2000){
				alert("单批次操作卡数量最大值为2000");
				return;
			}
			
			for(var i=start; i<=end; i++){
				$.ajax({
					   type: "POST",
					   url:'../web/card/checkCard?cardid='+i,   
			           type:'post',                    
			           dataType:'text',
			           async:false,
			           success:function(data){
			           		if(data == "0"){
			           			var tmp_card_type = $('#card_type').val();
			           			contral = "1";
								$("#dynamic-table").find("tbody").append("<tr class='success'><td class='center'><label class='pos-rel'><input type='checkbox' class='ace' id='pks'/><span class='lbl'></span></label></td><td>"+i+"</td><td>"+tmp_card_type+"</td><td>"+$('#card_property').val()+"</td><td>未使用</td><td>"+$('#operator').val()+"</td></tr>");
			           			$("#card_no_arr").val($("#card_no_arr").val()+i+",");
			           		}else{
								$("#dynamic-table").find("tbody").append("<tr class='danger'><td class='center'><label class='pos-rel'><input type='checkbox' class='ace' id='pks'/><span class='lbl'></span></label></td><td>"+i+"</td><td>"+$('#card_type').val()+"</td><td>"+$('#card_property').val()+"</td><td>已使用</td><td>"+$('#operator').val()+"</td></tr>");
			           		}
			            }
					});
			}
				
			//动态初始化详细列表
			jQuery(function($) {
				//initiate dataTables plugin
				var myTable = $('#dynamic-table')
				//.wrap("<div class='dataTables_borderWrap' />")   //if you are applying horizontal scrolling (sScrollX)
				.DataTable( {bAutoWidth: false,"aoColumns": [{ "bSortable": false },null, null,null, null, null],"aaSorting": [],					
					
					//"bProcessing": true,
			        //"bServerSide": true,
			        //"sAjaxSource": "http://127.0.0.1/table.php"	,
					//,
					//"sScrollY": "200px",
					//"bPaginate": false,
					//"sScrollX": "100%",
					//"sScrollXInner": "120%",
					//"bScrollCollapse": true,
					//Note: if you are applying horizontal scrolling (sScrollX) on a ".table-bordered"
					//you may want to wrap the table inside a "div.dataTables_borderWrap" element
			
					//"iDisplayLength": 50		
			
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
			$("#dynamic-table_div").remove();
			$("#dynamic-table_after_handler").after("<div class='col-sm-7' id='dynamic-table_div'><div class='table-header'>用户卡列表</div><table id='dynamic-table' class='table table-striped table-bordered table-hover'><thead><tr><th class='center'><label class='pos-rel'><input type='checkbox' class='ace' onclick='checkedAllRows(this);'' /><span class='lbl'></span></label></th><th id='card_no_order'>用户卡号</th><th id='card_type_order'>用户卡类型</th><th id='card_name_order'>用户卡属性</th> <th id='card_status_order'>用户卡状态</th><th id='operator_order'>操作人工号</th></tr></thead><tbody></tbody></table></div>");
			$("#init_dynamic_data").removeAttr("disabled");
		}
		</script>