package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductLabel  implements Serializable {
    @SerializedName("productLabelId")
    @Expose
    private int productLabelId;

    @SerializedName("isActive")
    @Expose
    private int isActive;

    @SerializedName("productLabelName")
    @Expose
    private String productLabelName;

    public ProductLabel() {
    }

    public int getProductLabelId() {
        return productLabelId;
    }

    public void setProductLabelId(int productLabelId) {
        this.productLabelId = productLabelId;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getProductLabelName() {
        return productLabelName;
    }

    public void setProductLabelName(String productLabelName) {
        this.productLabelName = productLabelName;
    }

}
