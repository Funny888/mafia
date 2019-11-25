package com.example.mafia.utils;

import android.content.Context;
import android.util.Log;

import com.example.mafia.databases.RepositoryDB;

import java.util.Timer;
import java.util.TimerTask;

public class ClickUtils {
    private int mTab;
    private Context mContext;
    private Timer mTimer;
    private TimerTask mTamerTask;
    private Runnable myRunnable;
    private Thread myThread;

    public ClickUtils(Context context) {
        mContext = context;
        mTamerTask = new TimerTask() {
            @Override
            public void run() {
                RepositoryDB.getInstanse(mContext).deleteStatistic();
            }
        };

        myRunnable = new Runnable() {
            @Override
            public void run() {
                int i = 1;
                while (i < 6) {
                    try {
                        Thread.sleep(1000);
                        if (mTab == 5) {
                            Log.d("ClickUtils", "tabs: " + mTab);
                            mTamerTask.run();
                        } else {
                            if (i == 5) {
                                mTab = 0;
                            }
                            i++;
                            Log.d("ClickUtils", "i: " + i);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    public void incrementTab() {
        mTab++;
        fiveTab();
    }

    private boolean fiveTab() {
        if (myThread == null){myThread = new Thread(myRunnable);}
        Log.d("fiveTab", "isAlive: " + myThread.isAlive());
        if (myThread.isAlive()) {
            Log.d("isAlive", "fiveTab: " + true);
            return true;
        } else {
            Log.d("isAlive", "fiveTab: " + false);
            myThread.start();
            return false;
        }
    }


}
