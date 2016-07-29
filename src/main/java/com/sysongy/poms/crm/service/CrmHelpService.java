package com.sysongy.poms.crm.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.sysongy.poms.crm.model.CrmHelp;


/**
 * @FileName: CrmHelpService
 * @Encoding: UTF-8
 * @Package: com.sysongy.crm.help.service
 * @Link: http://www.sysongy.com
 * @Created on: 2016年07月25日, 10:24
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
public interface CrmHelpService {
	
	public List<CrmHelp> queryCrmHelpServiceList(CrmHelp obj) throws Exception;//列表
	
	public void delete(String crmHelpId)throws Exception;//删除
	
    public Integer update(CrmHelp obj) throws Exception;//更新
    
    public CrmHelp queryCrmHelp(String crmHelpId) throws Exception;//编辑回显信息
    
    public Integer save(CrmHelp obj)throws Exception;//保存
    
    public PageInfo<CrmHelp> queryCrmHelpPage(CrmHelp obj) throws Exception;//分页
    
    public List<CrmHelp> queryQuestionListById(CrmHelp obj) throws Exception;//查询问题类型信息
    
    //public List<CrmHelp> queryCrmHelpNotice(String selectval)throws Exception;//公告查询

    /**
     * 查询问题列表
     * @param obj
     * @return
     * @throws Exception
     */
    public List<CrmHelp> queryQuestionListByName(CrmHelp obj) throws Exception;

}
