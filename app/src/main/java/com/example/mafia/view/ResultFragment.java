package com.example.mafia.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.example.mafia.R;
import com.example.mafia.databases.RepositoryDB;
import com.example.mafia.databinding.FragmentResultBinding;
import com.example.mafia.interfaces.StatisticDao;
import com.example.mafia.models.StatisticModel;
import com.example.mafia.viewmodels.ResultViewModel;

import java.util.List;


public class ResultFragment extends Fragment {

    private ResultViewModel resultViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        resultViewModel = ViewModelProviders.of(this).get(ResultViewModel.class);
        FragmentResultBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_result, container, false);

        RepositoryDB.getInstanse(this.getContext()).getList().observe(this,statisticModels -> {
          //  String [] result = new String[statisticModels.size() * 3];



        String[] result = {statisticModels.get(0).getRole(),String.valueOf(statisticModels.get(0).getGames()),String.valueOf(statisticModels.get(0).getTime())};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_list_item_1, result);


        binding.gridResult.setAdapter(arrayAdapter);
        });

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
