package com.ptit.baobang.piospaapp.data.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product  implements Serializable, Comparable<Product>{

    @SerializedName("productId")
    @Expose
    private int productId;

    @SerializedName("costPrice")
    @Expose
    private int costPrice;

    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("isActive")
    @Expose
    private int isActive;

    @SerializedName("price")
    @Expose
    private int price;

    @SerializedName("productName")
    @Expose
    private String productName;

    @SerializedName("amount")
    @Expose
    private int amount;

    @SerializedName("quantity")
    @Expose
    private int quantity;

    @SerializedName("quantityValue")
    @Expose
    private String quantityValue;

    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    @SerializedName("productGroup")
    @Expose
    private ProductGroup productGroup;

    @SerializedName("productLabel")
    @Expose
    private ProductLabel productLabel;

    @SerializedName("productOrigin")
    @Expose
    private ProductOrigin productOrigin;

    public Product() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(int costPrice) {
        this.costPrice = costPrice;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getQuantityValue() {
        return quantityValue;
    }

    public void setQuantityValue(String quantityValue) {
        this.quantityValue = quantityValue;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ProductGroup getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(ProductGroup productGroup) {
        this.productGroup = productGroup;
    }

    public ProductLabel getProductLabel() {
        return productLabel;
    }

    public void setProductLabel(ProductLabel productLabel) {
        this.productLabel = productLabel;
    }

    public ProductOrigin getProductOrigin() {
        return productOrigin;
    }

    public void setProductOrigin(ProductOrigin productOrigin) {
        this.productOrigin = productOrigin;
    }

    @Override
    public int compareTo(@NonNull Product o) {

        return this.productId == o.getProductId() ? 0 : 1;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
