package com.ptit.baobang.piospaapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {
    public static String DATE_PATTERN = "yyyy-MM-dd";
    public static String DATE_PATTERN_DDMMYY = "dd/MM/yyyy";
    public static String DATE_PATTERN_DDMMYYTHHMMSSSSSZ =  "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static String TIME_PATTERN = "HH:mm";

    private static Locale locale = new Locale("vi", "VN");

    private static SimpleDateFormat getDateFormat(String pattern) {
        return new SimpleDateFormat(pattern, locale);
    }

    public static Locale getLocale() {
        return locale;
    }

    public static Date getDateFromString(String date, String time){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = getDateFormat(DATE_PATTERN_DDMMYYTHHMMSSSSSZ);
        try {
            String s = date;
            if(time.length() > 0){
                s += "'T'"+time;
            }
            calendar.setTime(sdf.parse(s));// all done
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTime();
    }

    public static String formatDate(Date date, String pattern){
        return getDateFormat(pattern).format(date);
    }
}
