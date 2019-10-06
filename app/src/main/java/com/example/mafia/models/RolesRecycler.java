package com.example.mafia.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mafia.R;

import java.util.ArrayList;

public class RolesRecycler extends RecyclerView.Adapter<RolesRecycler.Holder> {
    private ArrayList<RoleModel> mList;
    private Context mContext;

    public RolesRecycler(Context context,ArrayList<RoleModel> list){
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.role,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.mRoleName.setText(mList.get(position).getRoleName());
        holder.mRoleImage.setImageDrawable(mList.get(position).getRoleDrawable());
        holder.mRoleLetter.setText(mList.get(position).getRoleLetter());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView mRoleName;
        private ImageView mRoleImage;
        private TextView mRoleLetter;

        public Holder(@NonNull View itemView) {
            super(itemView);
            mRoleName = itemView.findViewById(R.id.roleName);
            mRoleImage = itemView.findViewById(R.id.roleImage);
            mRoleLetter = itemView.findViewById(R.id.roleLetter);
        }
    }
}
