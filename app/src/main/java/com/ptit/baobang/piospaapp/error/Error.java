package com.ptit.baobang.piospaapp.error;

import com.ptit.baobang.piospaapp.utils.AppConstants;

public enum Error {
    // Login Activity
    ERROR_LOGIN_USR_NOT_VALID("LOGIN_001", "Tên đăng nhập phải từ 5-32 kí tự a-zA-Z0-9._-"),
    ERROR_LOGIN_PWD_NOT_VALID("LOGIN_002", "Mật khẩu phải từ 5-64 kí tự"),
    ERROR_LOGIN_FAILED("LOGIN_003", "Đăng nhập thất bại"),
    // Register Activity
    ERROR_REGISTER_NAME_EMPTY("REGISTER_001", "Nhập vào họ và tên"),
    ERROR_REGISTER_USR_INVALID("REGISTER_002", "Tài khoản ít nhất 5-32 kí tự a-zA-Z0-9._-"),
    ERROR_REGISTER_PWD_INVALID("REGISTER_003", "Mật khẩu ít nhất 5-64 kí tự"),
    ERROR_REGISTER_PWD_COMFIRM_NOT_SAME("REGISTER_004", "Mật khẩu xác nhận không giống mật khẩu"),
    ERROR_REGISTER_FAILED("REGISTER_005", "Đăng kí tài khoản thất bại"),
    // Change password activity
    ERROR_CHANGE_PWD_INVALID("CHANGE_PWD_001", "Mật khẩu ít nhất 5 kí tự"),
    ERROR_CHANGE_PWD_COMFIRM_EMPTY("REGISTER_002", "Vui lòng nhập vào xác nhận mật khẩu"),
    ERROR_CHANGE_PWD_COMFIRM_NOT_SAME("REGISTER_003", "Mật khẩu xác nhận không giống mật khẩu mớ"),
    // Payment Activity
    ERROR_PAYMENT_NAME_EMPTY("PAYMENT_001", "Nhập vào họ và tên"),
    // Product detail;
    ERROR_PRODUCT_DETAIL_NOT_ENOUGH_AMOUNT("PRODUCT_DETAIL_001", "Số lượng sản phẩm không đủ"),

    ERROR_UPDATE_PROFILE_PHONE_INVALID("UPDATE_PROFILE_002", "Số điện thoại không đúng"),
    ERROR_EMAIL_INVALID("UPDATE_PROFILE_003", "Địa chỉ email không đúng"),
    ERROR_UPDATE_PROFILE_NAME_EMPTY("UPDATE_PROFILE_001", "Vui lòng nhập vào họ và tên"),
    ERROR_UPDATE_PROFILE_FAILED("UPDATE_PROFILE_004", "Lỗi kết nối đến server"),
    ERROR_UPDATE_PROFILE_NOT_CHOOSE_PROVINCE("UPDATE_PROFILE_005", "Vui lòng chọn tỉnh/thành phố trước."),
    ERROR_UPDATE_PROFILE_NOT_CHOOSE_DISTRICT("UPDATE_PROFILE_006", "Vui lòng chọn quận/huyện trước."),
        ERROR_DISTRICT_EMPTY("UPDATE_PROFILE_007", "Vui lòng chọn quận/huyện"),
    ERROR_WARD_EMPTY("UPDATE_PROFILE_008", "Vui lòng chọn phường/xã"),
    ERROR_ADDRESS_EMPTY("UPDATE_PROFILE_009", "Vui lòng nhập vào địa chỉ cụ thể");


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
