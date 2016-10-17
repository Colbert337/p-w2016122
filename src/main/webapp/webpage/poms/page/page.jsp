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
<!DOCTYPE html>
<html class="index" lang="zh-CN">
<head>
<title>${page.pageTitle}</title>

<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1">
<link rel="stylesheet" href="<%=basePath%>/webpage/crm/css/webapp.css">
<link rel="stylesheet" href="<%=basePath%>/webpage/crm/css/fontello.css">
<link rel="stylesheet" href="<%=basePath%>/webpage/crm/css/fontello.css">
<script type="text/javascript"
	src="<%=basePath%>/webpage/crm/js/jquery.min.js">
	
</script>
<script type="text/javascript"
	src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
	// 当微信内置浏览器完成内部初始化后会触发WeixinJSBridgeReady事件。
	// 	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	// 		wx.onMenuShareAppMessage({
	// 		    title: '123', // 分享标题
	// 		    desc: '456', // 分享描述
	// 		    link: 'http://toutiao.com/item/6341655988533199362', // 分享链接
	// 		    imgUrl: 'http://toutiao.com/item/6341655988533199362', // 分享图标
	// 		    type: '', // 分享类型,music、video或link，不填默认为link
	// 		    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
	// 		    success: function () {
	// 		      alert(1);
	// 		    },
	// 		    cancel: function () {
	// 		        // 用户取消分享后执行的回调函数
	// 		    	alert(2);
	// 		    }
	// 		});
	// 	}, false);

	// 	var shareData = {
	// 		img_url : "",
	// 		img_width : 200,
	// 		img_height : 200,
	// 		link : 'sqaiyan.com',
	// 		desc : '微信分享测试',
	// 		title : '博客',
	// 		appid : 0
	// 	};
	// 	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	// 		alert("初始化");
	// 		// 发送给好友                 
	// 		WeixinJSBridge.on('menu:share:appmessage', function(argv) {
	// 			alert("好友");
	// 			shareFriend();
	// 		});
	// 		// 分享到朋友圈                 
	// 		WeixinJSBridge.on('menu:share:timeline', function(argv) {
	// 			shareTimeline();
	// 		});
	// 		//分享到微博                
	// 		WeixinJSBridge.on('menu:share:weibo', function(argv) {
	// 			shareWeibo();
	// 		})
	// 	}, false);
	// 	//关注指定的微信号        
	// 	function weixinAddContact(name) {
	// 		WeixinJSBridge.invoke("addContact", {
	// 			webtype : "1",
	// 			username : name
	// 		}, function(e) {
	// 			WeixinJSBridge.log(e.err_msg);
	// 			//e.err_msg:add_contact:added 已经添加             
	// 			//e.err_msg:add_contact:cancel 取消添加                  
	// 			//e.err_msg:add_contact:ok 添加成功                  
	// 			if (e.err_msg == 'add_contact:added'
	// 					|| e.err_msg == 'add_contact:ok') {
	// 				//关注成功，或者已经关注过          
	// 			}
	// 		})
	// 	}
	// 	function shareTimeline() {
	// 		WeixinJSBridge.invoke('shareTimeline', shareData, function(res) {
	// 			validateShare(res);
	// 			_report('timeline', res.err_msg);
	// 		});
	// 	}
	// 	function shareWeibo() {
	// 		WeixinJSBridge.invoke('shareWeibo', shareData, function(res) {
	// 			validateShare(res)
	// 			_report('weibo', res.err_msg);
	// 		});
	// 	}
	// 	function shareFriend() {
	// 		WeixinJSBridge.invoke('sendAppMessage', shareData, function(res) {
	// 			validateShare(res);
	// 			_report('send_msg', res.err_msg);
	// 		});
	// 	}
	// 	function validateShare(res) {
	// 		if (res.err_msg != 'send_app_msg:cancel' && res.err_msg != 'share_timeline:cancel') {
	// 			//返回信息判断              
	// 			alert(1);

	// 		}
	// 	}

	var url = null;
	var jsapi_ticket = null;
	var nonceStr = null;
	var timestamp = null;
	var signature = null;
	var showURL = null;
	var appid = null;
	wx.config({
		debug : true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		appId : appid, // 必填，公众号的唯一标识
		timestamp : timestamp, // 必填，生成签名的时间戳
		nonceStr : nonceStr, // 必填，生成签名的随机串
		signature : signature,// 必填，签名，见附录1
		jsApiList : [//此处列表，用到哪些方法，必须要在此提前声明，我当时要用到hideMenuItems，但是因为没有在此出声明，一直不起作用，后来查资料才知道，并且这                             //些方法必须放到wx.ready中
		'checkJsApi', 'onMenuShareTimeline', 'onMenuShareAppMessage', 'hideMenuItems' ]
	// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	wx.ready(function() {
		//2.3 隐藏不用的按钮
		alert("隐藏不用的按钮");
		wx.hideMenuItems({
			menuList : [ 'menuItem:share:qq', 'menuItem:share:weiboApp',
					'menuItem:favorite', 'menuItem:share:facebook',
					'/menuItem:share:QZone' ], // 要隐藏的菜单项，只能隐藏“传播类”和“保护类”按钮，所有menu项见附录3
			success : function(res) {
				alert("隐藏");
			}
		});

		wx.checkJsApi({
			jsApiList : [ 'getNetworkType', 'previewImage' ],
			success : function(res) {
				alert(JSON.stringify(res));
			}
		});
		wx.showMenuItems({
			menuList : [ 'onMenuShareAppMessage', 'onMenuShareTimeline' ]
		// 要显示的菜单项，所有menu项见附录3
		});
		// 2. 分享接口
		// 2.1 监听“分享给朋友”，按钮点击、自定义分享内容及分享结果接口
		wx.onMenuShareAppMessage({
			title : '话费购',
			desc : '话费红包，点击领取',
			link : shareUrl,
			imgUrl : 'http://wuming_ise.ngrok.cc/wcpay/images/tubiao.png',
			trigger : function(res) {
				// 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
				alert("点击分享朋友"+shareUrl);
			},
			success : function(res) {
				dismiss();
			},
			cancel : function(res) {
				dismiss();
			},
			fail : function(res) {
				dismiss();
				alert(JSON.stringify(res));
			}
		});
		//alert('已注册获取“发送给朋友”状态事件');
		// 2.2 监听“分享到朋友圈”按钮点击、自定义分享内容及分享结果接口
		wx.onMenuShareTimeline({
			title : '话费购',
			link : shareUrl,
			imgUrl : 'http://wuming_ise.ngrok.cc/wcpay/images/tubiao.png',
			trigger : function(res) {
				// 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
				alert("分享到朋友圈" + shareUrl);
			},
			success : function(res) {
				dismiss();
			},
			cancel : function(res) {
				dismiss();
			},
			fail : function(res) {
				dismiss();
				alert(JSON.stringify(res));
			}
		});
	});
</script>
</head>
<body>
	<c:if test="${page.show_title eq '1'}">
		<div class="activ-detail-hd">
			<h2>${page.pageTitle}</h2>
			<div class="date">
				<span>${page.pageBody}</span> <span>${page.pageCreator}</span> <span><fmt:formatDate
						value="${page.pageCreatedTime}" pattern="yyyy-MM-dd-HH:mm:ss" /></span>
			</div>
		</div>
	</c:if>

	<div class="activ-detail-bd">${page.pageHtml}</div>
	<c:if test="${page.show_download_button eq '1'}">
		<div class="btn-more-info">
			<div class="inner">
				<a href="<%=basePath%>/webpage/crm/webapp-download-app"
					class="btn-app-primary logic-download-app"><span
					class="icon-sy"></span>下载司集APP给您提供更多优质服务</a>
			</div>
		</div>
	</c:if>
</body>
</html>