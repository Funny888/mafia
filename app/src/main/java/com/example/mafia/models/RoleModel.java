package com.example.mafia.models;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

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

    private Drawable mRoleDrawable;
    private String mRoleLetter;

    public RoleModel(Long mId, String mRoleName, Boolean mIsBusy, Boolean mIsDead, Drawable mRoleDrawable, String mRoleLetter) {
        this.mId = mId;
        this.mRoleName = mRoleName;
        this.mIsBusy = mIsBusy;
        this.mIsDead = mIsDead;
        this.mRoleDrawable = mRoleDrawable;
        this.mRoleLetter = mRoleLetter;
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
}
