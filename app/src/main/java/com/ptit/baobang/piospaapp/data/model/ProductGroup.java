package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class ProductGroup  implements Serializable {

    @SerializedName("productGroupId")
    @Expose
    private int productGroupId;

    @SerializedName("isActive")
    @Expose
    private int isActive;

    @SerializedName("productGroupName")
    @Expose
    private String productGroupName;

    public ProductGroup() {
    }

    public int getProductGroupId() {
        return productGroupId;
    }

    public void setProductGroupId(int productGroupId) {
        this.productGroupId = productGroupId;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getProductGroupName() {
        return productGroupName;
    }

    public void setProductGroupName(String productGroupName) {
        this.productGroupName = productGroupName;
    }

}