package com.ptit.baobang.piospaapp.ui.fragments.fragment_booking;

import com.ptit.baobang.piospaapp.data.model.Room;
import com.ptit.baobang.piospaapp.data.network.firebase.BookingTimeFB;

import java.util.Date;

public interface IBookingFragmentPresenter {
    void loadData();
    void loadBookingTime(Room room, Date selectedDate);
    void clickBookingButton(int mServicePriceSelected, Date mSelectedDate, Room mRoomSelected, BookingTimeFB mTimeSelected);

}
