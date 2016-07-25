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
</script>
<script src="<%=basePath %>/dist/js/card/charge_update.js"></script>

			<div class="main-content">
				<div class="main-content-inner">

					<div class="">
						<!-- /section:settings.box -->
						<div class="page-header">
							<h1>
								编辑用户
							</h1>
						</div>

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<form class="form-horizontal" id="formcharge">
								    <div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="ownid">充值卡号： </label>
										<div class="col-sm-4">
											<input type="text" name="id" placeholder="充值卡号"  class="form-control" value="${sysongyChargeCard.id}"/>
										</div>
									</div>
								
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="ownid">用户卡号： </label>
										<div class="col-sm-4">
											<input type="text" name="ownid" placeholder="用户卡号"  class="form-control" value="${sysongyChargeCard.ownid}"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">用户卡密码： </label>
										<div class="col-sm-4">
											<input type="text" name="password" placeholder="用户卡密码"  class="form-control" value="${sysongyChargeCard.password}"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="useTime">充值时间：</label>
										<div class="col-sm-4">
											<input type="text" id="datepicker" name="useTime" placeholder="充值时间" class="form-control" value="<fmt:formatDate value="${sysongyChargeCard.useTime}" type="both" pattern="yyyy-MM-dd"/>"/>
										</div>                               
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="cash">充值金额： </label>

										<div class="col-sm-4">
											<input type="text" id="cash" name="cash" placeholder="充值金额" class="form-control" value="${sysongyChargeCard.cash}"/>
										</div>
									</div>																								
									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											
											<button class="btn btn-info" type="button" onclick="save();">
												<i class="ace-icon fa fa-check bigger-110"></i>
												确定
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
                    message: '充值卡号不能超过10个汉字'
                }
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
                    max: 5,
                    message: '充值金额不能超过5位'
                }
            }
        },
      }
});
</script>