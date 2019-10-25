package com.example.mafia.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mafia.R;
import com.example.mafia.databinding.FragmentSplashBinding;
import com.example.mafia.viewmodels.SplashViewModel;

public class SplashFragment extends Fragment {

    private SplashViewModel splashViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        splashViewModel = ViewModelProviders.of(this).get(SplashViewModel.class);
        FragmentSplashBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false);
        binding.setModel(splashViewModel);

        splashViewModel.startInitialLoading();

        return binding.getRoot();
    }

}