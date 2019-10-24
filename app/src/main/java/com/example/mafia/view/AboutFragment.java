package com.example.mafia.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mafia.BuildConfig;
import com.example.mafia.R;
import com.example.mafia.databinding.FragmentAboutBinding;
import com.example.mafia.viewmodel.AboutViewModel;

public class AboutFragment extends Fragment {

    private AboutViewModel aboutViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        aboutViewModel = ViewModelProviders.of(this).get(AboutViewModel.class);
        FragmentAboutBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false);

//        mToolbar = findViewById(R.id.toolbar);
//        ((TextView) mToolbar.findViewById(R.id.txt_title)).setText(getString(R.string.about_game));
//        setSupportActionBar(mToolbar);
//        mActionbar = getSupportActionBar();
//        mActionbar.setDisplayHomeAsUpEnabled(true);
//        mActionbar.setDisplayShowHomeEnabled(true);

        binding.txtVersion.setText(getString(R.string.version, BuildConfig.VERSION_NAME));

        return binding.getRoot();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            aboutViewModel.navigateTo(R.id.navigation_menu);
        }
        return super.onOptionsItemSelected(item);
    }


}
