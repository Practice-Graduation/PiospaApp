package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class ProductOrigin  implements Serializable {
    @SerializedName("productOriginId")
    @Expose
    private int productOriginId;

    @SerializedName("isActive")
    @Expose
    private int isActive;

    @SerializedName("productOriginName")
    @Expose
    private String productOriginName;

    public ProductOrigin() {
    }

    public int getProductOriginId() {
        return productOriginId;
    }

    public void setProductOriginId(int productOriginId) {
        this.productOriginId = productOriginId;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getProductOriginName() {
        return productOriginName;
    }

    public void setProductOriginName(String productOriginName) {
        this.productOriginName = productOriginName;
    }
}
