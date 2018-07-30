package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderProductObject implements Serializable{
    @SerializedName("productId")
    @Expose
    private int productId;

    @SerializedName("productName")
    @Expose
    private String productName;

    @SerializedName("productImage")
    @Expose
    private String productImage;

    @SerializedName("price")
    @Expose
    private int price;

    @SerializedName("amount")
    @Expose
    private int amount;

    public OrderProductObject(int productId, String productName, String productImage, int price, int amount) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.price = price;
        this.amount = amount;
    }

    public OrderProductObject() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
