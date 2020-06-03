package com.example.mafia.models;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mafia.animations.AnimationUtilsHelper;
import com.example.mafia.databases.FireStoreDB;
import com.example.mafia.network.NetworkUtils;
import com.example.mafia.utils.FabricDialogs;

import java.util.ArrayList;
import java.util.List;

import static com.example.mafia.utils.Logger.PTAG;


public class GameModel extends AndroidViewModel implements Observable {

    public static final String TAG = "GameModel";

    private transient PropertyChangeRegistry mCallbacks;

    private Context mContext;
    private String mActor;
    private Boolean mIsShowRole;
    private Boolean mIsShowPlayers;
    private Boolean mIsShowCardRole;
    private Boolean mBackImage = false;
    private Drawable mIdImage;
    private FireStoreDB mDatabase;
    private FabricDialogs fabricDialogs = new FabricDialogs();
    private NetworkUtils networkUtils;
    private String mRoom;
    private AnimationUtilsHelper mAnimation;
    private MutableLiveData<ArrayList<RoleModel>> mTest;
    private MutableLiveData<GamePlace> mLiveDataRole;


    public GameModel(@NonNull Application application) {
        super(application);
        Log.i(PTAG,TAG + "@GameModel: constructor");
        mContext = getApplication();
        networkUtils = new NetworkUtils(mContext);
        mDatabase = FireStoreDB.getInstance(mContext);
    }


    public void addOnPropertyChangedCallback(@NonNull OnPropertyChangedCallback callback) {
        synchronized (this) {
            if (mCallbacks == null) {
                mCallbacks = new PropertyChangeRegistry();
            }
        }
        mCallbacks.add(callback);
    }

    public void removeOnPropertyChangedCallback(@NonNull OnPropertyChangedCallback callback) {
        synchronized (this) {
            if (mCallbacks == null) {
                return;
            }
        }
        mCallbacks.remove(callback);
    }

    public void notifyPropertyChanged(int fieldId) {
        synchronized (this) {
            if (mCallbacks == null) {
                return;
            }
        }
        mCallbacks.notifyCallbacks(this, fieldId, null);
    }

    public void setActor(String actor) {
        Log.i(PTAG,TAG + "@setActor: actor is " + actor);
        mActor = actor;
        notifyPropertyChanged(BR.actor);
    }

    @Bindable
    public String getActor() {
        return mActor;
    }

    public void setIsShowRole(Boolean show) {
        Log.i(PTAG,TAG + "@setIsShowRole: show role is " + show);
        mIsShowRole = show;
        notifyPropertyChanged(BR.isShowRole);
    }

    @Bindable
    public Boolean getIsShowRole() {
        return mIsShowRole;
    }

    public void setShowCardRole(Boolean show) {
        Log.i(PTAG,TAG + "@setShowCardRole: show card role is " + show);
        mIsShowCardRole = show;
        notifyPropertyChanged(BR.showCardRole);
    }

    @Bindable
    public Boolean getShowCardRole() {
        return mIsShowCardRole;
    }

    public void setIdImage(Drawable show) {
        mIdImage = show;
        notifyPropertyChanged(BR.idImage);
    }

    @Bindable
    public Drawable getIdImage() {
        return mIdImage;
    }

    public void setIsShowPlayers(Boolean show) {
        Log.i(PTAG,TAG + "@setIsShowPlayers: show players is " + show);
        mIsShowPlayers = show;
        notifyPropertyChanged(BR.isShowPlayers);
    }

    @Bindable
    public Boolean getIsShowPlayers() {
        return mIsShowPlayers;
    }

    public void setBackImage(Boolean show) {
        Log.i(PTAG,TAG + "@setBackImage: show back image is " + show);
        mBackImage = show;
        notifyPropertyChanged(BR.backImage);
    }

    @Bindable
    public Boolean getBackImage() {
        return mBackImage;
    }


    public GameModel getModel() {
        return this;
    }


    public void resetRoles() {
        mDatabase.resetRoleBusy();
    }

    public LiveData<GamePlace> getRole() {
//        if (mLiveDataRole == null) {
            Log.i(PTAG, TAG + "@getRole: ");
            mLiveDataRole = new MutableLiveData<>();
            mLiveDataRole = networkUtils.getRole();
//        }
        return mLiveDataRole;
    }

    public MutableLiveData<Integer> getFreePlace(String room) {
        Log.i(PTAG,TAG + "@getFreePlace: room is " + room);
        mRoom = room;
        return mDatabase.getFreePlace(room);
    }

    public MutableLiveData<ArrayList<RoleModel>> getPlayers(String room) {
        if (mTest == null) {
            Log.i(PTAG, TAG + "@getPlayers: room is " + room);
            mTest = new MutableLiveData<>();
            mTest = networkUtils.getAllPlayers(room);
        }
        return mTest;
    }

    public Fragment getDialog(int code) {
        Log.i(PTAG,TAG + "@getDialog: code is " + code);
        return fabricDialogs.getDialog(code);
    }

    public MutableLiveData<List<ChatModel>> getMessages() {
        return networkUtils.getMessages();
    }

    public void sendMessages(String message) {
        networkUtils.sendMessage(getRole().getValue().getRole().getRoleName(), message);
    }

    public MutableLiveData<Boolean> startGame() {
        return networkUtils.startGame(mRoom);
    }

    public MutableLiveData<Integer> stopGame(Integer flag) {
        return networkUtils.stopGame(mRoom, flag);
    }

    public MutableLiveData<RoleModel> showIsDead() {
        return networkUtils.howIsDead(mRoom);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        fabricDialogs = null;
        mContext = null;
        networkUtils = null;
        mDatabase = null;
    }
}
