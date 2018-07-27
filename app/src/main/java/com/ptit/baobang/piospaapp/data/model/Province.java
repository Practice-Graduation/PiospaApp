package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmField;

public class Province extends RealmObject implements Serializable{

    @PrimaryKey
    @RealmField(name = "province_id")
    @SerializedName("provinceid")
    @Expose
    private int provinceid;

    @RealmField(name = "location_id")
    @SerializedName("locationId")
    @Expose
    private int locationId;

    @RealmField(name = "name")
    @SerializedName("name")
    @Expose
    private String name;

    @RealmField(name = "province_id")
    @SerializedName("type")
    @Expose
    private String type;

    public Province() {
    }

    public int getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(int provinceid) {
        this.provinceid = provinceid;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
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

}