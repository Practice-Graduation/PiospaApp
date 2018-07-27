
package com.ptit.baobang.piospaapp.data.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmField;

public class ServicePrice extends RealmObject implements Serializable, Comparable<ServicePrice> {

    @PrimaryKey
    @RealmField(name = "service_price_id")
    @SerializedName("servicePriceId")
    @Expose
    private int servicePriceId;

    @RealmField(name = "all_price")
    @SerializedName("allPrice")
    @Expose
    private int allPrice;

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

    @RealmField(name = "retail_price")
    @SerializedName("retailPrice")
    @Expose
    private int retailPrice;

    @RealmField(name = "updated_at")
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    @RealmField(name = "updated_by")
    @SerializedName("updatedBy")
    @Expose
    private int updatedBy;

    @RealmField(name = "service_group")
    @SerializedName("serviceGroup")
    @Expose
    private ServiceGroup serviceGroup;

    @RealmField(name = "service_package")
    @SerializedName("servicePackage")
    @Expose
    private ServicePackage servicePackage;

    @RealmField(name = "service_type")
    @SerializedName("serviceType")
    @Expose
    private ServiceType serviceType;

    @RealmField(name = "service")
    @SerializedName("service")
    @Expose
    private Service service;

    public ServicePrice() {
    }

    public int getServicePriceId() {
        return servicePriceId;
    }

    public void setServicePriceId(int servicePriceId) {
        this.servicePriceId = servicePriceId;
    }

    public int getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(int allPrice) {
        this.allPrice = allPrice;
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

    public int getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(int retailPrice) {
        this.retailPrice = retailPrice;
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

    public ServiceGroup getServiceGroup() {
        return serviceGroup;
    }

    public void setServiceGroup(ServiceGroup serviceGroup) {
        this.serviceGroup = serviceGroup;
    }

    public ServicePackage getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(ServicePackage servicePackage) {
        this.servicePackage = servicePackage;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public int compareTo(@NonNull ServicePrice o) {
        return this.servicePriceId == o.getServicePriceId() ? 0 : 1;
    }

    @Override
    public String toString() {
        if (servicePackage != null) {
            return servicePackage.getServicePackageName();
        }
        if (service != null) {
            return service.getServiceName();
        }
        return "";
    }
}
