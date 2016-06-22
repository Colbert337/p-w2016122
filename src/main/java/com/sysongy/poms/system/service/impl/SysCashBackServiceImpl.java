package com.sysongy.poms.system.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.service.OrderDealService;
import com.sysongy.poms.order.service.SysUserAccountService;
import com.sysongy.poms.system.dao.SysCashBackMapper;
import com.sysongy.poms.system.model.SysCashBack;
import com.sysongy.poms.system.service.SysCashBackService;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.UUIDGenerator;

@Service
public class SysCashBackServiceImpl implements SysCashBackService {
	
	@Autowired
	private SysCashBackMapper cashBackMapper;


	@Autowired
	private OrderDealService orderDealService;
	
	@Autowired
	private SysUserAccountService sysUserAccountService;
	
	
	  
	@Override
	public PageInfo<SysCashBack> queryCashBack(SysCashBack obj) throws Exception {
		PageHelper.startPage(obj.getPageNum(), obj.getPageSize(), obj.getOrderby());
		List<SysCashBack> list = cashBackMapper.queryForPage(obj);
		PageInfo<SysCashBack> pageInfo = new PageInfo<SysCashBack>(list);
		
		return pageInfo;
	}

	@Override
	public SysCashBack queryCashBackByPK(String cashBackid) throws Exception {
		SysCashBack cashback = cashBackMapper.selectByPrimaryKey(cashBackid);
		cashback.setStart_date_after(new SimpleDateFormat("yyyy-MM-dd").format(cashback.getStart_date()));
		cashback.setStart_date_before(new SimpleDateFormat("yyyy-MM-dd").format(cashback.getEnd_date()));
		return cashback;
	}

	@Override
	public String saveCashBack(SysCashBack record, String operation) throws Exception {
		record.setStart_date(new SimpleDateFormat("yyyy-MM-dd").parse(record.getStart_date_after()));
		record.setEnd_date(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(record.getStart_date_before()+" 23:59:59"));
		record.setSys_cash_back_id(UUIDGenerator.getUUID());
		
		SysCashBack check = new SysCashBack();
		
		check.setSys_cash_back_no(record.getSys_cash_back_no());
		check.setLevel(record.getLevel());
		List<SysCashBack> checklist = cashBackMapper.checkvalid(check);
		for (SysCashBack sysCashBack : checklist) {
			
			Date obj_start_date = record.getStart_date();
			Date obj_end_date = record.getEnd_date();
			String obj_min_value = record.getThreshold_min_value();
			String obj_max_value = record.getThreshold_max_value();
			
			//新插入的日期区间包含已经配置的日期区间
			boolean case_1 = !obj_start_date.after(sysCashBack.getStart_date()) && !obj_end_date.before(sysCashBack.getStart_date());
			//新插入的日期开始时间在已经配置的日期区间之内
			boolean case_2 = !obj_start_date.before(sysCashBack.getStart_date()) && !obj_start_date.after(sysCashBack.getEnd_date());
			
			if(case_1 || case_2){
				//例外CASE1，配置项的minvalue与maxvalue均小于已配置的minvalue
				boolean exceptionCase_1 = Float.valueOf(obj_min_value) <= Float.valueOf(sysCashBack.getThreshold_min_value()) && Float.valueOf(obj_max_value) <= Float.valueOf(sysCashBack.getThreshold_min_value());
				//例外CASE2，配置项的minvalue大于已配置的maxvalue
				boolean exceptionCase_2 = Float.valueOf(obj_min_value) >= Float.valueOf(sysCashBack.getThreshold_max_value());
				
				if(!(exceptionCase_1 || exceptionCase_2) && !record.getSys_cash_back_id().equals(sysCashBack.getSys_cash_back_id())){
					throw new Exception("该时间段的配置项与配置项号为【"+sysCashBack.getSys_cash_back_id()+"】重复，请检查");
				}
			}
		}
				
		if("insert".equals(operation)){
			record.setCreated_date(new Date());
			
			cashBackMapper.insert(record);
			return record.getSys_cash_back_id();
		}else{
			record.setUpdated_date(new Date());
			cashBackMapper.updateByPrimaryKeySelective(record);
			return record.getSys_cash_back_id();
		}
	}

