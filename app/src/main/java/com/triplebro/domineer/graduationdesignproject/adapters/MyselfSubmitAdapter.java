package com.triplebro.domineer.graduationdesignproject.adapters;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.triplebro.domineer.graduationdesignproject.R;

import java.util.ArrayList;
import java.util.List;

public class MyselfSubmitAdapter extends BaseAdapter {

    private Context context;
    private List<String> strings;

    public MyselfSubmitAdapter(Context context, ArrayList<String> strings) {
        this.context = context;
        this.strings = strings;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_myself_submit, null);
            viewHolder.tv_submit = convertView.findViewById(R.id.tv_submit);
            viewHolder.rv_photo_wall = convertView.findViewById(R.id.rv_photo_wall);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        viewHolder.rv_photo_wall.setLayoutManager(gridLayoutManager);
        viewHolder.rv_photo_wall.setAdapter(new PhotoWallAdapter(context,new ArrayList<String>()));
        return convertView;
    }

    private class ViewHolder{
        private TextView tv_submit;
        private RecyclerView rv_photo_wall;
    }
}
