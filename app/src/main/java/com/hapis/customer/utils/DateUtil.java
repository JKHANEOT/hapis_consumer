/**
 * Copyright � Altimetrik 2013. All rights reserved.
 * <p>
 * This software is the confidential and proprietary information
 * of Altimetrik. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms and conditions
 * entered into with Altimetrik.
 * <p>
 * Id: DateUtil.java,v 1.1
 * <p>
 * Date Author Changes
 * 7 Mar, 2013, 11:17:54 AM SBiswal Created
 */
package com.hapis.customer.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;


/**
 * The Class DateUtil.
 */
public final class DateUtil {

    /**
     * Instantiates a new date util.
     */
    private DateUtil() {
    }

    /**
     * The Constant DATE_FORMAT_MMM.
     */
    public static final String DATE_FORMAT_MMM = "MMM";

    /**
     * The Constant DATE_FORMAT_dd.
     */
    public static final String DATE_FORMAT_dd = "dd";

    /**
     * The Constant DATE_FORMAT_dd_MM_yyyy_SEP_HIPHEN.
     */
    public static final String DATE_FORMAT_dd_MM_yyyy_SEP_HIPHEN = "dd-MM-yyyy";

    /**
     * The Constant DATE_FORMAT_dd_MMM_yyyy_SEP_HIPHEN.
     */
    public static final String DATE_FORMAT_dd_MMM_yyyy_SEP_HIPHEN = "dd-MMM-yyyy";

    /**
     * The Constant DATE_FORMAT_dd_MM_yyyy_SEP_SLASH.
     */
    public static final String DATE_FORMAT_dd_MM_yyyy_SEP_SLASH = "dd/MM/yyyy";

    /**
     * The Constant DATE_FORMAT_yyyy_MM_dd_SEP_HIPHEN.
     */
    public static final String DATE_FORMAT_yyyy_MM_dd_SEP_HIPHEN = "yyyy-MM-dd";

    /**
     * The Constant DATE_FORMAT_HH_mm_ss.
     */
    public static final String DATE_FORMAT_HH_mm_ss = "HH:mm:ss";
    /**
     * The Constant DATE_FORMAT_HH_mm_ss.SSS.
     */
    public static final String DATE_FORMAT_HH_mm_ss_SSS = "HH:mm:ss.SSS";

    public static final String DATE_FORMAT_HH_mm_am_pm = "HH:mm a";

    /**
     * The Constant DATE_FORMAT_yy.
     */
    public static final String DATE_FORMAT_yy = "yy";

    /**
     * The Constant DATE_FORMAT_yyyy_MM_dd_HH_mm_ss_SEP_HIPHEN.
     */
    public static final String DATE_FORMAT_yyyy_MM_dd_HH_mm_ss_SEP_HIPHEN = "yyyy-MM-dd HH:mm:ss";

    /**
     * The Constant DATE_FORMAT_dd_MM_yyyy_HH_mm_ss_SEP_HIPHEN.
     */
    public static final String DATE_FORMAT_dd_MM_yyyy_HH_mm_ss_SEP_HIPHEN = "dd-MM-yyyy HH:mm:ss a";

    /**
     * The Constant DATE_FORMAT_dd_MM_yyyy_HH_mm_ss_SEP_SLASH.
     */
    public static final String DATE_FORMAT_dd_MM_yyyy_HH_mm_ss_SEP_SLASH = "dd/MM/yy-HH:mm:ss";

    /**
     * The Constant DATE_FORMAT_yyyy_MM_dd_HH_mm_SEP_HIPHEN.
     */
    public static final String DATE_FORMAT_yyyy_MM_dd_HH_mm_SEP_HIPHEN = "dd-MM-yyyy HHmm";

    /**
     * The Constant DATE_FORMAT_yyyyMMddHHmmss.
     */
    public static final String DATE_FORMAT_yyyyMMddHHmmss = "yyyyMMddHHmmss";

    /**
     * The Constant DATE_FORMAT_ddMMyyyy.
     */
    public static final String DATE_FORMAT_ddMMyyyy = "ddMMyyyy";

