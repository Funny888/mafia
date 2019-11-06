package com.example.mafia.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mafia.models.StatisticModel;

import java.util.List;

@Dao
public interface StatisticDao {
    @Query("SELECT * FROM Statistic")
    LiveData<List<StatisticModel>> getList();

    @Insert
    void insertStatistic(StatisticModel statistic);

    @Delete
    void deleteSttistic(StatisticModel statistic);

}
