package com.ptit.baobang.piospaapp.data.network.model_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ptit.baobang.piospaapp.data.model.OrderProduct;

import java.util.List;

public class OrderResponse {
    @SerializedName("orderProducts")
    @Expose
    private List<OrderProduct> orderProducts = null;

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }
}
