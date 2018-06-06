package com.ptit.baobang.piospaapp.data.network.model_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderCustomerBody {
    @SerializedName("orderStatusId")
    @Expose
    private int orderStatus;
    @SerializedName("customerId")
    @Expose
    private int customerId;

    public OrderCustomerBody(int orderStatus, int customerId) {
        this.orderStatus = orderStatus;
        this.customerId = customerId;
    }

    public OrderCustomerBody() {
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
