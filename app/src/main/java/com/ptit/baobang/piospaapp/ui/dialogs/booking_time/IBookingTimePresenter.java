package com.ptit.baobang.piospaapp.ui.dialogs.booking_time;

import android.content.Intent;

import com.ptit.baobang.piospaapp.data.model.ServicePrice;

import java.util.Date;

public interface IBookingTimePresenter {
    void loadData(ServicePrice servicePrice, Date date);

    Date getSelectedDateFromIntent(Intent intent);

    ServicePrice getServicePriceFromIntent(Intent intent);

    void clickBooking(String time);
}
