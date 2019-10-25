package com.example.mafia.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.mafia.R;
import com.example.mafia.models.SplashModel;
import com.example.mafia.databinding.SplashScreenBinding;
import com.example.mafia.utils.SharPref;

public class SplashScreen extends AppCompatActivity {
    private SharedPreferences mPref;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreenBinding binding = DataBindingUtil.setContentView(this, R.layout.splash_screen);
        mPref = SharPref.getInstance(SplashScreen.this).getPreferance();
        final SplashModel model = new SplashModel();
        binding.setModel(model);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (i<100) {
                    try {
                        Thread.sleep(100);
                        model.setLoad(i);
                        i++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (!mPref.contains("firstRun")) {
                    SharedPreferences.Editor editor = mPref.edit();
                    editor.putBoolean("firstRun",true).apply();
                    startActivity(new Intent(SplashScreen.this, LearnScreen.class));
                } else {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                }
                overridePendingTransition(R.anim.splash_open,R.anim.splash_close);

            }
        }).start();
    }
}
