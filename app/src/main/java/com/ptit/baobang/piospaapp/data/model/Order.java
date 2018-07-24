
package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Order implements Serializable {

    @SerializedName("orderId")
    @Expose
    private int orderId;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("addressDelivery")
    @Expose
    private String addressDelivery;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("createdBy")
    @Expose
    private int createdBy;
    @SerializedName("dateDelivery")
    @Expose
    private String dateDelivery;
    @SerializedName("deliveryCode")
    @Expose
    private String deliveryCode;
    @SerializedName("deliveryCost")
    @Expose
    private int deliveryCost;
    @SerializedName("discount")
    @Expose
    private int discount;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("staffId")
    @Expose
    private int staffId;
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
    @SerializedName("updatedBy")
    @Expose
    private int updatedBy;
    @SerializedName("voucherId")
    @Expose
    private int voucherId;
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
    @SerializedName("orderReasonCancel")
    @Expose
    private OrderReasonCancel orderReasonCancel;
    @SerializedName("orderStatus")
    @Expose
    private OrderStatus orderStatus;

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

    public String getDateDelivery() {
        return dateDelivery;
    }

    public void setDateDelivery(String dateDelivery) {
        this.dateDelivery = dateDelivery;
    }

    public String getDeliveryCode() {
        return deliveryCode;
    }

    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
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

    public OrderReasonCancel getOrderReasonCancel() {
        return orderReasonCancel;
    }

    public void setOrderReasonCancel(OrderReasonCancel orderReasonCancel) {
        this.orderReasonCancel = orderReasonCancel;
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
