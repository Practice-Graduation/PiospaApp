
package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderDeliveryType  implements Serializable {

    @SerializedName("orderDeliveryTypeId")
    @Expose
    private int orderDeliveryTypeId;

    @SerializedName("price")
    @Expose
    private int price;

    @SerializedName("orderDeliveryTypeName")
    @Expose
    private String orderDeliveryTypeName;


    public OrderDeliveryType() {
    }

    public int getOrderDeliveryTypeId() {
        return orderDeliveryTypeId;
    }

    public void setOrderDeliveryTypeId(int orderDeliveryTypeId) {
        this.orderDeliveryTypeId = orderDeliveryTypeId;
    }


    public String getOrderDeliveryTypeName() {
        return orderDeliveryTypeName;
    }

    public void setOrderDeliveryTypeName(String orderDeliveryTypeName) {
        this.orderDeliveryTypeName = orderDeliveryTypeName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
