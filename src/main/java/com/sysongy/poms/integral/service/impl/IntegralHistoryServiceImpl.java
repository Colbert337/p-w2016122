package com.sysongy.poms.integral.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.sysongy.util.BigDecimalArith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.sysongy.util.UUIDGenerator;
import com.github.pagehelper.PageHelper;
import com.sysongy.poms.integral.model.IntegralHistory;
import com.sysongy.poms.integral.model.IntegralRule;
import com.sysongy.poms.driver.dao.SysDriverMapper;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.integral.dao.IntegralHistoryMapper;
import com.sysongy.poms.integral.dao.IntegralRuleMapper;
import com.sysongy.poms.integral.service.IntegralHistoryService;
import com.sysongy.poms.mobile.dao.SysRoadConditionMapper;
import com.sysongy.poms.mobile.model.SysRoadCondition;
import com.sysongy.poms.mobile.service.SysRoadService;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.service.OrderService;
import com.sysongy.poms.system.model.SysOperationLog;

@Service
public class IntegralHistoryServiceImpl implements IntegralHistoryService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IntegralHistoryMapper integralHistoryMapper;

	@Autowired
	private IntegralRuleMapper integralRuleMapper;
	
	@Autowired
	private OrderService orderService;

	@Autowired
	private DriverService driverService;
	
	@Autowired
	private SysDriverMapper sysDriverMapper;
	@Autowired
	private SysRoadService sysRoadService;
	
	@Autowired 
	private SysRoadConditionMapper sysRoadConditionMapper;
	
	@Override
	public PageInfo<IntegralHistory> queryIntegralHistory(IntegralHistory integralHistory) throws Exception {
		if (integralHistory.getPageNum() == null) {
			integralHistory.setPageNum(1);
			integralHistory.setPageSize(10);
		}
		if(integralHistory.getConvertPageNum() != null){
			if(integralHistory.getConvertPageNum() > integralHistory.getPageNumMax()){
				integralHistory.setPageNum(integralHistory.getPageNumMax());
			}else if(integralHistory.getConvertPageNum() < 1){
				integralHistory.setPageNum(1);
			}else{
				integralHistory.setPageNum(integralHistory.getConvertPageNum());
			}
		}
		if (StringUtils.isEmpty(integralHistory.getOrderby())) {
			integralHistory.setOrderby("lastmodify_time desc");
		}
		PageHelper.startPage(integralHistory.getPageNum(), integralHistory.getPageSize(), integralHistory.getOrderby());
		List<IntegralHistory> list = integralHistoryMapper.queryForPage(integralHistory);
        for(IntegralHistory integral:list){																	
        	integral.setCreatedTimeStr((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(integral.getCreate_time())));
        }
		PageInfo<IntegralHistory> pageInfo = new PageInfo<IntegralHistory>(list);
		return pageInfo;
	}

	@Override
	public String addIntegralHistory(IntegralHistory integralHistory, String userID) throws Exception {
		integralHistory.setIntegral_history_id(UUIDGenerator.getUUID());
		integralHistory.setCreate_person_id(userID);
		integralHistory.setCreate_time(new Date());
		integralHistory.setLastmodify_person_id(userID);
		integralHistory.setLastmodify_time(new Date());
		integralHistoryMapper.insert(integralHistory);
		return integralHistory.getIntegral_history_id();
	}

	@Override
	public String modifyIntegralHistory(IntegralHistory integralHistory, String userID) throws Exception {
		integralHistory.setLastmodify_person_id(userID);
		integralHistory.setLastmodify_time(new Date());
		integralHistoryMapper.updateByPrimaryKey(integralHistory);
		return integralHistory.getIntegral_history_id();
	}

	@Override
	public Integer delIntegralHistory(String integral_history_id) throws Exception {
		return integralHistoryMapper.deleteByPrimaryKey(integral_history_id);
	}

	@Override
	public IntegralHistory queryIntegralHistoryByPK(String integral_history_id) throws Exception {
		return integralHistoryMapper.selectByPrimaryKey(integral_history_id);
	}
	
	/**
	 * 增加实名认证积分
	 */
	@Override
	public void addsmrzIntegralHistory(SysDriver sysDriver,String operator_id) throws Exception{
		//实名认证成功发放积分
		HashMap<String, String> smrzMap =  integralRuleMapper.selectRepeatIntegralType("smrz");
		//设置积分规则向积分记录表中插入积分历史数据
		if("true".equals(smrzMap.get("STATUS"))&&"1".equals(String.valueOf(smrzMap.get("integral_rule_num")))){
			String integral_rule_id = smrzMap.get("integral_rule_id");
			IntegralRule integralRule = integralRuleMapper.selectByPrimaryKey(integral_rule_id);
				if(null!=integralRule){
						IntegralHistory aIntegralHistory = new IntegralHistory();
						aIntegralHistory.setIntegral_num(integralRule.getIntegral_reward());
						aIntegralHistory.setSys_driver_id(sysDriver.getSysDriverId()); 
						aIntegralHistory.setIntegral_type(integralRule.getIntegral_type()); 
						PageInfo<IntegralHistory> list = this.queryIntegralHistory(aIntegralHistory);
						List<IntegralHistory> integralHistoryList =list.getList();
						if(integralHistoryList.size()==0){
							IntegralHistory integralHistory = new IntegralHistory();
							integralHistory.setIntegral_type("smrz");
							integralHistory.setIntegral_rule_id(smrzMap.get("integral_rule_id"));
							integralHistory.setSys_driver_id(sysDriver.getSysDriverId());
							integralHistory.setIntegral_num(integralRule.getIntegral_reward());
							this.addIntegralHistory(integralHistory, operator_id);	
							SysDriver aSysDriver = new SysDriver();
							aSysDriver.setIntegral_num(integralRule.getIntegral_reward());
							aSysDriver.setSysDriverId(sysDriver.getSysDriverId());
							sysDriverMapper.updateDriverByIntegral(aSysDriver);				
					}
				}
			}
		}
	
	/**
	 * 设置密保手机增加积分
	 * @param sysDriver
	 * @throws Exception
	 */
	@Override
	public void addszmbsjIntegralHistory(SysDriver sysDriver) throws Exception{
        if(null!=sysDriver.getSecurityMobilePhone()&&!"".equals(sysDriver.getSecurityMobilePhone())){
			//设置密保手机成功发放积分
			HashMap<String, String> szmbsjMap =  integralRuleMapper.selectRepeatIntegralType("szmbsj");
			//设置密保手机向积分记录表中插入积分历史数据
			if("true".equals(szmbsjMap.get("STATUS"))&&"1".equals(String.valueOf(szmbsjMap.get("integral_rule_num")))){
				String integral_rule_id = szmbsjMap.get("integral_rule_id");
				IntegralRule integralRule = integralRuleMapper.selectByPrimaryKey(integral_rule_id);
				if(null!=integralRule){
					IntegralHistory aIntegralHistory = new IntegralHistory();
					aIntegralHistory.setIntegral_num(integralRule.getIntegral_reward());
					aIntegralHistory.setSys_driver_id(sysDriver.getSysDriverId()); 
					aIntegralHistory.setIntegral_type(integralRule.getIntegral_type()); 
					PageInfo<IntegralHistory> list = this.queryIntegralHistory(aIntegralHistory);
					List<IntegralHistory> integralHistoryList =list.getList();
					if(integralHistoryList.size()==0){
						IntegralHistory szmbsjHistory = new IntegralHistory();
						szmbsjHistory.setIntegral_type("szmbsj");
						szmbsjHistory.setIntegral_rule_id(szmbsjMap.get("integral_rule_id"));
						szmbsjHistory.setSys_driver_id(sysDriver.getSysDriverId());
						szmbsjHistory.setIntegral_num(integralRule.getIntegral_reward());
						this.addIntegralHistory(szmbsjHistory, sysDriver.getSysDriverId());
						SysDriver aSysDriver = new SysDriver();
						aSysDriver.setIntegral_num(integralRule.getIntegral_reward());
						aSysDriver.setSysDriverId(sysDriver.getSysDriverId());
						driverService.updateDriverByIntegral(aSysDriver);			
					}
				}
			}     
        }
	}
	
	/**
	 * 邀请增加积分
	 * @param record
	 * @param invitationCode
	 * @param operator_id
	 * @throws Exception
	 */
	@Override
	public void addyqcgIntegralHistory(SysDriver record,String invitationCode, String operator_id) throws Exception{
		//判断邀请码是否存在，存在则根据条件发放积分
		if(null!=invitationCode &&!"".equals(invitationCode)){
			//邀请成功则发放积分
			HashMap<String, String> yqcgMap =  integralRuleMapper.selectRepeatIntegralType("yqcg");
			String integral_rule_id = yqcgMap.get("integral_rule_id");
			IntegralRule integralRule = integralRuleMapper.selectByPrimaryKey(integral_rule_id);
			//存在积分规则
			if(null!=integralRule){
				//没有发送过积分则进行积分规则判断
					HashMap<String,String> yqcgHashMap = new HashMap<String,String>();
					yqcgHashMap.put("reward_cycle", integralRule.getReward_cycle());
					yqcgHashMap.put("regis_company", invitationCode);
					yqcgHashMap.put("sys_driver_id",record.getSysDriverId());
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
					yqcgHashMap.put("integral_createTime",sdf.format(integralRule.getLastmodify_time()));
					List<HashMap<String,String>> driverList = sysDriverMapper.queryInvitationByCode(yqcgHashMap);
					//当前日/周/月 存在的 司机注册数
					if(driverList.size()>0){
							HashMap<String, String> driverMap = driverList.get(0);
							//被邀请向积分记录表中插入积分历史数据
							if("true".equals(yqcgMap.get("STATUS"))&&"1".equals(String.valueOf(yqcgMap.get("integral_rule_num")))){
								String llimitnumber = integralRule.getLimit_number();
								String reward_cycle = integralRule.getReward_cycle();
								String countDriver = String.valueOf(driverMap.get("count"));
								boolean nolimit="不限".equals(llimitnumber);
								boolean pass= (!"one".equals(reward_cycle))&&(!nolimit)&&(Integer.parseInt(countDriver)<=Integer.parseInt(llimitnumber));	
								boolean one = "one".equals(reward_cycle)&&(Integer.parseInt(countDriver)-1==Integer.parseInt(llimitnumber));	
									//如果不限则不判断，一次则数量比限制值大1条，否则只要比限制值多则都加
										if(nolimit||one||pass){
										IntegralHistory yqcgHistory = new IntegralHistory();
										yqcgHistory.setIntegral_type("yqcg");
										yqcgHistory.setIntegral_rule_id(yqcgMap.get("integral_rule_id"));
										SysDriver aSysDriver  = sysDriverMapper.selectByinvitationCode(invitationCode);	
										yqcgHistory.setSys_driver_id(aSysDriver.getSysDriverId());
										yqcgHistory.setIntegral_num(integralRule.getIntegral_reward());
										this.addIntegralHistory(yqcgHistory, operator_id);
										SysDriver sysDriver = new SysDriver();
										sysDriver.setIntegral_num(integralRule.getIntegral_reward());
										sysDriver.setSysDriverId(aSysDriver.getSysDriverId());
										sysDriverMapper.updateDriverByIntegral(sysDriver);				
								}	
						}					
					}	
		}
	}
		
	}
	
	/**
	 * 上报路况增加积分
	 * @param road
	 * @param userID
	 * @throws Exception
	 */
	public void addsblkIntegralHistory(SysRoadCondition road,String userID) throws Exception{
		//判断上报路况审核是否通过，存在则根据条件发放积分
		if(null!=road.getConditionStatus() &&!"".equals(road.getConditionStatus())&&"2".equals(road.getConditionStatus())){
			//上报路况成功发放积分
			HashMap<String, String> sblkMap =  integralRuleMapper.selectRepeatIntegralType("sblk");
			String integral_rule_id = sblkMap.get("integral_rule_id");
			IntegralRule integralRule = integralRuleMapper.selectByPrimaryKey(integral_rule_id);
			//存在积分规则
			if(null!=integralRule){
				SysDriver aSysDriver = new SysDriver();
				 SysRoadCondition aSysRoadCondition = sysRoadService.selectByPrimaryKey(road.getId());
				aSysDriver.setMobilePhone(aSysRoadCondition.getPublisherPhone());
				SysDriver sysDriver = driverService.queryDriverByMobilePhone(aSysDriver);
					HashMap<String,String> sblkHashMap = new HashMap<String,String>();
					sblkHashMap.put("reward_cycle", integralRule.getReward_cycle());
					sblkHashMap.put("publisher_phone", aSysRoadCondition.getPublisherPhone());
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
					sblkHashMap.put("integral_createTime",sdf.format(integralRule.getCreate_time()));
					sblkHashMap.put("id", road.getId());
					List<HashMap<String,String>> driverList = sysRoadConditionMapper.queryConditionByPhone(sblkHashMap);
					//当前日/周/月 存在的 司机注册数
					if(driverList.size()>0){
							HashMap<String, String> driverMap = driverList.get(0);
							//上报路况向积分记录表中插入积分历史数据
							if("true".equals(sblkMap.get("STATUS"))&&"1".equals(String.valueOf(sblkMap.get("integral_rule_num")))){
        							String llimitnumber = integralRule.getLimit_number();
        							String reward_cycle = integralRule.getReward_cycle();
        							String count = String.valueOf(driverMap.get("count"));
        							boolean nolimit="不限".equals(llimitnumber);
        							boolean pass= (!"one".equals(reward_cycle))&&(!nolimit)&&(Integer.parseInt(count)<=Integer.parseInt(llimitnumber));	
        							boolean one = "one".equals(reward_cycle)&&(Integer.parseInt(count)-1==Integer.parseInt(llimitnumber));	
        								//如果不限则不判断，一次则数量比限制值大1条，否则只要比限制值多则都加
        								if(nolimit||one||pass){	
										IntegralHistory sblkHistory = new IntegralHistory();
										sblkHistory.setIntegral_type("sblk");
										sblkHistory.setIntegral_rule_id(sblkMap.get("integral_rule_id"));
										sblkHistory.setSys_driver_id(sysDriver.getSysDriverId());
										sblkHistory.setIntegral_num(integralRule.getIntegral_reward());
										this.addIntegralHistory(sblkHistory, userID);
										SysDriver driver = new SysDriver();
										driver.setIntegral_num(integralRule.getIntegral_reward());
										driver.setSysDriverId(sysDriver.getSysDriverId());
										driverService.updateDriverByIntegral(driver);				
								}	
						}					
					}	
		}
	}	
		
	}
	
	/**
	 * 充值或者消费增加积分
	 * @param order
	 * @throws Exception
	 */
	@Override
	public void addIntegralHistory(SysOrder order,String type) throws Exception {
		if(null!=order.getOrderId()&&!"".equals(order.getOrderId())){
			//充值成功发放积分
			HashMap<String, String> integralMap =  integralRuleMapper.selectRepeatIntegralType(type);
			String integral_rule_id = integralMap.get("integral_rule_id");
			IntegralRule integralRule = integralRuleMapper.selectByPrimaryKey(integral_rule_id);
			//存在积分规则
			if(null!=integralRule){
					HashMap<String,String> hashMap = new HashMap<String,String>();
					hashMap.put("reward_cycle", integralRule.getReward_cycle());
					hashMap.put("debit_account", order.getDebitAccount());
					hashMap.put("order_id",order.getOrderId());
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
					hashMap.put("integral_createTime",sdf.format(integralRule.getLastmodify_time()));
					hashMap.put("order_type","130");
					if("xf".equals(type)){
						hashMap.put("order_type","220");
						hashMap.put("credit_account", order.getCreditAccount());
					}
					List<HashMap<String,String>> orderList = orderService.queryOrderByOperator(hashMap);
					if(orderList.size()==0){
						HashMap<String,String> temp = new HashMap<String,String>();
						temp.put("days","0");
						temp.put("count","0");
						orderList.add(temp);
					}
					//当前日/周/月 存在的 司机注册数
					if(orderList.size()>0){
							HashMap<String, String> driverMap = orderList.get(0);
							//充值成功向积分记录表中插入积分历史数据
							if("true".equals(integralMap.get("STATUS"))&&"1".equals(String.valueOf(integralMap.get("integral_rule_num")))){
								String llimitnumber = integralRule.getLimit_number();
								String reward_cycle = integralRule.getReward_cycle();
								String count = String.valueOf(driverMap.get("count"));
							boolean nolimit="不限".equals(llimitnumber);
							boolean pass= (!"one".equals(reward_cycle))&&(!nolimit)&&(Integer.parseInt(count)<Integer.parseInt(llimitnumber));	
							boolean one = "one".equals(reward_cycle)&&(Integer.parseInt(count)<Integer.parseInt(llimitnumber));
								//不限或一次或小于限制次数
									if(nolimit||one||pass){
										String account = order.getCreditAccount();
										if("cz".equals(type)){
											account=order.getDebitAccount();
										}
										IntegralHistory aIntegralHistory = new IntegralHistory();
										aIntegralHistory.setIntegral_type(type);
										aIntegralHistory.setIntegral_rule_id(integralMap.get("integral_rule_id"));
										aIntegralHistory.setSys_driver_id(account);
										String[] ladder_before = integralMap.get("ladder_before").split(",");
										String[] ladder_after = integralMap.get("ladder_after").split(",");
										String[] integral_reward = integralMap.get("integral_reward").split(",");
										String[] reward_factor = integralMap.get("reward_factor").split(",");
										String[] reward_max = integralMap.get("reward_max").split(",");
										String integralreward = "";
										for(int i=0;i<ladder_before.length;i++){
											//判断是否在阶梯的消费区间内
											if(order.getCash().compareTo(new BigDecimal(ladder_before[i]))>0&&order.getCash().compareTo(new BigDecimal(ladder_after[i]))<=0){
												if(integral_reward != null && integral_reward.length > 0 && null!=integral_reward[i]&&!"".equals(integral_reward[i])){
													aIntegralHistory.setIntegral_num(integral_reward[i]);
													integralreward = integral_reward[i];
												}else{
													BigDecimal integralNum = BigDecimalArith.round(order.getCash().multiply(new BigDecimal(reward_factor[i]).divide(new BigDecimal("100"))),0) ;
													if(integralNum.compareTo(BigDecimal.ZERO) == 0){
														integralNum = BigDecimal.ONE;
													}
													aIntegralHistory.setIntegral_num(integralNum.toString());
													integralreward = integralNum.toString();
													if(order.getCash().multiply(new BigDecimal(reward_factor[i])).compareTo(new BigDecimal(reward_max[i]).multiply(new BigDecimal("100")))>0){
														aIntegralHistory.setIntegral_num(reward_max[i]);
														integralreward = reward_max[i];
													}
												}
											}
										}
										if(!"".equals(integralreward)){
											this.addIntegralHistory(aIntegralHistory,account);
											SysDriver driver = new SysDriver();
											driver.setIntegral_num(integralreward);
											driver.setSysDriverId(account);
											driverService.updateDriverByIntegral(driver);		
										}
								}	
						}					
					}	
				}				
		}		
	}
}
