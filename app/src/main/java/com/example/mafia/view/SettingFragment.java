package com.example.mafia.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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

    private static final String TAG = "SettingFragment";

    private Context mContext;
    private SettingViewModel settingViewModel;
    private SettingModel model;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = inflater.getContext();
        settingViewModel = ViewModelProviders.of(this).get(SettingViewModel.class);
        FragmentSettingBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false);

        model = new SettingModel();
        binding.setModel(model);

        String[] settings = inflater.getContext().getResources().getStringArray(R.array.settings);
        for (int i = 0; i < settings.length; i++) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.setting_part, null, false);
            ((TextView) v.findViewById(R.id.text_turn)).setText(settings[i]);
            Switch sw = v.findViewById(R.id.switch_setting);
            sw.setId(i);
            sw.setOnCheckedChangeListener(click);
            binding.linSetting.addView(v);
        }


        return binding.getRoot();
    }

    CompoundButton.OnCheckedChangeListener click = (buttonView, isChecked) -> {
        switch (buttonView.getId()) {
            case 0: {
                if (isChecked) {
                    Toast.makeText(mContext,"Музыка включена",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext,"Музыка выключена",Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case 1: {
                if (isChecked) {
                    Toast.makeText(mContext,"Используется только Wi-Fi",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext,"Используется любой способ выхода в интернет",Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    };
}