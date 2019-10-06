package com.example.mafia.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mafia.R;
import com.example.mafia.databinding.SettingBinding;
import com.example.mafia.models.SettingModel;

public class Setting extends AppCompatActivity {

    private LinearLayoutCompat setting;
    private LinearLayout linearLayout;
    private Toolbar mToolbar;
    private ActionBar mActionbar;
    private SettingModel model;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        SettingBinding binding = DataBindingUtil.setContentView(this,R.layout.setting);
        model = new SettingModel();
        binding.setModel(model);
        mToolbar = findViewById(R.id.toolbar);
        ((TextView) mToolbar.findViewById(R.id.txt_title)).setText(getString(R.string.settigs));
        setSupportActionBar(mToolbar);
        mActionbar = getSupportActionBar();
        mActionbar.setDisplayHomeAsUpEnabled(true);
        mActionbar.setDisplayShowHomeEnabled(true);
        linearLayout = findViewById(R.id.lin_setting);



        for (int i = 0;i<4;i++){
           View v =  LayoutInflater.from(this).inflate(R.layout.setting_part,null,false);
            ((TextView)v.findViewById(R.id.text_turn)).setText("test " + i);
            linearLayout.addView(v);
        }

    }
}
