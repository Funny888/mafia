package com.example.mafia;


import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class Bind {
    private Bind(){}

@BindingAdapter({"setImage","getIdImage"})
public static void setImage(ImageView view, Boolean show, Drawable idImage){
        if (show) {
            view.setImageDrawable(idImage);
        } else {
            view.setImageDrawable(view.getContext().getDrawable(R.drawable.card_front));
        }
}

}
