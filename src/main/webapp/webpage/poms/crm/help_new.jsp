<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script type="text/javascript">
  
/*初始化选择菜单*/
$(function(){
	var Id = '${order.channel}';
	$.ajax({
		url:"../web/crm/help/type/query",
		data:{},
		async:false,
		type: "POST",
		success: function(data){
			$("#channel").empty();
			$("#channel").append("<option value=''>--请选择问题类型--</option>");
			$.each(data,function(i,val){
				if(val.crmHelpId == Id){
					$("#channel").append("<option value='"+val.crmHelpId+"' selected='selected'>"+val.crmHelpTypeId+"</option>");
				}else{
					$("#channel").append("<option value='"+val.crmHelpId+"'>"+val.crmHelpTypeId+"</option>");
				}
			});
		}
	})
})
</script>
<script src="<%=basePath %>/dist/js/crm/help_new.js"></script>

            <div class="main-content">
                <div class="main-content-inner">

                    <div class="">
                        <!-- /section:settings.box -->
                        <div class="page-header">
                            <h1>
                                                                           添加问题
                            </h1>
                        </div>

                        <div class="row">
                            <div class="col-xs-12">
                                <!-- PAGE CONTENT BEGINS -->
                                <form class="form-horizontal" id="formnew">
                                   
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
										    <textarea class="limited form-control" id="question" name="question" maxlength="100" style="resize: none;"></textarea>
                                          </div>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right">内容： </label>
                                        <div class="col-sm-4">
                                            <textarea class="limited form-control" id="answer" name="answer" maxlength="50" style="resize: none;"></textarea>
                                        </div>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="crmHelpTypeId">类型：</label>
                                        <div class="col-sm-4">
                                         <select class="chosen-select form-control" id="channel" name="channel" data-placeholder="类型"></select>
                                        </div>                               
                                    </div>
                                    
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="isNotice">是否公告： </label>

                                        <div class="col-sm-4">
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
                                    </div>                                                                       
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="issuer">发布人： </label>

                                        <div class="col-sm-4">
                                            <input type="text" id="issuer" name="issuer" placeholder="发布人" class="form-control" value="${crmHelp.issuer}"/>
                                        </div>
                                    </div> 
                                    
                                     <%-- <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="createdDate">发布时间：</label>
                                        <div class="col-sm-4">
                                            <div class="input-group">
														<input class="form-control date-picker" name="createdDate" id="datepicker" type="text" data-date-format="yyyy-mm-dd" readonly="readonly"/>
														<span class="input-group-addon">
																<i class="fa fa-calendar bigger-110"></i>
														</span>
										    </div>
                                        </div>                               
                                    </div> --%>
                                                                                                                                          
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
$("#formnew").bootstrapValidator({
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