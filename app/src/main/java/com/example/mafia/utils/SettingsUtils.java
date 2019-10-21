package com.example.mafia.utils;

public class SettingsUtils {
    private static SettingsUtils mInstance;
    private static Long mBase;

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
            mBase = 0l;
        }
        return mBase;
    }
}
