package com.hipepham.springboot.common.utils;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * The type Date utils.
 */
@Service
public final class DateUtils {

	private DateUtils() {
	}

	/**
	 * Gets current time in millis.
	 *
	 * @return the current time in millis
	 */
	public static long getCurrentTimeInMillis() {
		return Calendar.getInstance().getTimeInMillis();
	}

	public Date addDay(Date dt) {

		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, 1);
		dt = c.getTime();

		return dt;
	}

	public Date substractDay(Date dt, int amount) {

		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, amount);
		dt = c.getTime();
		return dt;
	}

	/**
	 * 
	 * @param dt
	 * @param month
	 * @return
	 */
	public Date subMonth(Date dt, int month) {

		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.MONTH, month);
		dt = c.getTime();

		return dt;
	}

	/**
	 * 
	 * @param dt
	 * @return
	 */
	public Date resetDateToStartOfDay(Date dt) {
		Calendar date = new GregorianCalendar();
		date.setTime(dt);
		// reset hour, minutes, seconds and millis
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		return date.getTime();
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public LocalDateTime convertDateToLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public boolean checkLastDayOfMonth() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		// get the calendar last day of this month
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int day = cal.get(Calendar.DATE);
		if (day == lastDay) {
			return true;
		}
		return false;
	}

	public static int getLastDayOfMonth() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		// get the calendar last day of this month
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		return lastDay;
	}

	public static int getLastDayOfMonth(String month, int year) {
		String date = month +"/01/" + year;
		LocalDate convertedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("M/d/yyyy"));
		convertedDate = convertedDate.withDayOfMonth(
				convertedDate.getMonth().length(convertedDate.isLeapYear()));
		// get the last day of this month
		return convertedDate.getDayOfMonth();
	}



	public static Pair<Date, Date> getDateRange() {
		Date begining, end;

		{
			Calendar calendar = getCalendarForNow();
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			setTimeToBeginningOfDay(calendar);
			begining = calendar.getTime();
		}

		{
			Calendar calendar = getCalendarForNow();
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			setTimeToEndofDay(calendar);
			end = calendar.getTime();
		}

		return Pair.of(begining, end);
	}

	private static Calendar getCalendarForNow() {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(new Date());
		return calendar;
	}

	private static void setTimeToBeginningOfDay(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}

	private static void setTimeToEndofDay(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
	}

}
