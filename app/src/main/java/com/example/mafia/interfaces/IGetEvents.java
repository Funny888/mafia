package com.example.mafia.interfaces;

import com.example.mafia.models.ChatModel;
import com.example.mafia.models.GamePlace;
import com.example.mafia.models.RoleModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IGetEvents {
    @GET("/getMessage")
    Call<List<ChatModel>> getMessage();

    @GET("/GetRoom")
    Call<GamePlace> getGamePlace();
}
