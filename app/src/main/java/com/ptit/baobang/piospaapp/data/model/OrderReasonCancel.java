package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmField;

public class OrderReasonCancel extends RealmObject implements Serializable {

    @RealmField(name = "created_at")
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @RealmField(name = "created_by")
    @SerializedName("createdBy")
    @Expose
    private int createdBy;

    @RealmField(name = "is_active")
    @SerializedName("isActive")
    @Expose
    private int isActive;

    @RealmField(name = "order_reason_cancel_descripton")
    @SerializedName("orderReasonCancelDescripton")
    @Expose
    private String orderReasonCancelDescripton;

    @PrimaryKey
    @RealmField(name = "order_reason_cancel_id")
    @SerializedName("orderReasonCancelId")
    @Expose
    private int orderReasonCancelId;

    @RealmField(name = "order_reason_cancel_name")
    @SerializedName("orderReasonCancelName")
    @Expose
    private String orderReasonCancelName;

    @RealmField(name = "updated_at")
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    @RealmField(name = "updated_by")
    @SerializedName("updatedBy")
    @Expose
    private int updatedBy;

    public OrderReasonCancel() {
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