    /**
     * The Constant DATE_FORMAT_ddMMyyyy.
     */
    public static final String TIME_HH_MM = "HH:mm";

    /**
     * The Constant DATE_FORMAT_MM_dd_YY_SEP_HIPHEN.
     */
    public static final String DATE_FORMAT_MM_dd_YY_SEP_HIPHEN = "MM-dd-yy";

    /**
     * The Constant DATE_FORMAT_dd_MM_yyyy_HH_mm_am_pm.
     */
    public static final String DATE_FORMAT_dd_MM_yyyy_HH_mm_am_pm = "dd-MM-yyyy HH:mm a";

    /**
     * The Constant DATE_FORMAT_MMM_dd, yyyy.
     */
    public static final String DATE_FORMAT_MMM_dd_yyyy = "MMM dd, yyyy";

    public static final String DATE_FORMAT_MMM_dd_yyyy_HH_mm_am_pm = "MMM dd, yyyy HH:mm a";

    //    https://docs.oracle.com/javase/6/docs/api/java/text/SimpleDateFormat.html
    public static final String DATE_FORMAT_EEE_MMM_d_YY = "EEE, d MMM";

    public static final String DATE_FORMAT_SERVICE_REQUEST_TRACK_CHAT_HISTORY = "EEE, d MMM  'at' HH:mm a";
    public static final String DATE_FORMAT_SERVICE_REQUEST_LANDING_PAGE = "EEE d yyyy  HH:mm a";
    public static final String DATE_FORMAT_EE_dd_MMM_yyyy = "EE dd MMM yyyy";
    public static final String DATE_FORMAT_yyyy_MM_dd_T_HH_mm_ss_SSS_Z  = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    /**
     * Calculate to date.
     *
     * @param date   the date
     * @param tenure the tenure
     * @return the date
     */
    public static Date calculateToDate(final Date date, final int tenure) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, tenure);
        return calendar.getTime();

    }


    /**
     * Adds the date.
     *
     * @param date          the date
     * @param calendarField the calendar field
     * @param amount        the amount
     * @return the date
     */
    public static Date addDate(final Date date, final int calendarField, final int amount) {

        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.add(calendarField, amount);
        return calendar.getTime();

    }

    /**
     * To convert date in string format to Date object.
     *
     * @param dateStr       the date str
     * @param dateFormatStr the date format str
     * @return Date
     */
    public static Date convertDateStrToDate(final String dateStr, final String dateFormatStr) {
        Date date = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr, new Locale(Locale.ENGLISH.getLanguage()));
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * To convert the given Date to String.
     *
     * @param date          the date
     * @param dateFormatStr the date format str
     * @return String
     */
    public static String convertDateToDateStr(final Date date, final String dateFormatStr) {
        SimpleDateFormat sdFormatter = new SimpleDateFormat(dateFormatStr, new Locale(Locale.ENGLISH.getLanguage()));
        return sdFormatter.format(date);
    }

    /**
     * To change the format of a given Date String to another format.
     *
     * @param date           the date
     * @param fromDateFormat the from date format
     * @param toDateFormat   the to date format
     * @return String
     */
    public static String changeDateFormat(final String date, final String fromDateFormat,
                                          final String toDateFormat) {
        String dateStr = "";
        try {
            Date formattedDate = convertDateStrToDate(date, fromDateFormat);
            dateStr = convertDateToDateStr(formattedDate, toDateFormat);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * To calculate Number of days between two dates.
     *
     * @param fromDate the from date
     * @param toDate   the to date
     * @return the number of days
     */
    public static int dateDiffInDays(final Date fromDate, final Date toDate) {
        long numberOfDays = 0L;
        try {
            Long diff = toDate.getTime() - fromDate.getTime();
            numberOfDays = diff / (1000 * 60 * 60 * 24);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (int) numberOfDays;
    }

    /**
     * Julian date in yyddd format.
     *
     * @param date the date
     * @return the string
     */
    public static String julianDateyyddd(final Date date) {
        Calendar ca1 = Calendar.getInstance();
        ca1.setTime(date);
        return convertDateToDateStr(date, DATE_FORMAT_yy)
                + String.format("%03d", ca1.get(Calendar.DAY_OF_YEAR));
    }

    /**
     * Convert time in milliseconds to date string based on the given format. If
     * format is not given, this method will take a default format(dd-MM-yyyy)
     * and return the date string accordingly.,
     *
     * @param timeInMillis the time in milliseconds
     * @param format       the format
     * @return the string
     */
    public static String convertTimeInMillisToDateStr(final Long timeInMillis, final String format) {
        if (timeInMillis != null) {
            Date date = new Date(timeInMillis);
            if (format != null && format.length() > 0) {
                return convertDateToDateStr(date, format);
            } else {
                return convertDateToDateStr(date, DATE_FORMAT_dd_MM_yyyy_SEP_HIPHEN);
            }
        }
        return "";
    }

    /**
     * Date comparison.
     *
     * @param fromDate   the from date
     * @param toDate     the to date
     * @param dateFormat the date format
     * @return true, if successful
     */
    public static boolean compareDates(final String fromDate, final String toDate,
                                       final String dateFormat) {
        Date date1 = convertDateStrToDate(fromDate, dateFormat);
        Date date2 = convertDateStrToDate(toDate, dateFormat);
        return date1.compareTo(date2) <= 0;
    }

    /**
     * Checks if is first date is greater than last date.
     *
     * @param firstDate the first date
     * @param lastDate  the last date
     * @return true, if is first date is greater than last date
     */
    public static boolean isFirstDateIsGreaterThanLastDate(Date firstDate, Date lastDate) {

        boolean isFirstDateIsGreater = false;
        if (firstDate.compareTo(lastDate) > 0) {
            isFirstDateIsGreater = true;
        }
        return isFirstDateIsGreater;
    }

    /**
     * Checks if is first date is greater than last date.
     *
     * @param firstDate  the first date
     * @param lastDate   the last date
     * @param dateFormat the date format
     * @return true, if is first date is greater than last date
     */
    public static boolean isFirstDateIsGreaterThanLastDate(final String firstDate, final String lastDate,
                                                           final String dateFormat) {

        Date date1 = convertDateStrToDate(firstDate, dateFormat);
        Date date2 = convertDateStrToDate(lastDate, dateFormat);

        boolean isFirstDateIsGreater = false;
        if (date1.compareTo(date2) >= 0) {
            isFirstDateIsGreater = true;
        }
        return isFirstDateIsGreater;
    }

    /**
     * Checks if is first date is same as last date.
     *
     * @param firstDate  the first date
     * @param lastDate   the last date
     * @param dateFormat the date format
     * @return true, if is first date is same as last date
     */
    public static boolean isFirstDateIsSameAsLastDate(final String firstDate, final String lastDate,
                                                      final String dateFormat) {

        Date date1 = convertDateStrToDate(firstDate, dateFormat);
        Date date2 = convertDateStrToDate(lastDate, dateFormat);

        boolean isFirstDateIsGreater = false;
        if (date1.compareTo(date2) == 0) {
            isFirstDateIsGreater = true;
        }
        return isFirstDateIsGreater;
    }


    /**
     * Convert age to DOB.
     * This will be calculated based only on year
     * EG: if the age is 23 , then the calculated DOB will be 01-01-1990
     *
     * @param age the age
     * @return the date
     */
    public static Date convertAgeToDob(final int age) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int ageYear = currentYear - age;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, ageYear);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);
        return cal.getTime();
    }

    /**
     * Convert dob to age.
     *
     * @param dateOfBirth the date of birth
     * @return the integer
     */
    public static Integer convertDobToAge(final Date dateOfBirth) {

        if (dateOfBirth != null) {
            Calendar now = Calendar.getInstance();
            Calendar dob = Calendar.getInstance();
            dob.setTime(dateOfBirth);
            //Calculate the age based on year
            int yearNow = now.get(Calendar.YEAR);
            int yearDob = dob.get(Calendar.YEAR);
            int age = yearNow - yearDob;
            //ReCalculate the month based on month
            int monthNow = now.get(Calendar.MONTH);
            int monthDob = dob.get(Calendar.MONTH);
            if (monthDob > monthNow) {
                age--;
            } else if (monthNow == monthDob) {
                //ReCalculate the month based on day
                int dayNow = now.get(Calendar.DAY_OF_MONTH);
                int dayDob = dob.get(Calendar.DAY_OF_MONTH);
                if (dayDob > dayNow) {
                    age--;
                }
            }
            return age;
        }
        return null;
    }

    /**
     * Day from date.
     *
     * @param date the date
     * @return the integer
     */
    public static Integer dayFromDate(Date date) {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK);
        return day;
    }

    /**
     * Checks if is this date valid.
     *
     * @param dateToValidate the date to validate
     * @param dateFromat     the date fromat
     * @return true, if is this date valid
     */
    public static boolean isThisDateValid(String dateToValidate, String dateFromat) {

        if (dateToValidate == null) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFromat, new Locale(Locale.ENGLISH.getLanguage()));
        sdf.setLenient(false);

        try {
            //if not valid, it will throw ParseException
            Date date = sdf.parse(dateToValidate);
            System.out.println(date);
        } catch (ParseException e) {
            //e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * Gets the last day of month.
     *
     * @param date the date [ Eg:- <b> 08 - 11 -2014 </b>]
     * @return the last day of month [ Eg:- 31 ]
     */
    public static int getLastDayOfMonth(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);

        System.out.println("Last Day of Month: " + calendar.get(Calendar.DATE));

        return calendar.get(Calendar.DATE);

    }

    public static boolean isDateAfter(String startDate, String endDate) {
        try {
            String myFormatString = "dd-MM-yyyy";
            SimpleDateFormat df = new SimpleDateFormat(myFormatString, new Locale(Locale.ENGLISH.getLanguage()));
            Date date1 = df.parse(endDate.trim());
            Date startingDate = df.parse(startDate.trim());

            return date1.after(startingDate);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static long getDateTimeInMillis(String eventDate, String dateStrFormat) {
        String toParse = eventDate;
        SimpleDateFormat formatter = new SimpleDateFormat(dateStrFormat, new Locale(Locale.ENGLISH.getLanguage()));
        Date date = null;
        try {
            date = (Date) formatter.parseObject(toParse);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millis = date.getTime();
        return millis;
    }

    public static long getDateTimeInMillis(String eventDate, String eventTime, String dateStrFormat) {
        String toParse = eventDate + " " + eventTime;
        //TimeZone utc = TimeZone.getTimeZone("Etc/UTC");
        SimpleDateFormat formatter = new SimpleDateFormat(dateStrFormat, new Locale(Locale.ENGLISH.getLanguage()));
        //formatter.setTimeZone(utc);
        Date date = null;
        try {
            date = (Date) formatter.parseObject(toParse);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millis = date.getTime();
        return millis;
    }

    public static long getDate(int counter) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, counter > 0 ? -counter : 0);
        return cal.getTime().getTime();
    }

    public static class StringDateComparator implements Comparator<String> {
        private String datePattern;
        SimpleDateFormat dateFormat;

        public StringDateComparator(String datePattern) {
            this.datePattern = datePattern;
            this.dateFormat = new SimpleDateFormat(datePattern, new Locale(Locale.ENGLISH.getLanguage()));
        }

        public int compare(String lhs, String rhs) {
            int returnValue = 0;
            try {
                returnValue = dateFormat.parse(lhs).compareTo(dateFormat.parse(rhs));
            } catch (ParseException e) {
                e.printStackTrace();
                returnValue = lhs.compareTo(rhs);
            }

            return returnValue;
        }
    }

    public static String dateFormater(String dateFromJSON, String expectedFormat, String oldFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(oldFormat);
        Date date = null;
        String convertedDate = null;
        try {
            date = dateFormat.parse(dateFromJSON);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(expectedFormat);
            convertedDate = simpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertedDate;
    }

}
