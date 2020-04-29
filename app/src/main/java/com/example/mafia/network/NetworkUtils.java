package com.example.mafia.network;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.mafia.R;
import com.example.mafia.interfaces.IGetEvents;
import com.example.mafia.interfaces.ISendMessages;
import com.example.mafia.models.ChatModel;
import com.example.mafia.models.GamePlace;
import com.example.mafia.models.RoleModel;
import com.example.mafia.utils.Logger;
import com.example.mafia.utils.RetofitBuilder;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mafia.utils.Logger.PTAG;

public class NetworkUtils {
    public static final String TAG = NetworkUtils.class.getSimpleName();
    public static final String Collection = "/Rooms/CommonChat/Messages";

    private Context mContext;
    private FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
    private CollectionReference mReference = mDatabase.collection(Collection);

    private MutableLiveData<List<ChatModel>> getMessages = new MutableLiveData<>();
    private MutableLiveData<GamePlace> getRole = new MutableLiveData<>();
    private MutableLiveData<ArrayList<RoleModel>> getAllPlayers = new MutableLiveData<>();

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
                GamePlace role = response.body();
                if(!response.body().getRoom().equals(mContext.getResources().getString(R.string.no_free_rooms))){
                    role.getRole().setRoleLetter("test");
                    role.getRole().setRoleDrawable(getImageRole(role.getRole().getRoleName()));
                }
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

    public MutableLiveData<ArrayList<RoleModel>> getAllPlayers(String room){
        IGetEvents players = new RetofitBuilder().getPlayers();
        try {
            players.getAllPlayers(room).enqueue(new Callback<ArrayList<RoleModel>>() {
                @Override
                public void onResponse(Call<ArrayList<RoleModel>> call, Response<ArrayList<RoleModel>> response) {
                    ArrayList<RoleModel> list = response.body();
                    for (RoleModel model: list) {
                        model.setRoleDrawable(getImageRole(model.getRoleName()));
                        model.setRoleLetter("player " + model.getId());
                    }
                    getAllPlayers.postValue(list);
                }

                @Override
                public void onFailure(Call<ArrayList<RoleModel>> call, Throwable t) {

                }
            });
        } catch (NullPointerException e) {
            Log.w(PTAG,TAG + "@getAllPlayers: exception -> ", e);
        }

        return getAllPlayers;
    }

    public void sendVote(String room, Long target, Long myId){
        IGetEvents sendVote = new RetofitBuilder().sendVoted();
        sendVote.sendVote(room,target,myId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    String result = response.body().get("Result").getAsString();
                    Toast.makeText(mContext,result,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
}
