package com.example.mafia.adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mafia.R;
import com.google.android.material.button.MaterialButton;

public class ShowRulesAdapter extends RecyclerView.Adapter<ShowRulesAdapter.Holder> {
    private Context mContext;
    public ShowRulesAdapter(Context context){
        mContext = context;
    }

    String s = "There will be rules the game";

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(mContext).inflate(R.layout.learn_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.mRuletext.setText(s);
        holder.mOk.setText("Понял");
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView mRuletext;
        private MaterialButton mOk;
        public Holder(@NonNull View itemView) {
            super(itemView);
            mRuletext = itemView.findViewById(R.id.motivate);
            mOk = itemView.findViewById(R.id.btn_ok);
        }
    }
}
