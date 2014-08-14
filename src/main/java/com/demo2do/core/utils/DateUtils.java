package com.demo2do.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Date related utilities
 *
 * @author David
 */
public abstract class DateUtils {

    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

    private static final String DEFAULT_MONTH_PATTERN = "yyyy-MM";

    public static final long DAY_IN_MILLISECOND = 24 * 60 * 60 * 1000;

    /**
     * As date
     *
     * @param date input date
     * @return Date of the input date
     */
    public static Date asDay(Date date) {
        return parseDate(format(date, DEFAULT_DATE_PATTERN), DEFAULT_DATE_PATTERN);
    }

    /**
     * As date
     *
     * @param dateString input string
     * @return date of the input string
     */
    public static Date asDay(String dateString) {
        return parseDate(dateString, DEFAULT_DATE_PATTERN);
    }

    /**
     * As month
     *
     * @param date input date
     * @return month of the input date
     */
    public static Date asMonth(Date date) {
        return parseDate(format(date, DEFAULT_MONTH_PATTERN), DEFAULT_MONTH_PATTERN);
    }

    /**
     * As month
     *
     * @param dateString input string
     * @return month of the input string
     */
    public static Date asMonth(String dateString) {
        return parseDate(dateString, DEFAULT_MONTH_PATTERN);
    }

    /**
     * Formatted string of today
     *
     * @return today's string
     */
    public static String formatToday() {
        return format(new Date());
    }

    /**
     * Formatted string of month
     *
     * @return month's string
     */
    public static String formatThisMonth() {
        return format(new Date(), DEFAULT_MONTH_PATTERN);
    }

    /**
     * Do a wrap of {@link org.apache.commons.lang.time.DateUtils#parseDate(String, String[])}
     *
     * @param dateString input string
     * @param pattern    date pattern
     * @return parsed date
     */
    public static Date parseDate(String dateString, String pattern) {
        return parseDate(dateString, new String[]{pattern});
    }

