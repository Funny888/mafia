package com.example.mafia.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mafia.R;
import com.example.mafia.databinding.FragmentHowBinding;
import com.example.mafia.viewmodels.HowViewModel;

public class HowFragment extends Fragment {

    private HowViewModel howViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        howViewModel = ViewModelProviders.of(this).get(HowViewModel.class);
        FragmentHowBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_how, container, false);


        return binding.getRoot();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            howViewModel.navigateTo(R.id.navigation_menu);
        }
        return super.onOptionsItemSelected(item);
    }

}
