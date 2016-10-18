package com.sysongy.poms.mobile.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sysongy.poms.mobile.dao.MbAppVersionMapper;
import com.sysongy.poms.mobile.dao.MbBannerMapper;
import com.sysongy.poms.mobile.model.MbAppVersion;
import com.sysongy.poms.mobile.model.MbBanner;
import com.sysongy.poms.mobile.service.MbAppVersionService;
import com.sysongy.poms.mobile.service.MbBannerService;
import com.sysongy.util.GlobalConstant;
import com.sysongy.util.PropertyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


@Service
public class MbAppVersionServiceImpl implements MbAppVersionService {
    @Autowired
    MbAppVersionMapper mbAppVersionMapper;
    String PATH = (String) PropertyUtil.read(GlobalConstant.CONF_PATH).get("http_poms_path");//域名端口


    public   int deleteByPrimaryKey(String appVersionId){
        return mbAppVersionMapper.deleteByPrimaryKey(appVersionId);
    }

    public  int insert(MbAppVersion record){
        record.setRemark(addStrN(record.getRemark()));
        return mbAppVersionMapper.insert(record);
    }

    public  int insertSelective(MbAppVersion record){
        return  mbAppVersionMapper.insertSelective(record);
    }

    public  MbAppVersion selectByPrimaryKey(String appVersionId){
        MbAppVersion  m= mbAppVersionMapper.selectByPrimaryKey(appVersionId);
        if(m==null){
            return  m;
        }
        m.setRemark(m.getRemark().replace('\n',' '));
        m.setUrl(PATH + m.getUrl());
        m.setCreatedDateStr(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(m.getCreatedDate()));
        return m;
    }

    public  int updateByPrimaryKeySelective(MbAppVersion record){
        return  mbAppVersionMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(MbAppVersion record) throws ParseException {
        record.setCreatedDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(record.getCreatedDateStr()));
        record.setRemark(addStrN(record.getRemark()));
        return  mbAppVersionMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageInfo<MbAppVersion> queryAppVersionListPage(MbAppVersion record)  throws Exception{

        if(record != null){
            PageHelper.startPage(record.getPageNum(),record.getPageSize(),record.getOrderby());
            List<MbAppVersion> MbAppVersionList = mbAppVersionMapper.queryAppVersionList(record);
            for(MbAppVersion mv:MbAppVersionList){
                mv.setUrl(PATH+mv.getUrl());
                mv.setCreatedDateStr(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(mv.getCreatedDate()));
                mv.setRemark(mv.getRemark().replace("\n",""));
            }
            PageInfo<MbAppVersion> fleetPageInfo = new PageInfo<>(MbAppVersionList);
            return fleetPageInfo;
        }else{
            return null;
        }
    }

	@Override
	public MbAppVersion queryNewest() {
		return mbAppVersionMapper.queryNewest();
	}
    /**
     * 给版本说明加上换行符，用于APP版本展示说明
     */
    public String addStrN(String remark){
        String s[]=remark.split("。");
        String str="";
        for(int i=0;i<s.length;i++){
            str=str+s[i]+"。\n";
        }
        return  str;
    }


}
