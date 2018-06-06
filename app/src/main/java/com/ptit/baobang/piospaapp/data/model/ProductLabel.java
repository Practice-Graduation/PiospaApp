package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductLabel implements Serializable {
    @SerializedName("productLabelId")
    @Expose
    private int productLabelId;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("createdBy")
    @Expose
    private int createdBy;
    @SerializedName("isActive")
    @Expose
    private int isActive;
    @SerializedName("productLabelCode")
    @Expose
    private String productLabelCode;
    @SerializedName("productLabelDescription")
    @Expose
    private String productLabelDescription;
    @SerializedName("productLabelName")
    @Expose
    private String productLabelName;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("updatedBy")
    @Expose
    private int updatedBy;

    public int getProductLabelId() {
        return productLabelId;
    }

    public void setProductLabelId(int productLabelId) {
        this.productLabelId = productLabelId;
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

    public String getProductLabelCode() {
        return productLabelCode;
    }

    public void setProductLabelCode(String productLabelCode) {
        this.productLabelCode = productLabelCode;
    }

    public String getProductLabelDescription() {
        return productLabelDescription;
    }

    public void setProductLabelDescription(String productLabelDescription) {
        this.productLabelDescription = productLabelDescription;
    }

    public String getProductLabelName() {
        return productLabelName;
    }

    public void setProductLabelName(String productLabelName) {
        this.productLabelName = productLabelName;
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
