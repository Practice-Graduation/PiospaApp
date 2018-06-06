package com.ptit.baobang.piospaapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {
    private static SimpleDateFormat dateFormat = null;
    private static SimpleDateFormat dateFormatDDMMYY = null;
    private static SimpleDateFormat timeFormat = null;
    private static Locale locale = new Locale("vi", "VN");

    private static SimpleDateFormat getDateFormat() {
        if(dateFormat == null){
            String DATE_PARTERN = "yyyy-MM-dd";
            dateFormat = new SimpleDateFormat(DATE_PARTERN, locale);
        }
        return dateFormat;
    }

    private static SimpleDateFormat getDateFormatDDMMYY() {
        if(dateFormatDDMMYY == null){
            String DATE_PARTERN_DDMMYY = "dd/MM/yyyy";
            dateFormatDDMMYY = new SimpleDateFormat(DATE_PARTERN_DDMMYY, locale);
        }
        return dateFormatDDMMYY;
    }

    private static SimpleDateFormat getTimeFormat() {
        if(timeFormat == null){
            String TIME_PARTERN = "HH:mm";
            timeFormat = new SimpleDateFormat(TIME_PARTERN, locale);
        }
        return timeFormat;
    }

    public static Locale getLocale() {
        return locale;
    }

    public static Date getDateFromString(String date){

        return new Date(date);
    }

    public static String formatDate(Date date){
        return getDateFormat().format(date);
    }

    public static String formatDateDDMMYYYY(Date date){
        return getDateFormatDDMMYY().format(date);
    }

    public static String formatTime(Date date){
        return getTimeFormat().format(date);
    }
}