    /**
     * Do a wrap of {@link org.apache.commons.lang.time.DateUtils#parseDate(String, String[])}
     *
     * @param dateString input string
     * @param patterns   date patterns
     * @return parsed date
     */
    public static Date parseDate(String dateString, String[] patterns) {
        try {
            return org.apache.commons.lang.time.DateUtils.parseDate(dateString, patterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Parse date using DEFAULT_DATE_PATTERN
     *
     * @param year  year
     * @param month month
     * @param date  date
     * @return parsed date
     */
    public static Date parseDate(int year, int month, int date) {
        return parseDate(format(year, month, date), DEFAULT_DATE_PATTERN);
    }

    /**
     * Format date according to year, month and date to DEFAULT_DATE_PATTERN
     *
     * @param year  year
     * @param month month
     * @param date  date
     * @return parsed date
     */
    public static String format(int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, date);
        return new SimpleDateFormat(DEFAULT_DATE_PATTERN).format(calendar.getTime());
    }

    /**
     * Format date according to year, month and date to DEFAULT_MONTH_PATTERN
     *
     * @param year  year
     * @param month month
     * @return parsed month
     */
    public static String format(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return new SimpleDateFormat(DEFAULT_MONTH_PATTERN).format(calendar.getTime());
    }

    /**
     * Format date according to input date to DEFAULT_DATE_PATTERN
     *
     * @param date input date
     * @return date string
     */
    public static String format(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        return new SimpleDateFormat(DEFAULT_DATE_PATTERN).format(calendar.getTime());
    }


    /**
     * Format date according to pattern
     *
     * @param date    input date
     * @param pattern date pattern
     * @return date string
     */
    public static String format(Date date, String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return new SimpleDateFormat(pattern).format(calendar.getTime());
    }

    /**
     * Add days to a date
     *
     * @param date input date
     * @param days number of days
     * @return added date
     */
    public static Date addDays(Date date, int days) {
        return org.apache.commons.lang.time.DateUtils.addDays(date, days);
    }

    /**
     * Add weeks to a date
     *
     * @param date  input date
     * @param weeks number of weeks
     * @return added date
     */
    public static Date addWeeks(Date date, int weeks) {
        return org.apache.commons.lang.time.DateUtils.addWeeks(date, weeks);
    }

    /**
     * Add months to a date
     *
     * @param date   input date
     * @param months number of months
     * @return added date
     */
    public static Date addMonths(Date date, int months) {
        return org.apache.commons.lang.time.DateUtils.addMonths(date, months);
    }

    /**
     * Get current time millis as a string
     *
     * @return current millis
     */
    public static String getCurrentTimeMillis() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * Get first month day for calendar.
     * Note: There are differences between the first day of the month and the first day for calendar.
     * <p/>
     * eg. First day for calendar for 2008.5.18 is 2008.4.27
     *
     * @param date input date
     * @return the first month day of date
     */
    public static Date getFirstMonthDayForCalendar(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        // Set first day of week is Sunday
        calendar.setFirstDayOfWeek(0);

        calendar.add(Calendar.DAY_OF_YEAR, -(calendar.get(Calendar.DAY_OF_WEEK) - 1));
        return calendar.getTime();
    }

    /**
     * Get last month day for calendar.
     * Note: There are differences between the last day of the month and the last day for calendar.
     * <p/>
     * eg. Last day for calendar for 2008.6.18 is 2008.7.5
     *
     * @param date input date
     * @return the last month day of date
     */
    public static Date getLastMonthDayForCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        // Set first day of week is Sunday
        calendar.setFirstDayOfWeek(0);

        calendar.add(Calendar.DAY_OF_YEAR, Calendar.DAY_OF_WEEK - calendar.get(Calendar.DAY_OF_WEEK));
        return calendar.getTime();
    }

    /**
     * Get the days of month
     *
     * @param year  year
     * @param month month
     * @return days of month
     */
    public static int getMonthDay(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


    /**
     * Get year of the date
     *
     * @param date input date
     * @return year of date
     */
    public static String getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    /**
     * Get month of the date
     *
     * @param date input date
     * @return month of date
     */
    public static String getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1;
        return month < 10 ? "0" + month : "" + month;
    }

    /**
     * Get day of the date
     *
     * @param date input date
     * @return day of date
     */
    public static String getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.DATE));
    }

    /**
     * Whether a date is weekend
     *
     * @param date input date
     * @return result
     */
    public static boolean isWeekend(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return dayOfWeek == 0 || dayOfWeek == 6;
    }

    /**
     * Whether two date are the same day
     *
     * @param date1 input date
     * @param date2 another input date
     * @return result
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    /**
     * Whether two date are the same month
     *
     * @param date1 input date
     * @param date2 another input date
     * @return result
     */
    public static boolean isSameMonth(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH));
    }

    /**
     * Get interval days between start and end
     *
     * @param start start date
     * @param end   end date
     * @return list fo dates between start and end date
     */
    public static List<Date> getIntervalDays(Date start, Date end) {
        List<Date> dates = new ArrayList<Date>();
        if (start.after(end)) {
            throw new IllegalArgumentException("The start date must not be after end date");
        } else {
            for (Date date = start; !isSameDay(date, end); date = org.apache.commons.lang.time.DateUtils.addDays(date, 1)) {
                dates.add(date);
            }
            dates.add(end);
        }
        return dates;
    }

    /**
     * compare the two dates, and return the subtraction between d1 and d2(d1 - d2)
     * result > 0 when d1 > d2 and result < 0 when d1 < d2
     *
     * @param start input date
     * @param end   another input date
     * @return subtraction between start and end
     */
    public static int compareTwoDatesOnDay(Date start, Date end) {
        if (start.getTime() == end.getTime())
            return 0;
        start = org.apache.commons.lang.time.DateUtils.truncate(start, Calendar.DATE);
        end = org.apache.commons.lang.time.DateUtils.truncate(end, Calendar.DATE);
        long l1 = start.getTime();
        long l2 = end.getTime();
        return (int) ((l1 - l2) / DAY_IN_MILLISECOND);
    }

    /**
     * Get interval months between start and end
     *
     * @param start input date
     * @param end   another input date
     * @return list fo dates between start and end date, interval in month
     */
    public static List<Date> getIntervalMonths(Date start, Date end) {
        List<Date> dates = new ArrayList<Date>();
        if (start.after(end)) {
            throw new IllegalArgumentException("The start date should not be after end date");
        } else {
            for (Date date = start; !isSameMonth(date, end); date = org.apache.commons.lang.time.DateUtils.addMonths(date, 1)) {
                dates.add(date);
            }
            dates.add(end);
        }
        return dates;
    }

    /**
     * Get the first date in a week
     *
     * @param date input date
     * @return first date in a week
     */
    public static Date getFirstDateOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return calendar.getTime();
    }

    /**
     * Get the last date in a week
     *
     * @param date input date
     * @return last date in a week
     */
    public static Date getLastDateOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        return calendar.getTime();
    }
}
