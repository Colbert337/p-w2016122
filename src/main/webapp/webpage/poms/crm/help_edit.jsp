<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script src="<%=basePath %>/dist/js/crm/help_edit.js"></script>
<script type="text/javascript">
$(function() {
    $("#datepicker").datepicker();
  });
</script>

            <div class="main-content">
                <div class="main-content-inner">
                    <div class="">
                        <div class="page-header">
                            <h1>
                                                                           编辑用户
                            </h1>
                        </div>

                        <div class="row">
                            <div class="col-xs-12">
                                <!-- PAGE CONTENT BEGINS -->
                                <form class="form-horizontal" id="formedit">
                                   
                                   <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="crmHelpId">题号： </label>
                                        <div class="col-sm-4">
                                            <input type="text" name="crmHelpId" placeholder="标题"  class="form-control" value="${crmHelp.crmHelpId}"/>
                                        </div>
                                    </div>
                                   
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="title">标题： </label>
                                        <div class="col-sm-4">
                                            <input type="text" name="title" placeholder="标题"  class="form-control" value="${crmHelp.title}"/>
                                        </div>
                                    </div>
                                
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="question">问题： </label>
                                        <div class="col-sm-4">
                                        <textarea class="limited form-control" id="question" name="question" maxlength="50" style="resize: none;">${crmHelp.question}</textarea>
                                            <!--  <input type="text" name="question" placeholder="问题"  class="form-control" value="${crmHelp.question}"/>-->
                                        </div>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right">内容： </label>
                                        <div class="col-sm-4">
                                           <textarea class="limited form-control" id="question" name="question" maxlength="50" style="resize: none;">${crmHelp.answer}</textarea>
                                            <!--  <input type="text" name="answer" placeholder="内容"  class="form-control" value="${crmHelp.answer}"/>-->
                                        </div>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="crmHelpTypeId">类型：</label>
                                        <div class="col-sm-4">
                                            <select class="chosen-select form-control" id="crmHelpTypeId" name="crmHelpTypeId" data-placeholder="类型">
											   <s:option flag="true" gcode="PLF_TYPE" link="false" />
										    </select>
                                            <!-- <input type="text"  name="crmHelpTypeId" placeholder="类型" class="form-control"  value="${crmHelp.crmHelpTypeId}"/>-->
                                        </div>                               
                                    </div>
                                    
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="isNotice">是否公告： </label>
                                          
                                          <div class="col-sm-8">
										      <div class="radio">
										      <label>
											    <input name="isMenu" id="isNotice_yes" type="radio" class="ace" checked="checked" value="0">
											      <span class="lbl">是</span>
										       </label>
										 <label>
											<input name="isMenu" id="isNotice_no" type="radio" class="ace" value="1">
											<span class="lbl">否</span>
										</label>
									</div>
									</div>
                                          
                                        <!-- <div class="col-sm-4">       
                                             <input type="text" id="isNotice" name="isNotice" placeholder="是否公告" class="form-control" value="${crmHelp.isNotice}"/>
                                        </div>
                                        -->
                                    </div>
                                    
                                     
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="issuer">发布人： </label>

                                        <div class="col-sm-4">
                                            <input type="text" id="issuer" name="issuer" placeholder="发布人" class="form-control" value="${crmHelp.issuer}"/>
                                        </div>
                                    </div> 
                                    
                                     <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="createdDate">发布时间：</label>
                                        <div class="col-sm-4">
                                         <div class="input-group">
														<input class="form-control date-picker" name="expiry_date_frompage" id="datepicker" type="text" data-date-format="yyyy-mm-dd" readonly="readonly"/>
														<span class="input-group-addon">
																<i class="fa fa-calendar bigger-110"></i>
														</span>
											</div>
                                           <!--   <input type="text" id="datepicker" name="createdDate" placeholder="发布时间" class="form-control" value="<fmt:formatDate value="${crmHelp.createdDate}" type="both" pattern="yyyy-MM-dd"/>"/>-->
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
$("#formedit").bootstrapValidator({
    message: 'This value is not valid',
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
    	crmHelpId: {
            message: 'The cardno is not valid',
            validators: {
                notEmpty: {
                    message: '题号不能为空'
                },
                stringLength: {
                    min: 1,
                    max: 5,
                    message: '题号不能超过5个数字'
                }
            }
        },
        title: {
            message: 'The cardno is not valid',
            validators: {
                notEmpty: {
                    message: '标题不能为空'
                },
                stringLength: {
                    min: 1,
                    max: 20,
                    message: '标题不能超过20个汉字'
                }
            }
        },
        question: {
            validators: {
                notEmpty: {
                    message: '问题不能为空'
                },
                stringLength: {
                    min: 1,
                    max: 50,
                    message: '问题不能超过20个字符'
                }
            }
        },
        answer: {
        	 validators: {
                 notEmpty: {
                     message: '内容不能为空'
                 },
                 stringLength: {
                     min: 1,
                     max: 50,
                     message: '内容不能超过50个字符'
                 }
             }
         },
         issuer: {
            message: 'The cardno is not valid',
            validators: {
                notEmpty: {
                    message: '发布人不能为空'
                },
                stringLength: {
                    min: 1,
                    max: 5,
                    message: '发布人不能超过5个字符'
                },
       createdDate: {
            message: 'The cardno is not valid',
            validators: { 
                notEmpty: {
                    message: '日期不能为空'
                },
                callback: {
                	message: '日期必须大于等于当前日期',
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
            }
        }
     }
});
</script>