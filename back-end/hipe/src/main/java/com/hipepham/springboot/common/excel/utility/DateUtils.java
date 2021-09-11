package com.hipepham.springboot.common.excel.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

	public static Date stringToDate(String strDate, String pattern) {
		if (strDate == null || strDate.isEmpty()) {
			return null;
		}
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			date = (Date) sdf.parseObject(strDate);
		} catch (ParseException e) {
			LOGGER.error("DateUtils stringToDate():" + e);
		}
		return date;
	}

	public static String dateToString(Date date, String pattern) {
		String result = null;
		if (date == null) {
			return null;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			result = sdf.format(date);
		} catch (Exception e) {
			LOGGER.error("DateUtils dateToString():" + e);
		}
		return result;
	}

}
