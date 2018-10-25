package com.ptit.baobang.piospaapp.utils;

import android.text.TextUtils;

public class InputUtils {

    public static boolean isValidEmail(String target) {
        return target.isEmpty() ||
                target.matches("[a-zA-Z0-9_\\.]+@[a-zA-Z]+\\.[a-zA-Z]+(\\.[a-zA-Z]+)*");
    }

    public static boolean isPhoneValid(String target) {
        return !target.isEmpty() && !target.matches("(\\+84|0)\\d{9}");
    }

    public static boolean isUsernameValid(String target) {
        return (TextUtils.isEmpty(target)
                || !target.matches("[a-zA-Z0-9\\._\\-]{5,32}"));
    }

    public static boolean isPasswordValid(String target) {
        return (TextUtils.isEmpty(target)
                || !target.matches(".{5,64}"));
    }
}
