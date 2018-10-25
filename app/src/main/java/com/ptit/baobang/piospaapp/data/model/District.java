package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ptit.baobang.piospaapp.utils.AppConstants;

import java.io.Serializable;

public class District  implements Serializable{

    @SerializedName("districtid")
    @Expose
    private int districtId;
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("province")
    @Expose
    private Province province;

    public District() {
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
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

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    @Override
    public String toString() {
        return type + AppConstants.SPACE_SYMBOL + name;
    }

    @Override
    public boolean equals(Object obj) {
        District district = (District) obj;
        return districtId == district.getDistrictId();
    }
}