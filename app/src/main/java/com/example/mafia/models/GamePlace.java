package com.example.mafia.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GamePlace {
    @Expose
    @SerializedName("RooomIs")
    private String Room;

    @Expose
    @SerializedName("RoleData")
    private RoleModel role;

    public String getRoom() {
        return Room;
    }

    public void setRoom(String room) {
        Room = room;
    }

    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }
}
