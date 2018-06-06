package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderReasonCancel implements Serializable {

    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("createdBy")
    @Expose
    private int createdBy;
    @SerializedName("isActive")
    @Expose
    private int isActive;
    @SerializedName("orderReasonCancelDescripton")
    @Expose
    private String orderReasonCancelDescripton;
    @SerializedName("orderReasonCancelId")
    @Expose
    private int orderReasonCancelId;
    @SerializedName("orderReasonCancelName")
    @Expose
    private String orderReasonCancelName;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("updatedBy")
    @Expose
    private int updatedBy;

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

    public String getOrderReasonCancelDescripton() {
        return orderReasonCancelDescripton;
    }

    public void setOrderReasonCancelDescripton(String orderReasonCancelDescripton) {
        this.orderReasonCancelDescripton = orderReasonCancelDescripton;
    }

    public int getOrderReasonCancelId() {
        return orderReasonCancelId;
    }

    public void setOrderReasonCancelId(int orderReasonCancelId) {
        this.orderReasonCancelId = orderReasonCancelId;
    }

    public String getOrderReasonCancelName() {
        return orderReasonCancelName;
    }

    public void setOrderReasonCancelName(String orderReasonCancelName) {
        this.orderReasonCancelName = orderReasonCancelName;
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
