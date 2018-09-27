package com.ptit.baobang.piospaapp.data.network.model_request;

public class RoomBody {
    private String date;
    private String time;

    public RoomBody() {
    }

    public RoomBody(String date, String time) {
        this.date = date;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
