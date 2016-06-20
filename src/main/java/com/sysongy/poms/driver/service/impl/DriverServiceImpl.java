package com.sysongy.poms.driver.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.driver.dao.SysDriverMapper;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.order.model.SysOrder;
import com.sysongy.poms.order.model.SysOrderDeal;
import com.sysongy.poms.order.service.OrderDealService;
import com.sysongy.poms.permi.dao.SysUserAccountMapper;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.util.GlobalConstant;

/**
 * Created by Administrator on 2016/6/7.
 */

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private SysDriverMapper sysDriverMapper;
    
    @Autowired
    private SysUserAccountMapper sysUserAccountMapper;
    
    @Autowired
    private OrderDealService orderDealService;

    @Override
    public PageInfo<SysDriver> queryDrivers(SysDriver record) throws Exception {
        PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
        List<SysDriver> list = sysDriverMapper.queryForPage(record);
        PageInfo<SysDriver> pageInfo = new PageInfo<SysDriver>(list);
        return pageInfo;
    }

    @Override
    public Integer saveDriver(SysDriver record, String operation) throws Exception {
        if("insert".equals(operation)){
            record.setCreatedDate(new Date());
            return sysDriverMapper.insert(record);
        }else{
            return sysDriverMapper.updateByPrimaryKeySelective(record);
        }
    }

    @Override
    public Integer delDriver(String sysDriverId) throws Exception {
        return sysDriverMapper.deleteByPrimaryKey(sysDriverId);
    }


    @Override
    public SysDriver queryDriverByPK(String sysDriverId) throws Exception {
        SysDriver sysDriver =  sysDriverMapper.selectByPrimaryKey(sysDriverId);
        return sysDriver;
    }

    
    /**
	 * 给司机充钱
	 * @param order
	 * @return
     * @throws Exception 
	 */
	public String chargeCashToDriver(SysOrder order) throws Exception{
		if (order ==null){
			   return GlobalConstant.OrderProcessResult.ORDER_IS_NULL;
		}
		
		String debit_account = order.getDebitAccount();
		if(debit_account==null ||debit_account.equalsIgnoreCase("")){
			return GlobalConstant.OrderProcessResult.DEBIT_ACCOUNT_IS_NULL;
		}
		
		//给账户充钱
		SysDriver driver = this.queryDriverByPK(debit_account);
		String driver_account = driver.getsysUserAccountId();
		SysUserAccount sysUserAccount = sysUserAccountMapper.selectByPrimaryKey(driver_account);
		BigDecimal cash = order.getCash();
		BigDecimal balance = new BigDecimal(sysUserAccount.getAccountBalance()) ;
		//在此增加金额，如果是负值则是充红,仍然用add。
		BigDecimal balance_result = balance.add(cash);
		sysUserAccount.setAccountBalance(balance_result.toPlainString());
		sysUserAccount.setUpdatedDate(new Date());
		//更新此account对象则保存到db中
		sysUserAccountMapper.updateAccount(sysUserAccount);
		
		//记录订单流水
		orderDealService.createOrderDeal(order, GlobalConstant.OrderDealType.CHARGE_TO_DRIVER_CHARGE, GlobalConstant.OrderProcessResult.SUCCESS);
		
		return GlobalConstant.OrderProcessResult.SUCCESS;
	}

	/**
	 * 给司机返现
	 * @param order
	 * @return
	 */
	public String cashBackToDriver(SysOrder order) throws Exception{
		//TODO
		return GlobalConstant.OrderProcessResult.SUCCESS;
	}
	
	public static void main(String[] args) {

	      // create 3 BigDecimal objects
	      BigDecimal bg1,bg2,bg3,result1,result2;

	      // assign value to bg1 and bg2
	      bg1 = new BigDecimal("40.55");
	      bg2 = new BigDecimal("30.33");
	      bg3 = new BigDecimal("-30.22");

	      // print bg1 and bg2 value
	      System.out.println("bg1 Value is " + bg1);
	      System.out.println("bg2 value is " + bg2);
	      System.out.println("bg3 value is " + bg3);

	      // perform add operation on bg1 with augend bg2
	      result1=bg1.add(bg2);
	      result2=bg1.add(bg3);

	      // print bg3 value
	      System.out.println("Result1 is " + result1);
	      System.out.println("result2 is " + result2);
	      
	      System.out.println("Result1 toPlainString " + result1.toPlainString());
	      System.out.println("result2 toPlainString " + result2.toPlainString());
	   }
	
}
