package com.example.mafia.utils;

import com.example.mafia.interfaces.IMyObservable;
import com.example.mafia.interfaces.IMyObserver;
import com.example.mafia.interfaces.OnFinished;

import java.util.ArrayList;

public class MyObservable implements IMyObservable {
    private ArrayList<IMyObserver> mList = new ArrayList<>();
    private Object mValue;
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
        mValue = value;
        for (IMyObserver subj: mList) {
            subj.result(mValue);
        }
    }

    @Override
    public Object getValue() {
        return mValue;
    }
}
