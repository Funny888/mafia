package com.example.mafia.models;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mafia.R;
import com.example.mafia.activities.MainActivity;

import java.util.List;

public class LearnAdapter extends RecyclerView.Adapter<LearnAdapter.MyPageAdapter> {
    private Context mContext;
    private List<String> mList;

    public LearnAdapter(List<String> list){
        mList = list;
    }

    @NonNull
    @Override
    public MyPageAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.learn_item,parent,false);
        return new MyPageAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPageAdapter holder, int position) {
            holder.tMot.setText(mList.get(position));
            if (mList.size() - 1 == position) {
                holder.bOk.setVisibility(View.VISIBLE);
                holder.bOk.setOnClickListener((c)->   mContext.startActivity(new Intent(mContext, MainActivity.class)));
            }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyPageAdapter extends RecyclerView.ViewHolder {
        private TextView tMot;
        private Button bOk;
        public MyPageAdapter(@NonNull View itemView) {
            super(itemView);
            tMot = itemView.findViewById(R.id.motivate);
            bOk = itemView.findViewById(R.id.btn_ok);
        }
    }
}
