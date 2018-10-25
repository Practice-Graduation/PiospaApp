package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import java.io.Serializable;

public class Province  implements Serializable{

    @SerializedName("provinceid")
    @Expose
    private int provinceId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("type")
    @Expose
    private String type;

    public Province() {
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
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

    @Override
    public String toString() {
        return type + AppConstants.SPACE_SYMBOL + name;
    }

    @Override
    public boolean equals(Object obj) {
        Province province = (Province) obj;
        return provinceId == province.getProvinceId();
    }
}