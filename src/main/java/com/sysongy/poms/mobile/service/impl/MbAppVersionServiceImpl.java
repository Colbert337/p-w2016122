package com.sysongy.poms.mobile.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.mobile.dao.MbAppVersionMapper;
import com.sysongy.poms.mobile.dao.MbBannerMapper;
import com.sysongy.poms.mobile.model.MbAppVersion;
import com.sysongy.poms.mobile.model.MbBanner;
import com.sysongy.poms.mobile.service.MbAppVersionService;
import com.sysongy.poms.mobile.service.MbBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MbAppVersionServiceImpl implements MbAppVersionService {
    @Autowired
    MbAppVersionMapper mbAppVersionMapper;



    public   int deleteByPrimaryKey(String appVersionId){
        return mbAppVersionMapper.deleteByPrimaryKey(appVersionId);
    }

    public  int insert(MbAppVersion record){
        return mbAppVersionMapper.insert(record);
    }

    public  int insertSelective(MbAppVersion record){
        return  mbAppVersionMapper.insertSelective(record);
    }

    public  MbAppVersion selectByPrimaryKey(String appVersionId){
        return mbAppVersionMapper.selectByPrimaryKey(appVersionId);
    }

    public  int updateByPrimaryKeySelective(MbAppVersion record){
        return  mbAppVersionMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(MbAppVersion record){
        return  mbAppVersionMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageInfo<MbAppVersion> queryAppVersionListPage(MbAppVersion record)  throws Exception{

        if(record != null){
            PageHelper.startPage(record.getPageNum(),record.getPageSize(),record.getOrderby());
            List<MbAppVersion> MbAppVersionList = mbAppVersionMapper.queryAppVersionList(record);
            PageInfo<MbAppVersion> fleetPageInfo = new PageInfo<>(MbAppVersionList);
            return fleetPageInfo;
        }else{
            return null;
        }
    }


}
