package com.example.mafia.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mafia.R;
import com.example.mafia.models.LearnAdapter;

import java.util.Arrays;

public class LearnScreen extends AppCompatActivity {
    private ViewPager2 mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn_screen);

        mPager = findViewById(R.id.learnPager);
        mPager.setAdapter(new LearnAdapter(Arrays.asList(getResources().getStringArray(R.array.naputstvie))));
    }

}
