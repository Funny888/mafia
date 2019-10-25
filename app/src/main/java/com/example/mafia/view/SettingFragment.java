package com.example.mafia.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mafia.R;
import com.example.mafia.databinding.FragmentSettingBinding;
import com.example.mafia.models.SettingModel;
import com.example.mafia.viewmodels.SettingViewModel;

public class SettingFragment extends Fragment {

    private SettingViewModel settingViewModel;

    private SettingModel model;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        settingViewModel = ViewModelProviders.of(this).get(SettingViewModel.class);
        FragmentSettingBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false);

        model = new SettingModel();
        binding.setModel(model);

        for (int i = 0;i<4;i++){
            View v =  LayoutInflater.from(inflater.getContext()).inflate(R.layout.setting_part,null,false);
            ((TextView)v.findViewById(R.id.text_turn)).setText("test " + i);
            binding.linSetting.addView(v);
        }

        return binding.getRoot();
    }

}
