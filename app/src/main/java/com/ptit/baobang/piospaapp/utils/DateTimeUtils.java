package com.ptit.baobang.piospaapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
    static String DATE_PARTERN = "yyyy-MM-dd";
    static String TIME_PARTERN = "HH:mm";
    static String DATE_PARTERN_DDMMYY = "dd/MM/yyyy";
    private static SimpleDateFormat dateFormat = null;
    private static SimpleDateFormat dateFormatDDMMYY = null;
    private static SimpleDateFormat timeFormat = null;

    public static SimpleDateFormat getDateFormat() {
        if(dateFormat == null){
            dateFormat = new SimpleDateFormat(DATE_PARTERN);
        }
        return dateFormat;
    }

    public static SimpleDateFormat getDateFormatDDMMYY() {
        if(dateFormatDDMMYY == null){
            dateFormatDDMMYY = new SimpleDateFormat(DATE_PARTERN_DDMMYY);
        }
        return dateFormatDDMMYY;
    }

    public static SimpleDateFormat getTimeFormat() {
        if(timeFormat == null){
            timeFormat = new SimpleDateFormat(TIME_PARTERN);
        }
        return timeFormat;
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
