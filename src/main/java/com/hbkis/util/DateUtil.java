/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hbkis.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @FileName     :  DateUtil.java
 * @Encoding     :  UTF-8
 * @Package      :  com.hbkis.base.controller
 * @Link         :  http://www.hbkis.com
 * @Created on   :  2015年10月20日, 下午5:53:21
 * @Author       :  Dongqiang.Wang [wdq_2012@126.com]
 * @Copyright    :  Copyright(c) 2015 西安海贝信息科技有限公司
 * @Description  :	日期函数
 *
 */
public class DateUtil {

    private Calendar calendar;
    private final DateFormat df_date = DateFormat.getDateInstance();
    private final DateFormat df_time = DateFormat.getTimeInstance();
    private final String[] WEEKNAME_CHINESE = new String[]{"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
    private final String[] WEEKNAME_ENGLISH_LONG = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private final String[] WEEKNAME_ENGLISH_SHORT = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
    private final String[] MONTHNAME_ENGLISH_LONG = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private final String[] MONTHNAME_ENGLISH_SHORT = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    public final static int YEAR = Calendar.YEAR;
    public final static int MONTH = Calendar.MONTH;
    public final static int DAY = Calendar.DAY_OF_MONTH;
    public final static int HOUR = Calendar.HOUR_OF_DAY;
    public final static int MINUTE = Calendar.MINUTE;
    public final static int SECOND = Calendar.SECOND;

    /**
     * 构造函数.
     */
    public DateUtil() {
        calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
    }

    /**
     * 构造函数.
     *
     * @param calendar Calendar 实例.
     */
    public DateUtil(Calendar calendar) {
        this.calendar = calendar;
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
    }

    /**
     * 构造函数.
     *
     * @param strDate 字符串形式的日期.
     */
    public DateUtil(String strDate) {
        try {
            this.calendar = Calendar.getInstance();
            this.calendar.setTime(DateFormat.getDateTimeInstance().parse(strDate));
            calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        } catch (Exception e) {
            System.out.println("转换日期出错!");
        }

    }

    /**
     * 设置 Calendar 实例.
     *
     * @param calendar Calendar 实例.
     */
    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
    }

    /**
     * 获得日期.
     *
     * @return 日期. 格式 "2005-4-11"
     */
    public String getDate() {
        String theResult = "";
        theResult = getDate(false);
        return theResult;
    }

    /**
     * 获得日期. fixupLength 为 true 时,日期格式为: 2005-03-01 fixupLength 为 false 时,日期格式为:
     * 2005-3-1
     *
     * @param fixupLength 日期格式. fixupLength 为 true 时,日期格式为: 2005-03-01
     * fixupLength 为 false 时,日期格式为: 2005-3-1
     * @return 日期. 格式 "2005-04-11"
     */
    public String getDate(boolean fixLength) {
        String theResult = "";
        if (fixLength) {
            String strMonth = String.valueOf(this.getMonth());
            String strDay = String.valueOf(this.getDay());
            theResult = this.getYear()
                    + "-"
                    + (strMonth.length() < 2 ? ("0" + strMonth) : strMonth)
                    + "-"
                    + (strDay.length() < 2 ? ("0" + strDay) : strDay);
        } else {
            theResult = df_date.format(calendar.getTime());
        }
        return theResult;
    }

