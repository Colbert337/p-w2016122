<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html>
<html class="help" lang="zh">
<head>
	<meta charset="UTF-8">
	<title>司集云平台</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width,initial-scale=1">
    <link type="image/x-icon" href="<%=basePath %>/common/favicon.ico" rel="shortcut icon" />
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/bootstrap.css">
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/fontello.css">
	<link rel="stylesheet" href="<%=basePath %>/webpage/crm/css/style.css">
</head>
<body>
<jsp:include page="common/header.jsp"></jsp:include>
<div class="subnav">
    <div class="inner">
        <ul>
            <li class="home"><a href="<%=basePath %>/webpage/crm/index.jsp">首页</a></li>
            <li class="current"><a href="<%=basePath %>/portal/crm/help/list/all">常见问题</a></li>
            <li><a href="<%=basePath %>/webpage/crm/hp_service.jsp">客服咨询</a></li>
            <li><a href="<%=basePath %>/portal/crm/help/list/notice">服务公告</a></li>
        </ul>
    </div>
</div>

	<div class="container-fluid wrap">
		<div class="help-container">
            <span class="icon-cs"></span>
            <div class="title">
                <div>司集帮助中心</div>
            </div>
            <div class="help-menu">
                <div class="help-menu-title">问题分类：</div>
                <ul>
                    <li><a href="">1账户安全</a></li>
                    <li><a href="">2日常操作</a></li>
                </ul>
            </div>
            <div class="search">
                <div class="inner">
                    <input class="txt" type="search" id="search" placeholder="请输入关键字">
                    <button class="button">搜 索</button>
                    <span class="icon icon-search-1"></span>
                </div>
            </div>
            <table class="search-list" id="table">
                <tr>
                    <td>
                        <div class="item">
                            <a class="tit" href="">1.密保手机丢了怎么办？</a>
                            <div class="info">
                                <h5>解决办法：</h5>先要电话联系司集客服人员，并提供本人身份证正反面照片和新密保手机号码，由司集工作人员手动操作更改。
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="item">
                            <a class="tit" href="">2.司集会员卡锁死，怎么办？</a>
                            <div class="info">
                                <h5>原因：</h5>银行卡磁条损坏或刷卡方式不对，或信号源问题。
                                <h5>解决办法：</h5>
                                1）新刷卡或换卡试一下；<br/>
                                2）换方向刷卡试一下；<br/>
                                3）如果多张卡不好用，应是磁头损坏，建议换机。<br/>
                                信号源问题，联系厂家，更换流量卡。<br/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="item">
                            <a class="tit" href="">3.如何修改加注站或车队注册时的邮箱？</a>
                            <div class="info">
                                <h5>解决办法：</h5>先要电话联系司集客服人员，并提供注册时营业执照的照片和新邮箱，由司集工作人员手动操作更改。
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="item">
                            <a class="tit" href="">4.司集会员卡锁死，怎么办？</a>
                            <div class="info">
                                <h5>解决办法：</h5>先要电话联系司集客服人员，挂失会员卡后，到司集任一联盟气站补办实体卡。
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="item">
                            <a class="tit" href="">5.车队主副卡怎么办理？</a>
                            <div class="info">
                                <h5>解决办法：</h5>
                                1）使用车队队长账号在CRM管理平台的卡池管理模块发卡管理中为队长发放车队主卡；<br/>
                                2）在CRM管理平台的卡池管理模块主副卡管理中为车队主卡发放副卡。<br/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="item">
                            <a class="tit" href="">6.车队主副卡怎么充值划款？</a>
                            <div class="info">
                                <h5>解决办法：</h5>
                                1）在CRM管理平台的会员充值模块中为车队主卡充值；<br/>
                                2）以车队队长账号登录"商家管理平台"，在卡资源管理中为副卡绑定车队司集，并划款。<br/>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="item">
                        <a class="tit" href="">7.首次安装CRM管理系统需要注意哪三点？</a>
                        <div class="info">
                            <h5>解决办法：</h5>
                            a.服务器地址；<br/>
                            b.激活码；<br/>
                            c.win7以上系统激活报错时，需要右键点击图标使用管理员权限运行。<br/>
                        </div>
                    </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="item">
                        <a class="tit" href="">8.司机会员卡个人账号忘记密码，如何重置支付密码？</a>
                        <div class="info">
                            <h5>解决办法：</h5>
                            司机需携带注册密保手机去所注册会员卡的气站，工作人员登录CRM系统后，点击“客户管理”选择“客户信息”，输入会员卡号或者密保手机号码，选择“审核通过”，点击“查询”，弹出会员信息，选中该会员，点击“修改”，弹出客户信息小菜单，选择“重置支付密码”，弹出修改密码小菜单，需向密保手机发送验证码，输入新密码和正确的验证码即可。
                        </div>
                    </div>
                    </td>
                </tr>
            </table>
        </div>
	</div>
    <script src="<%=basePath %>/webpage/crm/js/jquery.min.js"></script>
    <script src="<%=basePath %>/webpage/crm/js/jquery.searchable-1.0.0.min.js"></script>
    <script src="<%=basePath %>/webpage/crm/js/main.js"></script>
</body>
</html>