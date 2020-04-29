package com.example.mafia.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;


public class SplashModel extends BaseObservable {

    private int mLoadProgress;
    private int mSumTasks;

    private String mTask;

    public void setLoad(int load) {
        mLoadProgress = load;
        notifyPropertyChanged(BR.load);
    }

    @Bindable
    public int getLoad() {
        return mLoadProgress;
    }

    public void setTask(String task){
        mTask = task;
        notifyPropertyChanged(BR.task);
    }

    @Bindable
    public String getTask() {
       return mTask;
    }

    public void setSumTasks(int sum) {
        mSumTasks = sum;
        notifyPropertyChanged(BR.sumTasks);
    }

    @Bindable
    public int getSumTasks() {
        return mSumTasks;
    }
}