
package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("orderId")
    @Expose
    private Integer orderId;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("addressDelivery")
    @Expose
    private String addressDelivery;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;
    @SerializedName("dateDelivery")
    @Expose
    private String dateDelivery;
    @SerializedName("deliveryCode")
    @Expose
    private String deliveryCode;
    @SerializedName("deliveryCost")
    @Expose
    private Integer deliveryCost;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("staffId")
    @Expose
    private Integer staffId;
    @SerializedName("subTotal")
    @Expose
    private Integer subTotal;
    @SerializedName("taxId")
    @Expose
    private Integer taxId;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("updatedBy")
    @Expose
    private Integer updatedBy;
    @SerializedName("voucherId")
    @Expose
    private Integer voucherId;
    @SerializedName("customerSource")
    @Expose
    private CustomerSource customerSource;
    @SerializedName("customer")
    @Expose
    private Customer customer;
    @SerializedName("orderDeliveryStatus")
    @Expose
    private Object orderDeliveryStatus;
    @SerializedName("orderDeliveryType")
    @Expose
    private OrderDeliveryType orderDeliveryType;
    @SerializedName("orderPaymentType")
    @Expose
    private OrderPaymentType orderPaymentType;
    @SerializedName("orderReasonCancel")
    @Expose
    private Object orderReasonCancel;
    @SerializedName("orderStatus")
    @Expose
    private Object orderStatus;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
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

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
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

    public Integer getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(Integer deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
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

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Integer getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }

    public Integer getTaxId() {
        return taxId;
    }

    public void setTaxId(Integer taxId) {
        this.taxId = taxId;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public CustomerSource getCustomerSource() {
        return customerSource;
    }

    public void setCustomerSource(CustomerSource customerSource) {
        this.customerSource = customerSource;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Object getOrderDeliveryStatus() {
        return orderDeliveryStatus;
    }

    public void setOrderDeliveryStatus(Object orderDeliveryStatus) {
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

    public Object getOrderReasonCancel() {
        return orderReasonCancel;
    }

    public void setOrderReasonCancel(Object orderReasonCancel) {
        this.orderReasonCancel = orderReasonCancel;
    }

    public Object getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Object orderStatus) {
        this.orderStatus = orderStatus;
    }

}
