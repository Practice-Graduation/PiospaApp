package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServicePackage  implements Serializable {
    @SerializedName("servicePackageId")
    @Expose
    private int servicePackageId;

    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @SerializedName("createdBy")
    @Expose
    private int createdBy;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("time")
    @Expose
    private int time;

    @SerializedName("isActive")
    @Expose
    private int isActive;

    @SerializedName("servicePackageName")
    @Expose
    private String servicePackageName;

    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    @SerializedName("updatedBy")
    @Expose
    private int updatedBy;

    public ServicePackage() {
    }

    public int getServicePackageId() {
        return servicePackageId;
    }



    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getServicePackageName() {
        return servicePackageName;
    }

    public void setServicePackageName(String servicePackageName) {
        this.servicePackageName = servicePackageName;
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
