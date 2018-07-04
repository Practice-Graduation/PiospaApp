package com.ptit.baobang.piospaapp.utils;

import android.text.TextUtils;

public class InputUtils {
    private static String EMAIL = "[a-zA-Z0-9_\\.]+@[a-zA-Z]+\\.[a-zA-Z]+(\\.[a-zA-Z]+)*";
    private static String PHONE = "(\\+84|0)\\d{9,10}";
    public static boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && target.matches(EMAIL));
    }

    public static boolean isValidPhone(String target) {
        return (!TextUtils.isEmpty(target) && target.matches(PHONE));
    }
}
