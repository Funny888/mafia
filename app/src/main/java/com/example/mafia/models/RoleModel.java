package com.example.mafia.models;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

public class RoleModel {

    private Long mId;
    private String  mRoleName;
    private Drawable mRoleDrawable;
    private String mRoleLetter;
    private Boolean mIsBusy;
    private Boolean mIsDead;

    public RoleModel(Long id,String roleName,Drawable roleDrawable,Boolean isBusy,Boolean isDead,String roleLetter){
        mId = id;
        mRoleName = roleName;
        mRoleDrawable = roleDrawable;
        mRoleLetter = roleLetter;
        mIsBusy = isBusy;
        mIsDead = isDead;
    }

    public Long getId() {
        return mId;
    }


    public String getRoleName() {
        return mRoleName;
    }


    public Drawable getRoleDrawable() {
        return mRoleDrawable;
    }


    public String getRoleLetter() {
        return mRoleLetter;
    }

    public Boolean getIsBusy(){
        return mIsBusy;
    }

    public Boolean getIsDead(){
        return mIsDead;
    }

}
