package com.example.mafia.interfaces;

import com.example.mafia.models.ChatModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IGetMessages {
    @GET("/getMessage")
    Call<List<ChatModel>> getMessage();
}
