
package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmField;

public class OrderPaymentType extends RealmObject implements Serializable {

    @PrimaryKey
    @RealmField(name = "order_payment_type_id")
    @SerializedName("orderPaymentTypeId")
    @Expose
    private int orderPaymentTypeId;

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

    @RealmField(name = "order_payment_type_description")
    @SerializedName("orderPaymentTypeDescription")
    @Expose
    private String orderPaymentTypeDescription;

    @RealmField(name = "order_payment_type_name")
    @SerializedName("orderPaymentTypeName")
    @Expose
    private String orderPaymentTypeName;

    @RealmField(name = "updated_at")
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    @RealmField(name = "updated_by")
    @SerializedName("updatedBy")
    @Expose
    private int updatedBy;

    public OrderPaymentType() {
    }

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
