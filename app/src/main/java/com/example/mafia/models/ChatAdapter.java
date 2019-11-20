package com.example.mafia.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mafia.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.Holder> {
    private List<ChatModel> mList;
    private Context mContext;

    public ChatAdapter(List<ChatModel> list,Context context){
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(mContext).inflate(R.layout.message_maket,parent,false));
    }

    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.mName.setText(mList.get(position).getName());
        holder.mTime.setText(mList.get(position).getDate());
        holder.mMess.setText(mList.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        private TextView mName;
        private TextView mTime;
        private TextView mMess;

        public Holder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.mName);
            mTime = itemView.findViewById(R.id.mTime);
            mMess = itemView.findViewById(R.id.mMess);
        }
    }
}
