
package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BookingDetail  implements Serializable {

    @SerializedName("bookingDetailId")
    @Expose
    private int bookingDetailId;

    @Expose
    private int createdBy;

    @SerializedName("number")
    @Expose
    private int number;

    @SerializedName("dateBooking")
    @Expose
    private String dateBooking;

    @SerializedName("timeStart")
    @Expose
    private String timeStart;

    @SerializedName("booking")
    @Expose
    private Booking booking;

    @SerializedName("servicePrice")
    @Expose
    private ServicePrice servicePrice;

    public BookingDetail() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getBookingDetailId() {
        return bookingDetailId;
    }

    public void setBookingDetailId(int bookingDetailId) {
        this.bookingDetailId = bookingDetailId;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public String getDateBooking() {
        return dateBooking;
    }

    public void setDateBooking(String dateBooking) {
        this.dateBooking = dateBooking;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public ServicePrice getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(ServicePrice servicePrice) {
        this.servicePrice = servicePrice;
    }

}
