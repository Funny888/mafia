package com.example.mafia.utils;

import com.example.mafia.interfaces.IMyObservable;
import com.example.mafia.interfaces.IMyObserver;
import com.example.mafia.interfaces.OnFinished;

import java.util.ArrayList;

public class MyObservable implements IMyObservable {
    ArrayList<IMyObserver> mList = new ArrayList<>();

    @Override
    public void attach(Object obj) {
        mList.add((IMyObserver)obj);
    }

    @Override
    public void dettach(Object obj) {
        mList.remove(obj);
    }

    @Override
    public void setValue(Object value) {
        for (IMyObserver subj: mList) {
            subj.result(value);
        }
    }
}
