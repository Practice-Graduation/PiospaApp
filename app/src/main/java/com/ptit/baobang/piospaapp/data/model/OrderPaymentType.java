
package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderPaymentType implements Serializable {

    @SerializedName("orderPaymentTypeId")
    @Expose
    private int orderPaymentTypeId;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("createdBy")
    @Expose
    private int createdBy;
    @SerializedName("isActive")
    @Expose
    private int isActive;
    @SerializedName("orderPaymentTypeDescription")
    @Expose
    private String orderPaymentTypeDescription;
    @SerializedName("orderPaymentTypeName")
    @Expose
    private String orderPaymentTypeName;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("updatedBy")
    @Expose
    private int updatedBy;

    public int getOrderPaymentTypeId() {
        return orderPaymentTypeId;
    }

    public void setOrderPaymentTypeId(int orderPaymentTypeId) {
        this.orderPaymentTypeId = orderPaymentTypeId;
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

    public String getOrderPaymentTypeDescription() {
        return orderPaymentTypeDescription;
    }

    public void setOrderPaymentTypeDescription(String orderPaymentTypeDescription) {
        this.orderPaymentTypeDescription = orderPaymentTypeDescription;
    }

    public String getOrderPaymentTypeName() {
        return orderPaymentTypeName;
    }

    public void setOrderPaymentTypeName(String orderPaymentTypeName) {
        this.orderPaymentTypeName = orderPaymentTypeName;
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
