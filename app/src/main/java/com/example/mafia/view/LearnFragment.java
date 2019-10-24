package com.example.mafia.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mafia.R;
import com.example.mafia.databinding.FragmentLearnBinding;
import com.example.mafia.viewmodel.LearnViewModel;

public class LearnFragment extends Fragment {

    private LearnViewModel learnViewModel;

    ShowText showText;
    TextView mMotivate;
    String[] array;
    Button mBtn_ok;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        learnViewModel = ViewModelProviders.of(this).get(LearnViewModel.class);
        FragmentLearnBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_learn, container, false);

        mBtn_ok = binding.btnOk;
        mMotivate = binding.motivate;

        mBtn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View c) {
                learnViewModel.navigateTo(R.id.navigation_menu);
            }
        });
        array = getResources().getStringArray(R.array.naputstvie);
        showText = new ShowText();
        showText.execute(array.length);


        return binding.getRoot();
    }


    class ShowText extends AsyncTask<Integer, Integer, Void> {
        @Override
        protected void onProgressUpdate(Integer... values) {
            mMotivate.append(array[values[0]]);
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            int k = integers[0];
            int i = 0;
            while (i < k) {
                try {
                    publishProgress(i);
                    Thread.sleep(3000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mBtn_ok.setVisibility(View.VISIBLE);
        }
    }

}
