package com.like.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class DateUtil {

	public static Calendar toCalendar(Date date) {
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	public static Calendar getNextDay(Calendar calendar) {
		calendar.add(Calendar.DATE, 1);
		return calendar;
	}
	
	public static String getMonth(Calendar calendar) {
		String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);// 获取当前月份
		return month;
	}
	
	public static String getDay(Calendar c) {
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
		return day;
	}
	
	public static String getWeek(Calendar c) {
		String way = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
		if ("1".equals(way)) {
			way = "天";
		} else if ("2".equals(way)) {
			way = "一";
		} else if ("3".equals(way)) {
			way = "二";
		} else if ("4".equals(way)) {
			way = "三";
		} else if ("5".equals(way)) {
			way = "四";
		} else if ("6".equals(way)) {
			way = "五";
		} else if ("7".equals(way)) {
			way = "六";
		}
		return "星期" + way;
	}
	
	public static String getImgName() {
		DateFormat f2 = new SimpleDateFormat("yyyyMMddHHmmss");
		String day = f2.format(new Date());
		int max = 10000;
		int min = 99999;
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		 String serverImgName = day + s;
		return serverImgName;
	}

}
