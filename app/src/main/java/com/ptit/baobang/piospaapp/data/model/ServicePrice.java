
package com.ptit.baobang.piospaapp.data.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServicePrice  implements Serializable, Comparable<ServicePrice> {

    @SerializedName("servicePriceId")
    @Expose
    private int servicePriceId;

    @SerializedName("allPrice")
    @Expose
    private int allPrice;


    @SerializedName("serviceGroup")
    @Expose
    private ServiceGroup serviceGroup;

    @SerializedName("servicePackage")
    @Expose
    private ServicePackage servicePackage;

    @SerializedName("serviceType")
    @Expose
    private ServiceType serviceType;

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
