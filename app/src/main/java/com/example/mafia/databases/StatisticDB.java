package com.example.mafia.databases;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mafia.interfaces.StatisticDao;
import com.example.mafia.models.StatisticModel;

@Database(entities = StatisticModel.class, version = 1)
public abstract class StatisticDB extends RoomDatabase {
    public abstract StatisticDao statisticModelDao();
}
