package com.example.mafia.adpters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mafia.R;
import com.google.android.material.button.MaterialButton;

public class ShowRulesAdapter extends RecyclerView.Adapter<ShowRulesAdapter.Holder> {

    private MaterialButton mButtonFine;
    private MaterialButton mButtonSkip;
    public ShowRulesAdapter(){}

    String[] s = {"There will be rules the game","page2"};

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rules_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.mRuletext.setText(s[position]);
    }

    @Override
    public int getItemCount() {
        return s.length;
    }

    public MaterialButton getButtonFine() {
        return mButtonFine;
    }

    public MaterialButton getButtonSkip(){
        return mButtonSkip;
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView mRuletext;
        private MaterialButton mSkip;
        private MaterialButton mFine;
        public Holder(@NonNull View itemView) {
            super(itemView);
            mRuletext = itemView.findViewById(R.id.tv_rule);
            mSkip = itemView.findViewById(R.id.btn_skip);
            mFine = itemView.findViewById(R.id.btn_fine);
            mButtonFine = mFine;
            mButtonSkip = mSkip;
        }
    }
}
