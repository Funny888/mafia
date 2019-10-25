package com.example.mafia.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.mafia.models.ApplicationState;
import com.example.mafia.utils.Event;

public class MainViewModel extends CommonViewModel {

    private ApplicationState mAppState;

    public MainViewModel(@NonNull Application application) {
        super(application);

        mAppState = ApplicationState.getInstance(application);
    }

    public MutableLiveData<Event<Integer>> getNavFragmentLD() {
        return mAppState.getNavFragmentLD();
    }
}
