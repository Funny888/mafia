package com.example.mafia.databases;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.mafia.interfaces.StatisticDao;
import com.example.mafia.models.StatisticModel;

import java.util.List;

public class RepositoryDB {
    private static RepositoryDB mInstanse;
    private Context mContext;
    private StatisticDB mDB;

    private RepositoryDB(Context context){
        mContext = context;
        mDB = Room.databaseBuilder(mContext,StatisticDB.class,"Statistic").build();
    }

    public static RepositoryDB getInstanse(Context ctx) {
        if (mInstanse == null){
          mInstanse = new RepositoryDB(ctx);
        }
        return mInstanse;
    }

    public LiveData<List<StatisticModel>> getList(){
        return mDB.statisticModelDao().getList();
    }

    public void insertStatistic(StatisticModel data){
        new Thread(()-> mDB.statisticModelDao().insertStatistic(data)).start();
    }

    public void deleteStatistic(StatisticModel data){
        new Thread(()-> mDB.statisticModelDao().deleteSttistic(data)).start();
    }
}
