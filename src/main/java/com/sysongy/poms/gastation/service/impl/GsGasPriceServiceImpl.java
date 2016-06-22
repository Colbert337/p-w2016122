package com.sysongy.poms.gastation.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.gastation.dao.GastationMapper;
import com.sysongy.poms.gastation.dao.GsGasPriceMapper;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.poms.gastation.model.GsGasPrice;
import com.sysongy.poms.gastation.service.GsGasPriceService;

import com.sysongy.poms.permi.dao.SysUserAccountMapper;
import com.sysongy.poms.permi.model.SysUserAccount;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.UUIDGenerator;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class GsGasPriceServiceImpl implements GsGasPriceService {

	@Autowired
	private GsGasPriceMapper gsGasPriceMapper;

	@Override
	public PageInfo<GsGasPrice> queryGsPrice(GsGasPrice record) throws Exception {
		PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
		List<GsGasPrice> list = gsGasPriceMapper.queryForPage(record);
		PageInfo<GsGasPrice> pageInfo = new PageInfo<GsGasPrice>(list);
		return pageInfo;
	}

	@Override
	public GsGasPrice queryGsPriceByPK(String gsGasPriceID) throws Exception {
		GsGasPrice gsGasPrice =  gsGasPriceMapper.selectByPrimaryKey(gsGasPriceID);
		return gsGasPrice;
	}

	@Override
	public Integer saveGsPrice(GsGasPrice record, String operation) throws Exception {
		int ret = 0;
		if("insert".equals(operation)){
			record.setGsGasPriceId(UUIDGenerator.getUUID());
			record.setCreatedDate(new Date());
			record.setUpdatedDate(new Date());
			ret = gsGasPriceMapper.insert(record);
		}else{
			ret = gsGasPriceMapper.updateByPrimaryKeySelective(record);
		}
		return ret;
	}

	@Override
	public Integer delGsPrice(String gsPriceID) throws Exception {
		return gsGasPriceMapper.deleteByPrimaryKey(gsPriceID);
	}

	public Integer isExists(GsGasPrice obj) throws Exception {
		return gsGasPriceMapper.isExists(obj);
	}

}
