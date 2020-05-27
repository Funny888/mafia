package com.example.mafia.interfaces;

import com.example.mafia.models.ChatModel;
import com.example.mafia.models.GamePlace;
import com.example.mafia.models.ResponseApi;
import com.example.mafia.models.RoleModel;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IGetEvents {
    @POST("/getMessage")
    Call<List<ChatModel>> getMessage();

    @POST("/GetRoom")
    Call<GamePlace> getGamePlace();

    @POST("/GetAllPlayers")
    @FormUrlEncoded
    Call<ArrayList<RoleModel>> getAllPlayers(@Field("Room") String room);

    @POST("/SendVote")
    @FormUrlEncoded
    Call<JsonObject> sendVote(@Field("Room") String room, @Field("TargetId") Long targetId, @Field("MyId") Long myId);

    @POST("/StartGame")
    @FormUrlEncoded
    Call<ResponseApi> startGame(@Field("Room") String room);
}
