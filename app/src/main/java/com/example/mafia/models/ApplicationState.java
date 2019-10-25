package com.example.mafia.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.example.mafia.R;
import com.example.mafia.utils.Event;
import com.google.firebase.auth.FirebaseAuth;

public class ApplicationState {

    private static ApplicationState sInstance;
    private Context mContext;
    private SharedPreferences prefs = null;

    private ApplicationState(Context context) {
        mContext = context;
    }

    public static ApplicationState getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ApplicationState(context);
        }
        return sInstance;
    }


    private MutableLiveData<Event<Integer>> navFragmentLD = new MutableLiveData<>();

    public void navigateTo(int fragmentID) {
        navFragmentLD.setValue(new Event<>(fragmentID));
    }

    public MutableLiveData<Event<Integer>> getNavFragmentLD() {
        return navFragmentLD;
    }


    private SplashModel splashModel;

    public void startInitialLoading() {
        splashModel = new SplashModel();

        new AsyncTask<Integer, Integer, Integer>() {
            @Override
            protected Integer doInBackground(Integer... integers) {
                int i = 0;
                while (i < 100) {
                    try {
                        Thread.sleep(100);
                        splashModel.setLoad(i);
                        i++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                if (mAuth.getCurrentUser() != null) {
                    prefs = mContext.getSharedPreferences("com.example.mafia", Context.MODE_PRIVATE);
                    if (prefs.getBoolean("firstrun", true)) {
                        prefs.edit().putBoolean("firstrun", false).commit();
                        navFragmentLD.setValue(new Event<>(R.id.navigation_learn));
                    } else {
                        navFragmentLD.setValue(new Event<>(R.id.navigation_menu));
                    }
                } else {
                    navFragmentLD.setValue(new Event<>(R.id.navigation_auth));
                }

            }
        }.execute();

    }

    public SplashModel getSplashModel() {
        return splashModel;
    }

}
