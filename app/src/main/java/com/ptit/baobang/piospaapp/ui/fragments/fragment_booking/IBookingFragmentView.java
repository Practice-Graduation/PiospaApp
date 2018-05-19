package com.ptit.baobang.piospaapp.ui.fragments.fragment_booking;

import com.ptit.baobang.piospaapp.data.model.Room;
import com.ptit.baobang.piospaapp.data.network.firebase.BookingTimeFB;
import com.ptit.baobang.piospaapp.ui.base.BaseView;

import java.util.Date;
import java.util.List;

public interface IBookingFragmentView extends BaseView{
    void updateRecycleViewRoom(List<Room> rooms);
    void updateRecycleViewTime(List<BookingTimeFB>times);

    void openBookingInfoActivity(int mServicePriceSelected, Date mSelectedDate, Room mRoomSelected, BookingTimeFB mTimeSelected);
}
