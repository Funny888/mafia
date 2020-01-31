package com.example.mafia.network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.mafia.interfaces.IGetMessages;
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
    private MutableLiveData<Integer> getMsg = new MutableLiveData<>();

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

    public void sendMessage(String name, String message) {
        mReference.get().addOnCompleteListener(command -> {
            DocumentReference documentReference = mDatabase.document(Collection + "/Message" + (command.getResult().getDocuments().size() + 1));
            WriteBatch batch = mDatabase.batch();
            ChatModel mess = new ChatModel();
            mess.setDate(getData());
            mess.setMessage(message);
            mess.setName(name);
            batch.set(documentReference,mess);
            batch.commit();
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
