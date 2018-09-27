package com.ptit.baobang.piospaapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Room implements Serializable {
    @SerializedName("roomId")
    @Expose
    private int roomId;

    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @SerializedName("roomLimit")
    @Expose
    private int roomLimit;

    @SerializedName("roomName")
    @Expose
    private String roomName;

    @SerializedName("serving")
    @Expose
    private int serving;

    public Room() {
    }

    public Room(int roomId, String createdAt, int roomLimit, String roomName, int serving) {
        this.roomId = roomId;
        this.createdAt = createdAt;
        this.roomLimit = roomLimit;
        this.roomName = roomName;
        this.serving = serving;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getRoomLimit() {
        return roomLimit;
    }

    public void setRoomLimit(int roomLimit) {
        this.roomLimit = roomLimit;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getServing() {
        return serving;
    }

    public void setServing(int serving) {
        this.serving = serving;
    }
}
