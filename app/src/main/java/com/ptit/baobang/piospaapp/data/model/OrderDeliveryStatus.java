package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDeliveryStatus {


    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;
    @SerializedName("isActive")
    @Expose
    private Integer isActive;
    @SerializedName("isDelete")
    @Expose
    private Integer isDelete;
    @SerializedName("orderDeliveryStatusDescription")
    @Expose
    private String orderDeliveryStatusDescription;
    @SerializedName("orderDeliveryStatusId")
    @Expose
    private Integer orderDeliveryStatusId;
    @SerializedName("orderDeliveryStatusName")
    @Expose
    private String orderDeliveryStatusName;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("updatedBy")
    @Expose
    private Integer updatedBy;

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

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getOrderDeliveryStatusDescription() {
        return orderDeliveryStatusDescription;
    }

    public void setOrderDeliveryStatusDescription(String orderDeliveryStatusDescription) {
        this.orderDeliveryStatusDescription = orderDeliveryStatusDescription;
    }

    public Integer getOrderDeliveryStatusId() {
        return orderDeliveryStatusId;
    }

    public void setOrderDeliveryStatusId(Integer orderDeliveryStatusId) {
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

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

}

