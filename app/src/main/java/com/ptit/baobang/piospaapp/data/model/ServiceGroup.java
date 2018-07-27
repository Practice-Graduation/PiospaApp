
package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServiceGroup  implements Serializable {

    @SerializedName("serviceGroupId")
    @Expose
    private int serviceGroupId;

    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @SerializedName("createdBy")
    @Expose
    private int createdBy;

    @SerializedName("isActive")
    @Expose
    private int isActive;

    @SerializedName("serviceGroupName")
    @Expose
    private String serviceGroupName;

    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    @SerializedName("updatedBy")
    @Expose
    private int updatedBy;

    public ServiceGroup() {
    }

    public int getServiceGroupId() {
        return serviceGroupId;
    }

    public void setServiceGroupId(int serviceGroupId) {
        this.serviceGroupId = serviceGroupId;
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

    public String getServiceGroupName() {
        return serviceGroupName;
    }

    public void setServiceGroupName(String serviceGroupName) {
        this.serviceGroupName = serviceGroupName;
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
