package com.example.mafia.network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.mafia.interfaces.IGetMessages;
import com.example.mafia.models.ChatModel;
import com.example.mafia.utils.RetofitBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkUtils {
    public static final String TAG = NetworkUtils.class.getSimpleName();

    private MutableLiveData<List<ChatModel>> getMessages = new MutableLiveData<>();

    public MutableLiveData<List<ChatModel>> showMessages(){
        IGetMessages getM = new RetofitBuilder().getMessages();
        getM.getMessage().enqueue(new Callback<List<ChatModel>>() {
            @Override
            public void onResponse(Call<List<ChatModel>> call, Response<List<ChatModel>> response) {
                List<ChatModel> messages = response.body();
                getMessages.postValue(messages);
            }

            @Override
            public void onFailure(Call<List<ChatModel>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
            }
        });
        return getMessages;
    }
}
