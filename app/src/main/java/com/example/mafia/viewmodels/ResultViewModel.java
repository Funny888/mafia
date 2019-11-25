package com.example.mafia.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.mafia.utils.ClickUtils;

public class ResultViewModel extends CommonViewModel {
    ClickUtils mClick;

    public ResultViewModel(@NonNull Application application) {
        super(application);
        mClick = new ClickUtils(application);
    }

    public void resetData(){
        mClick.incrementTab();
    }
}
