package com.emay.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author ThinkGem
 * @version 2013-3-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm","yyyy-MM-dd HH:mm:ss" };

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}
	
    
	public static Date getDateStart(Date date) {
		if(date==null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date= sdf.parse(formatDate(date, "yyyy-MM-dd")+" 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date getDateEnd(Date date) {
		if(date==null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date= sdf.parse(formatDate(date, "yyyy-MM-dd") +" 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	
	/**
     * 本周
     * @param dt
     * @return
     * @throws ParseException 
     */
    public static Map<String,Date> getMyCurrentWeek(Date dt) throws ParseException {
    	Map<String,Date>map=new HashMap<String, Date>();
    	map.put("endTime", dt);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)w = 0;
        if(w==0){
        	map.put("startTime", new Date(dt.getTime()-6*24*60*60*1000));
        }else if(w>0){
        	long time=parseDate(formatDate(new Date(dt.getTime()-(w-1)*24*60*60*1000))+" 00:00:00").getTime();
        	map.put("startTime", new Date(time));
        }
        return map;
    }
    
    /**
     * 当天
     * @param dt
     * @return
     * @throws ParseException 
     */
    public static Map<String,Date>getMyCurrentDay(Date dt) throws ParseException{
    	Map<String,Date>map=new HashMap<String, Date>();
    	long etime=parseDate(formatDate(dt,"yyyy-MM-dd")+" 23:59:59").getTime();
    	Date edate=new Date(etime);
    	map.put("endTime", edate);
    	long stime=parseDate(formatDate(dt,"yyyy-MM-dd")+" 00:00:00").getTime();
    	Date sdate=new Date(stime);
    	map.put("startTime", sdate);
    	return map;
    }
    
    /**
     * 当月
     * @param week
     * @return
     * @throws ParseException 
     */
    public static Map<String,Date>getMyCurrentMonth(Date dt) throws ParseException{
    	Map<String,Date>map=new HashMap<String, Date>();
    	map.put("endTime", dt);
    	long time=DateUtils.parseDate(formatDate(dt,"yyyy-MM")+"-01 00:00:00").getTime();
    	Date sdate=new Date(time);
    	map.put("startTime", sdate);
    	return map;
    }
    
    public static String getWeekSpit(String week){
    	String[] weekDays = {"星日", "周一", "周二", "周三", "周四", "周五", "周六"};
    	StringBuffer buff=new StringBuffer();
    	String weeks[] =week.split("-");
    	for(int i=0;i<weeks.length;i++){
    		Integer weekID=Integer.parseInt(weeks[i]);
    		buff.append(weekDays[weekID]);
    	}
    	return buff.toString();
    }
    
    /**
     * 计算指定日期为当年第几周
     * @param year		指定的年份
     * @param month		指定的月份
     * @param day		指定的日
     * @return			指定日期为当年的第几周
     */
    public static int caculateWeekOfYear(int year,int month,int day){
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.YEAR, year);
    	c.set(Calendar.MONTH, month - 1);
    	c.set(Calendar.DATE, day);
    	return c.get(Calendar.WEEK_OF_YEAR);
    }
    
    /**
     * 获取本月1号是该年的第几周
     * @return
     */
    public static int getMonthStartWeek(Calendar c){
    	Calendar calendar = c;
    	calendar.set(Calendar.DATE, 1);
    	return calendar.get(Calendar.WEEK_OF_YEAR);
    }
    
    /**
     * 获取当天是该年的第几周
     */
    public static int getCurrentWeekOfYear(){
    	Calendar calendar = Calendar.getInstance();
    	return calendar.get(Calendar.WEEK_OF_YEAR);
    }
    /**
     * 根据指定时间获取第几周
     */
    public static int getCurrentWeekOfYear(Date date){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	return calendar.get(Calendar.WEEK_OF_YEAR);
    }
    
    
    
    /**
     * 获取上月的总天数
     * @return
     */
    public static int getLastMonthDays(Calendar c){
    	Calendar calendar = c;
    	calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
    	return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 获取指定月份的总天数
     * @return
     */
    public static int getCurrentMonthDays(int month){
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.MONTH, month);
    	return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 获取指定年份有多少周
     * @param year
     * @return
     */
    public static int getTotalWeekOfYear(int year){
    	Calendar c = Calendar.getInstance();
    	return c.getActualMaximum(Calendar.WEEK_OF_YEAR);
    }
    
    /**
     * 判断指定月份是否是当前月
     * @param month
     * @return
     */
    public static boolean isCurrentMonth(int month){
 	Calendar c = Calendar.getInstance();
 	return (c.get(Calendar.MONTH) == month)?true:false;
 }
    
    /**
     * 计算指定的月份共有多少天
     */
    public static int getTotalDaysOfMonth(int year, int month){
    	Calendar c = Calendar.getInstance();
 	c.set(Calendar.YEAR, year);
 	c.set(Calendar.MONTH, month);
 	c.set(Calendar.DATE, 1);
 	/**
 	 * 计算这个月有多少天
 	 */
 	return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    
    
    /**
     * 得到本周周一
     * 
     * @return yyyy-MM-dd
     */
    public static String getMondayOfThisWeek() {
     Calendar c = Calendar.getInstance();
     int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
     if (day_of_week == 0)
      day_of_week = 7;
     c.add(Calendar.DATE, -day_of_week + 1);
     return DateFormatUtils.format(c, "yyyy-MM-dd");
    }
    
    public static Date getMondayOfThisWeekDate() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
         day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 1);
        c.getTime();
        return c.getTime();
     }
    
    /**
     * 根据指定时间来获取周一
     * @return
     */
    public static String  getAssignMondayOfThisWeek(Date date){
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
         day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 1);
        c.getTime();
        return DateFormatUtils.format(c, "yyyy-MM-dd");
    }
    public static Date  getAssignMondayOfThisWeekDate(Date date){
    	Calendar c = Calendar.getInstance();
//    	c.set(year, mounth, date);
    	c.setTime(date);
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
         day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 1);
        c.getTime();
        return c.getTime();
    }
    /**
     * 得到本周周日
     * 
     * @return yyyy-MM-dd
     */
    public static String getSundayOfThisWeek() {
     Calendar c = Calendar.getInstance();
     int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
     if (day_of_week == 0)
      day_of_week = 7;
     c.add(Calendar.DATE, -day_of_week + 7);
     return DateFormatUtils.format(c, "yyyy-MM-dd");
    }
    
    public static Date getSundayOfThisWeekDate() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
         day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 7);
        return c.getTime();
     }
    
    
    /**
     * 根据指定时间获取周日
     */
    public static String getAssignSundayOfThisWeek(Date date) {
        Calendar c = Calendar.getInstance();
//      c.set(year, mounth, date);
        c.setTime(date);
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
         day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 7);
        return DateFormatUtils.format(c, "yyyy-MM-dd");
       }
    public static Date  getAssignSundayOfThisDate(Date date){
    	Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
         day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 7);
        return c.getTime();
    }
    
    
    
    
    /**
     * 获取当前日期是星期几<br>
     * 
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    
    public static String getMyWeekOfDate(Date dt) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    /**
	 * 得到几天前的时间
	 */
	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}
	
	/**
	 * 得到几天后的时间
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}
    
	
	/**
	 * 获取本月第一天
	 * @return
	 */
	public static Date getMonthFirstDaty(){
		Calendar calendar=Calendar.getInstance();//获取当前日期 
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        return calendar.getTime();
	}
	
	/**
	 * 当月最后一天
	 * @return
	 */
	public static Date getMonthLastDay(){
		 Calendar lastDate = Calendar.getInstance();  
	     lastDate.set(Calendar.DATE, 1);// 设为当前月的1号  
	     lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号  
	     lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天  
	     return lastDate.getTime();
	}
	/**
	
	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
//		System.out.println(formatDate(parseDate("2010/3/6")));
//		System.out.println(getDate("yyyy年MM月dd日 E"));
//		long time = new Date().getTime()-parseDate("2012-11-19").getTime();
//		System.out.println(time/(24*60*60*1000));
		
//		Calendar c = Calendar.getInstance();
//		 c.setTime(parseDate("2013-01-01"));
//       int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
//       if (day_of_week == 0)
//        day_of_week = 7;
//       c.add(Calendar.DATE, -day_of_week + 1);
//       String  as= DateFormatUtils.format(c, "yyyy-MM-dd");
//       System.out.println(as);
       
       
      int week= getCurrentWeekOfYear(parseDate("2013-02-01"));
      System.out.println(week);
	}
}
