package com.example.mafia.models;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.example.mafia.databases.FireStoreDB;
import com.example.mafia.utils.FabricDialogs;

import java.util.ArrayList;


public class GameModel extends BaseObservable {
    private Context mContext;
    private String mActor;
    private Boolean mIsShowRole;
    private Boolean mIsShowPlayers;
    private Boolean mIsShowCardRole;
    private Boolean mBackImage = false;
    private Drawable mIdImage;
    private FireStoreDB bd;
    private FabricDialogs fabricDialogs = new FabricDialogs();


    public GameModel(Context context){mContext = context;
        bd = FireStoreDB.getInstance(mContext);
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
        bd.resetRoleBusy();
    }

    public void getRoom(){
        bd.getRoom();
    }

    public LiveData<RoleModel> getRole(){
        return bd.getRole();
    }

    public LiveData<Integer> getFreePlace(){
        return bd.getFreePlace();
    }

    public ArrayList<RoleModel> getPlayers(){
        return bd.getPlayers();
    }

    public Fragment getDialog(int code){
        return fabricDialogs.getDialog(code);
    }
}
