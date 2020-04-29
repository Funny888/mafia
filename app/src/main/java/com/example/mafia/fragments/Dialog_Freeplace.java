package com.example.mafia.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.mafia.R;
import com.example.mafia.databases.FireStoreDB;

public class Dialog_Freeplace extends Fragment {


    private FireStoreDB mFirestoreDB;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.freeplacedialog,null,false);
        mFirestoreDB = FireStoreDB.getInstance(view.getContext());
        LiveData<Integer> freePlace = mFirestoreDB.getFreePlace(mFirestoreDB.getRoom());
        freePlace.observe(this,(integer -> {
            if (integer != 0) {
                ((TextView) view.findViewById(R.id.free_count)).setText(String.format(getString(R.string.count_free_place), integer));
            } else {

            }
        }) );

        return view;
    }
}
