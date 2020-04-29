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
import com.example.mafia.adapters.ShowRulesAdapter;

public class Dialog_Showrules extends Fragment {
    private static final String TAG = Dialog_Showrules.class.getSimpleName();
    private ViewPager2 mPager;
    private ShowRulesAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learn,container,false);
        mPager = view.findViewById(R.id.viewpager);
        mAdapter = new ShowRulesAdapter(view.getContext());
        mAdapter.setListener((button) -> actionClick(button));
        mPager.setAdapter(mAdapter);
        mPager.setUserInputEnabled(false);
        return view;
    }

    private void actionClick(View button) {
        switch (button.getId()){
            case R.id.btn_fine: {
                button.setOnClickListener((c) -> {
                    if (mAdapter.getItemCount() != mPager.getCurrentItem() + 1) {
                        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                    } else {
                        mPager.setVisibility(View.GONE);
                    }
                });
                break;
            }
            case R.id.btn_skip: {
                button.setOnClickListener((c) -> mPager.setVisibility(View.GONE));
                break;
            }
        }
    }
}
