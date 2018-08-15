package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Tax  implements Serializable {

    @SerializedName("taxId")
    @Expose
    private int taxId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("value")
    @Expose
    private int value;

    public Tax() {
    }

    public Tax(int taxId, String createdAt, String name, String createdBy, int isActive, String type, String updatedAt, int updatedBy, int value) {
        this.taxId = taxId;
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public int getTaxId() {
        return taxId;
    }

    public void setTaxId(int taxId) {
        this.taxId = taxId;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
