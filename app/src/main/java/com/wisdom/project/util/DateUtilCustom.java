package com.wisdom.project.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author HanXueFeng
 * @ProjectName project： Nhoa
 * @class package：com.wisdom.nhoa.util
 * @class describe：
 * @time 2018/8/14 9:29
 * @change
 */
public class DateUtilCustom {
    //计算给开始日期 结束日期 的间隔天数 yyyy-MM-dd hh:mm:ss
    public static final String DATE_FORMAT_NORMAL = "yyyy-MM-dd hh:mm";

    public static int getDayLength(String start_date, String end_date) throws Exception {
        Date fromDate = getStrToDate(start_date, DATE_FORMAT_NORMAL);  //开始日期
        Date toDate = getStrToDate(end_date, DATE_FORMAT_NORMAL); //结束日期
        long from = fromDate.getTime();

        long to = toDate.getTime();

        //一天等于多少毫秒：24*3600*1000
        int day = (int) ((to - from) / (24 * 60 * 60 * 1000));
        return day;

    }


    public static Date getStrToDate(String date, String fomtter) throws Exception {
        DateFormat df = new SimpleDateFormat(fomtter);
        return df.parse(date);
    }
}
