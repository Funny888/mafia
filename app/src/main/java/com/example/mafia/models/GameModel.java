package com.example.mafia.models;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.example.mafia.databases.FireStoreDB;
import com.example.mafia.R;
import com.example.mafia.utils.FabricDialogs;

import java.util.ArrayList;


public class GameModel extends BaseObservable {
    private Context mContext;
    private String mActor;
    private Boolean mIsShowRole;
    private Boolean mIsShowPlayers;
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

    public void setIsShowPlayers(Boolean show){
        mIsShowPlayers = show;
        notifyPropertyChanged(BR.isShowPlayers);
    }

    @Bindable
    public Boolean getIsShowPlayers(){
        return mIsShowPlayers;
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
