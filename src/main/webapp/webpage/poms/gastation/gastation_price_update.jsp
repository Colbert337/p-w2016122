<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	String imagePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
%>
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
								<a href="#">加注站管理</a>
							</li>
							<li class="active">加注站信息管理</li>
						</ul><!-- /.breadcrumb -->

						<!-- #section:basics/content.searchbox -->
						<div class="nav-search" id="nav-search">
							<form class="form-search" >
							
								<input id="retCode" type="hidden" value=" ${ret.retCode}" />
								<input id="retMsg" type="hidden" value=" ${ret.retMsg}" />
								
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
								油气品价格优惠设置
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<form class="form-horizontal"  id="gastationform">
									<!-- #section:elements.form -->
									<input type="hidden" id="gsGasPriceId"  name="gsGasPriceId" value="${gsGasPrice.gsGasPriceId}" />
							
							<div class="form-group">
										<label for="gas_station_name" class="col-sm-3 control-label no-padding-right"> 加注站编号： </label>

										<div class="col-sm-4">
											<input type="text" id="sysGasStationId"  name="sysGasStationId"  readOnly="true" class="form-control"  value="${gsGasPrice.sysGasStationId}"/>
											
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="gas_station_name"> 加注站名称： </label>

										<div class="col-sm-4">
											<input type="text" id="gas_station_name"  name="gas_station_name" readOnly="true" value="${gsGasPrice.gas_station.gas_station_name}"  class="form-control" maxlength="20"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" >当前单价： </label>

										<div class="col-sm-4">
											<input type="text" id="productPrice"  name="productPrice" readOnly="true" value="${gsGasPrice.productPriceInfo.productPrice}"  class="form-control" maxlength="20"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">气品类型： </label>

										<div class="col-sm-4">
											<input type="text" id="gasNum" name="gasNum" readOnly="true" class="form-control"  value="<s:Code2Name mcode="${gsGasPrice.gasNum}" gcode="CARDTYPE"></s:Code2Name>" maxlength="50"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="gasName"> 气品子类型： </label>

										<div class="col-sm-4">
											<input type="text" id="gasName"  name="gasName" readOnly="true" class="form-control" maxlength="20" value="<s:Code2Name mcode="${gsGasPrice.gasName}" gcode="CARDTYPE"></s:Code2Name>"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="fixed_discount">固定折扣：</label>

										<div class="col-sm-4">

											<input type="text"  id="fixed_discount" onKeyUp="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" onblur="javascript:if(parseFloat(value) >= 1){value='';alert('值不能大于1');}
											if($('#minus_money').val()!=''&&$('#fixed_discount').val()!=''){alert('选择固定折扣就不能再选择立减金额');$('#minus_money').val('');}"
												   name="fixed_discount" placeholder="固定折扣" class="form-control" maxlength="4" value="${gsGasPrice.fixed_discount}"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">立减金额：</label>

										<div class="col-sm-4">
											<input class="form-control" id="minus_money" name="minus_money" type="text" placeholder="输入立减金额"
												   onKeyUp="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"
												   onblur="javascript:if($('#fixed_discount').val()!=''&&$('#minus_money').val()!=''){alert('选择立减金额就不能再选择固定折扣');$('#fixed_discount').val('');}"
												   maxlength="20" value="${gsGasPrice.minus_money}" />
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">创建时间：</label>

										<div class="col-sm-4">
											<input class="form-control" id="createTime" name="createTime" type="text"  maxlength="20" value="<fmt:formatDate value="${gsGasPrice.productPriceInfo.createTime}" type="both"/>" readonly="readonly"/>
										</div>
									</div>

									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											
											<button class="btn btn-info" type="button" onclick="save();">
												<i class="ace-icon fa fa-check bigger-110"></i>
												保存
											</button>
											
											&nbsp; &nbsp; &nbsp;
											<button class="btn btn-success" type="button" onclick="returnpage();">
												<i class="ace-icon fa fa-undo bigger-110"></i>
												返回
											</button>
										</div>
									</div>

									<jsp:include page="/common/message.jsp"></jsp:include>
										
								</form>						
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div>
	</div>

		<!-- inline scripts related to this page -->
	<script type="text/javascript">
	




		
			//bootstrap验证控件		
		    $('#gastationform').bootstrapValidator({
		        message: 'This value is not valid',
		        feedbackIcons: {
		            valid: 'glyphicon glyphicon-ok',
		            invalid: 'glyphicon glyphicon-remove',
		            validating: 'glyphicon glyphicon-refresh'
		        },
		        fields: {
		        	/*company: {
					 message: 'The cardno is not valid',
					 validators: {
					 notEmpty: {
					 message: '所属公司不能为空'
					 },
					 stringLength: {
					 min: 1,
					 max: 20,
					 message: '加注站名称不能超过20个汉字'
					 }
					 }
					 },
					 gas_station_name: {
					 message: 'The cardno is not valid',
					 validators: {
					 notEmpty: {
					 message: '加注站名称不能为空'
					 },
					 stringLength: {
					 min: 1,
					 max: 20,
					 message: '加注站名称不能超过20个汉字'
					 }
					 }
					 },
					 station_level: {
					 validators: {
					 notEmpty: {
					 message: '加注站级别不能为空'
					 }
					 }
					 },
					 email: {
					 validators: {
					 notEmpty: {
					 message: '加注站注册邮箱不能为空'
					 },
					 stringLength: {
					 min: 1,
					 max: 50,
					 message: '加注站注册邮箱不能超过50个字符'
					 }
					 }
					 },
					 expiry_date_frompage: {
					 message: 'The cardno is not valid',
					 validators: {
					 notEmpty: {
					 message: '平台有效期不能为空'
					 },
					 callback: {
					 message: '平台有效期必须大于等于当前日期',
					 callback: function (value, validator, $field) {
					 if(compareDate(new Date().toLocaleDateString(),value)){
					 return false;
					 }
					 return true;
					 }
					 }
					 },
					 trigger: 'change'
					 },
					 station_manager: {
					 message: 'The cardno is not valid',
					 validators: {
					 notEmpty: {
					 message: '加注站站长不能为空'
					 },
					 stringLength: {
					 min: 1,
					 max: 20,
					 message: '加注站站长不能超过20个汉字'
					 }
					 }
					 },
					 contact_phone: {
					 message: 'The cardno is not valid',
					 validators: {
					 notEmpty: {
					 message: '联系电话不能为空'
					 },
					 regexp: {
					 regexp: '^[0-9]*[1-9][0-9]*$',
					 message: '联系电话必须是数字'
					 }
					 }
					 },
					 salesmen_name: {
					 validators: {
					 notEmpty: {
					 message: '销售负责人不能为空'
					 }
					 }
					 },
					 operations_id: {
					 validators: {
					 notEmpty: {
					 message: '运管负责人不能为空'
					 }
					 }
					 },
					 province: {
					 validators: {
					 notEmpty: {
					 message: '注册地址省不能为空'
					 }
					 }
					 },
					 city : {
					 validators: {
					 notEmpty: {
					 message: '注册地址市不能为空'
					 }
					 }
					 },
					 address: {
					 validators: {
					 notEmpty: {
					 message: '注册详细地址不能为空'
					 }
					 }
					 },
					 longitude: {
					 validators: {
					 notEmpty: {
					 message: '注册地址经度'
					 },
					 regexp: {
					 regexp: '^[0-9]+([.]{1}[0-9]+){0,1}$',
					 message: '注册地址经度必须是数字'
					 }
					 }
					 },
					 latitude: {
					 validators: {
					 notEmpty: {
					 message: '注册地址经度'
					 },
					 regexp: {
					 regexp: '^[0-9]+([.]{1}[0-9]+){0,1}$',
					 message: '注册地址纬度必须是数字'
					 }
					 }
					 },
					 indu_com_number: {
					 message: 'The cardno is not valid',
					 validators: {
					 stringLength: {
					 max: 18,
					 message: '工商注册号不能超过18位'
					 }
					 }
					 }
					 },
					 tax_number: {
					 message: 'The cardno is not valid',
					 validators: {
					 stringLength: {
					 max: 18,
					 message: '税务注册号不能超过18位'
					 }
					 }*/
					 }
		    });
			    
		function save(){

			
			/*手动验证表单，当是普通按钮时。*/
			/*	$('#gastationform').data('bootstrapValidator').validate();
			if(!$('#gastationform').data('bootstrapValidator').isValid()){
				return ;
			}*/
			
			var options ={   
		            url:'../crmInterface/crmGasPriceService/updateGasPrice',
		            type:'post',                    
		            dataType:'text',
		            success:function(data){
		            	$("#main").html(data);
						$("#modal-table").modal("show");
		            },error:function(XMLHttpRequest, textStatus, errorThrown) {

		 	       }
			}
						
			$("#gastationform").ajaxSubmit(options);
		}
		
		function returnpage(){
			loadPage('#main', '<%=basePath%>/crmInterface/crmGasPriceService/queryAllGasPriceList');
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
		</script>
