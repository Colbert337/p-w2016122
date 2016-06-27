package com.sysongy.core.interceptors;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * Created by Administrator on 2016/6/1.
 */
public class DateWebBinding  implements WebBindingInitializer {

    @Override
    public void initBinder(WebDataBinder binder, WebRequest request) {
        // 1. 使用spring自带的CustomDateEditor
        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // binder.registerCustomEditor(Date.class, new
        // CustomDateEditor(dateFormat, true));
        //2. 自定义的PropertyEditorSupport
        binder.registerCustomEditor(Date.class, new DateConvertEditor());
    }

}
