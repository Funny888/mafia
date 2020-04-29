package com.example.mafia.view;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mafia.R;
import com.example.mafia.databinding.FragmentSplashBinding;
import com.example.mafia.viewmodels.SplashViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.security.Permission;

public class SplashFragment extends Fragment {
    private static final String TAG = "SplashFragment";
    private SplashViewModel splashViewModel;
    private Context mContext;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        splashViewModel = ViewModelProviders.of(this).get(SplashViewModel.class);
        mContext = inflater.getContext();
        FragmentSplashBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false);
        binding.setModel(splashViewModel);
        if(this.getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && this.getActivity().
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE},1);
        } else {
            splashViewModel.startInitialLoading();
        }

        return mView = binding.getRoot();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                splashViewModel.startInitialLoading();
            else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Snackbar.make(mView,"Access needed",Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(mView,"Access isn't granted",Snackbar.LENGTH_SHORT).show();
                    this.getActivity().finish();
                }
            }
        }
    }
}