<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<script src="<%=basePath %>/dist/js/gastation/gastation_infomation.js"></script>

<div class="page-header">
    <h1>
        加注站信息
    </h1>
</div><!-- /.page-header -->

<div class="gastation-infomation">

    <div class="row">
        <div class="col-sm-3">
            <a class="gastation-log-colorbox" href="${gastation.indu_com_certif}" data-rel="colorbox">
				<img class="img-responsive" src="${gastation.indu_com_certif}" alt="">
				<div class="title">工商注册证</div>
			</a>
        </div>
        <div class="col-sm-3">
        	<a class="gastation-log-colorbox" href="${gastation.tax_certif}" data-rel="colorbox">
				<img class="img-responsive" src="${gastation.tax_certif}" alt="">
				<div class="title">税务注册证</div>
			</a>
        </div>
        <div class="col-sm-3">
        	<a class="gastation-log-colorbox" href="${gastation.lng_certif}" data-rel="colorbox">
				<img class="img-responsive" src="${gastation.lng_certif}" alt="">
				<div class="title">LNG储装证</div>
			</a>
        </div>
        <div class="col-sm-3">
        	<a class="gastation-log-colorbox" href="${gastation.dcp_certif}" data-rel="colorbox">
				<img class="img-responsive" src="${gastation.dcp_certif}" alt="">
				<div class="title">危化品证</div>
			</a>
        </div>
    </div><!--/.imgs-->

    <div class="gastation-infomation-items">
        <div class="item">
            <div class="row">
                <div class="col-sm-3 text-right">
                                                 加注站名称
                </div>
                <div class="col-sm-9">
                    <span class="value">${gastation.gas_station_name}</span>
                </div>
            </div>
        </div>
        <div class="item">
            <div class="row">
                <div class="col-sm-3 text-right">
                  	  站长姓名
                </div>
                <div class="col-sm-9">
                    <span class="value">${gastation.station_manager}</span>
                </div>
            </div>
        </div>
        <div class="item">
            <div class="row">
                <div class="col-sm-3 text-right">
                  	  联系电话
                </div>
                <div class="col-sm-9">
                    <span class="value">${gastation.contact_phone}</span>
                </div>
            </div>
        </div>
        <div class="item">
            <div class="row">
                <div class="col-sm-3 text-right">
                                                    加注站地址
                </div>
                <div class="col-sm-9">
                    <span class="value">${gastation.address}</span>
                </div>
            </div>
        </div>
        <div class="item">
            <div class="row">
                <div class="col-sm-3 text-right">
                                                    加注站地址坐标
                </div>
                <div class="col-sm-9">
                    <span class="value">${gastation.longitude}，${gastation.latitude}</span>
                </div>
            </div>
        </div>
        <div class="item">
            <div class="row">
                <div class="col-sm-3 text-right">
                                                    账户余额
                </div>
                <div class="col-sm-9">
                    <span class="value">${gastation.prepay_balance} 元</span>
                </div>
            </div>
        </div>
    </div>

</div><!-- /.row -->
