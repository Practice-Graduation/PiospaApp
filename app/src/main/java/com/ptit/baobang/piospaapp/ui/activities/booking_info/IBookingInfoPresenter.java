package com.ptit.baobang.piospaapp.ui.activities.booking_info;

import android.content.Intent;

import com.ptit.baobang.piospaapp.data.model.Room;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;

import java.util.Date;

public interface IBookingInfoPresenter {
    void loadDataFromBunble(Intent intent);

    void clickAdd(String text);

    void clickRemove(String text);

    void clickConfirm(ServicePrice mServicePrice, Date mSelectedDate, String mTimeSelected, String amount, Room mRoomSelected);

    void clickFloatButtonCart();

    void clickSelectDate();

    void clickSelectTime(Date date);

    void clickRoom(Date mSelectedDate, String mTimeSelected, Room mRoomSelected);

}
