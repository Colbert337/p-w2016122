package com.sysongy.poms.gastation.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.sysongy.poms.gastation.service.GastationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.gastation.dao.GastationMapper;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.util.GlobalConstant;

@Service
public class GastationServiceImpl implements GastationService{
	
	@Autowired
	private GastationMapper gasStationMapper;
	
	@Override
	public PageInfo<Gastation> queryGastation(Gastation record) throws Exception {
		
		PageHelper.startPage(record.getPageNum(), record.getPageSize(), record.getOrderby());
		List<Gastation> list = gasStationMapper.queryForPage(record);
		PageInfo<Gastation> pageInfo = new PageInfo<Gastation>(list);
		
		return pageInfo;
	}

	@Override
	public Integer saveGastation(Gastation record, String operation) throws Exception {
		if("insert".equals(operation)){
			Gastation station = gasStationMapper.findGastationid(record.getProvince_id());
			String newid;
			
			if(station == null || StringUtils.isEmpty(station.getSys_gas_station_id())){
				newid = record.getProvince_id() + "0001";
			}else{
				Integer tmp = Integer.valueOf(station.getSys_gas_station_id().substring(3, 7)) + 1;
				newid = record.getProvince_id() +StringUtils.leftPad(tmp.toString() , 4, "0");
			}
			record.setStatus(GlobalConstant.GastationStatus.USED);
			record.setCreated_time(new Date());
			record.setExpiry_date(new SimpleDateFormat("yyyy-MM-dd").parse(record.getExpiry_date_frompage()));
			record.setSys_gas_station_id(newid);
			return gasStationMapper.insert(record);
		}else{
			record.setExpiry_date(new SimpleDateFormat("yyyy-MM-dd").parse(record.getExpiry_date_frompage()));
			return gasStationMapper.updateByPrimaryKeySelective(record);
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
		 return station;
	}

}
