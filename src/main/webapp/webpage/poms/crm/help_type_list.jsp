<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<script src="<%=basePath %>/dist/js/crm/help_list.js"></script>
<div class="page-header">
    <h1>
                问题分类
    </h1>
</div><!-- /.page-header -->

<div class="row">
    <div class="col-xs-12">
        <form class="form-horizontal"  id="listForm">
            <jsp:include page="/common/page_param.jsp"></jsp:include>
            <!-- PAGE CONTENT BEGINS -->
            <div class="row">
                <div class="col-xs-12">
                      <div class="row">
				           <div class="col-xs-12">
					          <div class="pull-right btn-botton">
                                <button class="btn btn-sm btn-primary" type="button" onclick="loadPage('#main','../web/crm/help/list');">
							            返回常见问题
						        </button>
					         </div>
				        </div>
			         </div>                  
                    <div class="sjny-table-responsive">
                    <table id="simple-table" class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th onclick="orderBy(this,'title');commitForm();" id="title_order">分类名称</th>
                            <th onclick="orderBy(this,'remark');commitForm();" id="remark_order">分类备注</th>
                            <!-- <th onclick="orderBy(this,'isDeleted');commitForm();" id="isDeleted_order">是否删除</th> -->
                            <th onclick="orderBy(this,'createdDate');commitForm();" id="createdDate_order">添加时间</th>
                            <th onclick="orderBy(this,'updatedDate');commitForm();" id="updatedDate_order">修改时间</th>
                            <th class="text-center td-w2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pageInfo.list}" var="list" varStatus="status">
                            <tr>
                                <td>${status.index+1}</td>
                                <td>${list.title}</td>
                                <td>${list.remark}</td>                            
                               <%--  <td>
                                   <c:choose>
                                      <c:when test="${list.isDeleted==1}">未删除</c:when>
                                      <c:otherwise>删除</c:otherwise>
                                  </c:choose>
                                </td>         --%>                                                
                                 <td><fmt:formatDate value="${list.createdDate}" type="both" pattern="yyyy-MM-dd HH:mm"/></td>  
                                 <td><fmt:formatDate value="${list.updatedDate}" type="both" pattern="yyyy-MM-dd HH:mm"/></td>                                                                                   
                                <td class="text-center">
                                    <a class="" href="javascript:void(0);" title="编辑" data-rel="tooltip">
                                            <i class="ace-icon fa fa-pencil bigger-130" onclick="preUpdate(this);"></i>
                                    </a>
                                    <a class="" href="javascript:deletetype('${list.crmHelpTypeId}');" title="删除" data-rel="tooltip">
                                        <span class="ace-icon fa fa-trash-o bigger-130"></span>
                                    </a>
                                </td>
                           </tr>
                        </c:forEach>
                        </tbody>
                     </table>
                  </div>
               </div>
            </div>
        </form>
      </div>
</div>
<div class="row">
				<div class="col-sm-6">
					<div class="dataTables_info sjny-page" id="dynamic-table_info" role="status" aria-live="polite">
						每页 ${pageInfo.pageSize} 条 <span class="line">|</span> 共 ${pageInfo.total} 条 <span class="line">|</span> 共 ${pageInfo.pages} 页
						&nbsp;&nbsp;转到第 <input type="text" name="convertPageNum" style="height:25px;width:45px" maxlength="4"/>  页
						<button type="button" class="btn btn-white btn-sm btn-primary" onclick="commitForm();">跳转</button>
					</div>
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
<script type="text/javascript">  
    // 删除
    function deletetype(crmHelpTypeId){
        bootbox.setLocale("zh_CN");
        bootbox.confirm("确认要删除吗？", function (result) {
            if (result) {
                var deleteOptions ={
                    url:'<%=basePath%>/web/crm/help/type/delete',
                    data:{crmHelpTypeId:crmHelpTypeId},
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
//时间插件    
$(function() {
   $("#datepicker").datepicker();
      });
      
//编辑
function preUpdate(obj){        
        var crmHelpTypeId = $(obj).parents('tr').children("td").eq(1).text();        
        loadPage('#main', '../web/crm/help/type/edit?crmHelpTypeIdvalue='+crmHelpTypeId);
    }

function commitForm(obj){
	if(typeof obj == "undefined") {
		$("#pageNum").val("1");
	}else{
		$("#pageNum").val($(obj).text());
	}
	$("#listForm").ajaxSubmit(typeoptions);
}	
	var typeoptions ={ 
			url:'../web/crm/help/type/list', 
          type:'post',                    
          dataType:'html',
          success:function(data){
	              $("#main").html(data);	             
          },error:function(XMLHttpRequest, textStatus, errorThrown) {}
	}
    
function prepage(formid){
	//如果是第一页
	if(parseInt($("#pageNum").val()) <= 1){
		return ;
	}
	//设置当前页-1
	$("#pageNum").val(parseInt($("#pageNum").val())-1);
	$(formid).ajaxSubmit(typeoptions);
}

function nextpage(formid){
	//如果是最后一页
	if(parseInt($("#pageNum").val()) >= parseInt($("#pageNumMax").val())){
		return ;
	}
	//设置当前页+1
	$("#pageNum").val(parseInt($("#pageNum").val())+1);
	$(formid).ajaxSubmit(typeoptions);
}

</script>


