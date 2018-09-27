package com.ptit.baobang.piospaapp.utils;

import android.text.TextUtils;

public class InputUtils {
    public static boolean isValidEmail(String target) {
        String EMAIL = "[a-zA-Z0-9_\\.]+@[a-zA-Z]+\\.[a-zA-Z]+(\\.[a-zA-Z]+)*";
        return (!TextUtils.isEmpty(target) && target.matches(EMAIL));
    }

    public static boolean isValidPhone(String target) {
        String PHONE = "(\\+84|0)\\d{9,10}";
        return (!TextUtils.isEmpty(target) && target.matches(PHONE));
    }

    public static boolean isValidUsername(String target){
        String USERNAME = "[a-zA-Z0-9\\._\\-]{5,}";
        return (!TextUtils.isEmpty(target) && target.matches(USERNAME));
    }

    public static boolean isValidPassword(String target){
        String PASSWORD = ".{5,}";
        return (!TextUtils.isEmpty(target) && target.matches(PASSWORD));
    }
}
