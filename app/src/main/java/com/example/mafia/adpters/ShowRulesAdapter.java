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
    public interface OnButton{
        void onClick(View v);
    }
    private Context mContext;
    private String[] mRules;
    private OnButton mOnButton;

    public ShowRulesAdapter(Context context) {
        mContext = context;
        mRules = mContext.getResources().getStringArray(R.array.rules_game);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rules_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.mRuletext.setText(mRules[position]);
        if (position == mRules.length - 1) {
            holder.mSkip.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mRules.length;
    }

    public void setListener(OnButton button) {
        mOnButton = button;
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
            mOnButton.onClick(mFine);
            mOnButton.onClick(mSkip);
        }
    }
}
