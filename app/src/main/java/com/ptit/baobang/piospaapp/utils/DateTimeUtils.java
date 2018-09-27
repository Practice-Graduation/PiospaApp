package com.ptit.baobang.piospaapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {
    public static String DATE_PATTERN_DDMMYY = "dd/MM/yyyy";
    public static String DATE_PATTERN_DDMMYYTHHMMSSSSSZ =  "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static String DATE_PATTERN_DDMMYYTHHMMSSSSSX =  "yyyy-MM-dd'T'HH:mm:ss.SSSX";
    public static String DATE_PATTERN_DDMMYYTHHMMSS =  "yyyy-MM-dd'T'HH:mm:ss";
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

        String s = date;
        try {
            if(time.length() > 0){
                s += "'T'"+time;
            }
            calendar.setTime(sdf.parse(s));// all done
        } catch (ParseException e) {
            sdf = getDateFormat(DATE_PATTERN_DDMMYYTHHMMSS);
            try {
                calendar.setTime(sdf.parse(s));
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
        return calendar.getTime();
    }

    public static String formatDate(Date date, String pattern){
        return getDateFormat(pattern).format(date);
    }
}
