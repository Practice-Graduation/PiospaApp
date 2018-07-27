
package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderDeliveryType  implements Serializable {

    @SerializedName("orderDeliveryTypeId")
    @Expose
    private int orderDeliveryTypeId;

    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @SerializedName("createdBy")
    @Expose
    private int createdBy;

    @SerializedName("isActive")
    @Expose
    private int isActive;

    @SerializedName("price")
    @Expose
    private int price;

    @SerializedName("orderDeliveryTypeCode")
    @Expose
    private String orderDeliveryTypeCode;

    @SerializedName("orderDeliveryTypeName")
    @Expose
    private String orderDeliveryTypeName;

    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    @SerializedName("updatedBy")
    @Expose
    private int updatedBy;

    public OrderDeliveryType() {
    }

    public int getOrderDeliveryTypeId() {
        return orderDeliveryTypeId;
    }

    public void setOrderDeliveryTypeId(int orderDeliveryTypeId) {
        this.orderDeliveryTypeId = orderDeliveryTypeId;
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

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getOrderDeliveryTypeCode() {
        return orderDeliveryTypeCode;
    }

    public void setOrderDeliveryTypeCode(String orderDeliveryTypeCode) {
        this.orderDeliveryTypeCode = orderDeliveryTypeCode;
    }

    public String getOrderDeliveryTypeName() {
        return orderDeliveryTypeName;
    }

    public void setOrderDeliveryTypeName(String orderDeliveryTypeName) {
        this.orderDeliveryTypeName = orderDeliveryTypeName;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
