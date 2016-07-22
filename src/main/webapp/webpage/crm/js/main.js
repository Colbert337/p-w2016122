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
	
});