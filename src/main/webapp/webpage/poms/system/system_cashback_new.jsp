<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
	<script type="text/javascript" src="<%=basePath%>/dist/js/sysparam/system_cashback_new.js"></script>

					<div class="">
						<!-- /section:settings.box -->
						<div class="page-header">
							<h1>
								配置返现规则
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<form class="form-horizontal"  id="cashbackform">
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">配置对象： </label>
										
										<div class="col-sm-4">
											<label class="control-label no-padding-right" > <s:Code2Name mcode="${param.sys_cash_back_no}" gcode="CASHBACK"></s:Code2Name> </label>
											<input type="hidden" name="sys_cash_back_no" id="sys_cash_back_no" value="${param.sys_cash_back_no}"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">优先级： </label>
										<div class="col-sm-4">
											<select class="form-control" name="level" onchange="gainProp(this);">
												<s:option flag="true" gcode="CASHBACKLEVEL" form="sysCashBack" field="level"  />
											</select>
										</div>
									</div>
									
									<div class="form-group" id="div1">
										<label class="col-sm-3 control-label no-padding-right" id="label1">阈最小值： </label>
										
										<div class="col-sm-4">
											<input type="text" name="threshold_min_value" id="input1" placeholder="输入该阈最小值" class="form-control" maxlength="6" value="${sysCashBack.threshold_min_value}" readonly="readonly"/>
										</div>
									</div>
									
									<div class="form-group" id="div2">
										<label class="col-sm-3 control-label no-padding-right" id="label2">阈最大值（包含）： </label>

										<div class="col-sm-4">
											<input type="text" name="threshold_max_value" id="input2" placeholder="输入该阈最大值" class="form-control" maxlength="6" value="${sysCashBack.threshold_max_value}"/>
										</div>
									</div>
									
									<div class="form-group" id="div3">
										<label class="col-sm-3 control-label no-padding-right" id="label3">返现系数： </label>

										<div class="col-sm-4">
											<input type="text" name="cash_per" placeholder="输入返现系数" id="input3" class="form-control" maxlength="5" value="${sysCashBack.threshold_max_value}"/>
											<p class="text-error" id="p">（返现金额=充值金额 X 返现系数）</p>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">状态： </label>
										<div class="col-sm-4">
												<select class="form-control" name="status">
													<s:option flag="true" gcode="CASHBACKSTATUS" form="sysCashBack" field="status"  />
												</select>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">生效日期： </label>
										<div class="col-sm-4">
												<div class="input-group">
														<input class="form-control date-picker" name="start_date_after" type="text" readonly="readonly" data-date-format="yyyy-mm-dd" value="${param.start_date_after}"/>
														<span class="input-group-addon">
															<i class="fa fa-calendar bigger-110"></i>
														</span>
												</div>
										</div>
									</div>
									
									<div class="form-group start-date-before">
										<label class="col-sm-3 control-label no-padding-right">失效日期： </label>
										<div class="col-sm-4">
												<div class="input-group">
														<input class="form-control date-picker" name="start_date_before"  type="text" readonly="readonly" data-date-format="yyyy-mm-dd" value="${param.start_date_before}"/>
														<span class="input-group-addon">
																<i class="fa fa-calendar bigger-110"></i>
														</span>
												</div>
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
<script>
if('${param.sys_cash_back_no}'=='201'|| '${param.sys_cash_back_no}'=='202'){
	$("#div1").hide();
	$("#div2").hide();
	$("#label3").text("定额返现");
	$("#input3").attr('placeholder','请输入返现金额');
	$("#p").text("返现金额");
	$('#cashbackform').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
      
            cash_per: {
                validators: {
                    notEmpty: {
                        message: '返现金额不能为空'
                    },
                    regexp: {
                        regexp: '^([0-9])|([1-9]\d+)\.\d?$',
                        message: '返现金额必须是数字'
                    }
                }
            },
            status: {
                validators: {
                    notEmpty: {
                        message: '状态不能为空'
                    }
                }
            },
            level: {
                validators: {
                    notEmpty: {
                        message: '优先级不能为空'
                    }
                }
            },
            start_date_after: {
                validators: {
                    notEmpty: {
                        message: '生效日期不能为空'
                    }/*,
                    callback: {
                    	message: '生效日期必须大于等于当前日期',
                    	callback: function (value, validator, $field) {
                    		return compareDate(value ,new Date().toLocaleDateString());
                        }
                    }*/
                },
                trigger: 'change'
            },
            start_date_before: {
                validators: { 
                    notEmpty: {
                        message: '失效日期不能为空'
                    },
                    callback: {
                    	callback: function (value, validator, $field) {
                    		if(!compareDate(value ,new Date().toLocaleDateString())){
                    			var txt = $('.start-date-before small[data-bv-validator="callback"]').text('失效日期必须大于等于当前日期');

                    			return false;
                    		}     		
                    		return !compareDate($('[name=start_date_after]').val(), value);
                        },
                        message: '失效日期必须大于等于生效日期'
                    }
                },
                trigger: 'change'
            }
        }
    });
}else if('${param.sys_cash_back_no}'=='203'){
	$("#label1").text('邀请人返现金额');
	$("#input1").attr('placeholder','请输入邀请人返现金额');
	$("#input1").removeAttr("readonly");
	$("#label2").text('被邀请人返现金额');
	$("#input2").attr('placeholder','请输入被邀请人返现金额');
	$("#div3").hide();
	$('#cashbackform').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            threshold_max_value: {
                validators: { 
                    notEmpty: {
                        message: '被邀请人返现金额不能为空'
                    },
                    regexp: {
                        regexp: '^[0-9]*$',
                        message: '被邀请人返现金额必须是整数' 
                    }
                   
                }
            },
            cash_per: {
                validators: {
                    notEmpty: {
                        message: '返现系数不能为空'
                    },
                    regexp: {
                        regexp: '^([0-9])|([1-9]\d+)\.\d?$',
                        message: '返现系数必须是数字'
                    }
                }
            },
            status: {
                validators: {
                    notEmpty: {
                        message: '状态不能为空'
                    }
                }
            },
            level: {
                validators: {
                    notEmpty: {
                        message: '优先级不能为空'
                    }
                }
            },
            start_date_after: {
                validators: {
                    notEmpty: {
                        message: '生效日期不能为空'
                    }/*,
                    callback: {
                    	message: '生效日期必须大于等于当前日期',
                    	callback: function (value, validator, $field) {
                    		return compareDate(value ,new Date().toLocaleDateString());
                        }
                    }*/
                },
                trigger: 'change'
            },
            start_date_before: {
                validators: { 
                    notEmpty: {
                        message: '失效日期不能为空'
                    },
                    callback: {
                    	callback: function (value, validator, $field) {
                    		if(!compareDate(value ,new Date().toLocaleDateString())){
                    			var txt = $('.start-date-before small[data-bv-validator="callback"]').text('失效日期必须大于等于当前日期');

                    			return false;
                    		}     		
                    		return !compareDate($('[name=start_date_after]').val(), value);
                        },
                        message: '失效日期必须大于等于生效日期'
                    }
                },
                trigger: 'change'
            }
        }
    });
}else{
	$('#cashbackform').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            threshold_max_value: {
                validators: { 
                    notEmpty: {
                        message: '阈最大值不能为空'
                    },
                    regexp: {
                        regexp: '^[0-9]*$',
                        message: '阈最大值必须是整数'
                    },
                    callback: {
                    	message: '阈最大值必须大于最小值',
                    	callback: function (value, validator, $field) {
                             if(parseFloat($('[name=threshold_min_value]').val()) >= value){
                            	 return false;
                             }
                             return true;
                        }
                    }
                }
            },
            cash_per: {
                validators: {
                    notEmpty: {
                        message: '返现系数不能为空'
                    },
                    regexp: {
                        regexp: '^([0-9])|([1-9]\d+)\.\d?$',
                        message: '返现系数必须是数字'
                    }
                }
            },
            status: {
                validators: {
                    notEmpty: {
                        message: '状态不能为空'
                    }
                }
            },
            level: {
                validators: {
                    notEmpty: {
                        message: '优先级不能为空'
                    }
                }
            },
            start_date_after: {
                validators: {
                    notEmpty: {
                        message: '生效日期不能为空'
                    }/*,
                    callback: {
                    	message: '生效日期必须大于等于当前日期',
                    	callback: function (value, validator, $field) {
                    		return compareDate(value ,new Date().toLocaleDateString());
                        }
                    }*/
                },
                trigger: 'change'
            },
            start_date_before: {
                validators: { 
                    notEmpty: {
                        message: '失效日期不能为空'
                    },
                    callback: {
                    	callback: function (value, validator, $field) {
                    		if(!compareDate(value ,new Date().toLocaleDateString())){
                    			var txt = $('.start-date-before small[data-bv-validator="callback"]').text('失效日期必须大于等于当前日期');

                    			return false;
                    		}     		
                    		return !compareDate($('[name=start_date_after]').val(), value);
                        },
                        message: '失效日期必须大于等于生效日期'
                    }
                },
                trigger: 'change'
            }
        }
    });
	
	
	
}
</script>