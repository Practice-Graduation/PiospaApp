package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderReasonCancel {

    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;
    @SerializedName("isActive")
    @Expose
    private Integer isActive;
    @SerializedName("orderReasonCancelDescripton")
    @Expose
    private String orderReasonCancelDescripton;
    @SerializedName("orderReasonCancelId")
    @Expose
    private Integer orderReasonCancelId;
    @SerializedName("orderReasonCancelName")
    @Expose
    private String orderReasonCancelName;
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

    public String getOrderReasonCancelDescripton() {
        return orderReasonCancelDescripton;
    }

    public void setOrderReasonCancelDescripton(String orderReasonCancelDescripton) {
        this.orderReasonCancelDescripton = orderReasonCancelDescripton;
    }

    public Integer getOrderReasonCancelId() {
        return orderReasonCancelId;
    }

    public void setOrderReasonCancelId(Integer orderReasonCancelId) {
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

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }
}
