package com.ptit.baobang.piospaapp.utils;

public interface AppConstants {
    int SUCCESS_CODE = 200;
    String DATE_FORMAT = "dd/MM/yyyy";
    int MARGIN = 5;
    int SPAN_COUNT = 2;

    // Home fragment index
    int CART_INDEX = 1;
    int PRODUCT_INDEX = 0;

    int PADDING_TOOLBAR = 20;
    // order status id
    int ORDER_STATUS_NOT_PAYMENT = 1;
    int ORDER_STATUS_PAYMENT = 2;
    int ORDER_STATUS_CANCLE = 3;
    // order fragment index
    int ORDER_STATUS_NOT_PAYMENT_INDEX = 0;
    int ORDER_STATUS_PAYMENT_INDEX = 1;
    int ORDER_STATUS_CANCLE_INDEX = 2;
    //
    int PAYMENT_TYPE_DELIVERY = 1;
    int PAYMENT_TYPE_GET = 2;

    int HUNDRES_PERCENT = 100;
    //
    int TAX_ID = 1;
    int LIST_EMPTY_SIZE = 0;
    String PECENT = "percent";
    String MONEY = "money";
    String NOT_PAY_MENT = "Chưa thanh toán";
    String PAYMENT = "Thanh toán";
    String CANCEL = "Hủy";
    String PERCENT_SYMBOL = "%";
    String TAB_SYMBOL = "\t";
    String DOUBLE_DOT = ": ";

    String LABEL = "Thương hiệu: ";
    String ORGIGIN = "Nguồn gốc: ";
    String QUANLITY = "Trọng lượng: ";
    String DEFAULT_FILE_NAME = "avatar.jpg";
    String IMAGE_PATH = "image/*";
    String COMMA_SYMBOL = ",";
    String SPACE_SYMBOL = " ";

    String BREAK_LINE = "\r\n";
}
