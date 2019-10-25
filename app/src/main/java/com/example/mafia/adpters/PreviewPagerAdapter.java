package com.example.mafia.adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.example.mafia.R;

public class PreviewPagerAdapter extends PagerAdapter {

    private Context mContext;

    public PreviewPagerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.preview_image, null);

        ImageView imageView = view.findViewById(R.id.pagerImage);
        imageView.setImageDrawable(mContext.getResources().getDrawable(getImageAt(position)));

        TextView textView = view.findViewById(R.id.pagerText);
        textView.setText(getStringAt(position));

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    private int getImageAt(int position) {
        switch (position) {
            case 0:
                return R.drawable.pp1;
            case 1:
                return R.drawable.pp2;
            case 2:
                return R.drawable.pp3;
            case 3:
                return R.drawable.pp4;
            default:
                return R.drawable.pp5;
        }
    }

    private String getStringAt(int position) {
        String[] list = mContext.getResources().getStringArray(R.array.naputstvie);
        return list[position];
    }

}
