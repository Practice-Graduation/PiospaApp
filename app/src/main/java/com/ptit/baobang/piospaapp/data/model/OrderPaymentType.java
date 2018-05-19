
package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderPaymentType {

    @SerializedName("orderPaymentTypeId")
    @Expose
    private Integer orderPaymentTypeId;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;
    @SerializedName("isActive")
    @Expose
    private Integer isActive;
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
    private Integer updatedBy;

    public Integer getOrderPaymentTypeId() {
        return orderPaymentTypeId;
    }

    public void setOrderPaymentTypeId(Integer orderPaymentTypeId) {
        this.orderPaymentTypeId = orderPaymentTypeId;
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

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
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

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

}
