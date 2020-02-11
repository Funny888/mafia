package com.example.mafia.interfaces;

import com.example.mafia.models.ChatModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ISendMessages {
    @POST("/SendCommonMessage")
    Call<ChatModel> sendCommonChat(@Body ChatModel chatModel);

}
