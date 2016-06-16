package com.sysongy.poms.gastation.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.gastation.dao.GastationMapper;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.service.GastationService;
import com.sysongy.poms.permi.dao.SysUserAccountMapper;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.UUIDGenerator;

@Service
public class GastationServiceImpl implements GastationService {
	
	@Autowired
	private GastationMapper gasStationMapper;
	@Autowired
	private SysUserAccountMapper sysUserAccountMapper;
	
	@Override
	public PageInfo<Gastation> queryGastation(Gastation record) throws Exception {
		
		PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
		List<Gastation> list = gasStationMapper.queryForPage(record);
		PageInfo<Gastation> pageInfo = new PageInfo<Gastation>(list);
		
		return pageInfo;
	}

	@Override
	public String saveGastation(Gastation record, String operation) throws Exception {
		if("insert".equals(operation)){
			Gastation station = gasStationMapper.findGastationid("s"+record.getProvince_id());
			String newid;
			
			if(station == null || StringUtils.isEmpty(station.getSys_gas_station_id())){
				newid = "s"+record.getProvince_id() + "0001";
			}else{
				Integer tmp = Integer.valueOf(station.getSys_gas_station_id().substring(4, 8)) + 1;
				newid = "s"+record.getProvince_id() +StringUtils.leftPad(tmp.toString() , 4, "0");
			}
			//初始化钱袋信息
			SysUserAccount sysUserAccount = new SysUserAccount();
			sysUserAccount.setSysUserAccountId(UUIDGenerator.getUUID());
			sysUserAccount.setAccountCode("GS"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
			sysUserAccount.setAccountType(GlobalConstant.AccounType.GASTATION);
			sysUserAccount.setAccountBalance("0.0");
			sysUserAccount.setCreatedDate(new Date());
			sysUserAccount.setUpdatedDate(new Date());
			int ret = sysUserAccountMapper.insert(sysUserAccount);
			if(ret != 1){
				throw new Exception("钱袋初始化失败");
			}
			
			record.setSys_user_account_id(sysUserAccount.getSysUserAccountId());
			record.setStatus(GlobalConstant.GastationStatus.USED);
			record.setCreated_time(new Date());
			record.setExpiry_date(new SimpleDateFormat("yyyy-MM-dd").parse(record.getExpiry_date_frompage()));
			record.setSys_gas_station_id(newid);
			gasStationMapper.insert(record);
			
			return newid;
		}else{
			if(!StringUtils.isEmpty(record.getExpiry_date_frompage())){
				record.setExpiry_date(new SimpleDateFormat("yyyy-MM-dd").parse(record.getExpiry_date_frompage()));
			}
			record.setUpdated_time(new Date());
			gasStationMapper.updateByPrimaryKeySelective(record);
			return record.getSys_gas_station_id();
		}
	}

	@Override
	public Integer delGastation(String gastation) throws Exception {
		return gasStationMapper.deleteByPrimaryKey(gastation);
	}

	@Override
	public List<Gastation> getAllStationByArea(String areacode) throws Exception {
		return gasStationMapper.getAllStationByArea(areacode);
	}

	@Override
	public Gastation queryGastationByPK(String gastationid) throws Exception {
		 Gastation station =  gasStationMapper.selectByPrimaryKey(gastationid);
		 station.setExpiry_date_frompage(new SimpleDateFormat("yyyy-MM-dd").format(station.getExpiry_date()));
		 SysUserAccount account = sysUserAccountMapper.selectByPrimaryKey(station.getSys_user_account_id());
		 station.setAccount(account);
		 return station;
	}

}
