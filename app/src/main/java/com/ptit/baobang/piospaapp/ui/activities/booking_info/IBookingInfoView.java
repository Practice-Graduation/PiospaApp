package com.ptit.baobang.piospaapp.ui.activities.booking_info;

import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.ui.base.BaseView;

import java.util.Date;

public interface IBookingInfoView extends BaseView{
    void attachData(ServicePrice servicePrice);

    void setAmount(String s);

    void openFramentCart();

    void openDatePicker();

    void openTimePicker(Date date);
}
