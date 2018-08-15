
package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServiceTime  implements Serializable {

    @SerializedName("serviceTimeId")
    @Expose
    private int serviceTimeId;


    @SerializedName("time")
    @Expose
    private String time;

    public ServiceTime() {
    }

    public int getServiceTimeId() {
        return serviceTimeId;
    }

    public void setServiceTimeId(int serviceTimeId) {
        this.serviceTimeId = serviceTimeId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
