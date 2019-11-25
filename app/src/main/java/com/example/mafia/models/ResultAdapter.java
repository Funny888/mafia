package com.example.mafia.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mafia.R;

import java.util.List;

public class ResultAdapter extends BaseAdapter {
    private List<StatisticModel> mList;
    private Context mContext;

    public ResultAdapter(Context context, List<StatisticModel> list){
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.rezult_item,viewGroup,false);
        }

        ((TextView)view.findViewById(R.id.item_actor)).setText(mList.get(i).getRole());
        ((TextView)view.findViewById(R.id.item_count)).setText(String.valueOf(mList.get(i).getGames()));
        ((TextView)view.findViewById(R.id.item_time)).setText(String.valueOf(mList.get(i).getTime()));
        return view;
    }
}
