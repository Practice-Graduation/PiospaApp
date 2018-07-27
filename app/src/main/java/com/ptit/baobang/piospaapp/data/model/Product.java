package com.ptit.baobang.piospaapp.data.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmField;

public class Product extends RealmObject implements Serializable, Comparable<Product>{

    @PrimaryKey
    @RealmField(name = "product_id")
    @SerializedName("productId")
    @Expose
    private int productId;

    @RealmField(name = "cost_price")
    @SerializedName("costPrice")
    @Expose
    private int costPrice;

    @RealmField(name = "created_at")
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @RealmField(name = "created_by")
    @SerializedName("createdBy")
    @Expose
    private int createdBy;

    @RealmField(name = "description")
    @SerializedName("description")
    @Expose
    private String description;

    @RealmField(name = "image")
    @SerializedName("image")
    @Expose
    private String image;

    @RealmField(name = "info")
    @SerializedName("info")
    @Expose
    private String info;

    @RealmField(name = "is_active")
    @SerializedName("isActive")
    @Expose
    private int isActive;

    @RealmField(name = "price")
    @SerializedName("price")
    @Expose
    private int price;

    @RealmField(name = "product_code")
    @SerializedName("productCode")
    @Expose
    private String productCode;

    @RealmField(name = "product_name")
    @SerializedName("productName")
    @Expose
    private String productName;

    @RealmField(name = "amount")
    @SerializedName("amount")
    @Expose
    private int amount;

    @RealmField(name = "quantity")
    @SerializedName("quantity")
    @Expose
    private int quantity;

    @RealmField(name = "quantity_value")
    @SerializedName("quantityValue")
    @Expose
    private String quantityValue;

    @RealmField(name = "sku")
    @SerializedName("sku")
    @Expose
    private String sku;

    @RealmField(name = "thumbnail")
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;

    @RealmField(name = "updated_at")
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    @RealmField(name = "updated_by")
    @SerializedName("updatedBy")
    @Expose
    private int updatedBy;

    @RealmField(name = "product_group")
    @SerializedName("productGroup")
    @Expose
    private ProductGroup productGroup;

    @RealmField(name = "product_label")
    @SerializedName("productLabel")
    @Expose
    private ProductLabel productLabel;

    @RealmField(name = "product_origin")
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

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
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
