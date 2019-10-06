package com.example.mafia.models;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mafia.R;
import com.example.mafia.utils.MyObservable;
import com.example.mafia.utils.MyObserver;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

public class BD {
    public static final String Rooms = "Rooms";
    public static final String Roles = "Roles";
    public static final String Id = "Id";
    public static final String Role = "Role";
    public static final String isBusy = "isBusy";
    public static final String isDead = "isDead";
    ArrayList<RoleModel> mPlayers = new ArrayList<>();
    private MutableLiveData<RoleModel> mRoleMutable = new MutableLiveData();
    private MyObservable setRoom = new MyObservable();
    private MyObserver mGetRoom;
    private MyObserver mGetPlayers;
    private MyObserver mResetRoles;
    private Context mContext;

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference reference = firestore.collection(Rooms);

    private static BD sInstance;

    private BD(Context ctx) {
        mContext = ctx;


    }


    public static BD getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new BD(context);
        }
        return sInstance;
    }


    private int rand(QuerySnapshot queryDocumentSnapshots) {
        int max = queryDocumentSnapshots.size();
        System.out.println(max);
        Random r = new Random();
        int l = r.nextInt(max);
        return l;
    }

    public void getRoom() {
        reference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                boolean flag = false;
                DocumentSnapshot result = null;
                while (!flag) {
                    result = queryDocumentSnapshots.getDocuments().get(rand(queryDocumentSnapshots));

                    if (!result.getBoolean(isBusy)) {
                        flag = true;
                        setRoom.attach(mGetPlayers);
                        setRoom.attach(mGetRoom);
                        setRoom.setValue(result);
                        System.out.println(result.getId());
                    }
                }
            }
        });

    }

    public void  resetRoleBusy(){
        mResetRoles = new MyObserver() {
            @Override
            public void result(Object obj) {
                super.result(obj);
                DocumentSnapshot result = (DocumentSnapshot) obj;
                result.getReference().collection(Role).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot result : queryDocumentSnapshots.getDocuments()) {
                            result.getReference().update(isBusy,true);
                        }

                    }
                });
            }
        };
    }

    public LiveData<RoleModel> getRole() {

        mGetRoom = new MyObserver() {
            @Override
            public void result(Object obj) {
                super.result(obj);
              DocumentSnapshot room =  (DocumentSnapshot) obj;
                room.getReference().collection(Roles).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        boolean flag = false;
                        int times = 0;
                        DocumentSnapshot result = null;
                        while (!flag) {
                            result = queryDocumentSnapshots.getDocuments().get(rand(queryDocumentSnapshots));

                            if (!result.getBoolean(isBusy)) {
                                flag = true;
                                result.getReference().update(isBusy, true);
                                mRoleMutable.postValue(new RoleModel(result.getLong(Id), result.getString(Role), getImageRole(result.getString(Role)), result.getBoolean(isBusy), result.getBoolean(isDead), "ffd"));
                            }
                            if (times == 2) {
                                return;
                            }
                            times++;
                        }
                    }


                });
            }
        };

        return mRoleMutable;
    }


    public ArrayList<RoleModel> getPlayers() {
        mGetPlayers = new MyObserver() {
            @Override
            public void result(Object obj) {
                super.result(obj);
               DocumentSnapshot result = (DocumentSnapshot) obj;
                result.getReference().collection(Roles).addSnapshotListener((queryDocumentSnapshots, e) -> {
                    mPlayers.clear();
                    for (DocumentSnapshot play : queryDocumentSnapshots.getDocuments()) {
                        System.out.println(play.getId());
                        mPlayers.add(new RoleModel(play.getLong(Id), play.getString(Role), getImageRole(play.getString(Role)), play.getBoolean(isBusy), play.getBoolean(isDead), "ffd"));
                    }
                });
            }
        };

        return mPlayers;
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
