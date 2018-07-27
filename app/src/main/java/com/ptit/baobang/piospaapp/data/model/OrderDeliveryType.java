
package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmField;

public class OrderDeliveryType extends RealmObject implements Serializable {

    @PrimaryKey
    @RealmField(name = "order_delivery_type_id")
    @SerializedName("orderDeliveryTypeId")
    @Expose
    private int orderDeliveryTypeId;

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

    @RealmField(name = "price")
    @SerializedName("price")
    @Expose
    private int price;

    @RealmField(name = "order_delivery_type_code")
    @SerializedName("orderDeliveryTypeCode")
    @Expose
    private String orderDeliveryTypeCode;

    @RealmField(name = "order_delivery_type_name")
    @SerializedName("orderDeliveryTypeName")
    @Expose
    private String orderDeliveryTypeName;

    @RealmField(name = "updated_at")
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    @RealmField(name = "updated_by")
    @SerializedName("updatedBy")
    @Expose
    private int updatedBy;

    public OrderDeliveryType() {
    }

    public int getOrderDeliveryTypeId() {
        return orderDeliveryTypeId;
    }

    public void setOrderDeliveryTypeId(int orderDeliveryTypeId) {
        this.orderDeliveryTypeId = orderDeliveryTypeId;
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

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
