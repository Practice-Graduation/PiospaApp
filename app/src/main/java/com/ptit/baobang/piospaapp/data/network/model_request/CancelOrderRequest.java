package com.ptit.baobang.piospaapp.data.network.model_request;

public class CancelOrderRequest {
    private int orderId;

    public CancelOrderRequest() {
    }

    public CancelOrderRequest(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
