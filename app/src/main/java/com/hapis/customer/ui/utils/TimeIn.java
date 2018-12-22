package com.hapis.customer.ui.utils;

import com.hapis.customer.HapisApplication;
import com.hapis.customer.R;
import com.hapis.customer.utils.SupportLanguages;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rahul on 2/26/2018.
 */

public class TimeIn {
    public static final Map<String, Long> times = new LinkedHashMap<>();

    public static final String TIME_IN_YEAR = "year";
    public static final String TIME_IN_MONTH = "month";
    public static final String TIME_IN_WEEK = "week";
    public static final String TIME_IN_DAY = "day";
    public static final String TIME_IN_HOUR = "hour";
    public static final String TIME_IN_MINUTE = "minute";
    public static final String TIME_IN_SECOND = "second";

    static {
        times.put(TIME_IN_YEAR, TimeUnit.DAYS.toMillis(365));
        times.put(TIME_IN_MONTH, TimeUnit.DAYS.toMillis(30));
        times.put(TIME_IN_WEEK, TimeUnit.DAYS.toMillis(7));
        times.put(TIME_IN_DAY, TimeUnit.DAYS.toMillis(1));
        times.put(TIME_IN_HOUR, TimeUnit.HOURS.toMillis(1));
        times.put(TIME_IN_MINUTE, TimeUnit.MINUTES.toMillis(1));
        times.put(TIME_IN_SECOND, TimeUnit.SECONDS.toMillis(1));
    }

    public static String toRelative(long duration, int maxLevel) {
        StringBuilder res = new StringBuilder();
        int level = 0;
        for (Map.Entry<String, Long> time : times.entrySet()) {
            long timeDelta = duration / time.getValue();
            if (timeDelta > 0) {
                res.append(timeDelta)
                        .append(" ")
                        .append(time.getKey())
                        .append(timeDelta > 1 ? "s" : "")
                        .append(", ");
                duration -= time.getValue() * timeDelta;
                level++;
            }
            if (level == maxLevel) {
                break;
            }
        }
        if ("".equals(res.toString())) {
            return " in 0 seconds";
        } else {
            /*res.setLength(res.length() - 2);
            res.append(" in ");*/
            String str = res.toString().replaceAll(", $", "");
            return str;
        }
    }

    public static String toRelative(long duration, String timeInLevel) {
        StringBuilder res = new StringBuilder();

        long timeDelta = duration / times.get(timeInLevel);
        if (timeDelta > 0) {

            String timeInLevelAsStr = timeInLevel;

            if(timeDelta > 1)
                timeInLevelAsStr+="s";

            res.append(timeDelta + 1)
                    .append(" ")
                    .append(getTimeInLevel(timeInLevelAsStr))
                    .append(", ");
        }

        if ("".equals(res.toString())) {
            return " in 0 " + timeInLevel;
        } else {
            String str = res.toString().replaceAll(", $", "");
            return str;
        }
    }

    private static String getTimeInLevel(String timeInLevelAsStr){

        String selectedLocaleCode = AccessPreferences.get(HapisApplication.getApplication(), ApplicationConstants.SELECTED_LOCALE_CODE, ApplicationConstants.DEFAULT_LOCALE_CODE);

        if(selectedLocaleCode != null && !selectedLocaleCode.equalsIgnoreCase(SupportLanguages.LANGUAGE_ENGLISH.toString())){
            if(timeInLevelAsStr.startsWith(TIME_IN_YEAR)){
                if(timeInLevelAsStr.equalsIgnoreCase(TIME_IN_YEAR))
                    timeInLevelAsStr = HapisApplication.getApplication().getResources().getString(R.string.year);
                else
                    timeInLevelAsStr = HapisApplication.getApplication().getResources().getString(R.string.years);
            }else if(timeInLevelAsStr.startsWith(TIME_IN_MONTH)){
                if(timeInLevelAsStr.equalsIgnoreCase(TIME_IN_MONTH))
                    timeInLevelAsStr = HapisApplication.getApplication().getResources().getString(R.string.month);
                else
                    timeInLevelAsStr = HapisApplication.getApplication().getResources().getString(R.string.months);
            }else if(timeInLevelAsStr.startsWith(TIME_IN_WEEK)){
                if(timeInLevelAsStr.equalsIgnoreCase(TIME_IN_WEEK))
                    timeInLevelAsStr = HapisApplication.getApplication().getResources().getString(R.string.week);
                else
                    timeInLevelAsStr = HapisApplication.getApplication().getResources().getString(R.string.weeks);
            }else if(timeInLevelAsStr.startsWith(TIME_IN_DAY)){
                if(timeInLevelAsStr.equalsIgnoreCase(TIME_IN_DAY))
                    timeInLevelAsStr = HapisApplication.getApplication().getResources().getString(R.string.day);
                else
                    timeInLevelAsStr = HapisApplication.getApplication().getResources().getString(R.string.days);
            }else if(timeInLevelAsStr.startsWith(TIME_IN_HOUR)){
                if(timeInLevelAsStr.equalsIgnoreCase(TIME_IN_HOUR))
                    timeInLevelAsStr = HapisApplication.getApplication().getResources().getString(R.string.hour);
                else
                    timeInLevelAsStr = HapisApplication.getApplication().getResources().getString(R.string.hours);
            }else if(timeInLevelAsStr.startsWith(TIME_IN_MINUTE)){
                if(timeInLevelAsStr.equalsIgnoreCase(TIME_IN_MINUTE))
                    timeInLevelAsStr = HapisApplication.getApplication().getResources().getString(R.string.minute);
                else
                    timeInLevelAsStr = HapisApplication.getApplication().getResources().getString(R.string.minutes);
            }else if(timeInLevelAsStr.startsWith(TIME_IN_SECOND)){
                if(timeInLevelAsStr.equalsIgnoreCase(TIME_IN_SECOND))
                    timeInLevelAsStr = HapisApplication.getApplication().getResources().getString(R.string.second);
                else
                    timeInLevelAsStr = HapisApplication.getApplication().getResources().getString(R.string.seconds);
            }
        }

        return timeInLevelAsStr;
    }

    public static String toRelative(long duration) {
        return toRelative(duration, times.size());
    }

    public static String toRelative(Date start, Date end) {
        assert start.after(end);
        return toRelative(end.getTime() - start.getTime());
    }

    public static String toRelative(Date start, Date end, int level) {
        assert start.after(end);
        return toRelative(end.getTime() - start.getTime(), level);
    }
}
