package com.example.mafia.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.mafia.R;
import com.example.mafia.utils.Event;
import com.example.mafia.utils.SplashCheckUtils;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.mafia.utils.Logger.PTAG;

public class ApplicationState {
    private static final String TAG = "ApplicationState";
    private static ApplicationState sInstance;
    private Context mContext;
    private SharedPreferences prefs = null;
    private FirebaseAuth mAuth;

    private ApplicationState(Context context) {
        mContext = context;
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInAnonymously();
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
    private SplashCheckUtils mSplashCheckUtils;

    public void startInitialLoading() {
        splashModel = new SplashModel();
        mSplashCheckUtils = new SplashCheckUtils(mContext);

        LoadResurses loadResurses = new LoadResurses();
        try {
            loadResurses.execute(mSplashCheckUtils.startGetResultFunctions());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public SplashModel getSplashModel() {
        return splashModel;
    }

    class LoadResurses extends AsyncTask<LinkedHashMap<String, Callable<Boolean>>, String, Boolean> {
        private ExecutorService mExecutor = Executors.newSingleThreadExecutor();

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            splashModel.setTask(values[0]);
        }

        @Override
        protected Boolean doInBackground(LinkedHashMap<String, Callable<Boolean>>... arrayLists) {
            int sizeTasks = arrayLists[0].size();
            splashModel.setSumTasks(sizeTasks);
            Iterator<String> iteratorKeys = arrayLists[0].keySet().iterator();
            Iterator<Callable<Boolean>> iteratorCallables = arrayLists[0].values().iterator();
            for (int i = 1; i < sizeTasks + 1; i++) {
                String key = iteratorKeys.next();
                publishProgress(key);
                try {
                    if (!mExecutor.submit(iteratorCallables.next()).get()) {
                        return false;
                    }
                    splashModel.setLoad(i);
                    Thread.sleep(2000);
                } catch (ExecutionException e) {
                    Log.d(PTAG,TAG + "@doInBackground: key task is " + key);
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mExecutor.shutdown();
            if (!aBoolean) {
                Toast.makeText(mContext, "Retry", Toast.LENGTH_LONG).show();
            } else {

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
        }
    }
}