    /**
     * 年.
     *
     * @return 年.
     */
    public int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 月.
     *
     * @return 月.
     */
    public int getMonth() {
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 月份的英语完整表述.
     *
     * @return 月份.
     */
    public String getMonthForEnglishLong() {
        return MONTHNAME_ENGLISH_LONG[this.getMonth() - 1];
    }

    /**
     * 月份的英语简短表述.
     *
     * @return 月份.
     */
    public String getMonthForEnglishShort() {
        return MONTHNAME_ENGLISH_SHORT[this.getMonth() - 1];
    }

    /**
     * 月份的第几天.
     *
     * @return 月份的第几天.
     */
    public int getDay() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 星期几, 数字表述.
     *
     * @return 星期几.
     */
    public int getWeek() {
        int theResult;
        theResult = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        theResult = theResult == 0 ? 7 : theResult;
        return theResult;
    }

    /**
     * 星期几, 中文表述.
     *
     * @return 星期几, 中文表述.
     */
    public String getWeekForChinese() {
        return WEEKNAME_CHINESE[this.getWeek() - 1];
    }

    /**
     * 星期几, 英语的完整表述.
     *
     * @return 星期几, 英语的完整表述.
     */
    public String getWeekForEnglishLong() {
        return WEEKNAME_ENGLISH_LONG[this.getWeek() - 1];
    }

    /**
     * 星期几, 英语的简短表述.
     *
     * @return 星期几, 英语的简短表述.
     */
    public String getWeekForEnglishShort() {
        return WEEKNAME_ENGLISH_SHORT[this.getWeek() - 1];
    }

    /**
     * 时间. 格式: 8:23:25.
     *
     * @return 时间.
     */
    public String getTime() {
        //String theResult = "";
        //theResult = df_time.format(calendar.getTime());
        return getTime(false);
    }

    /**
     * 时间. 格式:08:23:25.
     *
     * @param fixLength
     * @return
     */
    public String getTime(boolean fixLength) {
        String theResult = "";
        if (fixLength) {
            String strHour = String.valueOf(this.getHour());
            String strMinute = String.valueOf(this.getMinute());
            String strSecond = String.valueOf(this.getSecond());
            theResult = (strHour.length() < 2 ? ("0" + strHour) : strHour)
                    + ":"
                    + (strMinute.length() < 2 ? ("0" + strMinute) : strMinute)
                    + ":"
                    + (strSecond.length() < 2 ? ("0" + strSecond) : strSecond);
        } else {
            theResult = df_time.format(calendar.getTime());
        }
        return theResult;
    }

    /**
     * 小时. 24 小时制.
     *
     * @return 小时.
     */
    public int getHour() {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 分钟.
     *
     * @return 分钟.
     */
    public int getMinute() {
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 秒数.
     *
     * @return 秒数.
     */
    public int getSecond() {
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 日期时间. 格式"2005-04-11 17:37:55"
     *
     * @return 日期时间. 格式"2005-04-11 17:37:55"
     */
    public String getDateTime() {
        return this.getDate(true) + " " + this.getTime(true);
    }

    /**
     * 获得日期对象的毫秒数.
     *
     * @return 日期对象的毫秒数.
     */
    public long getMillionSecone() {
        return this.calendar.getTime().getTime();
    }

    /**
     * 获得日期对象.
     *
     * @return 日期对象.
     */
    public Date getDateObject() {
        return this.calendar.getTime();
    }

    /**
     * 日期加减.
     *
     * @param field 段
     * @param amount 值.
     */
    public void add(int field, int amount) {
        switch (field) {
            case YEAR:
                calendar.add(YEAR, amount);
                break;
            case MONTH:
                calendar.add(MONTH, amount);
                break;
            case DAY:
                calendar.add(DAY, amount);
                break;
            case HOUR:
                calendar.add(HOUR, amount);
                break;
            case MINUTE:
                calendar.add(MINUTE, amount);
                break;
            case SECOND:
                calendar.add(SECOND, amount);
                break;
        }
    }

    /**
     * 字符串转日期
     *
     * @param str 需要转为日期的字符串
     * @param fomatString 日期格式
     * @return
     * @Author 王冬强 20131023
     */
    public static Date strToDate(String str, String fomatString) {
        SimpleDateFormat format = new SimpleDateFormat(fomatString);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException ex) {
            Logger.getLogger(DateUtil.class.getName()).log(Level.ALL, null, ex);
        }
        return date;
    }

    /**
     * 日期转转字符串
     *
     * @param time 需要转为字符串的日期
     * @param fomatString 日期格式
     * @return
     * @Author 王冬强 20131023
     */
    public static String dateTostr(Date time, String fomatString) {
        SimpleDateFormat format = new SimpleDateFormat(fomatString);
        String str = format.format(time);
        return str;
    }

    public static void main(String[] args) {
//    	  String s = DateUtil.getStrOfTime(100000);
            
//        DateUtil du = new DateUtil("2008-01-01 01:00:00");
//        System.out.println(du.getDate(true));
//        System.out.println(du.getTime());
//        du.add(DateUtil.MINUTE, -3);
//
//        System.out.println(du.getMillionSecone());
//        System.out.println(du.getMillionSecone());
        String s1 = "2013-11-19";
        String s2 = "2013-11-19 ";
        Logger log = Logger.getLogger("kk");
        System.out.println("fromart:" + String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL", new Date()));
        /*
         try
         {
         Thread.sleep(3000);
         }catch(Exception e){}
         du.setCalendar(Calendar.getInstance());
         System.out.println(du.getTime());
         Calendar calendar = Calendar.getInstance();
         calendar.setFirstDayOfWeek(2);
         System.out.println(calendar.getFirstDayOfWeek());

         du.add(du.HOUR, 2);
         System.out.println(du.getYear());
         System.out.println(du.getMonth());
         System.out.println(du.getDay());
         System.out.println(du.getWeek());
         System.out.println(du.getWeekForChinese());
         System.out.println(du.getWeekForEnglishShort());
         System.out.println(du.getWeekForEnglishLong());
         System.out.println(du.getMonthForEnglishLong());
         System.out.println(du.getMonthForEnglishShort());
         */
        Date date = null;
        date = strToDate("","");
    }
    
    
    /** 
     * 当前季度的开始时间，即2012-01-1 00:00:00 
     * 
     * @return 
     */ 
    public Date getCurrentQuarterStartTime() { 
    	SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    	SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance(); 
        int currentMonth = c.get(Calendar.MONTH) + 1; 
        Date now = null; 
        try { 
            if (currentMonth >= 1 && currentMonth <= 3) 
                c.set(Calendar.MONTH, 0); 
            else if (currentMonth >= 4 && currentMonth <= 6) 
                c.set(Calendar.MONTH, 3); 
            else if (currentMonth >= 7 && currentMonth <= 9) 
                c.set(Calendar.MONTH, 6); 
            else if (currentMonth >= 10 && currentMonth <= 12) 
                c.set(Calendar.MONTH, 9); 
            c.set(Calendar.DATE, 1); 
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00"); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        return now; 
    } 
    
    /** 
     * 当前季度的结束时间，即2012-03-31 23:59:59 
     * 
     * @return 
     */ 
    public   Date getCurrentQuarterEndTime() { 
    	SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    	SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance(); 
        int currentMonth = c.get(Calendar.MONTH) + 1; 
        Date now = null; 
        try { 
            if (currentMonth >= 1 && currentMonth <= 3) { 
                c.set(Calendar.MONTH, 2); 
                c.set(Calendar.DATE, 31); 
            } else if (currentMonth >= 4 && currentMonth <= 6) { 
                c.set(Calendar.MONTH, 5); 
                c.set(Calendar.DATE, 30); 
            } else if (currentMonth >= 7 && currentMonth <= 9) { 
                c.set(Calendar.MONTH,8); 
                c.set(Calendar.DATE, 30); 
            } else if (currentMonth >= 10 && currentMonth <= 12) { 
                c.set(Calendar.MONTH, 11); 
                c.set(Calendar.DATE, 31); 
            } 
            now = longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59"); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        return now; 
    } 
    

    
    /** 
     * 取得月第一天 
     *  
     * @param date 
     * @return 
     */  
    public static Date getFirstDateOfMonth(Date date) {  
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));  
        return c.getTime();  
    } 
    
    /** 
     * 取得月最后一天 
     *  
     * @param date 
     * @return 
     */  
    public static Date getLastDateOfMonth(Date date) {  
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
        return c.getTime();  
    } 
    
    /** 
     * 取得季度第一天 
     *  
     * @param date 
     * @return 
     */  
    public static Date getFirstDateOfSeason(Date date) {  
        return getFirstDateOfMonth(getSeasonDate(date)[0]);  
    } 
    
    
    /** 
     * 取得季度最后一天 
     *  
     * @param date 
     * @return 
     */  
    public static Date getLastDateOfSeason(Date date) {  
        return getLastDateOfMonth(getSeasonDate(date)[2]);  
    }  
    
    /** 
     * 取得季度月 
     *  
     * @param date 
     * @return 
     */  
    public static Date[] getSeasonDate(Date date) {  
        Date[] season = new Date[3];  
  
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
  
        int nSeason = getSeason(date);  
        if (nSeason == 1) {// 第一季度  
            c.set(Calendar.MONTH, Calendar.JANUARY);  
            season[0] = c.getTime();  
            c.set(Calendar.MONTH, Calendar.FEBRUARY);  
            season[1] = c.getTime();  
            c.set(Calendar.MONTH, Calendar.MARCH);  
            season[2] = c.getTime();  
        } else if (nSeason == 2) {// 第二季度  
            c.set(Calendar.MONTH, Calendar.APRIL);  
            season[0] = c.getTime();  
            c.set(Calendar.MONTH, Calendar.MAY);  
            season[1] = c.getTime();  
            c.set(Calendar.MONTH, Calendar.JUNE);  
            season[2] = c.getTime();  
        } else if (nSeason == 3) {// 第三季度  
            c.set(Calendar.MONTH, Calendar.JULY);  
            season[0] = c.getTime();  
            c.set(Calendar.MONTH, Calendar.AUGUST);  
            season[1] = c.getTime();  
            c.set(Calendar.MONTH, Calendar.SEPTEMBER);  
            season[2] = c.getTime();  
        } else if (nSeason == 4) {// 第四季度  
            c.set(Calendar.MONTH, Calendar.OCTOBER);  
            season[0] = c.getTime();  
            c.set(Calendar.MONTH, Calendar.NOVEMBER);  
            season[1] = c.getTime();  
            c.set(Calendar.MONTH, Calendar.DECEMBER);  
            season[2] = c.getTime();  
        }  
        return season;  
    }  
  
    /** 
     *  
     * 1 第一季度 2 第二季度 3 第三季度 4 第四季度 
     *  
     * @param date 
     * @return 
     */  
    public static int getSeason(Date date) {  
  
        int season = 0;  
  
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
        int month = c.get(Calendar.MONTH);  
        switch (month) {  
        case Calendar.JANUARY:  
        case Calendar.FEBRUARY:  
        case Calendar.MARCH:  
            season = 1;  
            break;  
        case Calendar.APRIL:  
        case Calendar.MAY:  
        case Calendar.JUNE:  
            season = 2;  
            break;  
        case Calendar.JULY:  
        case Calendar.AUGUST:  
        case Calendar.SEPTEMBER:  
            season = 3;  
            break;  
        case Calendar.OCTOBER:  
        case Calendar.NOVEMBER:  
        case Calendar.DECEMBER:  
            season = 4;  
            break;  
        default:  
            break;  
        }  
        return season;  
    }  

}
