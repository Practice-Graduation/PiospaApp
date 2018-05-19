
package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDeliveryType {

    @SerializedName("orderDeliveryTypeId")
    @Expose
    private Integer orderDeliveryTypeId;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;
    @SerializedName("isActive")
    @Expose
    private Integer isActive;
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
    private Integer updatedBy;

    public Integer getOrderDeliveryTypeId() {
        return orderDeliveryTypeId;
    }

    public void setOrderDeliveryTypeId(Integer orderDeliveryTypeId) {
        this.orderDeliveryTypeId = orderDeliveryTypeId;
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

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

}
