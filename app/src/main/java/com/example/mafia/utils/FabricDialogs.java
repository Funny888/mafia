package com.example.mafia.utils;

import androidx.fragment.app.Fragment;

import com.example.mafia.fragments.Dialog_Freeplace;
import com.example.mafia.fragments.Dialog_Loader;
import com.example.mafia.fragments.Dialog_Showrules;

public class FabricDialogs {
    public static final int CODE_DIALOG_LOADER = 1;
    public static final int CODE_DIALOG_FREEPLACE = 2;
    public static final int CODE_DIALOG_SHOW_RULES = 3;

    public Fragment getDialog(Integer code){
        Fragment dialog = null;
        switch (code){
            case CODE_DIALOG_LOADER:{
                dialog = new Dialog_Loader();
                break;
            }
            case CODE_DIALOG_FREEPLACE:{
                dialog = new Dialog_Freeplace();
                break;
            }

            case CODE_DIALOG_SHOW_RULES:{
                dialog = new Dialog_Showrules();
                break;
            }
        }
        return dialog;
    }

}
