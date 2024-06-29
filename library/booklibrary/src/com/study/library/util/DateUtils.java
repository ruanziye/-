package com.study.library.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期相关类
 */
public class DateUtils {

	public static String format(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	public static String format(Date date,String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}
}
