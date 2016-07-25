<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script>
  $(function() {
    $("#datepicker").datepicker();
  });
  
  /**
	 * 判断用户卡号是否存在
	 */
	function isExit(){
		var ownId = $("#ownid").val().replace(/\s/g,'');
		$.ajax({
			url:"<%=basePath%>/web/chargeCard/isExist",
			data:{ownId:ownId},
			type: "POST",
			success: function(data){
				if(data.valid){
					if($('.user-name-valid').is(':visible')){
						return false;
					}
					$("#ownid").after('<div class="tooltip fade top in user-name-valid"><div class="tooltip-arrow"></div><div class="tooltip-inner">用户卡号已存在!</div></div>');
				} else {
					$('.user-name-valid').remove();
				}
			}, error: function (XMLHttpRequest, textStatus, errorThrown) {

			}
		});
	}
	//重置
</script>
<script src="<%=basePath %>/dist/js/card/charge_new.js"></script>

			<div class="main-content">
				<div class="main-content-inner">
					<div class="">
						<div class="page-header">
							<h1>
								添加用户
							</h1>
						</div>

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<form class="form-horizontal" id="formcharge">
								    <div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="id">充值卡号： </label>

										<div class="col-sm-4">
											<input type="text" id="id" name="id" placeholder="请输入充值卡号" class="form-control"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="ownid">用户卡号： </label>

										<div class="col-sm-4">
											<input type="text" id="ownid" name="ownid" placeholder="请输入卡号" class="form-control"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="password">用户卡密码： </label>

										<div class="col-sm-4">
											<input type="text" id="password" name="password" placeholder="请输入密码" class="form-control"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="useTime">充值时间：</label>

										<div class="col-sm-4">
											<input type="text" id="datepicker" name="useTime" placeholder="请输入时间" class="form-control"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="cash">充值金额： </label>

										<div class="col-sm-4">
											<input type="text" id="cash" name="cash" placeholder="请输入金额" class="form-control"/>
										</div>
									</div>
									
									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											
											<button class="btn btn-info" type="button" onclick="save();">
												<i class="ace-icon fa fa-check bigger-110"></i>
												保存
											</button>
											&nbsp; &nbsp; &nbsp;
											<button class="btn" type="reset">
												<i class="ace-icon fa fa-repeat bigger-110"></i>
												重置
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
<script>
//bootstrap验证控件		
$("#formcharge").bootstrapValidator({
    message: 'This value is not valid',
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
    	id: {
            message: 'The cardno is not valid',
            validators: {
                notEmpty: {
                    message: '充值卡号不能为空'
                },
                regexp: {
                    regexp: '^[0-9]*[1-9][0-9]*$',
                    message: '充值卡号必须是数字'
                },
                stringLength: {
                    min: 1,
                    max: 10,
                    message: '充值卡号不能超过10个数字'
                },
            }
        },
        ownid: {
            message: 'The cardno is not valid',
            validators: {
                notEmpty: {
                    message: '用户卡号不能为空'
                },
                regexp: {
                    regexp: '^[0-9]*[1-9][0-9]*$',
                    message: '用户卡号必须是数字'
                },
                stringLength: {
                    min: 1,
                    max: 10,
                    message: '用户卡号不能超过10个汉字'
                }
            }
        },
        password: {
            message: 'The cardno is not valid',
            validators: {
                notEmpty: {
                    message: '用户密码不能为空'
                },
                stringLength: {
                    min: 6,
                    max: 9,
                    message: '用户密码长度必须大于等于6位'
                }
            }
        },
        cash: {
            message: 'The cardno is not valid',
            validators: {
                notEmpty: {
                    message: '充值金额不能为空'
                },
                regexp: {
                    regexp: '^[0-9]*[1-9][0-9]*$',
                    message: '充值金额必须是数字'
                },
                stringLength: {
                    min: 1,
                    max: 10,
                    message: '充值金额不能超过10位'
                }
            }
        },
      }
});
</script>