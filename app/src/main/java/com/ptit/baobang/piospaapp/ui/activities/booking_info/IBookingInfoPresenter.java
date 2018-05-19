package com.ptit.baobang.piospaapp.ui.activities.booking_info;

import android.content.Intent;

import com.ptit.baobang.piospaapp.data.model.ServicePackage;

public interface IBookingInfoPresenter {
    void loadDate(Intent intent);

    void clickAdd(String text);

    void clickRemove(String text);

    String sumTotalTimeOfServicePackage(ServicePackage servicePackage);
}
