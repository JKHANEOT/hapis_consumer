package com.hapis.customer.logger;

import android.util.Log;

/*
*
* Id: LOG
*
* Author
* JKHAN Created
*/
public class LOG {

    /**
     * Priority constant for the println method
     * As per requirment LOGLEVEL should be set
     * If no logs are required then LOGLEVEL should be set as NO_LOG while initializing 
     */
    public static final int VERBOSE = Log.VERBOSE;
    public static final int DEBUG = Log.DEBUG;
    public static final int INFO = Log.INFO;
    public static final int WARN = Log.WARN;
    public static final int ERROR = Log.ERROR;
    public static final int NO_LOG = 10;

    // Current log level
    public static int LOGLEVEL = Log.ERROR;

    /**
     * Set the current log level.
     *
     * @param logLevel
     */
    public static void setLogLevel(int logLevel) {
        LOGLEVEL = logLevel;
        Log.i("GroupMeeting", "Changing log level to " + logLevel);
    }

    /**
     * Determine if log level will be logged
     *
     * @param logLevel
     * @return true if the parameter passed in is greater than or equal to the current log level
     */
    public static boolean isLoggable(int logLevel) {
        return (logLevel >= LOGLEVEL);
    }

    /**
     * Verbose log message.
     *
     * @param tag
     * @param s
     */
    public static void v(String tag, String s) {
        if (LOG.VERBOSE >= LOGLEVEL) Log.v(tag, s);
    }

    /**
     * Debug log message.
     *
     * @param tag
     * @param s
     */
    public static void d(String tag, String s) {
        if (LOG.DEBUG >= LOGLEVEL) Log.d(tag, s);
    }

    /**
     * Info log message.
     *
     * @param tag
     * @param s
     */
    public static void i(String tag, String s) {
        if (LOG.INFO >= LOGLEVEL) Log.i(tag, s);
    }

    /**
     * Warning log message.
     *
     * @param tag
     * @param s
     */
    public static void w(String tag, String s) {
        if (LOG.WARN >= LOGLEVEL) Log.w(tag, s);
    }

    /**
     * Error log message.
     *
     * @param tag
     * @param s
     */
    public static void e(String tag, String s) {
        if (LOG.ERROR >= LOGLEVEL) Log.e(tag, s);
    }

    /**
     * Verbose log message.
     *
     * @param tag
     * @param s
     * @param e
     */
    public static void v(String tag, String s, Throwable e) {
        if (LOG.VERBOSE >= LOGLEVEL) Log.v(tag, s, e);
    }

    /**
     * Debug log message.
     *
     * @param tag
     * @param s
     * @param e
     */
    public static void d(String tag, String s, Throwable e) {
        if (LOG.DEBUG >= LOGLEVEL) Log.d(tag, s, e);
    }

    /**
     * Info log message.
     *
     * @param tag
     * @param s
     * @param e
     */
    public static void i(String tag, String s, Throwable e) {
        if (LOG.INFO >= LOGLEVEL) Log.i(tag, s, e);
    }

    /**
     * Warning log message.
     *
     * @param tag
     * @param s
     * @param e
     */
    public static void w(String tag, String s, Throwable e) {
        if (LOG.WARN >= LOGLEVEL) Log.w(tag, s, e);
    }

    /**
     * Error log message.
     *
     * @param tag
     * @param s
     * @param e
     */
    public static void e(String tag, String s, Throwable e) {
        if (LOG.ERROR >= LOGLEVEL) Log.e(tag, s, e);
    }

    /**
     * Verbose log message with printf formatting.
     *
     * @param tag
     * @param s
     * @param args
     */
    public static void v(String tag, String s, Object... args) {
        if (LOG.VERBOSE >= LOGLEVEL) Log.v(tag, String.format(s, args));
    }

    /**
     * Debug log message with printf formatting.
     *
     * @param tag
     * @param s
     * @param args
     */
    public static void d(String tag, String s, Object... args) {
        if (LOG.DEBUG >= LOGLEVEL) Log.d(tag, String.format(s, args));
    }

    /**
     * Info log message with printf formatting.
     *
     * @param tag
     * @param s
     * @param args
     */
    public static void i(String tag, String s, Object... args) {
        if (LOG.INFO >= LOGLEVEL) Log.i(tag, String.format(s, args));
    }

    /**
     * Warning log message with printf formatting.
     *
     * @param tag
     * @param s
     * @param args
     */
    public static void w(String tag, String s, Object... args) {
        if (LOG.WARN >= LOGLEVEL) Log.w(tag, String.format(s, args));
    }

    /**
     * Error log message with printf formatting.
     *
     * @param tag
     * @param s
     * @param args
     */
    public static void e(String tag, String s, Object... args) {
        if (LOG.ERROR >= LOGLEVEL) Log.e(tag, String.format(s, args));
    }

}