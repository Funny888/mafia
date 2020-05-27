package com.example.mafia.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseApi {

    @Expose
    @SerializedName("Result")
    private String mResult;

    public String getResult() {
        return mResult;
    }

    public void setResult(String result) {
        this.mResult = result;
    }
}
