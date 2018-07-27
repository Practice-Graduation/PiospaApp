package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmField;

public class OrderDeliveryStatus extends RealmObject implements Serializable {

    @RealmField(name="created_at")
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @RealmField(name="created_by")
    @SerializedName("createdBy")
    @Expose
    private int createdBy;


    @RealmField(name="is_active")
    @SerializedName("isActive")
    @Expose
    private int isActive;

    @RealmField(name="is_delete")
    @SerializedName("isDelete")
    @Expose
    private int isDelete;

    @RealmField(name="order_delivery_status_description")
    @SerializedName("orderDeliveryStatusDescription")
    @Expose
    private String orderDeliveryStatusDescription;

    @PrimaryKey
    @RealmField(name="order_delivery_status_id")
    @SerializedName("orderDeliveryStatusId")
    @Expose
    private int orderDeliveryStatusId;

    @RealmField(name="order_delivery_status_name")
    @SerializedName("orderDeliveryStatusName")
    @Expose
    private String orderDeliveryStatusName;

    @RealmField(name="updated_at")
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    @RealmField(name="updated_by")
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

