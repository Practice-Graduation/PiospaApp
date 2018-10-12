package com.ptit.baobang.piospaapp.error;

import com.ptit.baobang.piospaapp.utils.AppConstants;

public enum Error {
    // Login Activity
    ERROR_LOGIN_USR_EMPTY("LOGIN_001", "Vui lòng nhập vào tên đăng nhập"),
    ERROR_LOGIN_PWD_EMPTY("LOGIN_002", "Vui lòng nhập vào mật khẩu"),
    ERROR_LOGIN_FAILED("LOGIN_003", "Đăng nhập thất bại"),
    // Register Activity
    ERROR_REGISTER_NAME_EMPTY("REGISTER_001", "Nhập vào họ và tên"),
    ERROR_REGISTER_USR_EMPTY("REGISTER_002", "Vui lòng nhập vào tên đăng nhập"),
    ERROR_REGISTER_USR_INVALID("REGISTER_003", "Tài khoản ít nhất 5 kí tự a-zA-Z0-9._-"),
    ERROR_REGISTER_PWD_EMPTY("REGISTER_004", "Vui lòng nhập vào mật khẩu"),
    ERROR_REGISTER_PWD_INVALID("REGISTER_005", "Mật khẩu ít nhất 5 kí tự"),
    ERROR_REGISTER_PWD_COMFIRM_EMPTY("REGISTER_006", "Vui lòng nhập vào xác nhận mật khẩu"),
    ERROR_REGISTER_PWD_COMFIRM_NOT_SAME("REGISTER_007", "Mật khẩu xác nhận không giống mật khẩu mớ"),
    // Change password activity
    ERROR_CHANGE_PWD_INVALID("CHANGE_PWD_001", "Mật khẩu ít nhất 5 kí tự"),
    ERROR_CHANGE_PWD_COMFIRM_EMPTY("REGISTER_002", "Vui lòng nhập vào xác nhận mật khẩu"),
    ERROR_CHANGE_PWD_COMFIRM_NOT_SAME("REGISTER_003", "Mật khẩu xác nhận không giống mật khẩu mớ"),
    // Payment Activity
    ERROR_PAYMENT_NAME_EMPTY("PAYMENT_001", "Nhập vào họ và tên"),
    // Product detail;
    ERROR_PRODUCT_DETAIL_NOT_ENOUGH_AMOUNT("PRODUCT_DETAIL_001", "Số lượng sản phẩm không đủ");


    private String code;
    private String message;

    Error(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return code + AppConstants.DOUBLE_DOT + message;
    }
}
