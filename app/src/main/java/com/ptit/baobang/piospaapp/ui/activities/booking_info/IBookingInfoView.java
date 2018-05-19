package com.ptit.baobang.piospaapp.ui.activities.booking_info;

import com.ptit.baobang.piospaapp.data.model.Room;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.data.network.firebase.BookingTimeFB;
import com.ptit.baobang.piospaapp.ui.base.BaseView;

import java.util.Date;

public interface IBookingInfoView extends BaseView{
    void attachData(ServicePrice servicePrice, Date selectedDate, Room selectedRoom, BookingTimeFB selectedTime);

    void setAmount(String s);
}
