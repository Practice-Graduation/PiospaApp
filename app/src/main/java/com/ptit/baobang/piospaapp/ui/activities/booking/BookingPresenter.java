package com.ptit.baobang.piospaapp.ui.activities.booking;

import com.ptit.baobang.piospaapp.ui.base.BasePresenter;

public class BookingPresenter extends BasePresenter implements IBookingPresenter {
    private IBookingView mView;

    public BookingPresenter(IBookingView mView) {
        this.mView = mView;
    }



}
