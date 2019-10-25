package com.example.mafia.viewmodels;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.mafia.databases.FBAuth;
import com.example.mafia.utils.Event;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class AuthViewModel extends CommonViewModel {

    private FBAuth fbAuth;

    public AuthViewModel(@NonNull Application application) {
        super(application);

        fbAuth = new FBAuth(application.getApplicationContext());
    }

    public void signIn() {
        fbAuth.signIn();
    }

    public void authResult(int requestCode, int resultCode, Intent data) {
        fbAuth.authResult(requestCode, resultCode, data);
    }

    public MutableLiveData<Event<GoogleSignInClient>> getClienLD() {
        return fbAuth.getClientLD();
    }

    public MutableLiveData<Event<Boolean>> getAuthResultLD() {
        return fbAuth.getAuthResultLD();
    }
}
