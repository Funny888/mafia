package com.example.mafia.databases;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.mafia.R;
import com.example.mafia.utils.Event;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class FBAuth {

    private static final String TAG = FBAuth.class.getSimpleName();
    private static final int RC_SIGN_IN = 9001;

    private Context mContext;
    private FirebaseAuth mAuth;

    private MutableLiveData<Event<GoogleSignInClient>> clientLD = new MutableLiveData<>();
    private MutableLiveData<Event<Boolean>> authResultLD = new MutableLiveData<>();

    public FBAuth(Context context) {
        mContext = context;
    }

    public void signIn() {
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(mContext.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(mContext, gso);
        clientLD.setValue(new Event<>(mGoogleSignInClient));
    }

    public MutableLiveData<Event<GoogleSignInClient>> getClientLD() {
        return clientLD;
    }

    public void authResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(mContext, "Exception oAR: " + task.getException().toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                authResultLD.setValue(new Event<>(task.isSuccessful()));
            }
        });
    }

    public MutableLiveData<Event<Boolean>> getAuthResultLD() {
        return authResultLD;
    }
}
