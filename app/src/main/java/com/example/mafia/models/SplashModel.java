package com.example.mafia.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;



public class SplashModel extends BaseObservable {
   private int mLoadProgress;


    public void setLoad(int load){
        mLoadProgress = load;
        notifyPropertyChanged(BR.load);
    }

    @Bindable
    public int getLoad(){
        return mLoadProgress;
    }
}
