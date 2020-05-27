package com.example.mafia.utils;

public class SettingsUtils {
    private static SettingsUtils mInstance;
    private static Long mBase;
    private static Integer mRounds = 5;

    private SettingsUtils(){}

    public static SettingsUtils getInstance() {
        if (mInstance == null) {
            mInstance = new SettingsUtils();
        }
        return mInstance;
    }

    public void setTimeBase(Long base){
        mBase = base;
    }

    public Long getTimeBase(){
        if (mBase == null) {
            mBase = 0L;
        }
        return mBase;
    }

    public void setRounds(Integer rounds) {
        mRounds = rounds;
    }

    public Integer getRounds() {
        return mRounds;
    }
}
