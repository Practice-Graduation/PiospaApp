package com.ptit.baobang.piospaapp.ui.activities.service_detail;

import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.ui.base.BaseView;

public interface IServiceDetailView extends BaseView {
    void showServiceDetail(ServicePrice servicePrice);
    void openBookingActivity(int servicePriceId);
    void setPackageInfo(String s);
    void addServicePackageTime(String time);
    void startShimmerAnimation();
    void stopShimmerAnimation();
}
