package com.example.mafia.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "Statistic")
public class StatisticModel {


    @PrimaryKey(autoGenerate = true)
    private Long _Id;

    public Long get_Id() {
        return _Id;
    }

    public void set_Id(Long _Id) {
        this._Id = _Id;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public Long getTime() {
        return Time;
    }

    public void setTime(Long time) {
        Time = time;
    }

    public Integer getGames() {
        return Games;
    }

    public void setGames(Integer games) {
        Games = games;
    }

    private String Role;

    private Long Time;

    private Integer Games;


}
