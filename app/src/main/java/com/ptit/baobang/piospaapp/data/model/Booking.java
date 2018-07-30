
package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Booking implements Serializable {

    @SerializedName("bookingId")
    @Expose
    private int bookingId;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @SerializedName("createdBy")
    @Expose
    private int createdBy;

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

    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    @SerializedName("updatedBy")
    @Expose
    private int updatedBy;

    @SerializedName("voucherId")
    @Expose
    private int voucherId;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
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

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
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
