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
            </a>
        </div>
        <div class="col-sm-3">
            <a href="" class="thumbnail">
                <img class="img-responsive" src="<%=basePath %>/common/images/default_productBig.jpg" alt="">
            </a>
        </div>
        <div class="col-sm-3">
            <a href="" class="thumbnail">
                <img class="img-responsive" src="<%=basePath %>/common/images/default_productBig.jpg" alt="">
            </a>
        </div>
        <div class="col-sm-3">
            <a href="" class="thumbnail">
                <img class="img-responsive" src="<%=basePath %>/common/images/default_productBig.jpg" alt="">
            </a>
        </div>
    </div><!--/.imgs-->

    <form class="form-horizontal" role="form">
        <div class="form-group">
            <label class="col-sm-3 control-label" for="form-field-1"> Text Field </label>
            <div class="col-sm-6">
                <input type="text" id="form-field-1" placeholder="Username" class="form-control">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label" for="form-field-2"> Text Field </label>
            <div class="col-sm-6">
                <input type="text" id="form-field-2" placeholder="Username" class="form-control">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label" for="form-field-3"> Text Field </label>
            <div class="col-sm-6">
                <input type="text" id="form-field-3" placeholder="Username" class="form-control">
            </div>
        </div>
        <div class="clearfix form-actions">
            <div class="col-md-offset-3 col-md-9 no-padding-left">
                <button class="btn btn-info" type="button" onclick="save();">
                    <i class="ace-icon fa fa-check bigger-110"></i>
                    保存
                </button>

                &nbsp; &nbsp; &nbsp;
                <button class="btn" type="reset">
                    <i class="ace-icon fa fa-repeat bigger-110"></i>
                    重置
                </button>

                &nbsp; &nbsp; &nbsp;
                <button class="btn btn-success" type="button" onclick="returnpage();">
                    <i class="ace-icon fa fa-undo bigger-110"></i>
                    返回
                </button>
            </div>
        </div>
    </form>

</div><!-- /.row -->
