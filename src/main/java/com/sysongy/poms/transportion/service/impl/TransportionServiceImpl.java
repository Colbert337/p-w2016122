package com.sysongy.poms.transportion.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.permi.dao.SysUserAccountMapper;
import com.sysongy.poms.permi.model.SysUser;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.poms.permi.service.SysUserService;
import com.sysongy.poms.transportion.dao.TransportionMapper;
import com.sysongy.poms.transportion.model.Transportion;
import com.sysongy.poms.transportion.service.TransportionService;
import com.sysongy.poms.usysparam.model.Usysparam;
import com.sysongy.poms.usysparam.service.UsysparamService;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.PropertyUtil;
import com.sysongy.util.UUIDGenerator;

@Service
public class TransportionServiceImpl implements TransportionService {
	
	@Autowired
	private TransportionMapper transportionMapper;
	@Autowired
	private SysUserAccountMapper sysUserAccountMapper;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private UsysparamService usysparamService;
	
	@Override
	public PageInfo<Transportion> queryTransportion(Transportion record) throws Exception {
		
		PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
		List<Transportion> list = transportionMapper.queryForPage(record);
		PageInfo<Transportion> pageInfo = new PageInfo<Transportion>(list);
		
		return pageInfo;
	}

	@Override
	public String saveTransportion(Transportion record, String operation) throws Exception {
		if("insert".equals(operation)){
			Transportion station = transportionMapper.findTransportationid("t"+record.getProvince_id());
			String newid;
			
			if(station == null || StringUtils.isEmpty(station.getSys_transportion_id())){
				newid = "t"+record.getProvince_id() + "0001";
			}else{
				Integer tmp = Integer.valueOf(station.getSys_transportion_id().substring(4, 8)) + 1;
				newid = "t"+record.getProvince_id() +StringUtils.leftPad(tmp.toString() , 4, "0");
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
			
			Properties prop = PropertyUtil.read(GlobalConstant.CONF_PATH);
			String show_path = (String) prop.get("default_img");
			record.setSys_user_account_id(sysUserAccount.getSysUserAccountId());
			record.setStatus(GlobalConstant.GastationStatus.USED);
			record.setCreated_time(new Date());
			record.setExpiry_date(new SimpleDateFormat("yyyy-MM-dd").parse(record.getExpiry_date_frompage()));
			record.setSys_transportion_id(newid);
			record.setIndu_com_certif(show_path);
			record.setTax_certif(show_path);
			record.setLng_certif(show_path);
			record.setDcp_certif(show_path);
			
			transportionMapper.insert(record);
			//创建管理员
			SysUser user = new SysUser();
			user.setUserName(record.getAdmin_username());
			user.setPassword(record.getAdmin_userpassword());
			user.setUserType(GlobalConstant.USER_TYPE_TRANSPORT);
			sysUserService.addAdminUser(user);
			//同步系统参数字典表
			Usysparam usysparam = new Usysparam();
			usysparam.setGcode("TRANSTION");
			usysparam.setMcode(record.getSys_transportion_id());
			usysparam.setMname(record.getTransportion_name());
			usysparam.setScode("");
			usysparamService.saveUsysparam(usysparam);
			
			return newid;
		}else{
			if(!StringUtils.isEmpty(record.getExpiry_date_frompage())){
				record.setExpiry_date(new SimpleDateFormat("yyyy-MM-dd").parse(record.getExpiry_date_frompage()));
			}
			record.setUpdated_time(new Date());
			transportionMapper.updateByPrimaryKeySelective(record);
			//更新系统参数字典表
			Usysparam usysparam = new Usysparam();
			usysparam.setGcode("TRANSTION");
			usysparam.setMcode(record.getSys_transportion_id());
			usysparam.setMname(record.getTransportion_name());
			if(StringUtils.isEmpty(record.getTransportion_name())){
				Transportion tmp = transportionMapper.selectByPrimaryKey(record.getSys_transportion_id());
				usysparam.setMname(tmp.getTransportion_name());
			}
			usysparam.setScode("");
			usysparamService.updateUsysparam(usysparam);
			return record.getSys_transportion_id();
		}
	}

	@Override
	public Integer delTransportion(String transportionid) throws Exception {
		Transportion transportion = transportionMapper.selectByPrimaryKey(transportionid);
		//删除对应的管理员用户
		SysUser user = new SysUser();
		user.setUserName(transportion.getAdmin_username());
		user.setUserType(GlobalConstant.USER_TYPE_TRANSPORT);
		user.setStatus(GlobalConstant.STATUS_DELETE);
		sysUserService.updateUserByName(user);
		//删除对应的系统参数字典表
		Usysparam usysparam = new Usysparam();
		usysparam.setGcode("WORKSTATION");
		usysparam.setMcode(transportion.getTransportion_name());
		usysparamService.deleteUsysparam(usysparam);
		
		return transportionMapper.deleteByPrimaryKey(transportionid);
	}

	@Override
	public List<Transportion> getAllTransportionByArea(String areacode) throws Exception {
//		return transportionMapper.getAllStationByArea(areacode);
		return null;
	}

	@Override
	public Transportion queryTransportionByPK(String transportionid) throws Exception {
		Transportion station =  transportionMapper.selectByPrimaryKey(transportionid);
		 station.setExpiry_date_frompage(new SimpleDateFormat("yyyy-MM-dd").format(station.getExpiry_date()));
		 return station;
	}

}
