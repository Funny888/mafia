package com.example.mafia.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mafia.R;
import com.example.mafia.databinding.FragmentResultBinding;
import com.example.mafia.viewmodel.ResultViewModel;

public class ResultFragment extends Fragment {

    private ResultViewModel resultViewModel;

    String[] test = {"mafia", "2", "1:00", "mafia", "3", "2:00"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        resultViewModel = ViewModelProviders.of(this).get(ResultViewModel.class);
        FragmentResultBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_result, container, false);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_list_item_1, test);

        binding.gridResult.setAdapter(arrayAdapter);

        return binding.getRoot();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            resultViewModel.navigateTo(R.id.navigation_result);
        }
        return super.onOptionsItemSelected(item);
    }

}
