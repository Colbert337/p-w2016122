<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script src="<%=basePath %>/dist/js/crm/help_new.js"></script>
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
                                   
                                   <!--  <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="crmHelpId">题号： </label>
                                        <div class="col-sm-4">
                                            <input type="text" name="crmHelpId" placeholder="题号"  class="form-control" value="${crmHelp.crmHelpId}"/>
                                        </div>
                                    </div>
                                   -->
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="title">标题： </label>
                                        <div class="col-sm-4">
                                            <input type="text" name="title" placeholder="标题"  maxlength="10" class="form-control" value="${crmHelp.title}"/>
                                        </div>
                                    </div>
                                
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="question">问题： </label>
                                          <div class="col-sm-4">                               
										    <textarea class="limited form-control" id="question" name="question" maxlength="500" style="resize: none;"></textarea>
                                          </div>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right">内容： </label>
                                        <div class="col-sm-4">
                                            <textarea class="limited form-control" id="answer" name="answer" maxlength="500" style="resize: none;"></textarea>
                                        </div>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="crmHelpTypeId">类型：</label>
                                        <div class="col-sm-4">
                                         <select class="chosen-select form-control" id="crmHelpTypeId" name="crmHelpTypeId" data-placeholder="类型"></select>
                                        </div>                               
                                    </div>
                                    
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="isNotice">是否公告： </label>

                                        <div class="col-sm-4">
                                            <div class="radio">
										<label>
											<input name="isNotice" id="isNotice_yes" type="radio" class="ace" value="2">
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
                                            <input type="text" id="issuer" name="issuer" placeholder="发布人" maxlength="5" class="form-control" value="${crmHelp.issuer}"/>
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