package com.sysongy.poms.crm.controller;

import com.sysongy.poms.base.controller.BaseContoller;
import com.sysongy.poms.card.model.GasCard;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @FileName: CrmHelpController
 * @Encoding: UTF-8
 * @Package: com.sysongy.crm.help.controller
 * @Link: http://www.sysongy.com
 * @Created on: 2016年07月25日, 10:24
 * @Author: dongqiang.wang [wdq_2012@126.com]
 * @Version: V2.0 Copyright(c)陕西司集能源科技有限公司
 * @Description:
 */
@RequestMapping("/web/crm/help")
@Controller
public class CrmHelpController extends BaseContoller{

    @RequestMapping("/list")
    public String queryAllCardList(ModelMap map, GasCard gascard) throws Exception{
        return "webpage/poms/crm/help_list";
    }
}
