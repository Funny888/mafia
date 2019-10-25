package com.example.mafia.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class PreviewPage {

    private String desc;
    private String imgUri;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    @BindingAdapter({"img"})
    public static void loadImage(ImageView imageView, String uri) {
        Glide.with(imageView.getContext()).setDefaultRequestOptions(new RequestOptions()
                .centerCrop())
                .load(uri)
                .into(imageView);
    }
}
