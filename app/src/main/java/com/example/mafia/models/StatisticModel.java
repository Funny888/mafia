package com.example.mafia.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.sql.Date;

@Entity(active = true, nameInDb = "Statistic")
public class StatisticModel {
    @Id
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

    public Date getTime() {
        return Time;
    }

    public void setTime(Date time) {
        Time = time;
    }

    public Integer getGames() {
        return Games;
    }

    public void setGames(Integer games) {
        Games = games;
    }

    @NotNull
    private String Role;

    @NotNull
    private Date Time;

    @NotNull
    private Integer Games;
}
