package com.ptit.baobang.piospaapp.ui.activities.service_detail;

import android.content.Intent;

import com.ptit.baobang.piospaapp.data.model.ServicePrice;

public interface ISeriviceDetailPresenter {
    void loadData(ServicePrice servicePrice);
    void onClickAddBooking(ServicePrice servicePrice);
    void loadServicePackageInfo(int packageId);

    ServicePrice getDataFromBundle(Intent intent);
}
