package com.ptit.baobang.piospaapp.data.network.firebase;

import java.io.Serializable;

public class BookingTimeFB implements Serializable{
    private String id;
    private int servicePriceId;
    private String timeStart;
    private String timeEnd;
    private boolean isBooking = false;

    public BookingTimeFB() {
    }

    public BookingTimeFB(String id, int servicePriceId, String timeStart, String timeEnd, boolean isBooking) {
        this.id = id;
        this.servicePriceId = servicePriceId;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.isBooking = isBooking;
    }

    public boolean isBooking() {
        return isBooking;
    }

    public void setBooking(boolean booking) {
        isBooking = booking;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getServicePriceId() {
        return servicePriceId;
    }

    public void setServicePriceId(int servicePriceId) {
        this.servicePriceId = servicePriceId;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }
}
