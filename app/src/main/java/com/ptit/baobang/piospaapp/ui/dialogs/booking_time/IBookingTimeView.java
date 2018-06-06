package com.ptit.baobang.piospaapp.ui.dialogs.booking_time;

import com.ptit.baobang.piospaapp.ui.base.BaseView;

import java.util.List;

public interface IBookingTimeView extends BaseView {
    void updateRecycleViewTime(List<String> times);
    void stopShimmerAnimation();
    void startShimmerAnimation();

    void backToBookingActivity(String timeStart);
}
