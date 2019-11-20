package com.example.mafia.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatModel {
    @Expose
    @SerializedName("Date")
    private String Date;

    @Expose
    @SerializedName("Message")
    private String Message;

    @Expose
    @SerializedName("Name")
    private String Name;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}
