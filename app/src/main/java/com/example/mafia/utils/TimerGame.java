package com.example.mafia.utils;


import android.content.Context;
import android.os.SystemClock;
import android.widget.Chronometer;

import androidx.lifecycle.MutableLiveData;

import com.example.mafia.R;
import com.example.mafia.interfaces.OnFinished;
import com.example.mafia.models.GameModel;

public class TimerGame {


    private static TimerGame sInstance;
    private Context mContext;
    private Chronometer mChronometer;
    private OnFinished mFinish;
    //private final Long mTimeRound = SystemClock.elapsedRealtime() + 600000;
    private final Long mTimeRound = SystemClock.elapsedRealtime() + 25000;

    private TimerGame(Chronometer chronometer,Context context){
        mContext = context;
       chronometer.setOnChronometerTickListener(chron -> {
            if (mTimeRound - SystemClock.elapsedRealtime() <= 20000){
                chron.setTextColor(mContext.getResources().getColor(R.color.colorAccent,mContext.getTheme()));
            }
            if (mTimeRound - SystemClock.elapsedRealtime() <= 0){
                mFinish.isFinish(true);
                stop();
            }
       });
       chronometer.setBase(mTimeRound);
        mChronometer = chronometer;
    }

    public static TimerGame getInstance(Chronometer chronometer,Context context){
        if (sInstance == null){
            sInstance = new TimerGame(chronometer,context);
        }
       return sInstance;
    }

    public void start(){
        mChronometer.setBase(mTimeRound);
        mChronometer.start();
    }

    public void stop(){
        mChronometer.stop();
    }

    public void reset(){
        mChronometer.setBase(mTimeRound);
    }

    public void finalCountDown(OnFinished finished){
        mFinish = finished;
    }

}
