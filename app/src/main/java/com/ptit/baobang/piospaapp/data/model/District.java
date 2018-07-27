package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmField;

public class District extends RealmObject implements Serializable{

    @PrimaryKey
    @RealmField(name = "district_id")
    @SerializedName("districtid")
    @Expose
    private int districtid;

    @RealmField(name = "location")
    @SerializedName("location")
    @Expose
    private String location;

    @RealmField(name = "name")
    @SerializedName("name")
    @Expose
    private String name;

    @RealmField(name = "type")
    @SerializedName("type")
    @Expose
    private String type;

    @RealmField(name = "province")
    @SerializedName("province")
    @Expose
    private Province province;

    public District() {
    }

    public int getDistrictid() {
        return districtid;
    }

    public void setDistrictid(int districtid) {
        this.districtid = districtid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

}