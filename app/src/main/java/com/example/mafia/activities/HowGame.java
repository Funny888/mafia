package com.example.mafia.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mafia.R;

public class HowGame extends AppCompatActivity {
    private Toolbar mToolbar;
    private ActionBar mActionbar;

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
        setContentView(R.layout.how_game);
        mToolbar = findViewById(R.id.toolbar);
        ((TextView) mToolbar.findViewById(R.id.txt_title)).setText(getString(R.string.how_to_game));
        setSupportActionBar(mToolbar);
        mActionbar = getSupportActionBar();
        mActionbar.setDisplayHomeAsUpEnabled(true);
        mActionbar.setDisplayShowHomeEnabled(true);
    }


}
