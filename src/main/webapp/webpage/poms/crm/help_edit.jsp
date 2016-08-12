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
/*初始化选择菜单*/
$(function(){
	var Id = '${crmHelp.crmHelpTypeId}';
	$.ajax({
		url:"../web/crm/help/question/type/query",
		data:{},
		async:false,
		type: "POST",
		success: function(data){
			$("#crmHelpTypeId").empty();
			$("#crmHelpTypeId").append("<option value=''>--全部--</option>");
			$.each(data,function(i,val){
				if(val.crmHelpTypeId == Id){
					$("#crmHelpTypeId").append("<option value='"+val.crmHelpTypeId+"' selected='selected'>"+val.title+"</option>");					
				}else{
					$("#crmHelpTypeId").append("<option value='"+val.crmHelpTypeId+"'>"+val.title+"</option>");
				}
			});			
		}
	})
	var isNotice = '${crmHelp.isNotice}';
	if(isNotice == ""){
		$("#isNotice").val("");
	}else if(isNotice == "2"){
		$("#isNotice").val("2");
	}else if(isNotice == "1"){
		$("#isNotice").val("1");
	} 
})
</script>

            <div class="main-content">
                <div class="main-content-inner">
                    <div class="">
                        <div class="page-header">
                            <h1>
                                                                           编辑常见问题
                            </h1>
                        </div>

                        <div class="row">
                            <div class="col-xs-12">
                                <!-- PAGE CONTENT BEGINS -->
                                <form class="form-horizontal" id="formedit">
                                    <div class="form-group">
                                        <input type="hidden" id="crmHelpId" name="crmHelpId" value="${crmHelp.crmHelpId}"/>
                                        <label class="col-sm-3 control-label no-padding-right" for="title">标题： </label>
                                        <div class="col-sm-4">
                                            <input type="text" id="title" name="title" placeholder="标题"  class="form-control" value="${crmHelp.title}"/>
                                        </div>
                                    </div>
                                
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="question">问题： </label>
                                        <div class="col-sm-4">
                                        <textarea class="limited form-control" id="question" name="question" maxlength="100" style="resize: none;">${crmHelp.question}</textarea>
                                        </div>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right">内容： </label>
                                        <div class="col-sm-4">
                                           <textarea class="limited form-control" id="answer" name="answer" maxlength="500" style="resize: none;">${crmHelp.answer}</textarea>                                            
                                        </div>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="crmHelpTypeId">类型：</label>
                                        <div class="col-sm-4">
                                            <select class="chosen-select form-control" id="crmHelpTypeId" name="crmHelpTypeId"></select>                                           
                                        </div>                               
                                    </div>
                                    
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="isNotice">是否公告： </label>
                                          
                                          <div class="col-sm-8">
										      <div class="radio">
										      <label>
											    <input name="isNotice" id="isNotice_yes" type="radio" class="ace" checked="checked" value="2">
											      <span class="lbl">是</span>
										       </label>
										 <label>
											<input name="isNotice" id="isNotice_no" type="radio" class="ace" value="1">
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
                                    
                                   <%--   <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="createdDate">发布时间：</label>
                                        <div class="col-sm-4">
                                         <div class="input-group">
														<input class="form-control date-picker" name="createdDate" id="datepicker" type="text"  value="<fmt:formatDate value="${crmHelp.createdDate}" type="both" pattern="yyyy-MM-dd HH:mm"/>"/>														                                										
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
