package com.example.mafia.interfaces;

public interface IMyObservable {
    void attach(Object obj);

    void dettach(Object obj);

    void setValue(Object value);
}
