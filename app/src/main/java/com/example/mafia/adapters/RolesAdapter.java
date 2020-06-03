package com.example.mafia.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mafia.R;
import com.example.mafia.models.GamePlace;
import com.example.mafia.models.RoleModel;
import com.example.mafia.network.NetworkUtils;

import java.util.ArrayList;
import java.util.Iterator;

public class RolesAdapter extends RecyclerView.Adapter<RolesAdapter.Holder> {
    private ArrayList<RoleModel> mList;
    private Context mContext;
    private NetworkUtils mUtils;
    private GamePlace mGamePlace;

    public RolesAdapter(Context context, ArrayList<RoleModel> list, GamePlace gamePlace){
        mList = list;
        mContext = context;
        mUtils = new NetworkUtils(context);
        mGamePlace = gamePlace;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.role,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if (mList.get(position).getId() == mGamePlace.getRole().getId()) {
            holder.mRoleName.setText("Это Вы");
        } else {
            holder.mRoleName.setText(mList.get(position).getRoleName());
        }
        holder.mRoleImage.setImageDrawable(mList.get(position).getRoleDrawable());
        holder.mRoleLetter.setText(mList.get(position).getRoleLetter());
        holder.itemView.setOnClickListener((c) -> sendVote(mList.get(position)));
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

    public void removeRole(RoleModel role) {
        Iterator<RoleModel> iterator = mList.iterator();
        while (iterator.hasNext()) {
            RoleModel model = iterator.next();
            if (model.getId().equals(role.getId()))
                iterator.remove();
        }
        Log.d("TAG_MAF", "removeRole: " + mList.size());
    }


    private void sendVote(RoleModel role){
        mUtils.sendVote(mGamePlace.getRoom(),role.getId(),mGamePlace.getRole().getId());
    }


}
