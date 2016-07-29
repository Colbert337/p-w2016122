<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<meta charset="UTF-8">
	<title>司集云平台</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width,initial-scale=1">
    <link type="image/x-icon" href="<%=basePath %>/common/favicon.ico" rel="shortcut icon" />
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/bootstrap.css">
    <link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/jquery.fullPage.css">
    <link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/fontello.css">
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/style.css">
</head>
<body>
    <div id="ratp-fullpage">

        <div class="section fp-auto-height">
            <jsp:include page="common/header.jsp"></jsp:include>
        </div>

        <div class="section ys1">
            <div class="container-fluid wrap">
                <div class="clearfix">
                    <div class="slogan">
                        <h1>市场上气站<span>太多</span>，合作协议<span>不好谈</span>，又麻烦</h1>
                        <ul class="list">
                            <li>气站谈合作，优惠不稳定，心好累</li>
                            <li>气品质量难以一一验证，无法保证气品质量</li>
                            <li>每家气站都得交押金，资金周转率低</li>
                        </ul>
                        <span class="sicon"></span>
                    </div>
                </div>
                <div class="square-list">
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="squares">
                                <div class="inner">
                                    <span class="icon icon-trend"></span>
                                    <div class="text">加入司集，享受全国知名上百家LNG气站优惠价</div>
                                </div>
                                <div class="bg-opacity"></div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="squares">
                                <div class="inner">
                                    <span class="icon icon-gas-station"></span>
                                    <div class="text">加入司集，油气质量不定期抽查</div>
                                </div>
                                <div class="bg-opacity"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="section ys2">
            <div class="container-fluid wrap">
                <div class="clearfix">
                    <div class="slogan">
                        <h1>车辆<span>管理难</span>，车在外军令有所不受</h1>
                        <ul class="list">
                            <li>外出车辆身不放心</li>
                            <li>外出车辆加气价格、气品模糊</li>
                        </ul>
                        <span class="sicon"></span>
                    </div>
                </div>
                <div class="square-list">
                    <div class="row">
                        <div class="col-sm-4">
                            <div class="squares">
                                <div class="inner">
                                    <span class="icon icon-nav-guide"></span>
                                    <div class="text">GPS定位，实时掌握车辆位置</div>
                                </div>
                                <div class="bg-opacity"></div>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="squares">
                                <div class="inner">
                                    <span class="icon icon-phone"></span>
                                    <div class="text">各种数据线上统计，直观放心</div>
                                </div>
                                <div class="bg-opacity"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="go-to-link my-selected">
                <div class="container-fluid">
                    <div class="wrap">
                        <div class="inner">
                            <div class="row">
                                <div class="col-sm-4">
                                </div>
                                <div class="col-sm-4">
                                    <div class="key-item">
                                        <span class="icon icon-lng"></span>
                                        <a href="<%=basePath %>/webpage/crm/login_tc.jsp">
                                            登录运输公司
                                        </a>
                                    </div>
                                    <a class="key-item-xs" href="<%=basePath %>/webpage/crm/login_tc.jsp">
                                        <span class="icon icon-lng"></span>
                                        登录运输公司
                                    </a>
                                </div>
                                <div class="col-sm-4">
                                </div>
                            </div>
                            <a href="" class="key-close"><span class="icon-cancel"></span></a>
                        </div>
                        <div class="bg-opacity"></div>
                    </div>
                </div>
            </div>
        </div>

    </div>

<script src="<%=basePath %>/webpage/crm/js/jquery.min.js"></script>
<script src="<%=basePath %>/webpage/crm/js/jquery.fullpage.min.js"></script>
<script src="<%=basePath %>/webpage/crm/js/jquery.easings.min.js"></script>
<script src="<%=basePath %>/webpage/crm/js/main.js"></script>

</body>
</html>