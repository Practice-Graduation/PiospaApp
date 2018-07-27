package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderDeliveryStatus  implements Serializable {

    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @SerializedName("createdBy")
    @Expose
    private int createdBy;


    @SerializedName("isActive")
    @Expose
    private int isActive;

    @SerializedName("isDelete")
    @Expose
    private int isDelete;

    @SerializedName("orderDeliveryStatusDescription")
    @Expose
    private String orderDeliveryStatusDescription;

    @SerializedName("orderDeliveryStatusId")
    @Expose
    private int orderDeliveryStatusId;

    @SerializedName("orderDeliveryStatusName")
    @Expose
    private String orderDeliveryStatusName;

    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    @SerializedName("updatedBy")
    @Expose
    private int updatedBy;

    public OrderDeliveryStatus() {
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

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getOrderDeliveryStatusDescription() {
        return orderDeliveryStatusDescription;
    }

    public void setOrderDeliveryStatusDescription(String orderDeliveryStatusDescription) {
        this.orderDeliveryStatusDescription = orderDeliveryStatusDescription;
    }

    public int getOrderDeliveryStatusId() {
        return orderDeliveryStatusId;
    }

    public void setOrderDeliveryStatusId(int orderDeliveryStatusId) {
        this.orderDeliveryStatusId = orderDeliveryStatusId;
    }

    public String getOrderDeliveryStatusName() {
        return orderDeliveryStatusName;
    }

    public void setOrderDeliveryStatusName(String orderDeliveryStatusName) {
        this.orderDeliveryStatusName = orderDeliveryStatusName;
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

}

