package com.sysongy.poms.gastation.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.gastation.dao.GastationMapper;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.service.GastationService;
import com.sysongy.poms.permi.dao.SysUserAccountMapper;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.poms.permi.service.SysUserService;
import com.sysongy.poms.system.dao.SysDepositLogMapper;
import com.sysongy.poms.system.model.SysDepositLog;
import com.sysongy.poms.usysparam.model.Usysparam;
import com.sysongy.poms.usysparam.service.UsysparamService;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.PropertyUtil;
import com.sysongy.util.UUIDGenerator;

@Service
public class GastationServiceImpl implements GastationService {
	
	@Autowired
	private GastationMapper gasStationMapper;
	@Autowired
	private SysUserAccountMapper sysUserAccountMapper;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private UsysparamService usysparamService;
	@Autowired
	private SysDepositLogMapper sysDepositLogMapper;
	
	
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
			Gastation station = gasStationMapper.findGastationid("GS"+record.getStation_level()+record.getProvince_id());
			String newid;
			
			if(station == null || StringUtils.isEmpty(station.getSys_gas_station_id())){
				newid = "GS"+record.getStation_level()+record.getProvince_id() + "0001";
			}else{
				Integer tmp = Integer.valueOf(station.getSys_gas_station_id().substring(6, 10)) + 1;
				newid = "GS"+record.getStation_level()+record.getProvince_id() +StringUtils.leftPad(tmp.toString() , 4, "0");
			}
			//初始化钱袋信息
			SysUserAccount sysUserAccount = new SysUserAccount();
			sysUserAccount.setSysUserAccountId(UUIDGenerator.getUUID());
			sysUserAccount.setAccountCode("GS"+new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
			sysUserAccount.setAccountType(GlobalConstant.AccounType.GASTATION);
			sysUserAccount.setAccountBalance("0.0");
			sysUserAccount.setCreatedDate(new Date());
			sysUserAccount.setUpdatedDate(new Date());
			sysUserAccount.setAccount_status(GlobalConstant.AccountStatus.NORMAL);
			int ret = sysUserAccountMapper.insert(sysUserAccount);
			if(ret != 1){
				throw new Exception("钱袋初始化失败");
			}
			
			Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);
			String show_path = (String) prop.get("default_img");
			record.setSys_user_account_id(sysUserAccount.getSysUserAccountId());
			record.setStatus(GlobalConstant.StationStatus.USED);
			record.setCreated_time(new Date());
			record.setExpiry_date(new SimpleDateFormat("yyyy-MM-dd").parse(record.getExpiry_date_frompage()));
			record.setSys_gas_station_id(newid);
			record.setIndu_com_certif(show_path);
			record.setTax_certif(show_path);
			record.setLng_certif(show_path);
			record.setDcp_certif(show_path);
			
			gasStationMapper.insert(record);
			//创建管理员
			SysUser user = new SysUser();
			user.setUserName(record.getAdmin_username());
			user.setPassword(record.getAdmin_userpassword());
			user.setUserType(GlobalConstant.USER_TYPE_STATION);

			// 给用户关联站点编号
			user.setStationId(newid);
			user.setIsAdmin(0);//管理员
			sysUserService.addAdminUser(user);
			//同步系统参数字典表
			Usysparam usysparam = new Usysparam();
			usysparam.setGcode("WORKSTATION");
			usysparam.setMcode(record.getSys_gas_station_id());
			usysparam.setMname(record.getGas_station_name());
			usysparam.setScode("");
			usysparamService.saveUsysparam(usysparam);
			
			return newid;
		}else{
			if(!StringUtils.isEmpty(record.getExpiry_date_frompage())){
				record.setExpiry_date(new SimpleDateFormat("yyyy-MM-dd").parse(record.getExpiry_date_frompage()));
			}
			record.setUpdated_time(new Date());
			gasStationMapper.updateByPrimaryKeySelective(record);
			//更新系统参数字典表
			Usysparam usysparam = new Usysparam();
			usysparam.setGcode("WORKSTATION");
			usysparam.setMcode(record.getSys_gas_station_id());
			if(StringUtils.isEmpty(record.getGas_station_name())){
				Gastation tmp = gasStationMapper.selectByPrimaryKey(record.getSys_gas_station_id());
				usysparam.setMname(tmp.getGas_station_name());
			}else{
				usysparam.setMname(record.getGas_station_name());
			}
		
			usysparam.setScode("");
			usysparamService.updateUsysparam(usysparam);
			
			//维护系统参数表
			if(GlobalConstant.StationStatus.PAUSE.equals(record.getStatus())){
				usysparamService.deleteUsysparam(usysparam);
			}else{
				Usysparam tmp = usysparamService.queryUsysparamByCode("WORKSTATION", record.getSys_gas_station_id());
				if(tmp == null){
					usysparamService.saveUsysparam(usysparam);
				}
			}
			
			return record.getSys_gas_station_id();
		}
	}

	@Override
	public Integer delGastation(String gastationid) throws Exception {
		
		Gastation gastation = gasStationMapper.selectByPrimaryKey(gastationid);
		//删除对应的管理员用户
		SysUser user = new SysUser();
		user.setUserName(gastation.getAdmin_username());
		user.setUserType(GlobalConstant.USER_TYPE_STATION);
		user.setStatus(GlobalConstant.STATUS_DELETE);
		sysUserService.updateUserByName(user);
		//删除对应的系统参数字典表
		Usysparam usysparam = new Usysparam();
		usysparam.setGcode("WORKSTATION");
		usysparam.setMcode(gastation.getSys_gas_station_id());
		usysparamService.deleteUsysparam(usysparam);
		
		return gasStationMapper.deleteByPrimaryKey(gastationid);
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

	@Override
	public int updatedepositGastation(SysDepositLog log) throws Exception {
		SysUserAccount account = new SysUserAccount();
		account.setSysUserAccountId(log.getAccountId());
		account.setDeposit(log.getDeposit());
		int retbnum = sysUserAccountMapper.updateByPrimaryKeySelective(account);
		
		//写日志
		log.setOptime(new Date());
		log.setSysDepositLogId(UUIDGenerator.getUUID());
		sysDepositLogMapper.insert(log);
		return retbnum;
	}

}
