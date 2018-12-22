package com.hapis.customer.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hapis.customer.HapisApplication;
import com.hapis.customer.logger.LOG;
import com.hapis.customer.ui.models.enterprise.EnterpriseAddressRequest;
import com.hapis.customer.ui.utils.AccessPreferences;
import com.hapis.customer.ui.utils.ApplicationConstants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import org.bouncycastle.crypto.digests.MD5Digest;

public class Util {

    public static final String TAG = Util.class.getName();
    private static String TIME_ZONE = "GMT+5:30";

    private static Vector<String> vector = new Vector<String>();
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public static String[] split(String target, String delim) {

        if (target == null)
            target = "";
        vector.removeAllElements();

        int startIndex = 0;
        int nextIndexIndex = 0;
        while ((nextIndexIndex = target.indexOf(delim, startIndex)) > 0) {

            vector.addElement(target.substring(startIndex, nextIndexIndex).trim());
            startIndex = nextIndexIndex + 1;
        }
        if ((startIndex < target.length()) /*
         * && (vector.size() > 0)
         */) {

            vector.addElement(target.substring(startIndex).trim());
        }

        return convertVectorToStringArray(vector);
    }

    public static ArrayList<String> splitString(String target, String delim) {

        if (target == null)
            target = "";
        ArrayList<String> list = new ArrayList<String>();

        int startIndex = 0;
        int nextIndexIndex = 0;
        while ((nextIndexIndex = target.indexOf(delim, startIndex)) > 0) {

            list.add(target.substring(startIndex, nextIndexIndex).trim());
            startIndex = nextIndexIndex + 1;
        }
        if ((startIndex < target.length()) /*
         * && (vector.size() > 0)
         */) {

            list.add(target.substring(startIndex).trim());
        }

        return list;
    }

    public static String[] split(String target, int size) {

        String[] ret = new String[getArrayLength(target.length(), size)];

        int startIndex = 0;
        int nextIndexIndex = 0;
        int index = 0;
        while ((nextIndexIndex = (startIndex + size)) < target.length()) {

            ret[index++] = target.substring(startIndex, nextIndexIndex).trim();
            startIndex = nextIndexIndex;
        }
        if ((startIndex < target.length())) {

            ret[index++] = target.substring(startIndex).trim();
        }

        return ret;
    }

    public static byte[][] split(byte[] target, int size) {

        byte[][] ret = new byte[getArrayLength(target.length, size)][];

        int startIndex = 0;
        int nextIndexIndex = 0;
        int index = 0;
        while ((nextIndexIndex = (startIndex + size)) < target.length) {

            ret[index] = new byte[size];
            System.arraycopy(target, startIndex, ret[index], 0, size);
            startIndex = nextIndexIndex;
            index++;
        }
        if (startIndex < target.length) {

            ret[index] = new byte[target.length - startIndex];
            System.arraycopy(target, startIndex, ret[index], 0, ret[index].length);
        }

        return ret;
    }

    public static int getArrayLength(int totalDataLength, int splitBySize) {

        return (totalDataLength / splitBySize) + ((totalDataLength % splitBySize) > 0 ? 1 : 0);

    }

    public static String[] convertVectorToStringArray(Vector<String> vector) {

        String[] ret = new String[vector.size()];
        for (int i = 0; i < ret.length; i++) {

            ret[i] = vector.elementAt(i);
        }

        return ret;
    }

