<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/WEB-INF/sysongytag.tld" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

	<script type="text/javascript" src="<%=basePath %>/assets/js/date-time/moment.js"></script>
	<script type="text/javascript" src="<%=basePath %>/assets/js/date-time/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="<%=basePath %>/common/js/fileinput.js"></script>
	<script type="text/javascript" src="<%=basePath %>/common/js/zh.js"></script>
	<script type="text/javascript" src="<%=basePath %>/common/js/json2.js"></script>

	<link rel="stylesheet" href="<%=basePath %>/assets/css/font-awesome.css" />
	<link rel="stylesheet" href="<%=basePath %>/assets/css/jquery-ui.custom.css" />
		<link rel="stylesheet" href="<%=basePath %>/assets/css/chosen.css" />
		<link rel="stylesheet" href="<%=basePath %>/assets/css/bootstrap-datepicker3.css" />
		<link rel="stylesheet" href="<%=basePath %>/assets/css/bootstrap-timepicker.css" />
		<link rel="stylesheet" href="<%=basePath %>/assets/css/daterangepicker.css" />
		<link rel="stylesheet" href="<%=basePath %>/assets/css/bootstrap-datetimepicker.css" />
		<link rel="stylesheet" href="<%=basePath %>/assets/css/colorpicker.css" />
	
	<link rel="stylesheet" href="<%=basePath %>/common/css/fileinput.css" />

			<!-- /section:basics/sidebar -->
			<div class="main-content">
				<div class="main-content-inner">
					<!-- #section:basics/content.breadcrumbs -->
					<div class="breadcrumbs" id="breadcrumbs">
						<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>

						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								<a href="#">主页</a>
							</li>

							<li>
								<a href="#">液厂管理</a>
							</li>
							<li class="active">液厂信息管理</li>
						</ul><!-- /.breadcrumb -->

						<!-- #section:basics/content.searchbox -->
						<div class="nav-search" id="nav-search">
							<form class="form-search" >
							
								<input id="retCode" type="hidden" value=" ${ret.retCode}" />
								<input id="retMsg" type="hidden" value=" ${ret.retMsg}" />
								
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
									<i class="ace-icon fa fa-search nav-search-icon"></i>
								</span>
							</form>
						</div><!-- /.nav-search -->

						<!-- /section:basics/content.searchbox -->
					</div>

					<div class="">
						<!-- /section:settings.box -->
						<div class="page-header">
							<h1>
								新建液厂
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<form class="form-horizontal"  id="gastationform">
								
								<jsp:include page="/common/page_param.jsp"></jsp:include>

									<div class="form-group">
										<label for="gas_station_name" class="col-sm-3 control-label no-padding-right"> 液厂名称： </label>

										<div class="col-sm-4">
											<input type="text" name="gas_factory_name" placeholder="输入液厂名称" class="form-control" maxlength="20" value="${gasource.gas_factory_name}"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" >工艺类型： </label>

										<div class="col-sm-4">
											<input type="text" name="technology_type" placeholder="输入工艺类型" class="form-control" value="${gasource.technology_type}"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">配送方式： </label>

										<div class="col-sm-4">
											<input type="text" id="delivery_method" name="delivery_method" placeholder="输入配送方式" class="form-control" maxlength="20" value="${gasource.delivery_method}"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">价格：</label>

										<div class="col-sm-4">
											<input type="text" name="market_price" placeholder="输入价格" class="form-control" maxlength="15" value="${gasource.market_price}"/>
										</div>
									</div>
	
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">注册地址： </label>
										<div class="col-sm-4">
											<div class="row form-group">
												<div class="col-sm-6">
													<select class="form-control" id="province_id" name="province_id" onchange="chinaChange(this,document.getElementById('city_id'));">
															<option value ="请选择市区">请选择省份</option>
															<option value ="100">北京市</option>
															<option value ="220">天津市</option>
															<option value ="210">上海市</option>
															<option value ="230">重庆市</option>
															<option value ="310">河北省</option>
															<option value ="350">山西省</option>
															<option value ="240">辽宁省</option>
															<option value ="430">吉林省</option>
															<option value ="450">黑龙江省</option>
															<option value ="250">江苏省</option>
															<option value ="570">浙江省</option>
															<option value ="550">安徽省</option>
															<option value ="590">福建省</option>
															<option value ="790">江西省</option>
															<option value ="530">山东省</option>
															<option value ="370">河南省</option>
															<option value ="270">湖北省</option>
															<option value ="730">湖南省</option>
															<option value ="200">广东省</option>
															<option value ="891">海南省</option>
															<option value ="810">四川省</option>
															<option value ="850">贵州省</option>
															<option value ="870">云南省</option>
															<option value ="290">陕西省</option>
															<option value ="930">甘肃省</option>
															<option value ="970">青海省</option>
															<option value ="852">台湾省</option>
															<option value ="770">广西壮族自治区</option>
															<option value ="470">内蒙古自治区</option>
															<option value ="890">西藏自治区</option>
															<option value ="950">宁夏回族自治区</option>
															<option value ="990">新疆维吾尔自治区</option>
															<option value ="851">香港特别行政区</option>
															<option value ="853">澳门特别行政区</option>
													</select>
												</div>
												<div class="col-sm-6">
													<select class="form-control" id="city_id" name="city_id">
													</select>
												</div>
											</div>
											<input type="text" id="detail" name="detail" class="form-control" placeholder="输入详细地址" value="${gasource.detail}"/>
											<input type="hidden" id="gas_factory_addr" name="gas_factory_addr" class="col-sm-12"  value="${gasource.address}"/>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right">备注： </label>

										<div class="col-sm-4">
											<textarea class="form-control" name="remark" rows="5" value="${gasource.remark}"></textarea>
										</div>
									</div>
											
									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											
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
											
											<button class="btn btn-success" type="buttom" onclick="returnpage();">
												<i class="ace-icon fa fa-undo bigger-110"></i>
												返回
											</button>
										</div>
									</div>

									<jsp:include page="/common/message.jsp"></jsp:include>
										
								</form>						
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
				</div>
	</div>
	</div>
	</div>
	</div>
	</div>
	</div>
	</div>

	<script type="text/javascript">
		
			//bootstrap验证控件		
		    $('#gastationform').bootstrapValidator({
		        message: 'This value is not valid',
		        feedbackIcons: {
		            valid: 'glyphicon glyphicon-ok',
		            invalid: 'glyphicon glyphicon-remove',
		            validating: 'glyphicon glyphicon-refresh'
		        },
		        fields: {
		        	gas_factory_name: {
		                validators: {
		                    notEmpty: {
		                        message: '液厂名称不能为空'
		                    },
		                    stringLength: {
		                        min: 1,
		                        max: 20,
		                        message: '液厂名称不能超过20个汉字'
		                    }
		                }
		            },
		            technology_type: {
		                validators: {
		                    notEmpty: {
		                        message: '工艺类型不能为空'
		                    },
		                    stringLength: {
		                        min: 1,
		                        max: 20,
		                        message: '工艺类型不能超过20个字符'
		                    }
		                }
		            },
		            delivery_method: {
		                validators: {
		                    notEmpty: {
		                        message: '配送方式不能为空'
		                    },
		                    stringLength: {
		                        min: 1,
		                        max: 20,
		                        message: '配送方式不能超过20个汉字'
		                    }
		                }
		            },
		            market_price: {
		                validators: {
		                    notEmpty: {
		                        message: '价格不能为空'
		                    }
		                }
		            },
		            province_id: {
		                validators: {
		                    notEmpty: {
		                        message: '省份不能为空'
		                    }
		                }
		            },
		            city_id: {
		                validators: {
		                    notEmpty: {
		                        message: '市不能为空'
		                    }
		                }
		            },
		            detail: {
		                validators: {
		                    notEmpty: {
		                        message: '详细地址不能为空'
		                    },
		                    regexp: {
		                        regexp: '^[0-9]*[1-9][0-9]*$',
		                        message: '联系电话必须是数字'
		                    }
		                }
		            }
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
			
		  //下拉框默认选中当前对象的值
			var province_id = '${gasource.province_id}';
			var city_id =  '${gasource.city_id}';
			var detail = '${gasource.gas_factory_addr}';
			
			if(province_id!=null && province_id!=""){
				$("#province_id").find("option[value="+province_id+"]").attr("selected",true);
				$("#province_id").trigger("change");
				$("#city_id").find("option[value="+city_id+"]").attr("selected",true);
				$("#detail").val(detail.split(" ")[2]);
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
					$("#city_id").find("option").remove();
					return;
				}
		
				for (i = 0; i < china[pv].length; i++) { 
					ii = i;
				
					city.options[ii] = new Option();
					city.options[ii].text = china[pv][i];
					//city.options[ii].value = china[pv][i];
					city.options[ii].value = parseInt(pv+i);
				}
			};
			    
		function save(){
			$("#gas_factory_addr").val($("#province_id").find("option:selected").text()+" "+$("#city_id").find("option:selected").text()+" "+$("#detail").val());
			/*手动验证表单，当是普通按钮时。*/
			$('#gastationform').data('bootstrapValidator').validate();
			if(!$('#gastationform').data('bootstrapValidator').isValid()){
				return ;
			}
			
			var options ={   
		            url:'../web/liquid/saveLiquid',   
		            type:'post',                    
		            dataType:'text',
		            success:function(data){
		            	$("#main").html(data);
		            	$("#modal-table").modal("show");
		            },error:function(XMLHttpRequest, textStatus, errorThrown) {
		            	
		 	       }
			}
						
			$("#gastationform").ajaxSubmit(options);
		}
		
		function returnpage(){
			loadPage('#main', '../web/liquid/liquidList');
		}
		</script>
