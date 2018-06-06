package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductOrigin implements Serializable {
    @SerializedName("productOriginId")
    @Expose
    private int productOriginId;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("createdBy")
    @Expose
    private int createdBy;
    @SerializedName("isActive")
    @Expose
    private int isActive;
    @SerializedName("productOriginDescription")
    @Expose
    private String productOriginDescription;
    @SerializedName("productOriginName")
    @Expose
    private String productOriginName;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("updatedBy")
    @Expose
    private int updatedBy;

    public int getProductOriginId() {
        return productOriginId;
    }

    public void setProductOriginId(int productOriginId) {
        this.productOriginId = productOriginId;
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

    public String getProductOriginDescription() {
        return productOriginDescription;
    }

    public void setProductOriginDescription(String productOriginDescription) {
        this.productOriginDescription = productOriginDescription;
    }

    public String getProductOriginName() {
        return productOriginName;
    }

    public void setProductOriginName(String productOriginName) {
        this.productOriginName = productOriginName;
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
