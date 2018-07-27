package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmField;

public class Ward extends RealmObject implements Serializable{

    @PrimaryKey
    @RealmField(name = "ward_id")
    @SerializedName("wardid")
    @Expose
    private int wardid;

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

    @RealmField(name = "district")
    @SerializedName("district")
    @Expose
    private District district;

    public Ward() {
    }

    public int getWardid() {
        return wardid;
    }

    public void setWardid(int wardid) {
        this.wardid = wardid;
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

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

}