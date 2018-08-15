
package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Booking implements Serializable {

    @SerializedName("bookingId")
    @Expose
    private int bookingId;

    @SerializedName("discount")
    @Expose
    private int discount;

    @SerializedName("number")
    @Expose
    private int number;

    @SerializedName("price")
    @Expose
    private int price;

    @SerializedName("total")
    @Expose
    private int total;

    @SerializedName("customer")
    @Expose
    private Customer customer;

    @SerializedName("order")
    @Expose
    private Order order;

    public Booking() {
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
