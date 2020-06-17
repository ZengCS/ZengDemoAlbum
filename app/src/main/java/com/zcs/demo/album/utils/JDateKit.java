package com.zcs.demo.album.utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class JDateKit {
    private static final String TAG = "JDateKit";
    public static final long ONE_HOUR = 3600000L;

    public JDateKit() {
    }

    public static String getLastDay(String baseDay) {
        if (TextUtils.isEmpty(baseDay)) {
            return "";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date base = sdf.parse(baseDay);
                Calendar c = Calendar.getInstance();
                c.setTime(base);
                c.add(5, -1);
                String yesterday = sdf.format(c.getTime());
                return yesterday;
            } catch (Exception var5) {
                return "";
            }
        }
    }

    public static String dateToDay(String date) {
        if (TextUtils.isEmpty(date)) {
            return "";
        } else {
            SimpleDateFormat sdf = null;

            try {
                sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date curr = sdf.parse(date);
                date = sdf.format(curr);
            } catch (ParseException var5) {
                var5.printStackTrace();
                Log.e("Error", Log.getStackTraceString(var5));
            }

            Calendar c = Calendar.getInstance();
            String today = sdf.format(c.getTime());
            if (date.equals(today)) {
                return "今天";
            } else {
                c.add(Calendar.DATE, -1);
                String yesterday = sdf.format(c.getTime());
                return date.equals(yesterday) ? "昨天" : date;
            }
        }
    }

    public static String timeToString(long time) {
        int s = (int) (time / 1000L);
        int m = s / 60;
        int h = m / 60;
        int d = h / 24;
        if (d > 0) {
            return d + "d" + h % 24 + "h" + m % 60 + "'" + s % 60 + "\"";
        } else if (h > 0) {
            return h % 24 + "h" + m % 60 + "'" + s % 60 + "\"";
        } else {
            return m > 0 ? m % 60 + "'" + s % 60 + "\"" : "0'" + s % 60 + "\"";
        }
    }

    public static String timeToStringChineChinese(long time) {
        int s = (int) (time / 1000L);
        int m = s / 60;
        int h = m / 60;
        int d = h / 24;
        if (d > 0) {
            return d + "天" + h % 24 + "小时" + m % 60 + "分" + s % 60 + "秒";
        } else if (h > 0) {
            return h % 24 + "小时" + m % 60 + "分" + s % 60 + "秒";
        } else {
            return m > 0 ? m % 60 + "分" + s % 60 + "秒" : s % 60 + "秒";
        }
    }

    public static String dateToStr(Date date) {
        return dateToStr("yyyy-MM-dd", date);
    }

    public static String currDateTime() {
        return dateToStr("yyyy-MM-dd HH:mm:ss", new Date());
    }

    public static String dateToStr(String template, Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(template, Locale.CHINA);
        String dateString = formatter.format(date);
        return dateString;
    }

    public static String dateToStr(String template, String date) {
        Date d = getDateByDateStr("yyyy-MM-dd HH:mm:ss", date);
        SimpleDateFormat formatter = new SimpleDateFormat(template, Locale.CHINA);
        String dateString = formatter.format(d);
        return dateString;
    }

    public static Date getFirstDay() {
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(5, 1);
        return gcLast.getTime();
    }

    public static String getToday(String template) {
        SimpleDateFormat sdf = new SimpleDateFormat(template, Locale.CHINA);
        return sdf.format(new Date());
    }

    public static Date getDateByDateStr(String dateStr) {
        return getDateByDateStr("yyyy-MM-dd", dateStr);
    }

    public static Date getDateByDateStr(String template, String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(template, Locale.CHINA);
        ParsePosition pos = new ParsePosition(0);
        return sdf.parse(dateStr, pos);
    }

    public static Calendar getCalendarByDateStr(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = sdf.parse(dateStr, pos);
        calendar.setTime(strtodate);
        return calendar;
    }

    public static Date getLatelyDate(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(5, day);
        return cal.getTime();
    }

    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        return daysBetween(smdate, bdate, "yyyy-MM-dd");
    }

    public static int daysBetween(Date smdate, Date bdate, String template) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(template, Locale.CHINA);
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = Math.abs(time2 - time1) / 86400000L;
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static String timeToDate(long time) {
        Date date = new Date(time);
        return dateToStr(date);
    }

    public static String timeToDate(String format, long time) {
        Date date = new Date(time);
        return dateToStr(format, date);
    }
}