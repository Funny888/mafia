package com.example.mafia.utils;


import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Chronometer;

import com.example.mafia.R;
import com.example.mafia.interfaces.OnFinished;

import static com.example.mafia.utils.Logger.PTAG;

public class TimerGame {

    private static final String TAG = TimerGame.class.getSimpleName();

    private Context mContext;
    private Chronometer mChronometer;
    private OnFinished mFinish;

    public static final int CODE_WAIT = 100;
    public static final int CODE_ROUND = 101;
    public static final int CODE_VOTE = 102;
    public static final int CODE_FINISH = 103;
    public static final Long TIME_ROUND = 6000L;
    public static final Long TIME_WAITING = 12000L;
    public static final Long TIME_VOTING = 3000L;
    private Long mTime = SystemClock.elapsedRealtime() + TIME_WAITING;


    public TimerGame(Chronometer chronometer, Context context) {
        mContext = context;
       chronometer.setOnChronometerTickListener(chron -> {
            if (mTime - SystemClock.elapsedRealtime() <= 20000) {
                chron.setTextColor(mContext.getResources().getColor(R.color.colorAccent,mContext.getTheme()));
            } else {
                chron.setTextColor(mContext.getResources().getColor(android.R.color.black,mContext.getTheme()));
            }
            if (mTime - SystemClock.elapsedRealtime() <= 0) {
                stop();
                rounds(SettingsUtils.getInstance().getRounds(), SettingsUtils.getInstance().isVote());
            }
       });
        chronometer.setBase(mTime);
        mChronometer = chronometer;
    }

    private void rounds(Integer rounds, boolean vote) {
        Log.d(PTAG, TAG +"@rounds code: " + rounds + " vote: " + vote);
        switch (rounds) {
            case 5: {
                SettingsUtils.getInstance().setRounds(rounds - 1);
                mFinish.isFinish(true, CODE_WAIT);
                break;
            }
            case 0: {
                mFinish.isFinish(true, CODE_FINISH);
                break;
            }
            default: {
                if (!vote) {
                    SettingsUtils.getInstance().setVote(true);
                    mFinish.isFinish(true, CODE_ROUND);
                } else {
                    SettingsUtils.getInstance().setVote(false);
                    mFinish.isFinish(false, CODE_VOTE);
                }
                break;
            }
        }
    }


    public void start(Long time){
        mTime = SystemClock.elapsedRealtime() + time;
        mChronometer.setBase(mTime);
        mChronometer.start();
    }

    public void stop(){
        mChronometer.stop();
    }

    public void resume() {
        Long safeTime = SettingsUtils.getInstance().getTimeBase() - SystemClock.elapsedRealtime();
        Log.i(PTAG,TAG + "@resume: " + safeTime/1000);
        if (safeTime > 0L ) {
            mChronometer.setBase(safeTime + SystemClock.elapsedRealtime());
            mChronometer.start();
            Log.i(TAG, "@resume: if " + safeTime);
        } else {
            Log.i(TAG, "@resume: else");
            SettingsUtils.getInstance().setTimeBase(0L);
            mChronometer.setBase(SystemClock.elapsedRealtime());

        }
    }

    public void reset(){
        mChronometer.setBase(mTime);
    }

    public Chronometer getmChronometer(){
        return mChronometer;
    }

    public void finalCountDown(OnFinished finished){
        mFinish = finished;
    }

}
