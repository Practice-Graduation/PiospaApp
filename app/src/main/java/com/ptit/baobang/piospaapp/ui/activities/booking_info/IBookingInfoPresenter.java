package com.ptit.baobang.piospaapp.ui.activities.booking_info;

import android.content.Intent;

import com.ptit.baobang.piospaapp.data.model.Room;
import com.ptit.baobang.piospaapp.data.model.ServicePackage;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.data.network.firebase.BookingTimeFB;

import java.util.Date;

public interface IBookingInfoPresenter {
    void loadDate(Intent intent);

    void clickAdd(String text);

    void clickRemove(String text);

    String sumTotalTimeOfServicePackage(ServicePackage servicePackage);

    void clickConfirm(ServicePrice mServicePrice, Date mSelectedDate, Room mSelectedRoom,
                      BookingTimeFB mSelectedTime, String amount, String customerName,
                      String phone, String email, String note);

    void clickFloatButtonCart();
}
