package com.ptit.baobang.piospaapp.data.network.model_request;

public class BookingDetailRequest {
    private String date;
    private int roomId;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
}
