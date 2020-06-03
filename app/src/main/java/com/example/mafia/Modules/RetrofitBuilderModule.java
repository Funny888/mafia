package com.example.mafia.Modules;

import com.example.mafia.BuildConfig;
import com.example.mafia.interfaces.IGetEvents;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = OkHttpBuilderModule.class)
public class RetrofitBuilderModule {

    @Provides
    public IGetEvents events(Retrofit retrofit) {
        return retrofit.create(IGetEvents.class);
    }

    @Provides
    public Retrofit retrofit(OkHttpClient ok) {
        String BASE_URL = "https://us-central1-mafia-7b2ee.cloudfunctions.net";
        return new Retrofit.Builder().
                baseUrl(BASE_URL).
                client(ok).
                addConverterFactory(GsonConverterFactory.create()).
                build();
    }
}
