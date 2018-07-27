package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Tax  implements Serializable {

    @SerializedName("taxId")
    @Expose
    private int taxId;

    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("createdBy")
    @Expose
    private String createdBy;

    @SerializedName("isActive")
    @Expose
    private int isActive;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    @SerializedName("updatedBy")
    @Expose
    private int updatedBy;

    @SerializedName("value")
    @Expose
    private int value;

    public Tax() {
    }

    public Tax(int taxId, String createdAt, String name, String createdBy, int isActive, String type, String updatedAt, int updatedBy, int value) {
        this.taxId = taxId;
        this.createdAt = createdAt;
        this.name = name;
        this.createdBy = createdBy;
        this.isActive = isActive;
        this.type = type;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.value = value;
    }

    public int getTaxId() {
        return taxId;
    }

    public void setTaxId(int taxId) {
        this.taxId = taxId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
