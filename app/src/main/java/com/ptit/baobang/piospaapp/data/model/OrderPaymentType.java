
package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderPaymentType  implements Serializable {

    @SerializedName("orderPaymentTypeId")
    @Expose
    private int orderPaymentTypeId;

    @SerializedName("orderPaymentTypeName")
    @Expose
    private String orderPaymentTypeName;

    public OrderPaymentType() {
    }

    public int getOrderPaymentTypeId() {
        return orderPaymentTypeId;
    }

    public void setOrderPaymentTypeId(int orderPaymentTypeId) {
        this.orderPaymentTypeId = orderPaymentTypeId;
    }

    public String getOrderPaymentTypeName() {
        return orderPaymentTypeName;
    }

    public void setOrderPaymentTypeName(String orderPaymentTypeName) {
        this.orderPaymentTypeName = orderPaymentTypeName;
    }

}
