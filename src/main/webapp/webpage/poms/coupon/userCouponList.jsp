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
<form id="userCouponForm">
	<input id="pageNum1" type="hidden"   value="${userCouponInfo.pageNum}" />
	<input id="pageSize1" type="hidden"  value="${userCouponInfo.pageSize}" />
	<input id="pageNumMax1" type="hidden"  value="${userCouponInfo.pages}" />
	<input id="total1" type="hidden"  value="${userCouponInfo.total}" />
	<input id="orderBy1" type="hidden"   value="${userCouponInfo.orderBy}" />
	<input id="retValue" type="hidden" value="${ret.retValue}" />
	<input id="retCode" type="hidden" value="${ret.retCode}" />
	<input id="retMsg" type="hidden" value="${ret.retMsg}" />
	<div class="row">
		<div class="sjny-table-responsive">
			<table id="dynamic-table" class="table table-striped table-bordered table-hover col-sm-10">
				<thead>
					<tr>
						<th  onclick="orderBy1(this,'user_name');commitForm1();" id="user_name_order">会员账号</th>
						<th onclick="orderBy1(this,'full_name');commitForm1();" id="full_name_order">认证姓名</th>
						<th onclick="orderBy1(this,'plate_number');commitForm1();" id="plate_number_order">车牌号</th>
						<th onclick="orderBy1(this,'identity_card');commitForm1();" id="identity_card_order">身份证号</th>
						<th style="text-align:center" onclick="orderBy1(this,'fuel_type');commitForm1();" id="fuel_type_order">燃料类型</th>
						<th onclick="orderBy1(this,'station_id');commitForm1();" id="station_id_order">注册工作站编号</th>
						<th onclick="orderBy1(this,'regis_source');commitForm1();" id="regis_source_order">注册工作站名称</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${userCouponInfo.list}" var="list" varStatus="s">
						<tr>
						<td>${list.user_name }</td>
						<td>${list.full_name}</td>
						<td>${list.plate_number}</td>
						<td>${list.identity_card}</td>
						<td style="text-align:center"><s:Code2Name mcode="${list.fuel_type}" gcode="FUEL_TYPE"></s:Code2Name></td>
						<td>${list.station_id}</td>
						<td>${list.regis_source}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-6">
			<div class="dataTables_info sjny-page" id="dynamic-table_info" role="status" aria-live="polite">
				每页 ${userCouponInfo.pageSize} 条 <span class="line">|</span> 共 ${userCouponInfo.total} 条 <span class="line">|</span> 共 ${userCouponInfo.pages} 页
			</div>
		</div>
		<div class="col-sm-6">
			<nav>
				<ul id="ulhandle1" class="pagination pull-right no-margin">
					<li id="previous1">
						<a href="javascript:void(0);" aria-label="Previous" onclick="prepage1('#userCouponForm');">
							<span aria-hidden="true">上一页</span>
						</a>
					</li>
					<li id="next1">
						<a id="nexthandle" href="javascript:void(0);" aria-label="Next" onclick="nextpage1('#userCouponForm');">
							<span aria-hidden="true">下一页</span>
						</a>
					</li>
				</ul>
			</nav>
		</div>
	</div>
	<jsp:include page="/common/message.jsp"></jsp:include>
</form>
<script type="text/javascript">
	function scher() {
		$("#userCouponForm").ajaxSubmit({
			url : '../web/coupon/showUserCoupon?coupon_id=${userCoupon.coupon_id}',
			type : 'post',
			data:{
				pageNum:$("#pageNum1").val(),
				pageSize:$("#pageSize1").val(),
				pageNumMax:$("#pageNumMax1").val(),
				total:$("#total1").val(),
				orderby:$("#orderBy1").val()
			},
			dataType : 'html',
			success : function(data) {
				$("#content").html(data);
				$("#userCouponModel").modal('show');
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
	if (typeof obj == "undefined") {
		$("#pageNum1").val("1");
	} else {
		$("#pageNum1").val($(obj).text());
	}
		$("#userCouponForm").ajaxSubmit({
			url : '../web/coupon/showUserCoupon?coupon_id=${userCoupon.coupon_id}',
			type : 'post',
			data:{
				pageNum:$("#pageNum1").val(),
				pageSize:$("#pageSize1").val(),
				pageNumMax:$("#pageNumMax1").val(),
				total:$("#total1").val(),
				orderby:$("#orderBy1").val()
			},
			dataType : 'html',
			success : function(data) {
				$("#content").html(data);
				$("#userCouponModel").modal('show');
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
		if(parseInt($("#pageNum1").val()) >= parseInt($("#pageNumMax1").val())){
			return ;
		}
		//设置当前页+1
		$("#pageNum1").val(parseInt($("#pageNum1").val())+1);
			$(formid).ajaxSubmit({
				url : '../web/coupon/showUserCoupon?coupon_id=${userCoupon.coupon_id}',
				type : 'post',
				data:{
					pageNum:$("#pageNum1").val(),
					pageSize:$("#pageSize1").val(),
					pageNumMax:$("#pageNumMax1").val(),
					total:$("#total1").val(),
					orderby:$("#orderBy1").val()
				},
				dataType : 'html',
				success : function(data) {
					$("#content").html(data);
					$("#userCouponModel").modal('show');
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
			url : '../web/coupon/showUserCoupon?coupon_id=${userCoupon.coupon_id}',
			type : 'post',
			data:{
				pageNum:$("#pageNum1").val(),
				pageSize:$("#pageSize1").val(),
				pageNumMax:$("#pageNumMax1").val(),
				total:$("#total1").val(),
				orderby:$("#orderBy1").val()
			},
			dataType : 'html',
			success : function(data) {
				$("#content").html(data);
				$("#userCouponModel").modal('show');
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
		for(var i=5;i>0;i--){
			var num = parseInt(pagenum%5==0?pagenum-5+i:pagenum-(pagenum%5)+i);
			$("li[id=previous1]").after("<li id='navigator1'><a href='javascript:void(0);' onclick='commitForm1(this)'>"+num+"</a></li>");
		}
		//设置当前页按钮样式
		$("li[id=navigator1]").removeClass("active");
		$("li[id=navigator1]").each(function(){
			if($("#pageNum1").val() == $(this).find("a").html()){
				$(this).attr("class","active");
			}
			if(parseInt($(this).find("a").text())>pageNumMax){
				$(this).find("a").attr("style","display:none");
			}
		});
		//设置orderby箭头样式
		if($("#orderBy1").val() !="" && $("#orderBy1").val() != null){
			var tmp = $("#orderBy1").val().split(" ");
			if(tmp.length == 2){
				if(tmp[1] == "asc"){
					$("#"+tmp[0]+"_order").append("<i id='card_status' class='glyphicon glyphicon-chevron-up'>");
				}else{
					$("#"+tmp[0]+"_order").append("<i id='card_status' class='glyphicon glyphicon-chevron-down'>");
				}
			}
		}
	}

	function orderBy1(obj, order){
		if($("#orderBy1").val() == '' || $("#orderBy1").val() == null || $("#orderBy1").val()=='undefined'){
			$("#orderBy1").val(order+' desc');
			$(obj).append('<i id="card_status" class="glyphicon glyphicon-chevron-down">');
		}else{
			var tmp = $("#orderBy1").val().split(' ');
			if(tmp.length ==2){
				if(tmp[1] == 'asc'){
					tmp[1] = 'desc';
				}else{
					tmp[1] = 'asc';
				}
					$("#orderBy1").val(order+' '+tmp[1]);
			}
		}
	}
</script>