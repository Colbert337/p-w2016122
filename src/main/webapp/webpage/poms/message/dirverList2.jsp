<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
%>

<form id="formgastation">
		<input id="pageNum1" type="hidden"   value="${pageInfo1.pageNum}" />
	<input id="pageSize1" type="hidden"  value="${pageInfo1.pageSize}" />
	<input id="pageNumMax1" type="hidden"  value="${pageInfo1.pages}" />
	<input id="total1" type="hidden"  value="${pageInfo1.total}" />
	<input id="orderBy2" type="hidden"  value="${pageInfo1.orderBy}" />
	<input id="retValue" type="hidden" value="${ret.retValue}" />
	<input id="retCode" type="hidden" value="${ret.retCode}" />
	<input id="retMsg" type="hidden" value="${ret.retMsg}" />
	<div class="row">
	 
		<div class="sjny-table-responsive">
			<table id="dynamic-table"
				class="table table-striped table-bordered table-hover col-sm-10">
				<thead>
					<tr>
						<%-- <th class="center td-w1"><label class="pos-rel"> <!-- <input
								type="checkbox" class="ace" onclick="checkedAllRows(this);" /> -->
								<span class="lbl"></span>
						</label></th> --%>
						<th >账号</th>
						<th>姓名</th>
						<!-- 	<th onclick="orderBy1(this,'img_path');commitForm();"
											id="threshold_max_value_order">缩略图</th> -->
						<th>电话号码</th>
						<th>车牌号</th>
						<th >年检有效期</th>


					</tr>
				</thead>

				<tbody>

					<c:forEach items="${pageInfo1.list}" var="list" varStatus="s">
						<tr id="${list.sysDriverId }">
							<%-- <td class="center"><label class="pos-rel"> <input
									type="checkbox" class="ace checkbox"
									onchange="checkchange(this)" value1="${list.userName}" value="${list.deviceToken}"  id="${list.deviceToken}" /> <span
									class="lbl"></span>
							</label></td> --%>
							<td>${list.userName}</td>
							<td>${list.fullName}</td>
							<%-- 		<td><img width="150" height="150" alt="150x150"
												src="<%=imagePath %>${list.imgPath}" /></td> --%>
							<td>${list.mobilePhone}</td>
							<td>${list.plateNumber}</td>

							<td><fmt:formatDate value="${list.expiryDate}" type="both" /></td>

						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<!-- /.col -->
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
			<nav>
				<ul id="ulhandle1" class="pagination pull-right no-margin">
					<li id="previous1"><a href="javascript:void(0);"
						aria-label="Previous" onclick="prepage1('#formgastation');"> <span
							aria-hidden="true">上一页</span>
					</a></li>
					<li id="next1"><a id="nexthandle" href="javascript:void(0);"
						aria-label="Next" onclick="nextpage1('#formgastation');"> <span
							aria-hidden="true">下一页</span>
					</a></li>
				</ul>
			</nav>
		</div>
	</div>
	<jsp:include page="/common/message.jsp"></jsp:include>
</form>
<script type="text/javascript">


