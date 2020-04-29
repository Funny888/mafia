package com.example.mafia.databases;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.mafia.R;
import com.example.mafia.models.RoleModel;
import com.example.mafia.utils.MyObservable;
import com.example.mafia.utils.MyObserver;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private MyObserver mGetFreePlace;
    private Context mContext;
    private Boolean mFlagGetRoom;
    private String mRoom;

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference reference = firestore.collection(Rooms);

    private static FireStoreDB sInstance;

    private FireStoreDB(Context ctx) {
        mContext = ctx;
        mFlagGetRoom = false;
    }

    public static FireStoreDB getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new FireStoreDB(context);
        }
        return sInstance;
    }

    public void getRole2(MutableLiveData<RoleModel> role){
        role.observe((LifecycleOwner) this, roleModel -> {
            setRoom.setValue(roleModel.getId());
        });
        setRoom.attach(mGetPlayers);
        setRoom.attach(mGetFreePlace);
        setRoom.dettach(mGetPlayers);
        setRoom.dettach(mGetFreePlace);
    }

    // TODO This functional is for to comfortable develop the project and must be remove in releases
    public void resetRoleBusy() {
        reference.document("/Room1").get().addOnSuccessListener(documentSnapshot -> {
            documentSnapshot.getReference().collection(Roles).get().addOnSuccessListener((queryDocumentSnapshots) -> {
                for (DocumentSnapshot resultReset : queryDocumentSnapshots.getDocuments()) {
                    resultReset.getReference().update(isBusy, false);
                }
            });
        });

    }
    public String getRoom(){
        return mRoom;
    }

    public MutableLiveData<Integer> getFreePlace(String room) {
        mRoom = room;
        reference.document(room).get().addOnSuccessListener(documentSnapshot -> {
            documentSnapshot.getReference().collection(Roles).addSnapshotListener((queryDocumentSnapshots, FirebaseFirestoreException) -> {
                int free = queryDocumentSnapshots.size();
                for (QueryDocumentSnapshot query : queryDocumentSnapshots) {
                    if (query.getBoolean(isBusy))
                        free--;
                }
                mFreePlace.postValue(free);
            });
        });
        return mFreePlace;
    }
}
