package com.example.mafia.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharPref {
    private Context mContext;
    private static SharPref mInstance;
    private SharedPreferences mPref;

    private SharPref(Context ctx){
        mContext = ctx;
        mPref = mContext.getSharedPreferences("setting",Context.MODE_PRIVATE);
    }

    public static SharPref getInstance(Context context){
        if (mInstance == null){
            mInstance = new SharPref(context);
            return mInstance;
        }
        return mInstance;
    }

    public SharedPreferences getPreferance(){
        return mPref;
    }
}
