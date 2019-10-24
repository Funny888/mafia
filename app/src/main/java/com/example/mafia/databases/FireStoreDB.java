package com.example.mafia.databases;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mafia.R;
import com.example.mafia.models.RoleModel;
import com.example.mafia.utils.MyObservable;
import com.example.mafia.utils.MyObserver;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

public class FireStoreDB {
    public static final String TAG = FireStoreDB.class.getSimpleName();

    public static final String Rooms = "Rooms";
    public static final String Roles = "Roles";
    public static final String Id = "Id";
    public static final String Role = "Role";
    public static final String isBusy = "isBusy";
    public static final String isDead = "isDead";
    ArrayList<RoleModel> mPlayers = new ArrayList<>();
    private MutableLiveData<RoleModel> mRoleMutable = new MutableLiveData();
    private MutableLiveData<Integer> mFreePlace = new MutableLiveData();
    private MyObservable setRoom = new MyObservable();
    private MyObserver mGetRole;
    private MyObserver mGetPlayers;
    private MyObserver mResetRoles;
    private MyObserver mGetFreePlace;
    private Context mContext;

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference reference = firestore.collection(Rooms);

    private static FireStoreDB sInstance;

    private FireStoreDB(Context ctx) {
        mContext = ctx;


    }


    public static FireStoreDB getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new FireStoreDB(context);
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
        Log.d(TAG, "getRoom: ");
        reference.get().addOnSuccessListener((queryDocumentSnapshots) -> {

            boolean flag = false;
            DocumentSnapshot result = null;
            while (!flag) {
                result = queryDocumentSnapshots.getDocuments().get(rand(queryDocumentSnapshots));
                if (!result.getBoolean(isBusy)) {
                    flag = true;
                    setRoom.attach(mGetPlayers);
                    setRoom.attach(mGetRole);
//                        setRoom.attach(mResetRoles);
                    setRoom.attach(mGetFreePlace);
                    setRoom.setValue(result);
                    System.out.println(result.getId());
                    setRoom.dettach(mGetPlayers);
                    setRoom.dettach(mGetRole);
                    setRoom.dettach(mResetRoles);
                    setRoom.dettach(mGetFreePlace);
                }
            }
        });

    }

    public void resetRoleBusy() {
        Log.d(TAG, "resetRoleBusy: ");
        mResetRoles = new MyObserver() {
            @Override
            public void result(Object obj) {
                super.result(obj);
                DocumentSnapshot result = (DocumentSnapshot) obj;
                result.getReference().collection(Roles).get().addOnSuccessListener((queryDocumentSnapshots) -> {
                            for (DocumentSnapshot resultReset : queryDocumentSnapshots.getDocuments()) {
                                resultReset.getReference().update(isBusy, false);
                            }
                        }
                );
            }
        };
    }

    public LiveData<RoleModel> getRole() {
        Log.d(TAG, "getRole: ");
        mGetRole = new MyObserver() {
            @Override
            public void result(Object obj) {
                super.result(obj);
                DocumentSnapshot room = (DocumentSnapshot) obj;
                room.getReference().collection(Roles).get().addOnSuccessListener((queryDocumentSnapshots) -> {
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
                        if (times == 6) {
                            return;
                        }
                        times++;
                    }
                });
            }
        };
        return mRoleMutable;
    }

    public LiveData<Integer> getFreePlace() {
        Log.d(TAG, "getFreePlace: ");
        mGetFreePlace = new MyObserver() {
            @Override
            public void result(Object obj) {
                super.result(obj);
                DocumentSnapshot result = (DocumentSnapshot) obj;
                result.getReference().collection(Roles).addSnapshotListener((queryDocumentSnapshots, FirebaseFirestoreException) -> {
                    int free = queryDocumentSnapshots.size();
                    for (QueryDocumentSnapshot query : queryDocumentSnapshots) {
                        if (query.getBoolean(isBusy))
                            free--;
                    }
                    mFreePlace.postValue(free);

                });
            }
        };
        return mFreePlace;
    }


    public ArrayList<RoleModel> getPlayers() {
        Log.d(TAG, "getPlayers: ");
        mGetPlayers = new MyObserver() {
            @Override
            public void result(Object obj) {
                super.result(obj);
                DocumentSnapshot result = (DocumentSnapshot) obj;
                result.getReference().collection(Roles).addSnapshotListener((queryDocumentSnapshots, e) -> {
                    mPlayers.clear();
                    for (DocumentSnapshot play : queryDocumentSnapshots.getDocuments()) {
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
