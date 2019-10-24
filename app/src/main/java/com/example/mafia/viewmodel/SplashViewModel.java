package com.example.mafia.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.mafia.models.SplashModel;

public class SplashViewModel extends CommonViewModel {

    public SplashViewModel(@NonNull Application application) {
        super(application);
    }

    public void startInitialLoading() {
        getAppState().startInitialLoading();
    }

    public SplashModel getSplashModel() {
        return getAppState().getSplashModel();
    }
}
