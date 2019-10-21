package com.example.mafia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mafia.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mStartGame;
    private Button mResult;
    private Button mSetting;
    private Button mHowGame;
    private Button mAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStartGame = findViewById(R.id.btn_start);
        mStartGame.setOnClickListener(this);
        mResult = findViewById(R.id.btn_result);
        mResult.setOnClickListener(this);
        mSetting = findViewById(R.id.btn_setting);
        mSetting.setOnClickListener(this);
        mHowGame = findViewById(R.id.btn_how_game);
        mHowGame.setOnClickListener(this);
        mAbout = findViewById(R.id.btn_about);
        mAbout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start:{
                startActivity(new Intent(this, Game.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            }
            case R.id.btn_result:{
                startActivity(new Intent(this, Result.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            }
            case R.id.btn_setting:{
                startActivity(new Intent(this, Setting.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            }
            case R.id.btn_how_game:{
                startActivity(new Intent(this, HowGame.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            }
            case R.id.btn_about:{
                startActivity(new Intent(this, About.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            }
        }
    }
}
