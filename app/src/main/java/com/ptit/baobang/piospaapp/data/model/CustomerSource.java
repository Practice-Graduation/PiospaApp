
package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerSource {

    @SerializedName("customerSourceId")
    @Expose
    private Integer customerSourceId;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;
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
    private Integer isActive;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("updatedBy")
    @Expose
    private Integer updatedBy;

    public Integer getCustomerSourceId() {
        return customerSourceId;
    }

    public void setCustomerSourceId(Integer customerSourceId) {
        this.customerSourceId = customerSourceId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
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

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

}
