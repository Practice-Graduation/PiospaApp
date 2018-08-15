package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServicePackage  implements Serializable {
    @SerializedName("servicePackageId")
    @Expose
    private int servicePackageId;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getServicePackageName() {
        return servicePackageName;
    }

    public void setServicePackageName(String servicePackageName) {
        this.servicePackageName = servicePackageName;
    }

    public void setServicePackageId(int servicePackageId) {
        this.servicePackageId = servicePackageId;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }
}
