package com.sysongy.tcms.advance.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.tcms.advance.dao.TcFleetQuotaMapper;
import com.sysongy.tcms.advance.model.TcFleetQuota;
import com.sysongy.tcms.advance.service.TcFleetQuotaService;
import com.sysongy.util.GlobalConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @FileName: TcFleetQuotaServiceImpl
 * @Encoding: UTF-8
 * @Package: com.sysongy.tcms.advance.service.impl
 * @Link: http://www.sysongy.com
 * @Created on: 2016年06月22日, 12:10
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
@Service
public class TcFleetQuotaServiceImpl implements TcFleetQuotaService{
    @Autowired
    TcFleetQuotaMapper tcFleetQuotaMapper;

    @Override
    public TcFleetQuota queryFleetQuota(TcFleetQuota tcFleetQuota) {
        if(tcFleetQuota != null){
            return tcFleetQuotaMapper.queryFleetQuota(tcFleetQuota);
        }else{
            return null;
        }

    }

    @Override
    public PageInfo<TcFleetQuota> queryFleetQuotaList(TcFleetQuota tcFleetQuota) {
        if(tcFleetQuota != null){
            PageHelper.startPage(GlobalConstant.PAGE_NUM,GlobalConstant.PAGE_SIZE);

            List<TcFleetQuota> FleetQuotaList = tcFleetQuotaMapper.queryFleetQuotaList(tcFleetQuota);
            PageInfo<TcFleetQuota> FleetQuotaPageInfo = new PageInfo<>(FleetQuotaList);
            return FleetQuotaPageInfo;
        }else{
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> queryFleetQuotaMapList(TcFleetQuota tcFleetQuota) {
        if(tcFleetQuota != null){
            List<Map<String, Object>> fleetQuotaList = tcFleetQuotaMapper.queryFleetQuotaMapList(tcFleetQuota);
            return fleetQuotaList;
        }else{
            return null;
        }
    }

    @Override
    public int addFleetQuota(TcFleetQuota tcFleetQuota) {
        if(tcFleetQuota != null){
            return tcFleetQuotaMapper.addFleetQuota(tcFleetQuota);
        }else{
            return 0;
        }
    }

    @Override
    public int deleteFleetQuota(TcFleetQuota tcFleetQuota) {
        if(tcFleetQuota != null){
            return tcFleetQuotaMapper.deleteFleetQuota(tcFleetQuota);
        }else{
            return 0;
        }
    }

    @Override
    public int updateFleetQuota(TcFleetQuota tcFleetQuota) {
        if(tcFleetQuota != null){
            return tcFleetQuotaMapper.updateFleetQuota(tcFleetQuota);
        }else{
            return 0;
        }
    }
}
