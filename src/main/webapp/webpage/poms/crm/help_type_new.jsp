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
  
//保存
function typesave(){
	$('#formnew').data('bootstrapValidator').validate();
	if(!$('#formnew').data('bootstrapValidator').isValid()){
		return ;
	}
	if($('.user-name-valid').is(':visible')){
		return ;
	}
        var options ={   
                url:'../web/crm/help/type/save',   
                type:'post',                    
                dataType:'text',
                success:function(data){
                    $("#main").html(data);
                    alert("保存成功");
                 },error:function(XMLHttpRequest, textStatus, errorThrown) {
                        
                  }
            }    
        $("#formnew").ajaxSubmit(options);
    }
    
function returnpage(){
	loadPage('#main', '../web/crm/help/list');
} 
</script>
            <div class="main-content">
                <div class="main-content-inner">

                    <div class="">
                        <!-- /section:settings.box -->
                        <div class="page-header">
                            <h1>
                                                                           添加类型
                            </h1>
                        </div>

                        <div class="row">
                            <div class="col-xs-12">
                                <!-- PAGE CONTENT BEGINS -->
                                <form class="form-horizontal" id="formnew">
                                   
                                 <!--   <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="crmHelpTypeId">分类编号： </label>
                                        <div class="col-sm-4">
                                            <input type="text" name="crmHelpTypeId" placeholder="标题"  class="form-control"/>
                                        </div>
                                    </div> -->
                                   
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="title">分类名称： </label>
                                        <div class="col-sm-4">
                                            <input type="text" name="title" placeholder="分类名称"  maxlength="15" class="form-control" />
                                        </div>
                                    </div>
                                
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="remark">分类备注： </label>
                                          <div class="col-sm-4">                               
										    <textarea class="limited form-control" id="remark" name="remark" maxlength="30" style="resize: none;"></textarea>
                                          </div>
                                    </div>
                                    
                                     <!-- <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="createdDate">添加时间： </label>
                                            <div class="col-sm-4">
                                            <div class="input-group">
														<input class="form-control date-picker" name="createdDate" id="datepicker" type="text" data-date-format="yyyy-mm-dd" readonly="readonly"/>
														<span class="input-group-addon">
																<i class="fa fa-calendar bigger-110"></i>
														</span>
										    </div>
                                        </div>      
                                    </div>   
                                     <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="updatedDate">修改时间：</label>
                                        <div class="col-sm-4">
                                            <div class="input-group">
														<input class="form-control date-picker" name="updatedDate" id="datepicker1" type="text" data-date-format="yyyy-mm-dd" readonly="readonly"/>
														<span class="input-group-addon">
																<i class="fa fa-calendar bigger-110"></i>
														</span>
										    </div>
                                        </div>                               
                                    </div>
                                     -->                                                                                                  
                                    <div class="clearfix form-actions">
                                        <div class="col-md-offset-3 col-md-9">
                                            
                                            <button class="btn btn-info" type="button" onclick="typesave();">
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
        title: {
            message: 'The cardno is not valid',
            validators: {
                notEmpty: {
                    message: '分类名称不能为空'
                },
                stringLength: {
                    min: 1,
                    max: 15,
                    message: '分类名称不能超过15个字符'
                }
            }
        },
        remark: {
            validators: {
                notEmpty: {
                    message: '分类备注不能为空'
                },
                stringLength: {
                    min: 1,
                    max: 30,
                    message: '分类备注不能超过30个字符'
                }
            }
        },
     }
});
</script>