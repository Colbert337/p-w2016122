
	var projectfileoptions = {
	        showUpload : false,
	        showRemove : false,
	        language : 'zh',
	        allowedPreviewTypes : [ 'image' ],
	        allowedFileExtensions : [ 'jpg', 'png', 'gif', 'jepg' ],
	        maxFileSize : 1000,
	    }
	// 文件上传框
	$('input[class=projectfile]').each(function() {
	    var imageurl = $(this).attr("value");
	
	    if (imageurl) {
	        var op = $.extend({
	            initialPreview : [ // 预览图片的设置
	            "<img src='" + imageurl + "' class='file-preview-image'>", ]
	        }, projectfileoptions);
	
	        $(this).fileinput(op);
	    } else {
	        $(this).fileinput(projectfileoptions);
	    }
	});
	
		var china=new Object();
		china['100']=new Array('北京市区','北京市辖区');
		china['210']=new Array('上海市区','上海市辖区');
		china['220']=new Array('天津市区','天津市辖区');
		china['230']=new Array('重庆市区','重庆市辖区');
		china['310']=new Array('石家庄', '唐山市', '邯郸市', '秦皇市岛', '保市定', '张家市口', '承德市', '廊坊市', '沧州市', '衡水市', '邢台市');
		china['350']=new Array('太原市','大同市','阳泉市','长治市','晋城市','朔州市','晋中市','运城市','忻州市','临汾市','吕梁市');
		china['240']=new Array('沈阳市','大连市','鞍山市','抚顺市','本溪市','丹东市','锦州市','营口市','阜新市','辽阳市','盘锦市','铁岭市','朝阳市','葫芦岛市');
		china['430']=new Array('长春市','吉林市','四平市','辽源市','通化市','白山市','松原市','白城市','延边州','长白山管委会');
		china['450']=new Array('哈尔滨市','齐齐哈尔市','大庆市','佳木斯市','牡丹江市','七台河市','双鸭山市','黑河市','鸡西市','伊春市','绥化市','鹤岗市','加格达奇市');
		china['250']=new Array('南京市','苏州市','无锡市','常州市','南通市','扬州市','镇江市','泰州市','盐城市','连云港市','宿迁市','淮安市','徐州市');
		china['570']=new Array('杭州市','宁波市','温州市','嘉兴市','湖州市','绍兴市','金华市','衢州市','舟山市','台州市','丽水市');
		china['550']=new Array('合肥市','芜湖市','蚌埠市','淮南市','马鞍山市','淮北市','铜陵市','安庆市','黄山市','滁州市','阜阳市','宿州市','巢湖市','六安市','亳州市','池州市','宣城市');
		china['590']=new Array('福州市','厦门市','莆田市','三明市','泉州市','漳州市','南平市','龙岩市','宁德市');
		china['790']=new Array('南昌市','景德镇市','萍乡市','九江市','新余市','鹰潭市','赣州市','吉安市','宜春市','抚州市','上饶市');
		china['530']=new Array('济南市','青岛市','淄博市','枣庄市','东营市','烟台市','潍坊市','济宁市','泰安市','威海市','日照市','莱芜市','临沂市','德州市','聊城市','滨州市','菏泽市');
		china['370']=new Array('郑州市','开封市','洛阳市','平顶山市','安阳市','鹤壁市','新乡市','焦作市','濮阳市','许昌市','漯河市','三门峡市','南阳市','商丘市','信阳市','周口市','驻马店市');
		china['270']=new Array('武汉市','黄石市','十堰市','荆州市','宜昌市','襄樊市','鄂州市','荆门市','孝感市','黄冈市','咸宁市','随州市');
		china['730']=new Array('长沙市','株洲市','湘潭市','衡阳市','邵阳市','岳阳市','常德市','张家界市','益阳市','郴州市','永州市','怀化市','娄底市');
		china['200']=new Array('广州市','深圳市','珠海市','汕头市','韶关市','佛山市','江门市','湛江市','茂名市','肇庆市','惠州市','梅州市','汕尾市','河源市','阳江市','清远市','东莞市','中山市','潮州市','揭阳市','云浮市');
		china['890']=new Array('文昌市','琼海市','万宁市','五指山市','东方市','儋州市');
		china['810']=new Array('成都市','自贡市','攀枝花市','泸州市','德阳市','绵阳市','广元市','遂宁市','内江市','乐山市','南充市','眉山市','宜宾市','广安市','达州市','雅安市','巴中市','资阳市');
		china['850']=new Array('贵阳市','六盘水市','遵义市','安顺市');
		china['870']=new Array('昆明市','曲靖市','玉溪市','保山市','昭通市','丽江市','普洱市','临沧市');
		china['290']=new Array('西安市','铜川市','宝鸡市','咸阳市','渭南市','延安市','汉中市','榆林市','安康市','商洛市');
		china['930']=new Array('兰州市','金昌市','白银市','天水市','嘉峪关市','武威市','张掖市','平凉市','酒泉市','庆阳市','定西市','陇南市');
		china['970']=new Array('西宁市','海东市','玉树市','格尔木市','德令哈市');
		china['886']=new Array('台北市','高雄市','基隆市','台中市','台南市','新竹市','嘉义市');
		china['770']=new Array('南宁市','柳州市','桂林市','梧州市','北海市','防城港市','钦州市','贵港市','玉林市','百色市','贺州市','河池市','来宾市','崇左市');
		china['470']=new Array('呼和浩特市','包头市','乌海市','赤峰市','通辽市','鄂尔多斯市','呼伦贝尔市','巴彦淖尔市','乌兰察布市'); 
		china['890']=new Array('拉萨市');
		china['891']=new Array('海口市','三亚市','三沙市','儋州市');
		china['950']=new Array('银川市','石嘴山市','吴忠市','固原市','中卫市');
		china['990']=new Array('乌鲁木齐市','克拉玛依市');
		china['852']=new Array('台北市','高雄市','基隆市','台中市','台南市','新竹市','嘉义市');
		
		//初始化销售（运管）负责人下拉框
		$.ajax({
			   type: "POST",
			   url:'../web/permi/user/list/userType?userType=5',   
	           dataType:'text',
	           async:false,
	           success:function(data){
	           		if(data != ""){
			        	   $("#salesmen_id").empty();
			        	   var s = JSON.parse(data);
			        	   for(var i=0;i<s.length;i++){
			        		   $("#operations_id").append("<option value='"+s[i].userName+"''>"+s[i].userName+" - "+s[i].realName+"</option>");
			        	   }
	           		}
	            }
		});
		
		//下拉框默认选中当前对象的值
		var province_id = "${station.province_id}";
		var city_id = "${station.city_id}";
		var detail = "${station.address}";
		var salesmen_id = "${station.salesmen_id}";
		var operations_id = "${station.operations_id}";
		
		if(province_id!=null && province_id!=""){
			$("#province").find("option[value="+province_id+"]").attr("selected",true);
			$("#province").trigger("change");
			$("#city").find("option[value="+city_id+"]").attr("selected",true);
			
			$("#detail").val(detail.split(" ")[2]);
			
			$("#showprovince").val($("#province").find("option:selected").text());
		}
		
		if(operations_id!=null){
			$("#operations_id").find("option[value="+operations_id+"]").attr("selected",true);
		}
		
		function chinaChange(province, city) {
			var pv, cv;
			var i, ii;
			
			pv = province.value;
			cv = city.value;
			city.length = 1;
			
			if (pv == '0') {
				return;
			}
			
			if (typeof (china[pv]) == 'undefined'){
				$("#city").find("option").remove();
				return;
			} 
	
			for (i = 0; i < china[pv].length; i++) { 
				ii = i;
			
				city.options[ii] = new Option();
				city.options[ii].text = china[pv][i];
				//city.options[ii].value = china[pv][i];
				city.options[ii].value = parseInt(pv+i)
				}
		};
	
	//datepicker plugin
	$('.date-picker').datepicker({
		autoclose: true,
		todayHighlight: true,
		language: 'cn'
	}).next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
	
		var contral = "0";
		
			//bootstrap验证控件		
		    $('#transportionform').bootstrapValidator({
		        message: 'This value is not valid',
		        feedbackIcons: {
		            valid: 'glyphicon glyphicon-ok',
		            invalid: 'glyphicon glyphicon-remove',
		            validating: 'glyphicon glyphicon-refresh'
		        },
		        fields: {
		        	gas_station_name: {
		                message: 'The cardno is not valid',
		                validators: {
		                    notEmpty: {
		                        message: '运输公司名称不能为空'
		                    },
		                    stringLength: {
		                        min: 1,
		                        max: 20,
		                        message: '运输公司名称不能超过20个汉字'
		                    }
		                }
		            },
		            expiry_date_frompage: {
		                message: 'The cardno is not valid',
		                validators: { 
		                    notEmpty: {
		                        message: '平台有效期不能为空'
		                    },
		                    callback: {
		                    	message: '平台有效期必须大于等于当前日期',
		                    	callback: function (value, validator, $field) {
	                                 if(compareDate(new Date().toLocaleDateString(),value)){
	                                	 return false;
	                                 }
	                                 return true;
	                            }
		                    }
		                },
		                trigger: 'change'
		            },
		            station_manager: {
		                message: 'The cardno is not valid',
		                validators: {
		                    notEmpty: {
		                        message: '联系人不能为空'
		                    },
		                    stringLength: {
		                        min: 1,
		                        max: 20,
		                        message: '联系人不能超过20个汉字'
		                    }
		                }
		            },
		            contact_phone: {
		                message: 'The cardno is not valid',
		                validators: {
		                    notEmpty: {
		                        message: '联系电话不能为空'
		                    },
		                    regexp: {
		                        regexp: '^[0-9]*[1-9][0-9]*$',
		                        message: '联系电话必须是数字'
		                    }
		                }
		            },
		            salesmen_name: {
		                validators: {
		                    notEmpty: {
		                        message: '销售负责人不能为空'
		                    }
		                }
		            },
		            email: {
		                validators: {
		                    notEmpty: {
		                        message: '加气站注册邮箱不能为空'
		                    },
		                    stringLength: {
		                        min: 1,
		                        max: 50,
		                        message: '加注站注册邮箱不能超过50个字符'
		                    }
		                }
		            },
		            operations_id: {
		                validators: {
		                    notEmpty: {
		                        message: '运管负责人不能为空'
		                    }
		                }
		            },
		            province: {
		                validators: {
		                    notEmpty: {
		                        message: '注册地址省不能为空'
		                    }
		                }
		            },
		            city : {
		                validators: {
		                    notEmpty: {
		                        message: '注册地址市不能为空'
		                    }
		                }
		            },
		            address: {
		                validators: {
		                    notEmpty: {
		                        message: '注册详细地址不能为空'
		                    }
		                }
		            },
		            longitude: {
		                validators: {
		                    notEmpty: {
		                        message: '注册地址经度'
		                    },
		                    regexp: {
		                        regexp: '^[0-9]+([.]{1}[0-9]+){0,1}$',
		                        message: '注册地址经度必须是数字'
		                    }
		                }
		            },
		            latitude: {
		                validators: {
		                    notEmpty: {
		                        message: '注册地址经度'
		                    },
		                    regexp: {
		                        regexp: '^[0-9]+([.]{1}[0-9]+){0,1}$',
		                        message: '注册地址纬度必须是数字'
		                    }
		                }
		            },
		            indu_com_number: {
						validators: {
							stringLength: {
								max: 15,
								message: '工商注册号不能超过15位'
							}
		                    }
		            },
		            tax_number: {
						validators: {
		                    stringLength: {
		                        max: 15,
		                        message: '税务注册号不能超过15位'
							}
						}
		           	}
		        }
		    });
			    
		function save(){
			$("#address").val($("#province").find("option:selected").text()+" "+$("#city").find("option:selected").text()+" "+$("#detail").val());
			/*手动验证表单，当是普通按钮时。*/
			$('#transportionform').data('bootstrapValidator').validate();
			if(!$('#transportionform').data('bootstrapValidator').isValid()){
				return ;
			}
			
			var options ={   
		            url:'../web/transportion/saveTransportion',   
		            type:'post',                    
		            dataType:'text',
		            success:function(data){
		            	$("#main").html(data);
						$("#modal-table").modal("show");
		            },error:function(XMLHttpRequest, textStatus, errorThrown) {

		 	       }
			}
						
			$("#transportionform").ajaxSubmit(options);
		}
		
		function returnpage(){
			loadPage('#main', '../web/transportion/transportionList');
		}
		
		function setSalesmenName(obj){
			$("#salesmen_name").val($(obj).val());
		}
		
		function setOperationName(obj){
			$("#operations_name").val($(obj).val());
		}
		
		function save_photo(fileobj,obj,obj1){
			
			$(fileobj).parents("div").find("input[name=uploadfile]").each(function(){
				$(this).attr("name","");
			});
			
			$(fileobj).parent("div").find("input:first").attr("name","uploadfile");
			
			if($(obj).val()==null || $(obj).val()==""){
				bootbox.alert("请先上传文件");	
				return;
			}
			
			var multipartOptions ={   
					url:'../crmInterface/crmBaseService/web/upload?stationid='+$("#sys_transportion_id").val(),
		            type:'post',                    
		            dataType:'text',
		            enctype:"multipart/form-data",
		            success:function(data){
		            	var s = JSON.parse(data);
		            	if(s.success == true){
		            		bootbox.alert("上传成功");
		            		$(obj1).val(s.obj);
		            	}
		            	
		            },error:function(XMLHttpRequest, textStatus, errorThrown) {

		 	       }
			}
			$("#transportionform").ajaxSubmit(multipartOptions);
		}
		
		jQuery(function($) {
			var $overflow = '';
			var colorbox_params = {
				rel: 'colorbox',
				reposition:true,
				scalePhotos:true,
				scrolling:false,
				previous:'<i class="ace-icon fa fa-arrow-left"></i>',
				next:'<i class="ace-icon fa fa-arrow-right"></i>',
				close:'&times;',
				current:'{current} of {total}',
				maxWidth:'100%',
				maxHeight:'100%',
				onOpen:function(){
					$overflow = document.body.style.overflow;
					document.body.style.overflow = 'hidden';
				},
				onClosed:function(){
					document.body.style.overflow = $overflow;
				},
				onComplete:function(){
					$.colorbox.resize();
				}
			};
		
			$('.ace-thumbnails [data-rel="colorbox"]').colorbox(colorbox_params);
			$("#cboxLoadingGraphic").html("<i class='ace-icon fa fa-spinner orange fa-spin'></i>");//let's add a custom loading icon
			
			
			$(document).one('ajaxloadstart.page', function(e) {
				$('#colorbox, #cboxOverlay').remove();
		   });
		})