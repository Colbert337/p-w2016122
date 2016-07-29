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
	<link rel="stylesheet" href="css/bootstrap.css">
    <link rel="stylesheet" href="css/jquery.fullPage.css">
    <link rel="stylesheet" href="css/fontello.css">
	<link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div id="cng-fullpage">

        <div class="section fp-auto-height">
            <jsp:include page="common/header.jsp"></jsp:include>
        </div>

        <div class="section cp1">
            <div class="container-fluid wrap">
                <div class="clearfix">
                    <div class="slogan">
                        <h1>加气市场竞争激烈，销量<br>忽<span>高</span>忽低<span>不稳定</span>？</h1>
                        <ul class="list">
                            <li>怎么把客户成为常客</li>
                            <li>气站在做优惠活动，怎么让更多的司机都知道</li>
                            <li>销售任务重，怎样增长销售业绩</li>
                        </ul>
                        <span class="sicon"></span>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4">
                        <div class="squares">
                            <div class="inner">
                                <div class="icon icon-hand-focus"></div>
                                <div class="text">司集帮您构建互联网化的营销体系</div>
                            </div>
                            <div class="bg-opacity"></div>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="squares">
                            <div class="inner">
                                <span class="icon icon-crm"></span>
                                <div class="text">营销人员手把手教导推广客户</div>
                            </div>
                            <div class="bg-opacity"></div>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="squares">
                            <div class="inner">
                                <span class="icon icon-see-film"></span>
                                <div class="text">共享100000+LNG司机，都是你的客户</div>
                            </div>
                            <div class="bg-opacity"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="section cp2">
            <div class="container-fluid wrap">
                <div class="clearfix">
                    <div class="slogan">
                        <h1>加气站位置偏，来加气的车辆<span>少</span>，怎么破？</h1>
                        <ul class="list">
                            <li>传统导航气站信息位置不精确，站属性不准确</li>
                            <li>传统导航气站软件使用不方便，无推广，用户少</li>
                        </ul>
                        <span class="sicon"></span>
                    </div>
                </div>
                <div class="square-list">
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="squares">
                                <div class="inner">
                                    <span class="icon icon-nav-scan"></span>
                                    <div class="text">专业的气站导航准确引导司机 </div>
                                </div>
                                <div class="bg-opacity"></div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="squares">
                                <div class="inner">
                                    <span class="icon icon-extension"></span>
                                    <div class="text">100000+LNG司机轻松到站</div>
                                </div>
                                <div class="bg-opacity"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="section cp3">
            <div class="container-fluid wrap">
                <div class="clearfix">
                    <div class="slogan">
                        <h1>气站<span>线下</span>业务凌乱，统计繁琐，难以实时掌握经营状况</h1>
                        <ul class="list">
                            <li>纸质单据多，安全性低。</li>
                            <li>统计数据多，查一个数据，翻一堆报表。</li>
                            <li>统计效率低，一次统计，半天出报表。</li>

                        </ul>
                        <span class="sicon"></span>
                    </div>
                </div>
                <div class="square-list">
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="squares">
                                <div class="inner">
                                    <div class="inner-info">
                                        <span class="icon icon-cake"></span>
                                        <div class="text">实时统计，定时分析，业务数据一目了然</div>
                                    </div>
                                </div>
                                <div class="bg-opacity"></div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="squares">
                                <div class="inner">
                                    <span class="icon icon-search"></span>
                                    <div class="text">一键导出，一键查询，业务数据方便查阅</div>
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
                                        <a href="login_gs.jsp">
                                            登录加注站
                                        </a>
                                    </div>
                                    <a class="key-item-xs" href="login_gs.jsp">
                                        <span class="icon icon-lng"></span>
                                        登录加注站
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

<script src="js/jquery.min.js"></script>
<script src="js/jquery.fullpage.min.js"></script>
<script src="js/jquery.easings.min.js"></script>
<script src="js/main.js"></script>

</body>
</html>