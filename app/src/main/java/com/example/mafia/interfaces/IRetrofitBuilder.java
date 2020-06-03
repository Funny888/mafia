package com.example.mafia.interfaces;

import com.example.mafia.Modules.RetrofitBuilderModule;

import dagger.Component;

@Component(modules = {RetrofitBuilderModule.class})
public interface IRetrofitBuilder {
    IGetEvents getIGetEvents();
}
