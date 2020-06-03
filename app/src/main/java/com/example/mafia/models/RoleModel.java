package com.example.mafia.models;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoleModel {

    @Expose
    @SerializedName("Id")
    private Long mId;

    @Expose
    @SerializedName("Role")
    private String  mRoleName;

    @Expose
    @SerializedName("isBusy")
    private Boolean mIsBusy;

    @Expose
    @SerializedName("isDead")
    private Boolean mIsDead;

    @Expose
    @SerializedName("VoicesAgainst")
    private Integer mVoicesAgainst;

    @Expose
    @SerializedName("Voted")
    private Boolean mVoted;

    private Drawable mRoleDrawable;
    private String mRoleLetter;

    public RoleModel(Long pId, String pRoleName, Boolean pIsBusy, Boolean pIsDead, Drawable pRoleDrawable, String pRoleLetter, Integer pVoicesAgainst, Boolean pVoted) {
        this.mId = pId;
        this.mRoleName = pRoleName;
        this.mIsBusy = pIsBusy;
        this.mIsDead = pIsDead;
        this.mRoleDrawable = pRoleDrawable;
        this.mRoleLetter = pRoleLetter;
        this.mVoicesAgainst = pVoicesAgainst;
        this.mVoted = pVoted;
    }

    public void setId(Long mId) {
        this.mId = mId;
    }

    public void setRoleName(String RoleName) {
        this.mRoleName = RoleName;
    }

    public void setRoleDrawable(Drawable RoleDrawable) {
        this.mRoleDrawable = RoleDrawable;
    }

    public void setRoleLetter(String RoleLetter) {
        this.mRoleLetter = RoleLetter;
    }

    public void setIsBusy(Boolean IsBusy) {
        this.mIsBusy = IsBusy;
    }

    public void setIsDead(Boolean IsDead) {
        this.mIsDead = IsDead;
    }

    public Long getId() {
        return mId;
    }

    public String getRoleName() {
        return mRoleName;
    }

    public Boolean getIsBusy() {
        return mIsBusy;
    }

    public Boolean getIsDead() {
        return mIsDead;
    }

    public Drawable getRoleDrawable() {
        return mRoleDrawable;
    }

    public String getRoleLetter() {
        return mRoleLetter;
    }

    public Integer getVoicesAgainst() {
        return mVoicesAgainst;
    }

    public void setVoicesAgainst(Integer mVoicesAgainst) {
        this.mVoicesAgainst = mVoicesAgainst;
    }

    public Boolean getmVoted() {
        return mVoted;
    }

    public void setmVoted(Boolean mVoted) {
        this.mVoted = mVoted;
    }

    @NonNull
    @Override
    public String toString() {
        return "{" + mId + " " + mRoleName + " " +
                mIsBusy + " " + mIsDead + " " +
                mRoleDrawable + " " + mRoleLetter + " " +
                mVoicesAgainst + " " + mVoted + "}";
    }
}
