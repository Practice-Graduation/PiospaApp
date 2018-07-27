
package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmField;

public class Order extends RealmObject implements Serializable {

    @PrimaryKey
    @RealmField(name = "order_id")
    @SerializedName("orderId")
    @Expose
    private int orderId;

    @RealmField(name = "address")
    @SerializedName("address")
    @Expose
    private String address;


    @RealmField(name = "address_delivery")
    @SerializedName("addressDelivery")
    @Expose
    private String addressDelivery;


    @RealmField(name = "code")
    @SerializedName("code")
    @Expose
    private String code;


    @RealmField(name = "full_name")
    @SerializedName("fullName")
    @Expose
    private String fullName;


    @RealmField(name = "phone")
    @SerializedName("phone")
    @Expose
    private String phone;


    @RealmField(name = "created_at")
    @SerializedName("createdAt")
    @Expose
    private String createdAt;


    @RealmField(name = "created_by")
    @SerializedName("createdBy")
    @Expose
    private int createdBy;


    @RealmField(name = "date_delivery")
    @SerializedName("dateDelivery")
    @Expose
    private String dateDelivery;


    @RealmField(name = "delivery_code")
    @SerializedName("deliveryCode")
    @Expose
    private String deliveryCode;


    @RealmField(name = "delivery_cost")
    @SerializedName("deliveryCost")
    @Expose
    private int deliveryCost;


    @RealmField(name = "discount")
    @SerializedName("discount")
    @Expose
    private int discount;


    @RealmField(name = "email")
    @SerializedName("email")
    @Expose
    private String email;

    @RealmField(name = "note")
    @SerializedName("note")
    @Expose
    private String note;


    @RealmField(name = "staff_id")
    @SerializedName("staffId")
    @Expose
    private int staffId;


    @RealmField(name = "sub_total")
    @SerializedName("subTotal")
    @Expose
    private int subTotal;


    @RealmField(name = "tax")
    @SerializedName("tax")
    @Expose
    private Tax tax;


    @RealmField(name = "total")
    @SerializedName("total")
    @Expose
    private int total;

    @RealmField(name = "updated_at")
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    @RealmField(name = "updated_by")
    @SerializedName("updatedBy")
    @Expose
    private int updatedBy;


    @RealmField(name = "voucher_id")
    @SerializedName("voucherId")
    @Expose
    private int voucherId;

    @RealmField(name = "custommer")
    @SerializedName("customer")
    @Expose
    private Customer customer;


    @RealmField(name = "order_delivery_status")
    @SerializedName("orderDeliveryStatus")
    @Expose
    private OrderDeliveryStatus orderDeliveryStatus;

    @RealmField(name = "order_delivery_type")
    @SerializedName("orderDeliveryType")
    @Expose
    private OrderDeliveryType orderDeliveryType;

    @RealmField(name = "order_payment_type")
    @SerializedName("orderPaymentType")
    @Expose
    private OrderPaymentType orderPaymentType;

    @RealmField(name = "order_reason_cancel")
    @SerializedName("orderReasonCancel")
    @Expose
    private OrderReasonCancel orderReasonCancel;

    @RealmField(name = "order_status")
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
