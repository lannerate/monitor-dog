package com.heagle.monitor.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghui
 * Date: 14-5-20
 * Time: 下午2:40
 * To change this template use File | Settings | File Templates.
 */
public class DateUtil {
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);



    public static Date getTodayDay(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String strDay = format.format(new Date());
        Date curtime = null;
        try {
            curtime = format.parse(strDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return curtime;
    }


    public static Date getBeforeDayNow(int day){
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.DATE,day);
        return nowTime.getTime();
    }

    /**
     * 当前几小时之前或之后
     * @param amount
     * @return
     */
    public static Date getBeforeHourNow(int amount){
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.HOUR_OF_DAY,amount);
        return nowTime.getTime();
    }

    /**
     * 当前几分钟之前或之后
     * @param amount
     * @return
     */
    public static Date getBeforeMinNow(int amount){
      Calendar nowTime = Calendar.getInstance();
      nowTime.add(Calendar.MINUTE,amount);
      return nowTime.getTime();
    }

    public static Date getNowTime(){
       Calendar nowTime = Calendar.getInstance();
       return nowTime.getTime();
    }

    /**
     * 当前几秒之前或之后
     * <pre>
     *     例如：
     *      getBeforeSecondNon(-5) // before 5 second
     *      getBeforeSecondNon(5) // after 5 second
     * </pre>
     * @param amount
     * @return
     */
    public static Date getBeforeSecondNow(int amount){
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.SECOND,amount);
        return nowTime.getTime();
    }

    /**
     * 格式化日期
     * <pre>
     *     例如：
     *      DateUtil.formatDate2String(DateUtil.getBeforeHourNow(-5),"yyyy-MM-dd HH:mm:ss")
     * </pre>
     * @param date
     * @param format
     * @return
     */
    public static String formatDate2String(Date date,String format){
        if(format ==null || "".equals(format)){
            format = "yyyy-MM-dd HH:mm:ss";
        }
       return new SimpleDateFormat(format).format(date);
    }


    public static Date formatDate(Date time,String format){
        if(format ==null || "".equals(format)){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat ft =  new SimpleDateFormat(format);
        ft.format(time);
       return time;
    }

    public static Date formatString2Date(String dateStr,String format){
        try {
            return new SimpleDateFormat(format).parse(dateStr);
        } catch (ParseException e) {
            logger.error("String convert Date error:"+e);
            return null;
        }
    }

    public  static void  main(String[] args){
        System.out.println(DateUtil.formatDate(DateUtil.getBeforeSecondNow(-5),null));
        System.out.println(DateUtil.getBeforeMinNow(-5));
        System.out.println(DateUtil.getBeforeHourNow(-5));
        System.out.println(DateUtil.formatDate2String(DateUtil.getBeforeHourNow(-5),"yyyy-MM-dd HH:mm:ss"));
        System.out.println(DateUtil.formatString2Date("2014-05-20 14:00:00","yyyy-MM-dd HH:mm:ss"));

        System.out.println(getTodayDay().toString());
    }
}
