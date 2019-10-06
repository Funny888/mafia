package com.example.mafia.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mafia.R;

public class Result extends AppCompatActivity {

    private Toolbar mToolbar;
    private ActionBar mActionbar;
    private GridView mGridResult;
    String[] test = {"mafia","2","1:00","mafia","3","2:00"};

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        mToolbar = findViewById(R.id.toolbar);
        ((TextView) mToolbar.findViewById(R.id.txt_title)).setText(getString(R.string.result));
        setSupportActionBar(mToolbar);
        mActionbar = getSupportActionBar();
        mActionbar.setDisplayHomeAsUpEnabled(true);
        mActionbar.setDisplayShowHomeEnabled(true);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,test);
        mGridResult = findViewById(R.id.grid_result);
        mGridResult.setAdapter(arrayAdapter);
    }
}
