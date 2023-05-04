package cn.tycoding.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static Date offsetDay(Date day, Integer offset){
        Calendar calendar = Calendar.getInstance();//new一个Calendar类,把Date放进去
        return addAndSubtractDaysByGetTime(day,offset);
    }
    public static Date addAndSubtractDaysByGetTime(Date dateTime/*待处理的日期*/,int n/*加减天数*/){

        //日期格式
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println(df.format(new Date(dateTime.getTime() + n * 24 * 60 * 60 * 1000L)));
        //System.out.println(dd.format(new Date(dateTime.getTime() + n * 24 * 60 * 60 * 1000L)));
        //注意这里一定要转换成Long类型，要不n超过25时会出现范围溢出，从而得不到想要的日期值
        return new Date(dateTime.getTime() + n * 24 * 60 * 60 * 1000L);
    }
}


