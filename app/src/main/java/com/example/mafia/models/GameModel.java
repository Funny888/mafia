package com.example.mafia.models;

import android.app.Dialog;
import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.example.mafia.R;


public class GameModel extends BaseObservable {
    private Context mContext;
    private String mActor;
    private Boolean mIsShowRole;
    private Boolean mIsShowPlayers;
    private Dialog mDialog;

    public GameModel(Context context){mContext = context; myDialog();}


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

    public void showDialogLoader(boolean bool){
     if (bool){
         mDialog.show();
     } else {
         mDialog.cancel();
     }
    }

    private void myDialog(){
        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.startdialog);
        mDialog.create();
        mDialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
    }

}
