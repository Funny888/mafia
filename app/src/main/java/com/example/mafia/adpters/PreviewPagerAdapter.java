package com.example.mafia.adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.mafia.R;
import com.example.mafia.interfaces.ISend;
import com.google.android.material.button.MaterialButton;


public class PreviewPagerAdapter extends RecyclerView.Adapter<PreviewPagerAdapter.MyHolder> {
    private ISend<MaterialButton> mSend;
    private Context mContext;

    public PreviewPagerAdapter(Context context) {
        mContext = context;
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

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.preview_image, parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.mPagerImage.setImageDrawable(mContext.getResources().getDrawable(getImageAt(position)));
        holder.mPagerText.setText(getStringAt(position));
        if (position == 3){
            holder.mBtn_ok.setVisibility(View.VISIBLE);
            mSend.send(holder.mBtn_ok);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }


    public class MyHolder extends RecyclerView.ViewHolder{
        private TextView mPagerText;
        private ImageView mPagerImage;
        private MaterialButton mBtn_ok;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            mPagerText = itemView.findViewById(R.id.pagerText);
            mPagerImage = itemView.findViewById(R.id.pagerImage);
            mBtn_ok = itemView.findViewById(R.id.btn_ok);
        }
    }

    public ISend<MaterialButton> setClick(ISend<MaterialButton> send){
        mSend = send;
        return mSend;
    }
}
