package com.example.mafia.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mafia.R;
import com.example.mafia.activities.Game;
import com.example.mafia.databinding.FragmentMenuBinding;
import com.example.mafia.viewmodel.MenuViewModel;

public class MenuFragment extends Fragment implements View.OnClickListener {

    private MenuViewModel menuViewModel;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        menuViewModel = ViewModelProviders.of(this).get(MenuViewModel.class);
        FragmentMenuBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false);

        setHasOptionsMenu(false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        binding.btnStart.setOnClickListener(this);
        binding.btnResult.setOnClickListener(this);
        binding.btnSetting.setOnClickListener(this);
        binding.btnHowGame.setOnClickListener(this);
        binding.btnAbout.setOnClickListener(this);

        mContext = binding.getRoot().getContext();
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start: {
                //menuViewModel.navigateTo(R.id.navigation_game);
                startActivity(new Intent(mContext, Game.class));
                break;
            }
            case R.id.btn_result: {
                menuViewModel.navigateTo(R.id.navigation_result);
                break;
            }
            case R.id.btn_setting: {
                menuViewModel.navigateTo(R.id.navigation_setting);
                break;
            }
            case R.id.btn_how_game: {
                menuViewModel.navigateTo(R.id.navigation_how);
                break;
            }
            case R.id.btn_about: {
                menuViewModel.navigateTo(R.id.navigation_about);
                break;
            }
        }
    }
}
