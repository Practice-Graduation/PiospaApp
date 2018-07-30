package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BookingDetailObject implements Serializable{
    @SerializedName("serviceName")
    @Expose
    private String serviceName;

    @SerializedName("serviceImage")
    @Expose
    private String serviceImage;

    @SerializedName("dateBooking")
    @Expose
    private String dateBooking;

    @SerializedName("timeBooking")
    @Expose
    private String timeBooking;

    @SerializedName("numberCustomer")
    @Expose
    private int numberCustomer;

    @SerializedName("price")
    @Expose
    private int price;

    public BookingDetailObject(String serviceName, String serviceImage, String dateBooking, String timeBooking, int numberCustomer, int price) {
        this.serviceName = serviceName;
        this.serviceImage = serviceImage;
        this.dateBooking = dateBooking;
        this.timeBooking = timeBooking;
        this.numberCustomer = numberCustomer;
        this.price = price;
    }

    public BookingDetailObject() {
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    public String getDateBooking() {
        return dateBooking;
    }

    public void setDateBooking(String dateBooking) {
        this.dateBooking = dateBooking;
    }

    public String getTimeBooking() {
        return timeBooking;
    }

    public void setTimeBooking(String timeBooking) {
        this.timeBooking = timeBooking;
    }

    public int getNumberCustomer() {
        return numberCustomer;
    }

    public void setNumberCustomer(int numberCustomer) {
        this.numberCustomer = numberCustomer;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
