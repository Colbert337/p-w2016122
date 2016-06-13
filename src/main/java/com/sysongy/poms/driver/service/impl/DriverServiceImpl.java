package com.sysongy.poms.driver.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.driver.dao.SysDriverMapper;
import com.sysongy.poms.driver.model.SysDriver;
import com.sysongy.poms.driver.service.DriverService;
import com.sysongy.poms.gastation.dao.GastationMapper;
import com.sysongy.poms.gastation.model.Gastation;
import com.sysongy.util.GlobalConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/6/7.
 */

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private SysDriverMapper sysDriverMapper;

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

}
