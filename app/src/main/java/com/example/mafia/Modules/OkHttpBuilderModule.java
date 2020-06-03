package com.example.mafia.Modules;

import com.example.mafia.BuildConfig;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class OkHttpBuilderModule {

    @Provides
    public OkHttpClient okHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
    }

    @Provides
    public HttpLoggingInterceptor loggingInterceptor(){
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.level(BuildConfig.DEBUG ?
                HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return logger;
    }
}
