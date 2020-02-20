package com.example.mafia.utils;

import com.example.mafia.BuildConfig;
import com.example.mafia.interfaces.IGetEvents;
import com.example.mafia.interfaces.ISendMessages;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetofitBuilder {
    private String BASE_URL = "https://us-central1-mafia-7b2ee.cloudfunctions.net";
    private Retrofit mRetrofit;
    private OkHttpClient mOk;

    public RetofitBuilder() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        mOk = new OkHttpClient.Builder().
                addInterceptor(loggingInterceptor).
                build();
    }

    public IGetEvents getMessages() {
        mRetrofit = new Retrofit.Builder().
                baseUrl(BASE_URL).
                client(mOk).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        return mRetrofit.create(IGetEvents.class);
    }

    public ISendMessages SendMessage() {
        mRetrofit = new Retrofit.Builder().
                baseUrl(BASE_URL).
                client(mOk).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        return mRetrofit.create(ISendMessages.class);
    }

    public IGetEvents getRole(){
        mRetrofit = new Retrofit.Builder().
                baseUrl(BASE_URL).
                client(mOk).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        return  mRetrofit.create(IGetEvents.class);
    }
}
