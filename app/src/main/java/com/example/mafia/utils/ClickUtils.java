package com.example.mafia.utils;

import android.content.Context;

import com.example.mafia.databases.RepositoryDB;

import java.util.TimerTask;

public class ClickUtils {
    private int mTab;
    private Context mContext;
    private TimerTask mTamerTask;
    private Runnable myRunnable;
    private Thread myThread;
    private boolean mIsStop = false;

    public ClickUtils(Context context) {
        mContext = context;
        mTamerTask = new TimerTask() {
            @Override
            public void run() {
                RepositoryDB.getInstanse(mContext).deleteStatistic();
                mIsStop = false;
            }
        };

        myRunnable = () -> {
            int i = 1;
            while (i < 6) {
                try {
                    Thread.sleep(1000);
                    if (mTab >= 5) {
                        mTamerTask.run();
                        return;
                    } else if (i == 5) {
                        mTab = 0;
                        mIsStop = false;
                    }
                    i++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public void incrementTab() {
        mTab++;
        if (!mIsStop) {
            mIsStop = true;
            myThread = new Thread(myRunnable);
            myThread.start();
        }
    }

}
