package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderProduct implements Serializable{

    @SerializedName("orderProductId")
    @Expose
    private int orderProductId;

    @SerializedName("discount")
    @Expose
    private int discount;

    @SerializedName("number")
    @Expose
    private int number;

    @SerializedName("price")
    @Expose
    private int price;

    @SerializedName("total")
    @Expose
    private int total;


    @SerializedName("order")
    @Expose
    private Order order;

    @SerializedName("product")
    @Expose
    private Product product;

    public OrderProduct() {
    }

    public int getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(int orderProductId) {
        this.orderProductId = orderProductId;
    }


    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}