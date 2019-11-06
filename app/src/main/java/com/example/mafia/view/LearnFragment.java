package com.example.mafia.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mafia.R;
import com.example.mafia.adpters.PreviewPagerAdapter;
import com.example.mafia.databinding.FragmentLearnBinding;
import com.example.mafia.interfaces.ISend;
import com.example.mafia.viewmodels.LearnViewModel;
import com.google.android.material.button.MaterialButton;

public class LearnFragment extends Fragment {

    private LearnViewModel learnViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        learnViewModel = ViewModelProviders.of(this).get(LearnViewModel.class);
        FragmentLearnBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_learn, container, false);


        PreviewPagerAdapter adapter = new PreviewPagerAdapter(inflater.getContext());

        ViewPager2 viewPager = binding.viewpager;
        viewPager.setAdapter(adapter);

        adapter.setClick((click) -> click.setOnClickListener((c) -> learnViewModel.navigateTo(R.id.navigation_menu)));

        return binding.getRoot();
    }

}
