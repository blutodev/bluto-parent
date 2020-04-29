/**
 * <p>DateUtil.java </p>
 *
 * <p>系统名称: 南康智慧房地产事业部工具包<p>  
 * <p>功能描述: 日期对象工具<p>
 *
 * <p>公司名称: 上海南康科技有限公司<p> 
 * <p>作    者: 刀斌</p>
 * <p>创建时间: 2020年4月27日<p>
 * 
 */ 
package com.bluto.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

	public static Date afterHourMinute(Date date,int hours,int minutes,int seconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, hours);
		calendar.add(Calendar.MINUTE, minutes);
		calendar.add(Calendar.SECOND, seconds);
		return calendar.getTime();
	}


	public static Date afterDays(Date date,int count) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, count);
		return calendar.getTime();
	}

	public static Date afterMonths(Date date,int count) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, count);
		return calendar.getTime();
	}

	public static Date afterYears(Date date,int count) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, count);
		return calendar.getTime();
	}

	/**
	 * 功能描述：日期差异小时
	 * @param endDate
	 * @param currentDate
	 * @return 差异小时
	 */
	public static long differentMinute(Date endDate,Date currentDate) {
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		Calendar now = Calendar.getInstance();
		now.setTime(currentDate);

		long minutes = (end.getTimeInMillis() - now.getTimeInMillis()) / (60 * 1000);

		return minutes;
	}

	/**
	 * 功能描述：日期差异小时
	 * @param endDate
	 * @param currentDate
	 * @return 差异小时
	 */
	public static long differentHour(Date endDate,Date currentDate) {
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		Calendar now = Calendar.getInstance();
		now.setTime(currentDate);

		long minutes = (end.getTimeInMillis() - now.getTimeInMillis()) / (60 * 1000);
		long hours = minutes / 60;

		return hours;
	}

	/**
	 * 功能描述：日期差异天数
	 * @param endDate
	 * @param currentDate
	 * @return 差异天数
	 */
	public static long differentDay(Date endDate,Date currentDate) {
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		Calendar now = Calendar.getInstance();
		now.setTime(currentDate);

		long minutes = (end.getTimeInMillis() - now.getTimeInMillis()) / (60 * 1000);
		long days = minutes / (60 * 24);

		return days;
	}


	/**
	 * 功能描述：字符转换为日期
	 * @param strDate
	 * 格式：yyyy-mm-dd或yyyy/mm/dd或yyyymmdd或yyyy-mm-dd hh24:mi:ss或yyyymmddhh24:mi:ss
	 * @return
	 * @throws Exception
	 */
	public static Date parseDate(String strDateTime) throws Exception {
		if(strDateTime == null || strDateTime.trim().equals(""))
			return null;
		if(strDateTime.indexOf("-") > 0)
			strDateTime = strDateTime.replace("-", "");
		if(strDateTime.indexOf("/") > 0)
			strDateTime = strDateTime.replace("/", "");
		if(strDateTime.indexOf(".") > 0)
			strDateTime = strDateTime.replace(".", "");
		if(strDateTime.indexOf(":") > 0)
			strDateTime = strDateTime.replace(":", "");
		if(strDateTime.indexOf(" ") > 0)
			strDateTime = strDateTime.replace(" ", "");

		int year = Integer.parseInt(strDateTime.substring(0, 4));
		int month = Integer.parseInt(strDateTime.substring(4, 6));
		int day = Integer.parseInt(strDateTime.substring(6, 8));

		int hours = 0, minutes = 0, seconds = 0;
		if(strDateTime.length() == 14){ //yyyymmddhh24:mi:ss
			hours = Integer.parseInt(strDateTime.substring(8, 10));
			minutes = Integer.parseInt(strDateTime.substring(10, 12));
			seconds = Integer.parseInt(strDateTime.substring(12, 14));
		}else if(strDateTime.length() == 13){
			hours = Integer.parseInt(strDateTime.substring(8, 9));
			minutes = Integer.parseInt(strDateTime.substring(9, 11));
			seconds = Integer.parseInt(strDateTime.substring(11, 13));
		}else if(strDateTime.length() == 12){
			hours = Integer.parseInt(strDateTime.substring(8, 9));
			minutes = Integer.parseInt(strDateTime.substring(9, 10));
			seconds = Integer.parseInt(strDateTime.substring(10, 12));
		}

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day, hours, minutes, seconds);

		return calendar.getTime();
	}

	/**
	 * 功能描述：转换日期
	 * @param millis
	 * @return
	 * @throws Exception
	 */
	public static Date parseDate(long millis) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

	/**
	 * 功能描述：获得当前月的第一天日期
	 * @return
	 */
	public static Date getFirstDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date firstDate = calendar.getTime();
		return firstDate;
	}

	/**
	 * 功能描述：获得当前月的最后天日期
	 * @return
	 */
	public static Date getLastDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		Date endTime = calendar.getTime();
		return endTime;
	}

	//取系统时间
	public static Date getSysDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8")); //取北京时间
		Date date = calendar.getTime();
		return date;
	}

	//取系统时间字符串
	public static String getSysDateString(String patten) {
		Date date = getSysDate();
		SimpleDateFormat sdf = new SimpleDateFormat(patten);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return sdf.format(date);
	}

	/**
	 * 功能描述：获取日期内容，如年、月、日
	 * @param date
	 * @param dateField(Calendar.DAY_OF_MONTH...)
	 * @return
	 */
	public static int getDateField(Date date,int dateField) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(dateField);
	}

}
