package com.example.mafia.utils;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.mafia.fragments.Dialog_Freeplace;
import com.example.mafia.fragments.Dialog_Loader;

public class FabricDialogs {

    public Fragment getDialog(Integer code){

        if (code == 1){
            return new Dialog_Loader();
        } else {
            return new Dialog_Freeplace();
        }
    }

}
