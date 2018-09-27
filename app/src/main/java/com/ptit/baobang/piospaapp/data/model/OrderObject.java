package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrderObject implements Serializable {
    @SerializedName("order")
    @Expose
    private Order order;

    @SerializedName("orderProducts")
    @Expose
    private List<OrderProductObject> orderProducts;


    public OrderObject(Order order, List<OrderProductObject> orderProducts) {
        this.order = order;
        this.orderProducts = orderProducts;
    }

    public OrderObject() {
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderProductObject> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProductObject> orderProducts) {
        this.orderProducts = orderProducts;
    }
}
