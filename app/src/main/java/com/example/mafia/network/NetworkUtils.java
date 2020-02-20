package com.example.mafia.network;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.mafia.R;
import com.example.mafia.interfaces.IGetEvents;
import com.example.mafia.interfaces.ISendMessages;
import com.example.mafia.models.ChatModel;
import com.example.mafia.models.GamePlace;
import com.example.mafia.utils.RetofitBuilder;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkUtils {
    public static final String TAG = NetworkUtils.class.getSimpleName();
    public static final String Collection = "/Rooms/CommonChat/Messages";

    private Context mContext;
    private FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
    private CollectionReference mReference = mDatabase.collection(Collection);

    private MutableLiveData<List<ChatModel>> getMessages = new MutableLiveData<>();
    private MutableLiveData<GamePlace> getRole = new MutableLiveData<>();

    public NetworkUtils(Context context){ mContext = context;}

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

    public MutableLiveData<GamePlace> getRole(){
        IGetEvents role = new RetofitBuilder().getRole();
        role.getGamePlace().enqueue(new Callback<GamePlace>() {
            @Override
            public void onResponse(Call<GamePlace> call, Response<GamePlace> response) {
                Log.d(TAG, "onResponse: " + response.toString());
                GamePlace role = response.body();
                role.getRole().setRoleLetter("test");
                role.getRole().setRoleDrawable(getImageRole(role.getRole().getRoleName()));
                getRole.postValue(role);
            }

            @Override
            public void onFailure(Call<GamePlace> call, Throwable t) {

            }
        });

        return getRole;
    }

    private String getData(){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(new Date());
    }

    private Drawable getImageRole(String role) {
        Drawable image = null;
        switch (role) {
            case "Mafia": {
                image = mContext.getDrawable(R.drawable.card_image_mafia);
                break;
            }

            case "Civilian": {
                image = mContext.getDrawable(R.drawable.card_image_people);
                break;
            }
        }
        return image;
    }
}
