<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script src="<%=basePath %>/dist/js/crm/help_list.js"></script>
<script type="text/javascript">
//删除用户
function deletequestion(crmHelpId){
    bootbox.setLocale("zh_CN");
    bootbox.confirm("确认要删除吗？", function (result) {
        if (result) {
            var deleteOptions ={
                url:'<%=basePath%>/web/crm/help/delete',
                data:{crmHelpId:crmHelpId},
                type:'post',
                dataType:'text',
                success:function(data){
                    $("#main").html(data);
                    $('[data-rel="tooltip"]').tooltip();
                }
            }
            $("#listForm").ajaxSubmit(deleteOptions);
    }
 })
}

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
<div class="page-header">
    <h1>
      常见问题
    </h1>
</div><!-- /.page-header -->

<div class="row">
    <div class="col-xs-12">
        <form class="form-horizontal"  id="listForm">
            <jsp:include page="/common/page_param.jsp"></jsp:include>
            <!-- PAGE CONTENT BEGINS -->
            <div class="row">
                <div class="col-xs-12">
                    <div class="search-types">
                        <div class="item">
						    <label>问题类型:</label>								    
						    <select id="crmHelpTypeId" name="crmHelpTypeId"></select>						    
						</div>

						<div class="item">
							<label>是否公告:</label>
							<select id="isNotice" class="chosen-select " name="isNotice">							                                         
							         <option value="">--全部--</option>
									 <option value="1">帮助</option>							    						    
									 <option value="2">公告</option>
							</select>
						</div>
                        <div class="item">
                            <button class="btn btn-sm btn-primary" type="button" onclick="commitForm();">
                                <i class="ace-icon fa fa-flask align-top bigger-125"></i>
                                                                                   查询
                            </button>
                            <button class="btn btn-sm" type="button" onclick="init();">
                                                                                  重置
                            </button>
                            <div class="item"></div>
                            <button class="btn btn-sm btn-primary" type="button" onclick="loadPage('#main','<%=basePath%>/webpage/poms/crm/help_type_new.jsp');">
                                <i class="ace-icon fa fa-flask align-top bigger-125"></i>
                                                                                 添加类型
                            </button>
                            <button class="btn btn-sm btn-primary" type="button" onclick="loadPage('#main','<%=basePath%>/webpage/poms/crm/help_new.jsp');">
                                <i class="ace-icon fa fa-flask align-top bigger-125"></i>
                                                                                 添加问题
                            </button>
                            <button class="btn btn-sm btn-primary" type="button" onclick="loadPage('#main','../web/crm/help/type/list');">
                                <i class="ace-icon fa fa-flask align-top bigger-125"></i>
                                                                                 查看问题分类
                            </button>           
                        </div>                         				 					  				      
                    </div>                   
                    <div class="sjny-table-responsive">
                    <table id="simple-table" class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th onclick="orderBy(this,'crmHelpId');commitForm();" id="crmHelpId_order">编号</th>
                            <th>标题</th>
                            <th>问题</th>
                            <th>内容</th>
                            <th onclick="orderBy(this,'crmHelpTypeId');commitForm();" id="crmHelpTypeId_order">类型</th>
                            <th onclick="orderBy(this,'isNotice');commitForm();" id="isNotice_order">是否公告</th>
                            <th onclick="orderBy(this,'issuer');commitForm();" id="issuer_order">发布人</th>
                            <th onclick="orderBy(this,'createdDate');commitForm();" id="createdDate_order">发布时间</th>
                            <th class="text-center td-w2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pageInfo.list}" var="list" varStatus="status">
                            <tr>
                                <td>${status.index+1}</td>
                                <td>${list.title}</td>
                                <td>${list.question}</td>
                                <td answer="${list.answer}">
                                  <c:choose>
                                    <c:when test="${fn:length(list.answer)>30}"><a title="${list.answer }" >${fn:substring(list.answer,0,30)}......</a></c:when>
                                    <c:otherwise>${list.answer}</c:otherwise>
                                  </c:choose>                              
                                <td/>                                                           
                                <td>${list.titleType}</td> 
                                <td>
                                   <c:choose>
                                      <c:when test="${list.isNotice==2}">公告</c:when>
                                      <c:otherwise>帮助</c:otherwise>
                                  </c:choose>
                               </td>
                                <td>${list.issuer}</td>  
                                <td><fmt:formatDate value="${list.createdDate}" type="both" pattern="yyyy-MM-dd HH:mm"/></td>                                                                                
                                <td class="text-center">
                                    <a class="" href="javascript:void(0);" title="编辑" data-rel="tooltip">
                                            <i class="ace-icon fa fa-pencil bigger-130" onclick="preUpdate(this);"></i>
                                    </a>
                                    <a class="" href="javascript:deletequestion('${list.crmHelpId}');" title="删除" data-rel="tooltip">
                                        <span class="ace-icon fa fa-trash-o bigger-130"></span>
                                    </a>
                                </td>
                           </tr>
                        </c:forEach>
                        </tbody>
                     </table>
                </div> 
     			<div class="row">
				<div class="col-sm-6">
					<div class="dataTables_info" id="dynamic-table_info" role="status" aria-live="polite">每页 ${pageInfo.pageSize} 条|共 ${pageInfo.total} 条|共 ${pageInfo.pages} 页</div>
				</div>
				<div class="col-sm-6">
					<div class="dataTables_paginate paging_simple_numbers" id="dynamic-table_paginate">
						<ul id="ulhandle" class="pagination">
							<li id="previous" class="paginate_button previous" aria-controls="dynamic-table" tabindex="0">
								<a href="javascript:void(0);" aria-label="Previous" onclick="prepage('#listForm');">
									<span aria-hidden="true">上一页</span>
								</a>
							</li>
							<li id="next" class="paginate_button next" aria-controls="dynamic-table" tabindex="0">
								<a id="nexthandle" href="javascript:nextpage('#listForm');" aria-label="Next" >
									<span aria-hidden="true">下一页</span>
								</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
            <jsp:include page="/common/message.jsp"></jsp:include>
          </div>
        </div>
     </form>
</div>