	@Override
	public Integer delCashBack(String sysCashBackId) throws Exception {
		return cashBackMapper.deleteByPrimaryKey(sysCashBackId);
	}
	
	/**
	 * 通过cashBack的number得到对象
	 * @param cashBackid
	 * @return 
	 * @return
	 * @throws Exception
	 */
	public List<SysCashBack> queryCashBackByNumber(String cashBackNumber) throws Exception {
		return  cashBackMapper.queryCashBackByNumber(cashBackNumber);
	}
	
	/**
	 * 取出规则，计算返现值，然后操作对应账户的金额。
	 * 如果是充红，cash是负数，---不调用返现规则，直接调用历史记录
	 * 返现步骤：
	 * 1.从传过来的某个充值类型的cashBackList中，计算过滤得到符合条件的一条返现规则：算法
	 *   计算算法：
	 *	  a.计算是否启用，去掉不启用的
	 *	  b.计算是否在有效期
	 *	  c.计算当前阈值，是否在阈值里面
	 *	  d.在阈值里面的，根据优先级，确定启用哪条记录,冒泡法
	 *   如果未找到符合条件的返现记录，则记录交易流水，返回正常success
	 * 2.根据得到符合条件的一条返现规则：计算当前的返现金额
	 * 3.给这个账户增加返现
	 * 4.写入订单处理流程
	 * 
	 * @param order
	 * @param cashBack
	 * @return
	 */
	public String cashToAccount(SysOrder order, List<SysCashBack> cashBackList,String accountId,String accountUserName, String orderDealType){
		//1.步骤一：从传过来的某个充值类型的cashBackList中，计算过滤得到符合条件的一条返现规则：
		BigDecimal cash = order.getCash();
		List<SysCashBack> eligible_list = new ArrayList<SysCashBack>();
		for(SysCashBack cashback : cashBackList){
			String status = cashback.getStatus();
			if(GlobalConstant.CASHBACK_STATUS_ENABLE.equalsIgnoreCase(status)){
				//如果启用，则执行
				Date start_date = cashback.getStart_date();
				Date end_date = cashback.getEnd_date();
				Date now = new Date();
				//大于等于0 则是当前日期大于等于start_date.
				if((now.compareTo(start_date)>=0)&&(now.compareTo(end_date)<=0)){
					//判断阈值是否在区间
					BigDecimal min = new BigDecimal(cashback.getThreshold_min_value());
					BigDecimal max = new BigDecimal(cashback.getThreshold_max_value());
					if((cash.compareTo(min)>=0)&&(cash.compareTo(max)<0)){
						eligible_list.add(cashback);	
					}
				}
			}
		}
		SysCashBack eligible_cashback = null;
		if(eligible_list.size()==0){
			//记录订单流水，未找到有效记录
			String remark ="给"+accountUserName+"返现"+cash.toPlainString()+",未找到符合条件的返现规则。";
			orderDealService.createOrderDeal(order.getOrderId(),orderDealType, remark, GlobalConstant.OrderProcessResult.SUCCESS);
			return GlobalConstant.OrderProcessResult.SUCCESS;
		}else{
			eligible_cashback = eligible_list.get(0);
		}
		//冒泡法得到最高级别的level对应记录
		int select_level = Integer.parseInt(eligible_cashback.getLevel());
		for(SysCashBack eligible : eligible_list){
			String level = eligible.getLevel();
			int lev = Integer.parseInt(level);
			if(lev > select_level){
				select_level = lev;
				eligible_cashback = eligible;
			}
		}
		
		//2.步骤二：根据得到符合条件的一条返现规则：计算当前的返现金额
		String cash_per_str = eligible_cashback.getCash_per();
		BigDecimal cash_per = new BigDecimal(cash_per_str);  
		BigDecimal back_money = cash.multiply(cash_per) ;
		
		//3.给这个账户增加返现
		String addCash_success = sysUserAccountService.addCashToAccount(accountId, back_money);
		
		//4.写入订单处理流程
		String remark = "给"+ accountUserName+"的账户，返现"+back_money.toPlainString()+"。";
		orderDealService.createOrderDealWithCashBack(order.getOrderId(), orderDealType, remark, cash_per_str, cash_per, addCash_success);
		
		return addCash_success;
	}
	
}