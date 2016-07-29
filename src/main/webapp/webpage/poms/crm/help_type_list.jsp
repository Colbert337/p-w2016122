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
<script type="text/javascript">  
    // 删除用户
    function deletequestion(crmHelpId){
        bootbox.setLocale("zh_CN");
        bootbox.confirm("确认要删除用户吗？", function (result) {
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
    
$(function() {
   $("#datepicker").datepicker();
      });
</script>
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
                    <div class="search-types">
                        <div class="item">
						    <label>分类编号:</label>
							    <input type="text" name="crmHelpTypeId" placeholder="分类编号"  maxlength="15" value="${list.crmHelpTypeId}"/>
						</div>
                        <div class="item">
                            <button class="btn btn-sm btn-primary" type="button" onclick="commitForm();">
                                <i class="ace-icon fa fa-flask align-top bigger-125"></i>
                                                                                   类型查询
                            </button>
                            <button class="btn btn-sm" type="button" onclick="init();">
                                                                                  重置
                            </button>
                            <div class="item"></div>
                            <!--  <button class="btn btn-sm btn-primary" type="button" onclick="loadPage('#main','<%=basePath%>/webpage/poms/card/charge_new.jsp');">
                                <i class="ace-icon fa fa-flask align-top bigger-125"></i>
                                                                                 添加类型
                            </button>
                             <button class="btn btn-sm btn-primary" type="button" onclick="loadPage('#main','<%=basePath%>/webpage/poms/crm/help_new.jsp');">
                                <i class="ace-icon fa fa-flask align-top bigger-125"></i>
                                                                                 添加问题
                            </button>
                            -->
                        </div>
                    </div>
                    <div class="sjny-table-responsive">
                    <table id="simple-table" class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th onclick="orderBy(this,'id');commitForm();" id="id_order">分类编号</th>
                            <th onclick="orderBy(this,'id');commitForm();" id="id_order">分类名称</th>
                            <th onclick="orderBy(this,'ownid');commitForm();" id="ownid_order">分类备注</th>
                            <th onclick="orderBy(this,'useTime');commitForm();" id="useTime_order">是否删除</th>
                            <th onclick="orderBy(this,'cash');commitForm();" id="cash_order">添加时间</th>
                            <th onclick="orderBy(this,'cash');commitForm();" id="cash_order">修改时间</th>
                            <th class="text-center td-w2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${crmHelpTypeList.list}" var="list">
                            <tr>
                                <td>${list.crmHelpTypeId}</td>
                                <td>${list.title}</td>
                                <td>${list.remark}</td>
                                <td>${list.isDeleted}</td>                                                         
                                 <td><fmt:formatDate value="${list.createdDate}" type="both" pattern="yyyy-MM-dd"/></td>  
                                 <td><fmt:formatDate value="${list.updatedDate}" type="both" pattern="yyyy-MM-dd"/></td>                                                                                   
                                <td class="text-center">
                                    <a class="" href="javascript:void(0);" title="编辑" data-rel="tooltip">
                                            <i class="ace-icon fa fa-pencil bigger-130" onclick="preUpdate(this);"></i>
                                    </a>
                                    <a class="" href="javascript:deletequestion('${list.crmHelpTypeId}');" title="删除" data-rel="tooltip">
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
					<div class="dataTables_info" id="dynamic-table_info" role="status" aria-live="polite">每页 ${crmHelpTypeList.pageSize} 条|共 ${crmHelpTypeList.total} 条|共 ${crmHelpTypeList.pages} 页</div>
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



