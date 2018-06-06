
package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CustomerSource implements Serializable {

    @SerializedName("customerSourceId")
    @Expose
    private int customerSourceId;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("createdBy")
    @Expose
    private int createdBy;
    @SerializedName("customerSourceCode")
    @Expose
    private String customerSourceCode;
    @SerializedName("customerSourceDescription")
    @Expose
    private String customerSourceDescription;
    @SerializedName("customerSourceName")
    @Expose
    private String customerSourceName;
    @SerializedName("isActive")
    @Expose
    private int isActive;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("updatedBy")
    @Expose
    private int updatedBy;

    public int getCustomerSourceId() {
        return customerSourceId;
    }

    public void setCustomerSourceId(int customerSourceId) {
        this.customerSourceId = customerSourceId;
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

    public String getCustomerSourceCode() {
        return customerSourceCode;
    }

    public void setCustomerSourceCode(String customerSourceCode) {
        this.customerSourceCode = customerSourceCode;
    }

    public String getCustomerSourceDescription() {
        return customerSourceDescription;
    }

    public void setCustomerSourceDescription(String customerSourceDescription) {
        this.customerSourceDescription = customerSourceDescription;
    }

    public String getCustomerSourceName() {
        return customerSourceName;
    }

    public void setCustomerSourceName(String customerSourceName) {
        this.customerSourceName = customerSourceName;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
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
