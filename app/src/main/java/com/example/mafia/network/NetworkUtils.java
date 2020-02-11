package com.example.mafia.network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.mafia.interfaces.IGetMessages;
import com.example.mafia.interfaces.ISendMessages;
import com.example.mafia.models.ChatModel;
import com.example.mafia.utils.RetofitBuilder;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkUtils {
    public static final String TAG = NetworkUtils.class.getSimpleName();
    public static final String Collection = "/Rooms/CommonChat/Messages";
    private FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
    private CollectionReference mReference = mDatabase.collection(Collection);

    private MutableLiveData<List<ChatModel>> getMessages = new MutableLiveData<>();

    public void sendMessage(String name, String message) {
        ChatModel model = new ChatModel();
        model.setName(name);
        model.setMessage(message);
        model.setDate(getData());
        ISendMessages sendMessages = new RetofitBuilder().SendMessage();
        sendMessages.sendCommonChat(model).enqueue(new Callback<ChatModel>() {
            @Override
            public void onResponse(Call<ChatModel> call, Response<ChatModel> response) {

            }

            @Override
            public void onFailure(Call<ChatModel> call, Throwable t) {

            }
        });
    }

    public  MutableLiveData<List<ChatModel>> getMessages() {
        mReference.addSnapshotListener((queryDocumentSnapshots, e) -> {
            getMessages.postValue(queryDocumentSnapshots.toObjects(ChatModel.class));
        });
        return getMessages;
    }

    private String getData(){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(new Date());
    }
}
