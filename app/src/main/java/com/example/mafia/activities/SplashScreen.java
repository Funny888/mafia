package com.example.mafia.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.mafia.R;
import com.example.mafia.models.SplashModel;
import com.example.mafia.databinding.SplashScreenBinding;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreenBinding binding = DataBindingUtil.setContentView(this, R.layout.splash_screen);

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
                startActivity(new Intent(SplashScreen.this, LearnScreen.class));
                overridePendingTransition(R.anim.splash_open,R.anim.splash_close);

            }
        }).start();
    }
}