function scher() {
	$("#formgastation").ajaxSubmit({
		url : '../web/message/showUser?id=${message.id}',
		type : 'post',
		data:{
			pageNum:$("#pageNum1").val(),
			pageSize:$("#pageSize1").val(),
			pageNumMax:$("#pageNumMax1").val(),
			total:$("#total1").val(),
			orderby:$("#orderBy2").val()
		},
		dataType : 'html',
		success : function(data) {
			// console.log("这里分页之后");
			$("#content").html(data);
			
			$("#editModel").modal('show');
			if ($("#retCode").val() != "100") {

			}
			$('[data-rel="tooltip"]').tooltip();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {

		}
	});
}

function commitForm1(obj) {
	// 设置当前页的值
	console.log('commitForm1');
	if (typeof obj == "undefined") {
		$("#pageNum1").val("1");
	} else {
		$("#pageNum1").val($(obj).text());
	}

	$("#formgastation").ajaxSubmit({
		url : '../web/message/showUser?id=${message.id}',
		type : 'post',
		data:{
			pageNum:$("#pageNum1").val(),
			pageSize:$("#pageSize1").val(),
			pageNumMax:$("#pageNumMax1").val(),
			total:$("#total1").val(),
			orderby:$("#orderBy2").val()
		},
		dataType : 'html',
		success : function(data) {
			// console.log("这里分页之后");
			$("#content").html(data);
			
			$("#editModel").modal('show');
			if ($("#retCode").val() != "100") {

			}
			$('[data-rel="tooltip"]').tooltip();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {

		}
	});
}
 
function nextpage1(formid){
	//如果是最后一页
	console.log(parseInt($("#pageNum1").val()) +"----"+ parseInt($("#pageNumMax1").val()));
	if(parseInt($("#pageNum1").val()) >= parseInt($("#pageNumMax1").val())){
		return ;
	}
	//设置当前页+1
	$("#pageNum1").val(parseInt($("#pageNum1").val())+1);
	$(formid).ajaxSubmit({
		url : '../web/message/showUser?id=${message.id}',
		type : 'post',
		data:{
			pageNum:$("#pageNum1").val(),
			pageSize:$("#pageSize1").val(),
			pageNumMax:$("#pageNumMax1").val(),
			total:$("#total1").val(),
			orderby:$("#orderBy2").val()
		},
		dataType : 'html',
		success : function(data) {
			// console.log("这里分页之后");
			$("#content").html(data);
			
			$("#editModel").modal('show');
			if ($("#retCode").val() != "100") {

			}
			$('[data-rel="tooltip"]').tooltip();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {

		}
	});
}
function prepage1(formid){
	//如果是第一页
	if(parseInt($("#pageNum1").val()) <= 1){
		return ;
	}

	//设置当前页-1
	$("#pageNum1").val(parseInt($("#pageNum1").val())-1);

	$(formid).ajaxSubmit({
		url : '../web/message/showUser?id=${message.id}',
		type : 'post',
		data:{
			pageNum:$("#pageNum1").val(),
			pageSize:$("#pageSize1").val(),
			pageNumMax:$("#pageNumMax1").val(),
			total:$("#total1").val(),
			orderby:$("#orderBy2").val()
		},
		dataType : 'html',
		success : function(data) {
			// console.log("这里分页之后");
			$("#content").html(data);
			
			$("#editModel").modal('show');
			if ($("#retCode").val() != "100") {

			}
			$('[data-rel="tooltip"]').tooltip();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {

		}
	});
} 
window.onload = setCurrentPage1();


function setCurrentPage1(){
	var pagenum = parseInt($("#pageNum1").val());
	var pageNumMax = parseInt($("#pageNumMax1").val());

	if(pagenum == 1){
		$("#previous1").attr("class","disabled");
	}else if(pagenum == pageNumMax){
		$("#next1").attr("class","disabled");
	}else{
		$("#previous1").removeClass("disabled");
		$("#next1").removeClass("disabled");
	}
	//动态加载分页按钮并设定页数
	for(var i='${pageInfo1.pages}';i>0;i--){
		var num = pagenum%5==0?pagenum-5+i:pagenum-(pagenum%5)+i;
		$("li[id=previous1]").after("<li id='navigator'><a href='javascript:void(0);' onclick='commitForm1(this)'>"+num*1+"</a></li>");
	}
	//设置当前页按钮样式
	$("li[id=navigator1]").removeClass("active");
	$("li[id=navigator1]").each(function(){
		if($("#pageNum").val() == $(this).find("a").html()){
			$(this).attr("class","active");
		}

		if(parseInt($(this).find("a").text())>pageNumMax){
			$(this).find("a").attr("style","display:none");
		}
	});

	//设置orderby箭头样式
	if($("#orderby1").val() !="" && $("#orderby1").val() != null){
		var tmp = $("#orderby1").val().split(" ");
		if(tmp.length == 2){
			if(tmp[1] == "asc"){
				$("#"+tmp[0]+"_order").append("<i id='card_status' class='glyphicon glyphicon-chevron-up'>");
			}else{
				$("#"+tmp[0]+"_order").append("<i id='card_status' class='glyphicon glyphicon-chevron-down'>");
			}
		}

	}
}
</script>


