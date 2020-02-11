package com.example.mafia.models;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mafia.databases.FireStoreDB;
import com.example.mafia.network.NetworkUtils;
import com.example.mafia.utils.FabricDialogs;

import java.util.ArrayList;
import java.util.List;


public class GameModel extends AndroidViewModel implements Observable {

    public static final String TAG = GameModel.class.getSimpleName();

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
    private NetworkUtils networkUtils = new NetworkUtils();


    public GameModel(@NonNull Application application){
        super(application);
        mContext = getApplication();
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

    public void setActor(String actor){
        mActor = actor;
        notifyPropertyChanged(BR.actor);
    }

    @Bindable
    public String getActor() {
        return mActor;
    }

    public void setIsShowRole(Boolean show){
        mIsShowRole = show;
        notifyPropertyChanged(BR.isShowRole);
    }

    @Bindable
    public Boolean getIsShowRole(){
        return mIsShowRole;
    }

    public void setShowCardRole(Boolean show){
        mIsShowCardRole = show;
        notifyPropertyChanged(BR.showCardRole);
    }

    @Bindable
    public Boolean getShowCardRole(){
        return mIsShowCardRole;
    }

    public void setIdImage(Drawable show){
        mIdImage = show;
        notifyPropertyChanged(BR.idImage);
    }

    @Bindable
    public Drawable getIdImage(){
        return mIdImage;
    }

    public void setIsShowPlayers(Boolean show){
        mIsShowPlayers = show;
        notifyPropertyChanged(BR.isShowPlayers);
    }

    @Bindable
    public Boolean getIsShowPlayers(){
        return mIsShowPlayers;
    }

    public void setBackImage(Boolean show){
        mBackImage = show;
        notifyPropertyChanged(BR.backImage);
    }

    @Bindable
    public Boolean getBackImage(){
        return mBackImage;
    }


    public GameModel getModel(){
        return this;
    }


    public void resetRoles(){
        mDatabase.resetRoleBusy();
    }

    public void getRoom(){
        mDatabase.getRoom();
    }

    public LiveData<RoleModel> getRole(){
        return mDatabase.getRole();
    }

    public LiveData<Integer> getFreePlace(){
        return mDatabase.getFreePlace();
    }

    public ArrayList<RoleModel> getPlayers(){
        return mDatabase.getPlayers();
    }

    public Fragment getDialog(int code){
        return fabricDialogs.getDialog(code);
    }

    public MutableLiveData<List<ChatModel>> getMessages(){
        return networkUtils.getMessages();
    }

    public void sendMessages(String message){
        networkUtils.sendMessage(getRole().getValue().getRoleName(),message);
    }

}
