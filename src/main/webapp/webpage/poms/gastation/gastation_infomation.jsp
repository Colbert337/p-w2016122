<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<div class="page-header">
    <h1>
        加注站信息
    </h1>
</div><!-- /.page-header -->

<div class="gastation-infomation">

    <div class="row">
        <div class="col-sm-3">
            <a href="" class="thumbnail">
                <img class="img-responsive" src="<%=basePath %>/common/images/default_productBig.jpg" alt="">
                <div class="title">fdfdf</div>
            </a>
        </div>
        <div class="col-sm-3">
            <a href="" class="thumbnail">
                <img class="img-responsive" src="<%=basePath %>/common/images/default_productBig.jpg" alt="">
                <div class="title">fdfdf</div>
            </a>
        </div>
        <div class="col-sm-3">
            <a href="" class="thumbnail">
                <img class="img-responsive" src="<%=basePath %>/common/images/default_productBig.jpg" alt="">
                <div class="title">fdfdf</div>
            </a>
        </div>
        <div class="col-sm-3">
            <a href="" class="thumbnail">
                <img class="img-responsive" src="<%=basePath %>/common/images/default_productBig.jpg" alt="">
                <div class="title">fdfdf</div>
            </a>
        </div>
    </div><!--/.imgs-->

    <div class="gastation-infomation-items">
        <div class="item">
            <div class="row">
                <div class="col-sm-3 text-right">
                    tttt
                </div>
                <div class="col-sm-9">
                    tttt
                </div>
            </div>
        </div>
        <div class="item">
            <div class="row">
                <div class="col-sm-3 text-right">
                    tttt
                </div>
                <div class="col-sm-9">
                    tttt
                </div>
            </div>
        </div>
        <div class="item">
            <div class="row">
                <div class="col-sm-3 text-right">
                    tttt
                </div>
                <div class="col-sm-9">
                    tttt
                </div>
            </div>
        </div>
        <div class="item">
            <div class="row">
                <div class="col-sm-3 text-right">
                    tttt
                </div>
                <div class="col-sm-9">
                    tttt
                </div>
            </div>
        </div>
    </div>

</div><!-- /.row -->
