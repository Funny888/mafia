package com.example.mafia.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.mafia.models.ApplicationState;

public class CommonViewModel extends AndroidViewModel {

    private ApplicationState mAppState;

    public CommonViewModel(@NonNull Application application) {
        super(application);

        mAppState = ApplicationState.getInstance(application);
    }

    public void navigateTo(int fragmentId) {
        mAppState.navigateTo(fragmentId);
    }

    public ApplicationState getAppState() {
        return mAppState;
    }

}