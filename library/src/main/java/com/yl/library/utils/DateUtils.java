package com.yl.library.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Yang Shihao
 */

public class DateUtils {

    public static final String FORMAT_Y = "yyyy";

    public static final String FORMAT_HM = "HH:mm";

    public static final String FORMAT_MDHM = "MM-dd HH:mm";

    public static final String FORMAT_YMD = "yyyy-MM-dd";

    public static final String FORMAT_YMDHM = "yyyy-MM-dd HH:mm";

    public static final String FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";

    public static final String FORMAT_FULL_SN = "yyyyMMddHHmmssS";

    public static final String FORMAT_YMD_CN = "yyyy年MM月dd日";

    public static final String FORMAT_YMDH_CN = "yyyy年MM月dd日 HH时";

    public static final String FORMAT_YMDHM_CN = "yyyy年MM月dd日 HH时mm分";

    public static final String FORMAT_YMDHMS_CN = "yyyy年MM月dd日  HH时mm分ss秒";

    public static final String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final int MILLIS_SECOND = 1000;

    private static final int MILLIS_MINUTE = 60000;

    private static final int MILLIS_HOUR = 3600000;

    private static final int MILLIS_DAY = 86400000;

    /**
     * 字符串转换成日期，默认格式
     */
    public static Date str2Date(String str) {
        return str2Date(str, null);
    }

    /**
     * 字符串转换成日期，指定格式
     */
    public static Date str2Date(String str, String format) {
        if (str == null || str.length() == 0) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = FORMAT;
        }
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(str);
        } catch (Exception e) {
        }
        return date;
    }

    /**
     * 日期转换成字符串，默认格式
     */
    public static String date2Str(Date d) {// yyyy-MM-dd HH:mm:ss
        return date2Str(d, null);
    }

    /**
     * 日期转换成字符串，默认格式
     */
    public static String date2YMD(Date d) {// yyyy-MM-dd HH:mm:ss
        return date2Str(d, FORMAT_YMD);
    }

    /**
     * 日期转换成字符串，指定格式
     */
    public static String date2Str(Date d, String format) {
        if (d == null) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = FORMAT;
        }
        return new SimpleDateFormat(format).format(d);
    }

    /**
     * 获取当前时间，默认格式
     */
    public static String getTimeStr() {
        return getTimeStr(null);
    }

    /**
     * 获取当前时间，指定格式
     */
    public static String getTimeStr(String format) {
        if (format == null || format.length() == 0) {
            format = FORMAT;
        }
        return new SimpleDateFormat(format).format(new Date());
    }

    /**
     * 获取当前时间，指定格式
     */
    public static String getTimeStr(long time,String format) {
        return new SimpleDateFormat(format).format(new Date(time));
    }

    /**
     * 时间格式化
     */
    public static String millis2time(long millis) {
        long second = (long) Math.ceil(millis / MILLIS_SECOND);
        StringBuffer sb = new StringBuffer("");
        if (second < 60) {
            sb.append("00:");
            appendStr(sb, second);
        } else {
            appendStr(sb, second / 60);
            sb.append(":");
            appendStr(sb, second % 60);
        }
        return sb.toString();
    }

    public static void appendStr(StringBuffer sb, long l) {
        if (l < 10) {
            sb.append("0").append(l);
        } else {
            sb.append(l);
        }
    }

    /**
     * 某一时间多少分钟后的时间
     */
    public static String appendTime(String format, String startTime, int minute) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = sdf.parse(startTime);
            date.setTime(date.getTime() + minute * MILLIS_MINUTE);
            return sdf.format(date);
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * 计算时间差
     */
    public static String getDuration(String format, String start, String end) {
        StringBuffer sb = new StringBuffer("");
        try {
            Date date1 = new SimpleDateFormat(format).parse(start);
            Date date2 = new SimpleDateFormat(format).parse(end);
            long l = date1.getTime() - date2.getTime() < 0 ? date2.getTime() - date1.getTime() : date1.getTime() - date2.getTime();
            if (l < MILLIS_SECOND) {
                sb.append("1秒");
            } else if (l < MILLIS_MINUTE) {
                appendStr(sb, l, MILLIS_SECOND, "秒");
            } else if (l < MILLIS_HOUR) {
                appendStr(sb, l, MILLIS_MINUTE, "分钟");
                appendStr(sb, l % MILLIS_MINUTE, MILLIS_SECOND, "秒");
            } else {
                appendStr(sb, l, MILLIS_HOUR, "小时");
                appendStr(sb, l % MILLIS_HOUR, MILLIS_MINUTE, "分钟");
                appendStr(sb, l % MILLIS_MINUTE, MILLIS_SECOND, "秒");
            }
        } catch (ParseException e) {
            return sb.toString();
        }
        return sb.toString();
    }

    private static void appendStr(StringBuffer sb, long time, long divisor, String unit) {
        long s = time / divisor;
        if (s != 0) {
            sb.append(s).append(unit);
        }
    }
}
