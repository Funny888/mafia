package com.example.mafia.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mafia.R;
import com.example.mafia.databinding.FragmentAuthBinding;
import com.example.mafia.utils.Event;
import com.example.mafia.viewmodels.AuthViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.snackbar.Snackbar;

public class AuthFragment extends Fragment {

    private static final String TAG = AuthFragment.class.getSimpleName();
    private static final int RC_SIGN_IN = 9001;

    private AuthViewModel authViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        authViewModel = ViewModelProviders.of(this).get(AuthViewModel.class);
        FragmentAuthBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_auth, container, false);

        binding.btnGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authViewModel.signIn();
            }
        });

        authViewModel.getClienLD().observe(this, new Observer<Event<GoogleSignInClient>>() {
            @Override
            public void onChanged(Event<GoogleSignInClient> googleSignInClientEvent) {
                if (!googleSignInClientEvent.isHasBeenHandled()) {
                    Intent signInIntent = googleSignInClientEvent.getContentIfNotHandled().getSignInIntent();
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                }
            }
        });

        authViewModel.getAuthResultLD().observe(this, new Observer<Event<Boolean>>() {
            @Override
            public void onChanged(Event<Boolean> booleanEvent) {
                if (!booleanEvent.isHasBeenHandled()) {
                    if (booleanEvent.getContentIfNotHandled()) {
                        authViewModel.navigateTo(R.id.navigation_learn);
                    } else {
                        Snackbar snackbar = Snackbar.make(binding.getRoot(), "Auth error", Snackbar.LENGTH_INDEFINITE);
                        snackbar.setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                authViewModel.signIn();
                            }
                        });
                        snackbar.show();
                    }
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        authViewModel.authResult(requestCode, resultCode, data);
    }

}
