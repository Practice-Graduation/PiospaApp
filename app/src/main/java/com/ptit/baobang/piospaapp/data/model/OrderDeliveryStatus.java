package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderDeliveryStatus  implements Serializable {


    @SerializedName("orderDeliveryStatusId")
    @Expose
    private int orderDeliveryStatusId;

    @SerializedName("orderDeliveryStatusName")
    @Expose
    private String orderDeliveryStatusName;


    public OrderDeliveryStatus() {
    }

    public int getOrderDeliveryStatusId() {
        return orderDeliveryStatusId;
    }

    public void setOrderDeliveryStatusId(int orderDeliveryStatusId) {
        this.orderDeliveryStatusId = orderDeliveryStatusId;
    }

    public String getOrderDeliveryStatusName() {
        return orderDeliveryStatusName;
    }

    public void setOrderDeliveryStatusName(String orderDeliveryStatusName) {
        this.orderDeliveryStatusName = orderDeliveryStatusName;
    }

}

