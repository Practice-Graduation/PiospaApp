package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmField;

public class ProductLabel extends RealmObject implements Serializable {
    @PrimaryKey
    @RealmField(name = "product_label_id")
    @SerializedName("productLabelId")
    @Expose
    private int productLabelId;

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

    @RealmField(name = "product_label_code")
    @SerializedName("productLabelCode")
    @Expose
    private String productLabelCode;

    @RealmField(name = "product_label_description")
    @SerializedName("productLabelDescription")
    @Expose
    private String productLabelDescription;

    @RealmField(name = "product_label_name")
    @SerializedName("productLabelName")
    @Expose
    private String productLabelName;

    @RealmField(name = "updated_at")
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    @RealmField(name = "updated_by")
    @SerializedName("updatedBy")
    @Expose
    private int updatedBy;

    public ProductLabel() {
    }

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