    public static String getDayTimeStringFromDate(Date date) {

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = (calendar.get(Calendar.MONTH) + 1);
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);
        int mili = calendar.get(Calendar.MILLISECOND);
        String dayString = (day < 10) ? "0" + day : "" + day;
        String monString = (month < 10) ? "0" + month : "" + month;
        return dayString + "/" + monString + "/" + year + " " + hour + ":" + min + ":" + sec + ":" + mili;
    }

    /*
     * public static Image createThumbnail(Image image, int thumbWidth, int
	 * thumbHeight) {
	 *
	 * int sourceWidth = image.getWidth(); int sourceHeight = image.getHeight();
	 *
	 * Image thumb = Image.createImage(thumbWidth, thumbHeight); Graphics g =
	 * thumb.getGraphics(); for (int y = 0; y < thumbHeight; y++) { for (int x =
	 * 0; x < thumbWidth; x++) { g.setClip(x, y, 1, 1); int dx = x * sourceWidth
	 * / thumbWidth; int dy = y * sourceHeight / thumbHeight; g.drawImage(image,
	 * x - dx, y - dy, Graphics.LEFT | Graphics.TOP); } }
	 *
	 * //Image immutableThumb = Image.createImage(thumb); return thumb; }
	 */


    public static Date getDateTimeFromString(String inputString, boolean yearFirst) throws Exception {

        Calendar calendar = null;
        try {

            String[] data = null;
            if (inputString.indexOf(" ") > 0) {

                data = split(inputString.trim(), " ");
            } else {

                data = new String[1];
                data[0] = inputString;
            }

            String dateString = null;
            String timeString = null;

            if (data.length > 0) {

                if (inputString.indexOf(" ") > 0) {

                    dateString = data[0];
                    timeString = data[1];
                } else
                    dateString = data[0];

            } else {

                dateString = inputString;
            }

            String[] dateArray = split(dateString, "-");

            calendar = Calendar.getInstance();
            if (dateArray.length >= 3) {

                if (yearFirst) {

                    calendar.set(Calendar.YEAR, Integer.parseInt(dateArray[0].trim()));
                    calendar.set(Calendar.MONTH, Integer.parseInt(dateArray[1].trim()) - 1);
                    calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateArray[2].trim()));
                } else {

                    calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateArray[0].trim()));
                    calendar.set(Calendar.MONTH, Integer.parseInt(dateArray[1].trim()) - 1);
                    calendar.set(Calendar.YEAR, Integer.parseInt(dateArray[2].trim()));
                }

            }

            String[] timeArray = timeString != null ? split(timeString, ":") : null;

            if (timeArray != null) {

                if (timeArray.length >= 2) {

                    calendar.set(Calendar.HOUR, Integer.parseInt(timeArray[0].trim()));
                    calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1].trim()));
                    if (timeArray.length >= 3) {

                        calendar.set(Calendar.SECOND, Integer.parseInt(timeArray[2].trim()));
                    }
                    if (timeArray.length >= 4) {

                        calendar.set(Calendar.MILLISECOND, Integer.parseInt(timeArray[3].trim()));
                    }
                }
            }

        } catch (Exception e) {

            throw e;
        }

        return calendar.getTime();

    }

    public static void createPathIfNecessary(String prePath, String path) throws IOException {/*
     *
     * File
     * fileConnection
     * ;
     * String
     * [
     * ]
     * dirs
     * =
     * split
     * (
     * path
     * ,
     * "/"
     * )
     * ;
     * for
     * (
     * int
     * i
     * =
     * 0
     * ;
     * i
     * <
     * dirs
     * .
     * length
     * ;
     * i
     * ++
     * )
     * {
     *
     * fileConnection
     * =
     * new
     * File
     * (
     * prePath
     * +=
     * (
     * "/"
     * +
     * dirs
     * [
     * i
     * ]
     * )
     * )
     * ;
     *
     * fileConnection
     * =
     * (
     * FileConnection
     * )
     * Connector
     * .
     * open
     * (
     * prePath
     * +=
     * (
     * "/"
     * +
     * dirs
     * [
     * i
     * ]
     * )
     * ,
     * Connector
     * .
     * READ_WRITE
     * )
     * ;
     *
     * /
     * /
     * fileConnection
     * .
     * setHidden
     * (
     * true
     * )
     * ;
     * if
     * (
     * !
     * fileConnection
     * .
     * exists
     * (
     * )
     * )
     * {
     *
     * fileConnection
     * .
     * mkdir
     * (
     * )
     * ;
     * /
     * /
     * fileConnection
     * .
     * close
     * (
     * )
     * ;
     * }
     * }
     */


        File fileConnection;
        String[] dirs = split(path, "/");
        for (int i = 0; i < dirs.length; i++) {

            fileConnection = new File(prePath += ("/" + dirs[i]));
            if (!fileConnection.exists()) {

                fileConnection.mkdir();
            }
        }
    }

    public static File createPath(File extFileDir, String logFileName) throws IOException {

        File logFile = new File(extFileDir, "/" + logFileName + "/");
        if (!logFile.exists()) {
            logFile.mkdir();
        }

        return logFile;
    }

    public static boolean checkDateIsCurrent(Date date) {

        Calendar currentDate = Calendar.getInstance();
        Calendar dateToCheck = Calendar.getInstance();
        dateToCheck.setTime(date);

        return currentDate.get(Calendar.YEAR) == dateToCheck.get(Calendar.YEAR) && currentDate.get(Calendar.DAY_OF_MONTH) == dateToCheck.get(Calendar.DAY_OF_MONTH)
                && currentDate.get(Calendar.DAY_OF_MONTH) == dateToCheck.get(Calendar.DAY_OF_MONTH);

    }

    public static byte[] getHash(byte[] data) {
        byte[] hash = new byte[16];
       /* byte[] hash = new byte[16];
        MD5Digest digest = new MD5Digest();
        digest.update(data, 0, data.length);
        digest.doFinal(hash, 0);
        */
        return hash;
    }

    public static boolean checkStrWhiteSpace(String str) {

        String whiteSpace = " ";
        int startPos = 0;
        int whiteSpacePos = 0;
        boolean whiteSpaceStatus = false;

        whiteSpacePos = str.indexOf(whiteSpace, startPos);

        if (whiteSpacePos < 0) {
            return whiteSpaceStatus;
        } else {
            whiteSpaceStatus = true;
            return whiteSpaceStatus;
        }
    }

    public static boolean checkStrConseWhiteSpaces(String str) {

        boolean conseWhiteSpaceStatus = false;
        Vector<String> vt = new Vector<String>();

        for (int j = 0; j < str.length(); j++) {
            if (str.charAt(j) == ' ') {
                vt.addElement(j + "");
            }
        }

        String[] whiteSpacePosArray = convertVectorToStringArray(vt);

        for (int k = 0; k < whiteSpacePosArray.length; k++) {
            String nextStrAfterWhiteSpace = str.substring(Integer.parseInt(whiteSpacePosArray[k]) + 1, Integer.parseInt(whiteSpacePosArray[k]) + 2);
            if (nextStrAfterWhiteSpace.equals(" ")) {
                conseWhiteSpaceStatus = true;
                break;
            }
        }
        return conseWhiteSpaceStatus;
    }

    public static int getIntFromString(String age) {
        return Integer.parseInt(age);
    }

    public static void main(String[] args) {

    }

    public static String getHexValueWithUpperCase(String value, int Maxpadding, String paddingString, boolean isLeftPad) {

        value = Integer.toHexString(Integer.parseInt(value));
        value = value.toUpperCase(Locale.US);

        while (value.length() < Maxpadding) {
            if (isLeftPad) {
                value = paddingString + value;
            } else {
                value = value + paddingString;
            }
        }
        return value;
    }

    public static boolean dateIsInValidFormat(String date) {

        boolean status = true;

        if (date.length() < 8) {
            status = false;
        } else {
            int totaNoOfHyphen = getTotalNoOfHyphen(date);
            int totalNoOfSclash = getTotalNoOfSclash(date);

            if (date.startsWith("-") || date.startsWith("/")) {
                status = false;
            } else if (totaNoOfHyphen < 2 || totaNoOfHyphen > 2 || totalNoOfSclash >= 1) {
                status = false;
            } else if (checkContinousHyphen(date)) {
                status = false;
            } else if (!Character.isDigit(date.charAt(date.length() - 1))) {
                status = false;
            } else if (date.length() == 10) {
                String[] dateArray = Util.split(date, "-");

                if (dateArray.length != 3) {
                    status = false;
                } else if (dateArray[0].length() != 4) {
                    status = false;
                } else if (Integer.parseInt(dateArray[1]) > 12) {
                    status = false;
                }
            }

        }
        return status;
    }

    private static int getTotalNoOfHyphen(String date) {
        int count = 0;
        for (int i = 0; i < date.length(); i++) {
            if (date.charAt(i) == '-') {
                count++;
            }
        }
        return count;
    }

    private static boolean checkContinousHyphen(String date) {
        boolean status = false;
        int count = 1;
        for (int i = 1; i < date.length(); i++) {
            if (date.charAt(i) == '-') {
                if (date.charAt(i) == (date.charAt(i - 1))) {
                    count++;
                }
            }
        }
        status = count > 1;
        return status;
    }

    private static int getTotalNoOfSclash(String date) {
        int count = 0;
        for (int i = 0; i < date.length(); i++) {
            if (date.charAt(i) == '/') {
                count++;
            }
        }
        return count;
    }

    public static boolean isValidDate(String date) {
        boolean status = true;
        String[] dateArrya = Util.split(date, "-");
        int year = Integer.parseInt(dateArrya[0]);
        int month = Integer.parseInt(dateArrya[1]);
        int day = Integer.parseInt(dateArrya[2]);

        switch (month) {
            case 1:// Jan
            case 3:// Mar
            case 5:// May
            case 7:// July
            case 8:// Aug
            case 10:// Oct
            case 12:// Dec
            {
                if (day > 31) {
                    status = false;
                }
                break;
            }
            case 2:// Feb
            {
                if (year % 4 == 0 && year % 100 != 0)// leap year means feb month
                // has 29days
                {
                    if (day > 29) {
                        status = false;
                    }
                } else {
                    if (day > 28) {
                        status = false;
                    }
                }
                break;
            }
            case 4:// Apr
            case 6:// Jun
            case 9:// Sep
            case 11:// Nov
            {
                if (day > 30) {
                    status = false;
                }
                break;
            }

            default:
                break;
        }

        return status;
    }

    public static boolean isValidDate(String date, boolean dOBOrIssueOrExpiry) {

        boolean status = true;

        String[] dateArray = Util.split(date, "-");
        int year = Integer.parseInt(dateArray[0]);
        int month = Integer.parseInt(dateArray[1]);
        int day = Integer.parseInt(dateArray[2]);

        Calendar currentCal = Calendar.getInstance(TimeZone.getTimeZone(("GMT+5:30")));
        currentCal.setTime(new Date());

        long currentCalInMilliSec = currentCal.getTime().getTime();// current in
        // millisec.

        Calendar userCal = Calendar.getInstance(TimeZone.getTimeZone(("GMT+5:30")));
        userCal.set(Calendar.YEAR, year);
        userCal.set(Calendar.MONTH, (month - 1));
        userCal.set(Calendar.DAY_OF_MONTH, day);
        userCal.set(Calendar.HOUR_OF_DAY, 0);
        userCal.set(Calendar.MINUTE, 0);
        userCal.set(Calendar.SECOND, 0);
        userCal.set(Calendar.MILLISECOND, 0);

        long userCalInMilliSec = userCal.getTime().getTime();// user set date.

        if (dOBOrIssueOrExpiry)// DOB and Issue date should be less than current
        // date.
        {
            if (userCalInMilliSec > currentCalInMilliSec) {
                status = false;
            }
        } else// Expire date should be greater than current date.
        {
            if (userCalInMilliSec < currentCalInMilliSec) {
                status = false;
            }
        }
        return status;
    }

    public static long getCurrentDateAndTimeInMilliSec(Date date, boolean onlyDateInMilliSec) {

        Calendar currentCal = Calendar.getInstance(TimeZone.getTimeZone(TIME_ZONE));
        currentCal.setTime(date);

        if (onlyDateInMilliSec) {
            currentCal.set(Calendar.HOUR_OF_DAY, 0);
            currentCal.set(Calendar.MINUTE, 0);
            currentCal.set(Calendar.SECOND, 0);
            currentCal.set(Calendar.MILLISECOND, 0);
        }
        return currentCal.getTime().getTime();
    }

    public static long getCurrentDateLongValue() {
        Calendar currentCal = Calendar.getInstance();
        // LOG.d("getCurrentDateLongValue",
        // "getCurrentDateLongValue"+currentCal.getTimeInMillis());

        return currentCal.getTimeInMillis();
    }

    public static long getDaysDiff(long sodDate) {

        Calendar currentCal = Calendar.getInstance(TimeZone.getTimeZone(TIME_ZONE));
        return (int) ((currentCal.getTimeInMillis() - sodDate) / (1000 * 60 * 60 * 24));
    }

    public static long getExactDateAndTimeInMilliSec(long dateAndTime, boolean onlyDateInMilliSec) {

        Calendar dateAndTimeInMilliSec = Calendar.getInstance();
        dateAndTimeInMilliSec.setTime(new Date(dateAndTime));

        if (onlyDateInMilliSec) {
            dateAndTimeInMilliSec.set(Calendar.HOUR_OF_DAY, 0);
            dateAndTimeInMilliSec.set(Calendar.MINUTE, 0);
            dateAndTimeInMilliSec.set(Calendar.SECOND, 0);
            dateAndTimeInMilliSec.set(Calendar.MILLISECOND, 0);
        }
        return dateAndTimeInMilliSec.getTime().getTime();
    }


    public static String doPadding(String value, int max, String paddingString, boolean isLeftPadding) {

        while (value.length() < max) {
            if (!isLeftPadding) {
                value = value + paddingString;
            } else {
                value = paddingString + value;
            }
        }
        return value;
    }

    public static int getDaysOfMonth(int currentMonth, int currentYear) {
        int exactDays = 0;
        switch (currentMonth) {
            case 1:// Jan
            case 3:// Mar
            case 5:// May
            case 7:// July
            case 8:// Aug
            case 10:// Oct
            case 12:// Dec
            {
                exactDays = 31;
                break;
            }
            case 4:// Apr
            case 6:// Jun
            case 9:// Sep
            case 11:// Nov
            {
                exactDays = 30;
                break;
            }
            case 2:// Feb
            {
                if (currentYear % 4 == 0 && currentYear % 100 != 0)// leap year
                // means feb
                // month has
                // 29days
                {
                    exactDays = 29;
                } else {
                    exactDays = 28;
                }
            }
            break;
        }
        return exactDays;
    }

    @SuppressWarnings("unused")
    private static String formateDate(int day, int month, int year) {

        return (day + "") + "-" + (month + "") + "-" + (year + "");
    }

    @SuppressWarnings("unused")
    private static int getOverAllDays(int day, int month, int year) {

        int totalDays = day;
        int inititalValue = month - 1;
        while (inititalValue != 0) {
            totalDays = totalDays + getDaysOfMonth(inititalValue, year);
            inititalValue--;
        }
        return totalDays;
    }

    /**
     * This method returns to gets the day time string from date.
     *
     * @param date the date
     * @return the day time string from date
     */
    public static String getDayTimeStringFromDate(Date date, boolean isTimeRequired) {

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TIME_ZONE));
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = (calendar.get(Calendar.MONTH) + 1);
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);
        // int mili = calendar.get(Calendar.MILLISECOND);
        String dayString = (day < 10) ? "0" + day : "" + day;
        String monString = (month < 10) ? "0" + month : "" + month;
        String hourStr = (hour < 10) ? "0" + hour : "" + hour;
        String minStr = (min < 10) ? "0" + min : "" + min;
        String secStr = (sec < 10) ? "0" + sec : "" + sec;

        if (isTimeRequired) {
            return dayString + "-" + monString + "-" + year + " " + hourStr + ":" + minStr + ":" + secStr/*
             * +
             * ":"
             * +
             * mili
             */;
        } else {
            return dayString + "-" + monString + "-" + year;
        }
    }

    /**
     * This method returns to gets the day time string from date.
     *
     * @param date the date
     * @return the day time string from date
     */
    public static String getTimeStringFromDate(Date date) {

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TIME_ZONE));
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);
        String hourStr = (hour < 10) ? "0" + hour : "" + hour;
        String minStr = (min < 10) ? "0" + min : "" + min;
        String secStr = (sec < 10) ? "0" + sec : "" + sec;

        return hourStr + ":" + minStr + ":" + secStr;
    }

    public static String getDayTimeStringFromDate(long timeInMillis, boolean isTimeRequired) {

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TIME_ZONE));
        calendar.setTimeInMillis(timeInMillis);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = (calendar.get(Calendar.MONTH) + 1);
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);
        // int mili = calendar.get(Calendar.MILLISECOND);
        String dayString = (day < 10) ? "0" + day : "" + day;
        String monString = (month < 10) ? "0" + month : "" + month;
        String hourStr = (hour < 10) ? "0" + hour : "" + hour;
        String minStr = (min < 10) ? "0" + min : "" + min;
        String secStr = (sec < 10) ? "0" + sec : "" + sec;

        if (isTimeRequired) {
            return dayString + "-" + monString + "-" + year + " " + hourStr + ":" + minStr + ":" + secStr/*
             * +
             * ":"
             * +
             * mili
             */;
        } else {
            return dayString + "-" + monString + "-" + year;
        }
    }

    public static long getDateAsMillisec(String date, boolean isYearFirst) {

        Calendar calendar = null;

        String[] dateArray = split(date, "-");

        calendar = Calendar.getInstance();
        if (dateArray.length >= 3) {

            if (isYearFirst) {

                calendar.set(Calendar.YEAR, Integer.parseInt(dateArray[0].trim()));
                calendar.set(Calendar.MONTH, Integer.parseInt(dateArray[1].trim()) - 1);
                calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateArray[2].trim()));
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.MILLISECOND, 0);
            } else {

                calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateArray[0].trim()));
                calendar.set(Calendar.MONTH, Integer.parseInt(dateArray[1].trim()) - 1);
                calendar.set(Calendar.YEAR, Integer.parseInt(dateArray[2].trim()));
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.MILLISECOND, 0);
            }
        }

        return calendar.getTime().getTime();
    }

    public static long getDateAndTimeInMilliSec(String dateAsString) {

        String[] dateArray = split(dateAsString, "-");// Format DD-MM-YY

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(dateArray[2].trim()));
        calendar.set(Calendar.MONTH, Integer.parseInt(dateArray[1].trim()) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateArray[0].trim()));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime().getTime();
    }

    public static byte[][] merge(byte[][] array1, byte[][] array2) {

        byte[][] mergedData = new byte[array1.length + array2.length][];
        int z = 0;
        for (int i = 0; i < array1.length; i++) {
            mergedData[z++] = array1[i];
        }
        for (int j = 0; j < array2.length; j++) {
            mergedData[z++] = array2[j];
        }

        return mergedData;
    }

    public static String getBlankStringIfNull(String data) {

        return data == null ? "" : data;
    }

    public static int parseInt(String value) {
        // return Integer.parseInt(value);

        try {

            return Integer.parseInt(value);

        } catch (NumberFormatException e) {
            // TODO: handle exception
            return 0;
        }

    }

    public static long parseLong(String value) {
        // return Integer.parseInt(value);

        try {

            return Long.parseLong(value);

        } catch (NumberFormatException e) {
            // TODO: handle exception
            return 0;
        }

    }

    public static String getCurrentDay() {

        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        String day = "";

        switch (dayOfWeek) {
            case 1:
                day = "Sunday";
                break;
            case 2:
                day = "Monday";
                break;
            case 3:
                day = "Tuesday";
                break;
            case 4:
                day = "Wednesday";
                break;
            case 5:
                day = "Thursday";
                break;
            case 6:
                day = "Friday";
                break;
            case 7:
                day = "Saturday";
                break;
        }
        return day;

    }

    public static String getCurrentDayValueByIndex(int index) {

        String day = "";

        switch (index) {
            case 1:
                day = "Sunday";
                break;
            case 2:
                day = "Monday";
                break;
            case 3:
                day = "Tuesday";
                break;
            case 4:
                day = "Wednesday";
                break;
            case 5:
                day = "Thursday";
                break;
            case 6:
                day = "Friday";
                break;
            case 7:
                day = "Saturday";
                break;
        }
        return day;

    }

    public static int getCurrentDayIndex() {

        Calendar c = Calendar.getInstance();

        return c.get(Calendar.DAY_OF_WEEK);

    }

    public static int getCurrentDateValue() {

        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getDayIndex(Date date) {

        Calendar c = Calendar.getInstance();
        if (date != null)
            c.setTime(date);

        return c.get(Calendar.DAY_OF_WEEK);

    }

    public static int getDateValue(Date date) {

        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static ArrayList<Date> getDatesBetweenSpecifiedAndCurrentDate(long fromDateTimeInMilliSecs) {

        /*
         * Date fromDate = Calendar.getInstance().getTime(); try { fromDate =
         * simpleDateFormat.parse(fromDateString); } catch (ParseException e) {
         * // TODO Auto-generated catch block e.printStackTrace(); }
         */
        Date toDate = Calendar.getInstance().getTime();
        ArrayList<Date> dates = new ArrayList<Date>();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(fromDateTimeInMilliSecs);
        while (cal.getTime().before(toDate)) {
            cal.add(Calendar.DATE, 1);
            dates.add(cal.getTime());
        }
        return dates;
    }

    public static List<Date> getDatesBetween(long date1, long date2, int weekDayToFetchDates) {
        List<Date> dates = new ArrayList<Date>();
        long interval = 14 * 24 * 1000 * 60 * 60; // 14 days in milliseconds
        long oneDayInterval = 24 * 1000 * 60 * 60;
        long endTime = date2;//endDate.getTime() ;
        long curTime = date1;//startDate.getTime();

        while (curTime <= endTime) {
            Calendar cal = Calendar.getInstance(TimeZone.getDefault());
            cal.setTimeInMillis(curTime);
            if (cal.get(Calendar.DAY_OF_WEEK) != weekDayToFetchDates) {
                String ds = simpleDateFormat.format(new Date(curTime));
                System.out.println("Ignored Dates : " + ds);
                curTime += oneDayInterval;
            } else {
                dates.add(new Date(curTime));
                curTime += interval;
            }
        }
        return dates;
    }

    public static boolean isTodayMeetingForFortnightlyFrequency(long groupRegisteredDate, long tillDate, int groupMeetingDay) {
        if (groupRegisteredDate > 0) {
            List<Date> listOfMeetingDatesForGroup = getDatesBetween(groupRegisteredDate, tillDate, groupMeetingDay);
            String currentDate = simpleDateFormat.format(new Date(tillDate));
            String frequencyDate = simpleDateFormat.format(new Date(listOfMeetingDatesForGroup.get(listOfMeetingDatesForGroup.size() - 1).getTime()));

            LOG.d(TAG, "isTodayMeetingForFortnightlyFrequency : frequencyDate : " + frequencyDate + " : currentDate : " + currentDate);
            return frequencyDate.equalsIgnoreCase(currentDate);
        } else {
            return false;
        }
    }

    public static String convertLongTodate(long dateValue) {
        return simpleDateFormat.format(new Date(dateValue));
    }

    public static String getCurrentDateString() {

        return simpleDateFormat.format(Calendar.getInstance().getTime());
    }

    public static Double parseDouble(String value) {
        // return Double.parseDouble(value);

        try {

            /*
             * BigDecimal roundOff = new BigDecimal(Double.parseDouble(value))
             * .setScale(2, BigDecimal.ROUND_HALF_EVEN);
             */
            return getRoundedAmount(Double.parseDouble(value));

        } catch (NumberFormatException e) {
            // TODO: handle exception
            return 0.00;
        } catch (Exception e) {
            // TODO: handle exception
            return 0.00;
        }

    }

    public static int compareDateWithCurrentDate(Date dateObj) {

        // SimpleDateFormat sdf = new
        // SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());

        Calendar c = Calendar.getInstance();

        Date currentDate = c.getTime();

        String dateString = simpleDateFormat.format(dateObj);
        String currentDateString = simpleDateFormat.format(currentDate);

        int value = -1;

        try {
            value = simpleDateFormat.parse(dateString).compareTo(simpleDateFormat.parse(currentDateString));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return -100;
        }

        return value;

    }

    /**
     * @param dateInMilliSecs - a long value of Date to compare against.
     * @return an int < 0 if this Date is less than the specified Date, 0 if they are equal, and an int > 0 if this Date is greater.
     */
    public static int compareDateWithCurrentDate(long dateInMilliSecs) {

        // SimpleDateFormat sdf = new
        // SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        // LOG.d("compareDateWithCurrentDate",
        // "getCurrentDateLongValue"+dateInMilliSecs);

        Calendar c1 = Calendar.getInstance();

        c1.setTimeInMillis(dateInMilliSecs);

        return compareDateWithCurrentDate(simpleDateFormat.format(c1.getTime()));

    }

    public static String getDateString(long dateInMilliSecs) {

        return simpleDateFormat.format(new Date(dateInMilliSecs));
    }

    public static int compareDateWithCurrentDate(String dateString) {

        // SimpleDateFormat sdf = new
        // SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());

        Calendar c = Calendar.getInstance();

        Date currentDate = c.getTime();

        String currentDateString = simpleDateFormat.format(currentDate);

        int value = -1;

        try {

            value = simpleDateFormat.parse(dateString).compareTo(simpleDateFormat.parse(currentDateString));

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return -100;
        }

        return value;

    }

    public static int compareDates(Date toDate, Date fromDate) {

        int retVal = -100;
        if (fromDate != null && toDate != null) {
            retVal = fromDate.compareTo(toDate);

        }
        return retVal; // date1 is less than date2
    }

    public static int compareDates(String toDate, String fromDate) {

        if (fromDate != null && toDate != null) {
            int retVal = fromDate.compareTo(toDate);

            if (retVal > 0)
                return 1; // date1 is greatet than date2
            else if (retVal == 0) // both dates r equal
                return 0;

        }
        return -1; // date1 is less than date2
    }

    public static int getStringIndexFromStringArray(String str, String[] strArray) {

        List<String> programmingList = Arrays.asList(strArray);

        return programmingList.indexOf(str);

    }

    public static String getStringValue(double value) {

        String stringValue = String.valueOf(value);

        return stringValue;
    }

    public static boolean isStringExistedInArray(String str, String[] strArray) {

        List<String> programmingList = Arrays.asList(strArray);

        return programmingList.contains(str);

    }

    public static String getFormattedAmount(String amount) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(Util.parseDouble(amount));
    }

    /*
     * public static boolean isSODDoneTodayOnly(){

     *
     * long lastSodDateTime =
     * Long.parseLong(BusinessController.getInstance().getValue
     * ("LAST_SOD_DATE_TIME")); Calendar c = Calendar.getInstance();
     * c.setTimeInMillis(lastSodDateTime); String sodDay =
     * Util.getDayOfWeekFromDate(c);
     *
     * return sodDay.equals(getCurrentDay());
     *
     * }
     */

    public static String getDayOfWeekFromDate(Calendar c) {
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        String day = "";

        switch (dayOfWeek) {
            case 1:
                day = "Sunday";
                break;
            case 2:
                day = "Monday";
                break;
            case 3:
                day = "Tuesday";
                break;
            case 4:
                day = "Wednesday";
                break;
            case 5:
                day = "Thursday";
                break;
            case 6:
                day = "Friday";
                break;
            case 7:
                day = "Saturday";
                break;
        }
        return day;
    }

    public static String getDayOfWeekFromDayIndex(int dayOfWeek) {

        String day = "";

        switch (dayOfWeek) {
            case 1:
                day = "SUN";
                break;
            case 2:
                day = "MON";
                break;
            case 3:
                day = "TUE";
                break;
            case 4:
                day = "WED";
                break;
            case 5:
                day = "THU";
                break;
            case 6:
                day = "FRI";
                break;
            case 7:
                day = "SAT";
                break;
        }
        return day;
    }

    // public static ArrayList<String> getAccountNumberList(Hashtable
    // accountNumberList) {
    //
    // ArrayList<String> accList = new ArrayList<String>();
    // // String[] accountNumberList = new
    // String[this.accountNumberList.size()];
    // int i = 0;
    // for (Enumeration e = accountNumberList.elements(); e.hasMoreElements();)
    // {
    //
    // AccountDetails accountDetails = (AccountDetails)e.nextElement();
    // accList.add(accountDetails.accountNumber);
    // //accountNumberList[i++] = accountDetails.accountNumber;
    // }
    // return accList;
    // }

    public static boolean compareWithCurrentDate(String date, boolean isYYMMDD) {

        boolean status = true;

        String[] dateArray = Util.split(date, "-");

        int year, month, day;

        if (!isYYMMDD) {
            year = Integer.parseInt(dateArray[2]);
            month = Integer.parseInt(dateArray[1]);
            day = Integer.parseInt(dateArray[0]);
        } else {
            year = Integer.parseInt(dateArray[0]);
            month = Integer.parseInt(dateArray[1]);
            day = Integer.parseInt(dateArray[2]);
        }

        Calendar currentCal = Calendar.getInstance(TimeZone.getTimeZone(("GMT+5:30")));
        currentCal.setTime(new Date());
        currentCal.set(Calendar.HOUR_OF_DAY, 0);
        currentCal.set(Calendar.MINUTE, 0);
        currentCal.set(Calendar.SECOND, 0);
        currentCal.set(Calendar.MILLISECOND, 0);

        long currentCalInMilliSec = currentCal.getTime().getTime();// current in
        // millisec.

        Calendar userCal = Calendar.getInstance(TimeZone.getTimeZone(("GMT+5:30")));
        userCal.set(Calendar.YEAR, year);
        userCal.set(Calendar.MONTH, (month - 1));
        userCal.set(Calendar.DAY_OF_MONTH, day);
        userCal.set(Calendar.HOUR_OF_DAY, 0);
        userCal.set(Calendar.MINUTE, 0);
        userCal.set(Calendar.SECOND, 0);
        userCal.set(Calendar.MILLISECOND, 0);

        long userCalInMilliSec = userCal.getTime().getTime();// user set date.

        if (userCalInMilliSec < currentCalInMilliSec) {
            status = false;
        }
        return status;
    }

    public static Vector<String[]> copyVectorOfArrayToVectorOfArray(Vector<String[]> destination, Vector<String[]> source) {

        if (source != null && source.size() != 0) {
            String[] out = null;
            for (String[] value : source) {
                if (out != null)
                    out = null;

                out = new String[value.length];

                System.arraycopy(value, 0, out, 0, value.length);
                destination.add(out);
            }
            return destination;
        }
        return destination;
    }

    public static Vector<String> copyVectorToVector(Vector<String> destination, Vector<String> source) {

        if (source != null && source.size() != 0) {
            for (String value : source) {
                destination.add(value);
            }
            return destination;
        }
        return destination;
    }

    public static String[] addStringToArray(String[] strings, String newString) {

        String[] newStrings = new String[strings.length + 1];
        for (int i = 0; i < strings.length; i++) {

            newStrings[i] = strings[i];
        }
        newStrings[newStrings.length - 1] = newString;

        return newStrings;
    }

    public static String getKeyByValue(HashMap<String, String> hm, String value) {
        for (String o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return "1";
    }

	/*public static int getSizeInFormat(long dataLen) {

		int dataSize = ApplicationConstants.BYTE_DATA;

		if (dataLen < 1024) { // less than 1KB
			dataSize = ApplicationConstants.LESS_THAN_KB_DATA;
			LOG.i("Util", "Less than 1KB");
		}
		if (dataLen > 1024) { // more than 1KB
			dataSize = ApplicationConstants.KB_DATA;
			LOG.i("Util", "More than 1KB");
		}
		if (dataLen < 1024 * 1024) { // less than 1MB
			dataSize = ApplicationConstants.LESS_THAN_MB_DATA;
			LOG.i("Util", "Less than 1MB");
		}
		if (dataLen > 1024 * 1024) { // more than 1MB
			dataSize = ApplicationConstants.MB_DATA;
			LOG.i("Util", "More than 1MB");
		}

		return dataSize;
	}*/

    public static boolean validateEmail(String emailAddress) {

        if (emailAddress == null || (emailAddress != null && emailAddress.equals("")))
            return true;

        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(emailAddress);

        return matcher.matches();
    }

    public static List<String> getValidEmails(List<String> emails) {

        if (emails == null || (emails != null && emails.size() == 0))
            return null;

        List<String> newList = new ArrayList<String>();

        for (String emailAddress : emails) {
            if (validateEmail(emailAddress))
                newList.add(emailAddress);
        }

        return newList;
    }

    public static Bitmap convertByteArryToBitmap(byte[] imageData) {
        Bitmap bmp = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);

        return bmp;

    }


	/*public static Bitmap convertByteArryToBitmap(byte[] imageData) {

		BitmapFactory.Options options = new BitmapFactory.Options();

		Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length, options);


		int actualHeight = options.outHeight;
		int actualWidth = options.outWidth;

		float maxHeight = 816.0f;
		float maxWidth = 612.0f;
		float imgRatio = actualWidth / actualHeight;
		float maxRatio = maxWidth / maxHeight;


		if (actualHeight > maxHeight || actualWidth > maxWidth) {
			if (imgRatio < maxRatio) {
				imgRatio = maxHeight / actualHeight;
				actualWidth = (int) (imgRatio * actualWidth);
				actualHeight = (int) maxHeight;
			} else if (imgRatio > maxRatio) {
				imgRatio = maxWidth / actualWidth;
				actualHeight = (int) (imgRatio * actualHeight);
				actualWidth = (int) maxWidth;
			} else {
				actualHeight = (int) maxHeight;
				actualWidth = (int) maxWidth;

			}
		}

		System.out.println("convertByteArryToBitmap ***************************");

		options.inSampleSize = Util.calculateInSampleSize(options, actualWidth, actualHeight);
		options.inJustDecodeBounds = false;

		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inTempStorage = new byte[16 * 1024];

		bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length, options);

		Bitmap scaledBitmap = null;
		try {
			scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
		} catch (OutOfMemoryError exception) {
			exception.printStackTrace();
		}

		float ratioX = actualWidth / (float) options.outWidth;
		float ratioY = actualHeight / (float) options.outHeight;
		float middleX = actualWidth / 2.0f;
		float middleY = actualHeight / 2.0f;

		Matrix scaleMatrix = new Matrix();
		scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

		Canvas canvas = new Canvas(scaledBitmap);
		canvas.setMatrix(scaleMatrix);
		canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

		Matrix matrix = new Matrix();
		scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

		FileOutputStream out = null;
        String filename = "/mnt/sdcard/AltifinGmt/temp.jpg";
        Bitmap newScaledBitmap=null;
        try {
            out = new FileOutputStream(filename);

            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            newScaledBitmap = BitmapFactory.decodeFile(filename);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

		System.out.println("Util Width KYC :" + bitmap.getWidth());
		System.out.println("Util Height KYC:" + bitmap.getHeight());
		System.out.println("Util Width scaledBitmap KYC :" + scaledBitmap.getWidth());
		System.out.println("Util Height scaledBitmap KYC:" + scaledBitmap.getHeight());

		return newScaledBitmap;
	}*/


    public static String scaleAndSaveByteArry(byte[] imageData, File fileToSave) {

        BitmapFactory.Options options = new BitmapFactory.Options();

        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }
        options.inSampleSize = Util.calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length, options);

        Bitmap scaledBitmap = null;
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        Matrix matrix = new Matrix();
        scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);

        FileOutputStream fout = null;
        try {

            if (fileToSave.exists()) {
                fileToSave.delete();
            } else {
                fileToSave.createNewFile();
            }

            fout = new FileOutputStream(fileToSave);
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fout);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Util Width KYC :" + bitmap.getWidth());
        System.out.println("Util Height KYC:" + bitmap.getHeight());
        System.out.println("Util Width scaledBitmap KYC :" + scaledBitmap.getWidth());
        System.out.println("Util Height scaledBitmap KYC:" + scaledBitmap.getHeight());
        return fileToSave.getAbsolutePath();
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        System.out.println("height :" + height);
        System.out.println("width :" + width);

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        System.out.println("Util inSampleSize :" + inSampleSize);

        return inSampleSize;
    }
    /**
     * @param photoFile
     * @param photoData
     * @return String Photofile AbsolutePath
     * @throws MWException
     *//*
    public static String savePhotoFile(File photoFile, byte[] photoData) throws MWException {

		BufferedOutputStream bfOutStream = null;
		FileOutputStream fout = null;
		try {

			if (photoFile.exists()) {
				photoFile.delete();
			} else {
				photoFile.createNewFile();
			}

			fout = new FileOutputStream(photoFile);

			bfOutStream = new BufferedOutputStream(fout);
			bfOutStream.write(photoData);
			bfOutStream.flush();

			return photoFile.getAbsolutePath();

		} catch (Exception e) {
			throw new MWException(MWException.PHOTO_ACCESS_ERROR, e.getMessage());
		} finally {
			try {
				bfOutStream.close();
				fout.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new MWException(MWException.PHOTO_ACCESS_ERROR, e.getMessage());
			}
		}
	}*/

    /**
     * public static byte[] getPhotoData(String photoFilePath) throws NhanceException {
     * <p/>
     * LOG.d("getPhotoData", " getPhotoData   --->  " + photoFilePath);
     * File fpFile = null;
     * FileInputStream fis = null;
     * BufferedInputStream bis = null;
     * <p/>
     * try {
     * byte[] photoData = null;
     * <p/>
     * fpFile = new File(photoFilePath);
     * <p/>
     * if (fpFile.exists()) {
     * fis = new FileInputStream(fpFile);
     * bis = new BufferedInputStream(fis);
     * <p/>
     * photoData = new byte[bis.available()];
     * bis.read(photoData);
     * return photoData;
     * } else {
     * <p/>
     * LOG.d("getPhotoData", " file not exists " + photoFilePath);
     * throw new MWException(MWException.PHOTO_ACCESS_ERROR);
     * }
     * } catch (Exception e) {
     * e.printStackTrace();
     * throw new MWException(MWException.PHOTO_ACCESS_ERROR, e.toString());
     * } finally {
     * try {
     * if (fis != null) {
     * fis.close();
     * }
     * if (bis != null) {
     * bis.close();
     * }
     * } catch (IOException e) {
     * }
     * }
     * }
     */
    public static String getFormattedAmount(double amount) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(amount);
    }

    public static int getFractionalValueFromDouble(double value) {

        String s = value + "";

        return Util.parseInt(s.substring(s.indexOf(".") + 1));

    }

    public static double getMainValueFromDouble(double value) {

        String s = value + "";

        return Util.parseDouble(s.substring(0, s.indexOf(".")));

    }

    /*	*/

    /**
     * Gets the rounded amount.
     *
     * @param amount the amount
     * @return the rounded amount
     */
    public static Double getRoundedAmount(Double amount) {
        DecimalFormat df = new DecimalFormat("###.00");
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        return Double.parseDouble(df.format(amount));
    }

    public static String getDoubleValueWith2Digits(Double amount){
        DecimalFormat df = new DecimalFormat("###.00");
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        return df.format(amount);
    }


    public static Double getRoundedCeilingAmount(Double amount) {
        DecimalFormat df = new DecimalFormat("###.00");
        df.setMaximumFractionDigits(0);
        df.setRoundingMode(RoundingMode.CEILING);
        return parseDouble(df.format(amount));
    }

    /**
     * Gets the rounded ceiling amount.
     *
     * @param amount the amount
     * @return the rounded ceiling amount
     */
    public static Double getRoundedUPAmount(Double amount) {
        DecimalFormat df = new DecimalFormat("###.00");
        df.setMaximumFractionDigits(0);
        df.setRoundingMode(RoundingMode.UP);
        return parseDouble(df.format(amount));
    }

    /**
     * Gets the rounded ceiling amount.
     *
     * @param amount the amount
     * @return the rounded ceiling amount
     */
    public static Double getRoundedHalfEvenAmount(Double amount) {
        DecimalFormat df = new DecimalFormat("###.00");
        df.setMaximumFractionDigits(0);
        df.setRoundingMode(RoundingMode.HALF_EVEN);
        return parseDouble(df.format(amount));
    }

    /**
     * Slipt interest component.
     *
     * @param baseInterestRate     the base interest rate
     * @param marginalInterestRate the marginal interest rate
     * @param interestAmount       the interest amount
     * @return the double[]
     */
    public static Double[] sliptInterestComponent(Double baseInterestRate, Double marginalInterestRate, Double interestAmount) {
        Double[] amounts = new Double[2];
        Double baseInterestAmount = (interestAmount * baseInterestRate) / (baseInterestRate + marginalInterestRate);
        baseInterestAmount = getRoundedAmount(baseInterestAmount);
        if (baseInterestAmount.isNaN()) {

            baseInterestAmount = 0d;
        }
        Double marginalInterestAmount = getRoundedAmount(interestAmount - baseInterestAmount);
        amounts[0] = baseInterestAmount;
        amounts[1] = marginalInterestAmount;
        return amounts;
    }

    /**
     * Extract only date.
     *
     * @param date the date
     * @return the date
     */
    public static Date extractOnlyDate(Date date) {
        String dateFormatStr = DateUtil.DATE_FORMAT_dd_MM_yyyy_SEP_HIPHEN;
        String dateStr = DateUtil.convertDateToDateStr(date, dateFormatStr);
        return DateUtil.convertDateStrToDate(dateStr, dateFormatStr);
    }

    public static String getFormattedString(String actualString, int stringLength) {

        if (actualString.length() >= stringLength)
            return actualString;

        while (actualString.length() < stringLength)
            actualString = actualString + " ";

        return actualString;
    }

    public static byte[] convertBitmapToByteArray(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;

    }

    public static boolean isFirmwareNewer(String newVersion, String oldVersion) {

        int[] testVer = getVersionNumbers(newVersion);
        int[] baseVer = getVersionNumbers(oldVersion);

        for (int i = 0; i < testVer.length; i++)
            if (testVer[i] != baseVer[i])
                return testVer[i] > baseVer[i];

        return true;
    }

    private static int[] getVersionNumbers(String ver) {
        Matcher m = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)(beta(\\d*))?")
                .matcher(ver);
        if (!m.matches())
            throw new IllegalArgumentException("Malformed FW version");

        return new int[]{Integer.parseInt(m.group(1)),  // major
                Integer.parseInt(m.group(2)),             // minor
                Integer.parseInt(m.group(3)),             // rev.
                m.group(4) == null ? Integer.MAX_VALUE    // no beta suffix
                        : m.group(5).isEmpty() ? 1        // "beta"
                        : Integer.parseInt(m.group(5))    // "beta3"
        };
    }

    public static List<String[]> getParsedResponse(String actualResponse) {

        List<String[]> parsedListArray = new ArrayList<String[]>();

        String[] splittedArray = actualResponse.split("\n");
        System.out.println("Test : splittedArray : " + splittedArray);
        for (int i = 0; i < splittedArray.length; i++) {
            String individual = splittedArray[i].trim();
            System.out.println("position at " + i + ")" + individual);
            String[] individualArray = individual.split(":");
            parsedListArray.add(individualArray);
            for (int j = 0; j < individualArray.length; j++)
                System.out.println("inner position at " + j + ")" + individualArray[j].trim());
        }

        return parsedListArray;
    }

    public static int getSizeInFormat(long dataLen) {

        int dataSize = ApplicationConstants.BYTE_DATA;

        if (dataLen < 1024) { //less than 1KB
            dataSize = ApplicationConstants.LESS_THAN_KB_DATA;
            LOG.i("Util", "Less than 1KB");
        }
        if (dataLen > 1024) { //more than 1KB
            dataSize = ApplicationConstants.KB_DATA;
            LOG.i("Util", "More than 1KB");
        }
        if (dataLen < 1024 * 1024) { //less than 1MB
            dataSize = ApplicationConstants.LESS_THAN_MB_DATA;
            LOG.i("Util", "Less than 1MB");
        }
        if (dataLen > 1024 * 1024) { //more than 1MB
            dataSize = ApplicationConstants.MB_DATA;
            LOG.i("Util", "More than 1MB");
        }

        return dataSize;
    }

    /**
     * This method is used to validate entered email id.
     */
    public static boolean isValidEmaillId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    public static boolean isAlphaNumericExists(String s) {
       /* String pattern= "^[a-zA-Z0-9]*$";
        if(s.matches(pattern)){
            return true;
        }
        return false;*/
        boolean status = false;

        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (Character.isDigit(c) || Character.isLetter(c)) {
                status = true;
                break;
            }
        }

        return status;
    }

    public static boolean isStartsWithSpecialChar(String s) {
        boolean status = true;

        char[] chars = s.toCharArray();
        char c = chars[0];
        if (Character.isDigit(c) || Character.isLetter(c)) {
            status = false;
        }

        return status;
    }

    public static boolean isSpecialCharAdded(String email) {

        if (email.startsWith("@") || email.startsWith(".") || email.startsWith("_"))
            return false;
        else return !(email.endsWith("@") || email.endsWith(".") || email.endsWith("_"));
    }

    public static Drawable changeIconColor(Context mContext, int drawable, int color) {
        Drawable mDrawable = ContextCompat.getDrawable(mContext, drawable);
        mDrawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
        return mDrawable;
    }

    public static Bitmap changeImageColor(Bitmap sourceBitmap, int color) {
        Bitmap resultBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0,
                sourceBitmap.getWidth() - 1, sourceBitmap.getHeight() - 1);
        Paint p = new Paint();
        ColorFilter filter = new LightingColorFilter(color, 3);
        p.setColorFilter(filter);

        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(resultBitmap, 0, 0, p);
        return resultBitmap;
    }

    public static Drawable covertBitmapToDrawable(Context context, Bitmap bitmap) {
        Drawable d = new BitmapDrawable(context.getResources(), bitmap);
        return d;
    }

    public static Bitmap convertDrawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static TextView getActionBarTextView(Toolbar mToolBar) {
        TextView titleTextView = null;

        try {
            Field f = mToolBar.getClass().getDeclaredField("mTitleTextView");
            f.setAccessible(true);
            titleTextView = (TextView) f.get(mToolBar);
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }
        return titleTextView;
    }

    public static String getAppVersion(Context mContext) {
        PackageInfo pInfo = null;
        try {
            pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = "v" + pInfo.versionName;

        return version;
    }

    public static String getAppVersionFor(Context mContext) {
        PackageInfo pInfo = null;
        try {
            pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = pInfo.versionName;

        return version;
    }

    public static void disableView(View v) {

        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i);
                disableView(child);
            }
        }
    }

    public static void enableView(View v) {

        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i);
                enableView(child);
            }
        }
    }

    /*public static int getAppRes(Context context, String folderName, String resourceName) {
        int id = 0;
        try {

            String packageName = NhanceApplication.packageNhanceDemo;
            PackageManager manager = context.getPackageManager();
            Resources res = null;
            try {
                res = manager.getResourcesForApplication(packageName);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            id = res.getIdentifier(resourceName, folderName, packageName);
            if (folderName.equals("styleable")) {
                try {
                    id = res.getIdentifier("" + Class.forName(context.getPackageName() + ".R$styleable").getField(resourceName).get(null), null, null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            if (folderName.equals("style")) {
                try {
                    id = res.getIdentifier("" + Class.forName(context.getPackageName() + ".R$style").getField(resourceName).get(null), null, null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }*/

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static int generateRandom() {
        Random random = new Random();
        return random.nextInt(999999999 - 100000000) + 100000000;
    }

    public static int generateIncrementalNumber() {
        int incrementNum = AccessPreferences.get(HapisApplication.getContext(), ApplicationConstants.INCREMENTAL_NUMBER, 0);
        incrementNum = incrementNum + 1;
        AccessPreferences.put(HapisApplication.getContext(), ApplicationConstants.INCREMENTAL_NUMBER, incrementNum);
        return incrementNum;
    }

    public static String loggerTimeCurrent(long currentMilliseconds) {
        String loggerTime = "";
        loggerTime = DateUtil.convertTimeInMillisToDateStr(currentMilliseconds, DateUtil.DATE_FORMAT_HH_mm_ss);
        return loggerTime;
    }

    public static String greetingsBasedOnDeviceTime() {
        String greetings = "";
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 4 && timeOfDay < 12) {
            greetings = "Good Morning";
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            greetings = "Good Afternoon";
        } else if (timeOfDay >= 16 && timeOfDay < 24) {
            greetings = "Good Evening";
        } else if (timeOfDay >= 0 && timeOfDay < 4) {
            greetings = "Good Evening";
        }
        return greetings;
    }

    private static int startWidth = 0;
    private static int endWidth = 0;
    private static int startHeight = 0;
    private static int endHeight = 0;

    public static Bitmap trimBitmapWhiteSpaces(final Bitmap bmp) {
        final int imgHeight = bmp.getHeight();
        final int imgWidth = bmp.getWidth();

        int corePoolSize = 10;
        int maxPoolSize = 10;
        long keepAliveTime = 5000;
        LinkedBlockingQueue lbq = new LinkedBlockingQueue<Runnable>();
        ExecutorService threadPoolExecutor =
                new ThreadPoolExecutor(
                        corePoolSize,
                        maxPoolSize,
                        keepAliveTime,
                        TimeUnit.MILLISECONDS,
                        lbq);

        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 4; i++) {
                    switch (i) {
                        case 0:
                            //TRIM WIDTH - LEFT
                            // int startWidth = 0;
                            for (int x = 0; x < imgWidth; x++) {
                                if (startWidth == 0) {
                                    for (int y = 0; y < imgHeight; y++) {
                                        if (bmp.getPixel(x, y) != Color.WHITE) {
                                            startWidth = x;
                                            break;
                                        }
                                    }
                                } else break;
                            }
                            break;
                        case 1:
                            //TRIM WIDTH - RIGHT
                            // int endWidth = 0;
                            for (int x = imgWidth - 1; x >= 0; x--) {
                                if (endWidth == 0) {
                                    for (int y = 0; y < imgHeight; y++) {
                                        if (bmp.getPixel(x, y) != Color.WHITE) {
                                            endWidth = x;
                                            break;
                                        }
                                    }
                                } else break;
                            }
                            break;
                        case 2:
                            //TRIM HEIGHT - TOP
                            // int startHeight = 0;
                            for (int y = 0; y < imgHeight; y++) {
                                if (startHeight == 0) {
                                    for (int x = 0; x < imgWidth; x++) {
                                        if (bmp.getPixel(x, y) != Color.WHITE) {
                                            startHeight = y;
                                            break;
                                        }
                                    }
                                } else break;
                            }
                            break;
                        case 3:
                            //TRIM HEIGHT - BOTTOM
                            // int endHeight = 0;
                            for (int y = imgHeight - 1; y >= 0; y--) {
                                if (endHeight == 0) {
                                    for (int x = 0; x < imgWidth; x++) {
                                        if (bmp.getPixel(x, y) != Color.WHITE) {
                                            endHeight = y;
                                            break;
                                        }
                                    }
                                } else break;
                            }
                            break;
                    }
                }
            }
        });

        return Bitmap.createBitmap(
                bmp,
                startWidth,
                startHeight,
                endWidth - startWidth,
                endHeight - startHeight
        );
    }

    /**
     * Converting ISO8601 formatted duration to normal readable time
     */
    public static String convertISO8601DurationToNormalTime(String isoTime) {
        String formattedTime = new String();

        if (isoTime.contains("H") && isoTime.contains("M") && isoTime.contains("S")) {
            String hours = isoTime.substring(isoTime.indexOf('T') + 1, isoTime.indexOf('H'));
            String minutes = isoTime.substring(isoTime.indexOf('H') + 1, isoTime.indexOf('M'));
            String seconds = isoTime.substring(isoTime.indexOf('M') + 1, isoTime.indexOf('S'));
            formattedTime = hours + ":" + formatTo2Digits(minutes) + ":" + formatTo2Digits(seconds);
        } else if (!isoTime.contains("H") && isoTime.contains("M") && isoTime.contains("S")) {
            String minutes = isoTime.substring(isoTime.indexOf('T') + 1, isoTime.indexOf('M'));
            String seconds = isoTime.substring(isoTime.indexOf('M') + 1, isoTime.indexOf('S'));
            formattedTime = minutes + ":" + formatTo2Digits(seconds);
        } else if (isoTime.contains("H") && !isoTime.contains("M") && isoTime.contains("S")) {
            String hours = isoTime.substring(isoTime.indexOf('T') + 1, isoTime.indexOf('H'));
            String seconds = isoTime.substring(isoTime.indexOf('H') + 1, isoTime.indexOf('S'));
            formattedTime = hours + ":00:" + formatTo2Digits(seconds);
        } else if (isoTime.contains("H") && isoTime.contains("M") && !isoTime.contains("S")) {
            String hours = isoTime.substring(isoTime.indexOf('T') + 1, isoTime.indexOf('H'));
            String minutes = isoTime.substring(isoTime.indexOf('H') + 1, isoTime.indexOf('M'));
            formattedTime = hours + ":" + formatTo2Digits(minutes) + ":00";
        } else if (!isoTime.contains("H") && !isoTime.contains("M") && isoTime.contains("S")) {
            String seconds = isoTime.substring(isoTime.indexOf('T') + 1, isoTime.indexOf('S'));
            formattedTime = "0:" + formatTo2Digits(seconds);
        } else if (!isoTime.contains("H") && isoTime.contains("M") && !isoTime.contains("S")) {
            String minutes = isoTime.substring(isoTime.indexOf('T') + 1, isoTime.indexOf('M'));
            formattedTime = minutes + ":00";
        } else if (isoTime.contains("H") && !isoTime.contains("M") && !isoTime.contains("S")) {
            String hours = isoTime.substring(isoTime.indexOf('T') + 1, isoTime.indexOf('H'));
            formattedTime = hours + ":00:00";
        }

        return formattedTime;
    }

    /**
     * Makes values consist of 2 letters "01"
     */
    private static String formatTo2Digits(String str) {
        if (str.length() < 2) {
            str = "0" + str;
        }
        return str;
    }


    /*public static class trimBitmapWhiteSpacesAsync extends AsyncTask<Bitmap, Void, Bitmap> {
        private ImageView mImageView;
        private final WeakReference<ImageView> imageViewReference;

        public trimBitmapWhiteSpacesAsync(ImageView imageView) {
            mImageView = imageView;
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mImageView.setImageResource(R.drawable.no_product_img);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        protected Bitmap doInBackground(Bitmap... params) {
            Bitmap bitmap = trimBitmapWhiteSpaces(params[0]);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (isCancelled()) {
                bitmap = null;
            }
            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    mImageView.setImageBitmap(bitmap);
                }
            }
        }
    }*/

    public static float convertPixelsToDp(float px) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return Math.round(dp);
    }

    public static float convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

    public static int convertDpToPx(int dp) {
        return Math.round(dp * (HapisApplication.getContext().getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));

    }

    public static int convertPxToDp(int px) {
        return Math.round(px / (Resources.getSystem().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static float dpFromPx(float px) {
        return px / HapisApplication.getContext().getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(float dp) {
        return dp * HapisApplication.getContext().getResources().getDisplayMetrics().density;
    }

    /*public static void showAppForceUpdateDialog(final Context context) {
        OnClickListener clickListener = new OnClickListener() {
            @Override
            public void onClick(DialogPlus dialog, View view) {

                switch (view.getId()) {
                    case R.id.positive_btn: {
                        dialog.dismiss();
                        launchPlayStore(context);
                        break;
                    }
                }
            }
        };
        AlertUtil.showAlert(context, R.string.force_update, context.getString(R.string.force_update_msg), context.getString(R.string.update), clickListener, DialogIconCodes.DIALOG_FAILED.getIconCode());
    }

    public static void showTokenExpiredDialog(final Context context, String message) {
        OnClickListener clickListener = new OnClickListener() {
            @Override
            public void onClick(DialogPlus dialog, View view) {

                switch (view.getId()) {
                    case R.id.positive_btn: {
                        dialog.dismiss();
                        launchLoginScreen(context);
                        break;
                    }
                }
            }
        };
        AlertUtil.showAlert(context, R.string.session_expired, (message != null ? message : context.getString(R.string.session_expired_message)), context.getString(R.string.ok_label), clickListener, DialogIconCodes.DIALOG_FAILED.getIconCode());
    }

    private static void launchLoginScreen(Context context) {
        AccessPreferences.put(context, ApplicationConstants.NHANCE_JWT_HEADER, null);
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        try {
            if (context instanceof BaseFragmentActivity)
                ((BaseFragmentActivity) context).finishAffinity();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            HapisApplication.applicationDataBase.resetAllTables();
        } catch (NhanceException e) {
            e.printStackTrace();
        }
    }*/

    public static void launchPlayStore(Context context) {
        Intent intent = null;
        String packageName = "com.nhance.b2c";
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id=" + packageName));
            context.startActivity(intent);
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
        }
    }

    public static String getCurrencySymbolFromUniCode(String unicodeValue) {
        String currencySymbol = "";
        if (unicodeValue != null) {
            if (unicodeValue.contains(",")) {
                StringTokenizer st = new StringTokenizer(unicodeValue.trim(), ",");
                while (st.hasMoreTokens()) {
                    currencySymbol += new String(Character.toChars(Integer.parseInt(st.nextToken().trim(), 16)));
                }
            } else {
                currencySymbol = new String(Character.toChars(Integer.parseInt(unicodeValue.trim(), 16)));
            }
        }
        return currencySymbol;
    }

    public static String getFormattedAddress(EnterpriseAddressRequest selectedAddress) {

        String completeAddress = null;

        if (selectedAddress != null) {
            StringBuilder stringBuilder = new StringBuilder();
            if (selectedAddress.getAddressLine1() != null && selectedAddress.getAddressLine1().length() > 0)
                stringBuilder.append(selectedAddress.getAddressLine1() + ", ");
            if (selectedAddress.getAddressLine2() != null && selectedAddress.getAddressLine2().length() > 0)
                stringBuilder.append(selectedAddress.getAddressLine2() + ", ");
            if (selectedAddress.getCity() != null && selectedAddress.getCity().length() > 0)
                stringBuilder.append(selectedAddress.getCity() + ", ");
            if (selectedAddress.getDistrict() != null && selectedAddress.getDistrict().length() > 0)
                stringBuilder.append(selectedAddress.getDistrict() + ", ");
            if (selectedAddress.getPostalCode() != null && selectedAddress.getPostalCode().length() > 0)
                stringBuilder.append(" - " + selectedAddress.getPostalCode());

            completeAddress = stringBuilder.toString();
        }

        return completeAddress;
    }
}
