package com.example.mafia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.mafia.R;
import com.example.mafia.adpters.ShowRulesAdapter;

public class Dialog_Showrules extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learn,container,false);
        ViewPager2 pager = view.findViewById(R.id.viewpager);
        ShowRulesAdapter adapter = new ShowRulesAdapter();
        pager.setAdapter(adapter);
        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                adapter.getButtonFine().setOnClickListener((c)-> pager.setCurrentItem(position + 1));
            }
        });
        return view;
    }
}
