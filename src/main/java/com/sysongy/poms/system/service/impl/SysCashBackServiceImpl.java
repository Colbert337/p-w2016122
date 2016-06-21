package com.sysongy.poms.system.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.order.model.SysOrder;
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
	private DriverService driverService;

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
	 * @param order
	 * @param cashBack
	 * @return
	 */
	private String cashToAccount(SysOrder order, List<SysCashBack> cashBackList){
		BigDecimal cash = order.getCash();
		for(SysCashBack cashback : cashBackList){
			String status = cashback.getStatus();
			if(GlobalConstant.CASHBACK_STATUS_ENABLE.equalsIgnoreCase(status)){
				//如果启用，则执行
				String 
			}
		}
		return "";
	}
	@Override
	public String cashBackToDriver(SysOrder order) throws Exception{
		//1.判断是否首次返现，是则调用首次返现规则
		String accountId = order.getDebitAccount();
		SysDriver driver = driverService.queryDriverByPK(accountId);
        Integer is_first_charge = driver.getIsFirstCharge();
        if(is_first_charge.intValue() == GlobalConstant.FIRST_CHAGRE_YES){
        	List<SysCashBack>  cashBackList = this.queryCashBackByNumber(GlobalConstant.CashBackNumber.CASHBACK_FIRST_CHARGE);
        	String cashTo_success = cashToAccount(order,cashBackList);
        	//TODO
        }
		//2.根据当前订单类型，调用对应的返现规则
		//TODO
		return "";
	}
	
	@Override
	public String cashBackToTransportion(SysOrder order) throws Exception{
		//TODO
		return "";
	}
}
