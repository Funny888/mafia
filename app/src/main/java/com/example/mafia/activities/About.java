package com.example.mafia.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mafia.BuildConfig;
import com.example.mafia.R;

public class About extends AppCompatActivity {
    private Toolbar mToolbar;
    private ActionBar mActionbar;
    private TextView mVersion;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        mToolbar = findViewById(R.id.toolbar);
        ((TextView) mToolbar.findViewById(R.id.txt_title)).setText(getString(R.string.about_game));
        setSupportActionBar(mToolbar);
        mActionbar = getSupportActionBar();
        mActionbar.setDisplayHomeAsUpEnabled(true);
        mActionbar.setDisplayShowHomeEnabled(true);
        mVersion = findViewById(R.id.txt_version);
        mVersion.setText(getString(R.string.version, BuildConfig.VERSION_NAME));
    }
}
