/**
 * <p>StringUtil.java </p>
 *
 * <p>系统名称: 南康智慧房地产事业部工具包<p>  
 * <p>功能描述: 字符串工具类<p>
 *
 * <p>公司名称: 上海南康科技有限公司<p> 
 * <p>作    者: 刀斌</p>
 * <p>创建时间: 2020年4月27日<p>
 * 
 */ 
package com.bluto.utils;


public class StringUtil {

	/**
	 * 功能描述：判断字符串是否为空
	 * @param str
	 * @return
	 */
	static public boolean isBlankD1(String str) {
		if(str==null || "".equals(str.trim())) 
			return true;
		return false;
	}

	/**** 字符集转换 *****/
	/**
	 * 功能描述:将字符串由 ISO8859转为GBK
	 * @param str
	 * @return
	 */
	public static String ISO8859toGBK(String str) {
		if(str == null)
			return null;
		try{
			return new String(str.getBytes("ISO8859-1"), "GBK");
		}catch (Exception e){
			return "";
		}
	}

	/**
	 * 功能描述：将字符串由GBK转为ISO8859
	 * @param str
	 * @return
	 */
	public static String GBKtoISO8859(String str) {
		if(str == null)
			return null;
		try{
			return new String(str.getBytes("GBK"), "ISO8859-1");
		}catch (Exception e){
			return "";
		}
	}

	/**
	 * 功能描述：将字符串由GBK转为UTF8
	 * @param str
	 * @return
	 */
	public static String GBKtoUTF8(String str) {
		if(str == null)
			return null;
		try{
			return new String(str.getBytes("GBK"), "UTF-8");
		}catch (Exception e){
			return "";
		}
	}

	/**
	 * 功能描述：将字符串由UTF8转为GBK
	 * @param str
	 * @return
	 */
	public static String UTF8toGBK(String str) {
		if(str == null)
			return null;
		try{
			return new String(str.getBytes("UTF-8"), "GBK");
		}catch (Exception e){
			return "";
		}
	}

	public static String escapeHtml(String str) {
		str = replace(str, "<", "&lt;");
		str = replace(str, ">", "&gt;");
		str = replace(str, " ", "&nbsp;");
		str = replace(str, "\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		str = replace(str, "\r\n", "<br>");
		return str;
	}

	/**
	 * 功能描述：字符替换
	 * @param text
	 * @param repl
	 * @param with
	 * @return
	 */
	public static String replace(String text,String replace,String with) {
		return replace(text, replace, with, -1);
	}

	public static String replace(String text,String replace,String with,int max) {
		if(text == null)
			return null;
		StringBuffer buffer = new StringBuffer(text.length());
		int start = 0;
		for(int end = 0; (end = text.indexOf(replace, start)) != -1;){
			buffer.append(text.substring(start, end)).append(with);
			start = end + replace.length();
			if(--max == 0)
				break;
		}

		buffer.append(text.substring(start));
		return buffer.toString();
	}


}
