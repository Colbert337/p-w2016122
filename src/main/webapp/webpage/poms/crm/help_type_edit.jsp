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
//修改  
function save(){
        var options ={   
                url:'../web/crm/help/type/update',   
                type:'post',                    
                dataType:'text',
                success:function(data){
                    $("#main").html(data);
                    alert("修改成功");
                 },error:function(XMLHttpRequest, textStatus, errorThrown) {
                        
                  }
            }    
        $("#formedit").ajaxSubmit(options);
}
    
//返回
function returnpage(){
    loadPage('#main', '../web/crm/help/type/list');
}
</script>

            <div class="main-content">
                <div class="main-content-inner">
                    <div class="">
                        <div class="page-header">
                            <h1>
                                                                           编辑问题分类
                            </h1>
                        </div>

                        <div class="row">
                            <div class="col-xs-12">
                                <!-- PAGE CONTENT BEGINS -->
                                <form class="form-horizontal" id="formedit">
                                    <div class="form-group">
                                        <input type="hidden" id="crmHelpTypeId" name="crmHelpTypeId" value="${crmHelpType.crmHelpTypeId}"/>
                                        <label class="col-sm-3 control-label no-padding-right" for="title">分类名称： </label>
                                        <div class="col-sm-4">
                                            <input type="text" id="title" name="title" placeholder="分类名称"  class="form-control" value="${crmHelpType.title}"/>
                                        </div>
                                    </div>
                                
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="remark">分类备注： </label>
                                        <div class="col-sm-4">
                                        <textarea class="limited form-control" id="remark" name="remark" maxlength="50" style="resize: none;">${crmHelpType.remark}</textarea>
                                        </div>
                                    </div>                                                                      
                                    
                                                                                                                                      
                                     <%-- <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="createdDate">添加时间：</label>
                                        <div class="col-sm-4">
                                         <div class="input-group">
														<input class="form-control date-picker" name="expiry_date_frompage" id="datepicker" type="text"  value="<fmt:formatDate value="${crmHelpType.createdDate}" type="both" pattern="yyyy-MM-dd HH:mm"/>"/>														                                										
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
														<input class="form-control date-picker" name="expiry_date_frompage" id="datepicker" type="text"  value="<fmt:formatDate value="${crmHelpType.updatedDate}" type="both" pattern="yyyy-MM-dd HH:mm"/>"/>														                                										
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
                                                                                                                         确定
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