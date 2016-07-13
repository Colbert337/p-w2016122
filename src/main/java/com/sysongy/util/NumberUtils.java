package com.sysongy.util;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016/7/13.
 */
public class NumberUtils {

    private static final String AUTO_INCREASE_FORMAT_STRING = "00000000";

    public static String convertFormatNumber(Integer nInput){
        DecimalFormat df = new DecimalFormat(AUTO_INCREASE_FORMAT_STRING);
        return "P" + df.format(nInput);
    }

}
