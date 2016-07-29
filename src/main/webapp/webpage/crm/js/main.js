$(function(){

	//二级菜单
	$('.navbar-toggle').on('click',function(){
		var $nav = $('.nav');
		if($nav.hasClass('nav-selected')){
			$nav.removeClass('nav-selected');
		} else {
			$nav.addClass('nav-selected');
		}
	});
	$(document).on('click',function(e){
		if( $(e.target).is(".nav") || $(e.target).is(".nav-login") || $(e.target).is(".navbar-toggle span") || $(e.target).is(".navbar-toggle") || $(e.target).parent().is(".navbar-toggle") ) {
            return;
        }
		var $nav = $('.nav');
		if($nav.hasClass('nav-selected')){
			$nav.removeClass('nav-selected');
		}
	})

	//关闭底部弹出
	$('.my-selected .key-close').on('click', function(event){
		event.preventDefault();
		$(this).parents('.my-selected').addClass('hidden');
	});

	//气站产品cng
	if($('#cng-fullpage').length){
		$('#cng-fullpage').fullpage({
	        'navigation': true,
	        afterLoad: function(anchorLink, index){
	            if(index == 4){
	                $('.cp3').find('.go-to-link').delay(500).animate({
	                    bottom: '20px'
	                }, 1000, 'easeOutExpo');
	            }
	            
	        },
	        onLeave: function(index, direction){
	            if(index == '4'){
	                $('.cp3').find('.go-to-link').delay(500).animate({
	                    bottom: '-20%'
	                }, 1000, 'easeOutExpo');
	            }
	            
	        }
	    });
	}

	//运输公司ratp
	if($('#ratp-fullpage').length){
		$('#ratp-fullpage').fullpage({
			'navigation': true,
			    afterLoad: function(anchorLink, index){
			        if(index == 3){
			            $('.ys2').find('.go-to-link').delay(500).animate({
			                bottom: '20px'
			            }, 1000, 'easeOutExpo');
			        }
			        
			    },
			    onLeave: function(index, direction){
			        if(index == '3'){
			            $('.ys2').find('.go-to-link').delay(500).animate({
			                bottom: '-20%'
			            }, 1000, 'easeOutExpo');
			        }
			        
			    }
	    });
	}

	//帮助中心内容
	$(".search-list .tit").on("click", function(event){
		event.preventDefault();
		var parent = $(this).parent();
		if(parent.hasClass("selected")){
			parent.removeClass("selected");
		} else {
			parent.addClass("selected");
			parent.parents('tr').siblings().find(".item").removeClass("selected");
		}
	});

	if($("#table").length){
		$("#table").searchable();
	}

	//手机视图下点击展示二级菜单
	function clickSubMenu(){
		var $width = $(window).width();
		if($width<768){
			$('.help-menu').on('click',function(){
				var $this = $(this);
				if($this.hasClass('toggle')){
					$this.removeClass('toggle');
				} else {
					$this.addClass('toggle');
				}
			});
		} else {
			$('.help-menu').removeClass('toggle').off('click');
		}
	}
	clickSubMenu();
	$(window).resize(function() {
		clickSubMenu();
	});

	if($("#login").length){
		$('#login').validate({

			/* 设置验证规则 */
			rules: {
				userName: {
					required: true,
					rangelength: [3, 20]
				},
				password: {
					required: true,
					rangelength: [6, 16]
				}
			},

			/* 设置错误信息 */
			messages: {
				userName: {
					required: "请填写用户名",
					rangelength: "用户名需由3-20个字符（数字、字母、下划线）组成！"
				},
				password: {
					required: "请填写密码",
					rangelength: "密码需由6-16个字符（数字、字母、下划线）组成！"
				}
			},

			/*错误提示位置*/
			errorPlacement: function (error, element) {
				error.appendTo(element.siblings("div"));
			}

		});
	}
	
});