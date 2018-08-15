
package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Order  implements Serializable {

    @SerializedName("orderId")
    @Expose
    private int orderId;

    @SerializedName("address")
    @Expose
    private String address;


    @SerializedName("addressDelivery")
    @Expose
    private String addressDelivery;

    @SerializedName("fullName")
    @Expose
    private String fullName;


    @SerializedName("phone")
    @Expose
    private String phone;


    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @SerializedName("deliveryCost")
    @Expose
    private int deliveryCost;


    @SerializedName("discount")
    @Expose
    private int discount;

    @SerializedName("email")
    @Expose
    private String email;


    @SerializedName("subTotal")
    @Expose
    private int subTotal;


    @SerializedName("tax")
    @Expose
    private Tax tax;


    @SerializedName("total")
    @Expose
    private int total;

    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    @SerializedName("customer")
    @Expose
    private Customer customer;


    @SerializedName("orderDeliveryStatus")
    @Expose
    private OrderDeliveryStatus orderDeliveryStatus;

    @SerializedName("orderDeliveryType")
    @Expose
    private OrderDeliveryType orderDeliveryType;

    @SerializedName("orderPaymentType")
    @Expose
    private OrderPaymentType orderPaymentType;

    @SerializedName("orderStatus")
    @Expose
    private OrderStatus orderStatus;

    public Order() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressDelivery() {
        return addressDelivery;
    }

    public void setAddressDelivery(String addressDelivery) {
        this.addressDelivery = addressDelivery;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(int deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public OrderDeliveryStatus getOrderDeliveryStatus() {
        return orderDeliveryStatus;
    }

    public void setOrderDeliveryStatus(OrderDeliveryStatus orderDeliveryStatus) {
        this.orderDeliveryStatus = orderDeliveryStatus;
    }

    public OrderDeliveryType getOrderDeliveryType() {
        return orderDeliveryType;
    }

    public void setOrderDeliveryType(OrderDeliveryType orderDeliveryType) {
        this.orderDeliveryType = orderDeliveryType;
    }

    public OrderPaymentType getOrderPaymentType() {
        return orderPaymentType;
    }

    public void setOrderPaymentType(OrderPaymentType orderPaymentType) {
        this.orderPaymentType = orderPaymentType;
    }
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
