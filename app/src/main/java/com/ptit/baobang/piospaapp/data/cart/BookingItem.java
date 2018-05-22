package com.ptit.baobang.piospaapp.data.cart;

import android.support.annotation.NonNull;

import com.ptit.baobang.piospaapp.data.model.Room;
import com.ptit.baobang.piospaapp.data.model.ServicePrice;
import com.ptit.baobang.piospaapp.data.network.firebase.BookingTimeFB;

import java.io.Serializable;
import java.util.Date;

public class BookingItem implements Serializable, Comparable<BookingItem>{
    private ServicePrice servicePrice;
    private Date selectedDate;
    private Room selectedRoom;
    private BookingTimeFB selectedTime;

    public BookingItem(ServicePrice servicePrice, Date selectedDate, Room selectedRoom, BookingTimeFB selectedTime) {
        this.servicePrice = servicePrice;
        this.selectedDate = selectedDate;
        this.selectedRoom = selectedRoom;
        this.selectedTime = selectedTime;
    }



    public ServicePrice getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(ServicePrice servicePrice) {
        this.servicePrice = servicePrice;
    }

    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    public Room getSelectedRoom() {
        return selectedRoom;
    }

    public void setSelectedRoom(Room selectedRoom) {
        this.selectedRoom = selectedRoom;
    }

    public BookingTimeFB getSelectedTime() {
        return selectedTime;
    }

    public void setSelectedTime(BookingTimeFB selectedTime) {
        this.selectedTime = selectedTime;
    }

    @Override
    public int compareTo(@NonNull BookingItem o) {
        return servicePrice.getServicePriceId() == o.getServicePrice().getServicePriceId() ? 0 : 1;
    }
}
