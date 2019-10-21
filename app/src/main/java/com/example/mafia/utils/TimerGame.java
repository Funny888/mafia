package com.example.mafia.utils;


import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Chronometer;

import com.example.mafia.R;
import com.example.mafia.interfaces.OnFinished;

public class TimerGame {

    public static final String TAG = TimerGame.class.getSimpleName();

    private Context mContext;
    private Chronometer mChronometer;
    private OnFinished mFinish;
    //private final Long mTimeRound = SystemClock.elapsedRealtime() + 600000;
    private Long mTimeRound = SystemClock.elapsedRealtime() + 25000;

    public TimerGame(Chronometer chronometer,Context context){
        mContext = context;
       chronometer.setOnChronometerTickListener(chron -> {
            if (mTimeRound - SystemClock.elapsedRealtime() <= 20000){
                chron.setTextColor(mContext.getResources().getColor(R.color.colorAccent,mContext.getTheme()));
            }
            if (mTimeRound - SystemClock.elapsedRealtime() <= 0L){
                mFinish.isFinish(true);
                stop();
//                reset();
//                start();

            }
       });
        chronometer.setBase(mTimeRound);
        mChronometer = chronometer;
    }



    public void start(){
        mChronometer.setBase(SettingsUtils.getInstance().getTimeBase()!=0L?SettingsUtils.getInstance().getTimeBase():mTimeRound);
        mChronometer.start();
    }

    public void stop(){
        mChronometer.stop();
    }

    public void reset(){
        mChronometer.setBase(mTimeRound);
    }

    public Chronometer getmChronometer(){
        return mChronometer;
    }

    public void finalCountDown(OnFinished finished){
        mFinish = finished;
    }

}
