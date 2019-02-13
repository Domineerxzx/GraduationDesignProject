package com.triplebro.domineer.graduationdesignproject.adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.triplebro.domineer.graduationdesignproject.R;
import com.triplebro.domineer.graduationdesignproject.beans.SubmitInfo;
import com.triplebro.domineer.graduationdesignproject.database.MyOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyselfSubmitAdapter extends BaseAdapter {

    private Context context;
    private List<SubmitInfo> data;
    private ArrayList<String> submitImageList;

    public MyselfSubmitAdapter(Context context, List<SubmitInfo> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
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
        viewHolder.tv_submit.setText(data.get(position).getSubmit_content());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        viewHolder.rv_photo_wall.setLayoutManager(gridLayoutManager);
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        Cursor submitImageInfoCursor = db.query("submitImageInfo", new String[]{"submit_image"}, "submit_id = ?", new String[]{String.valueOf(data.get(position).getSubmit_id())}, null, null, null);
        submitImageList = new ArrayList<String>();
        if(submitImageInfoCursor!=null&&submitImageInfoCursor.getCount()>0){
            while (submitImageInfoCursor.moveToNext()){
                String submitImage = submitImageInfoCursor.getString(0);
                submitImageList.add(submitImage);
            }
        }
        if (submitImageInfoCursor != null) {
            submitImageInfoCursor.close();
        }
        db.close();
        viewHolder.rv_photo_wall.setAdapter(new PhotoWallAdapter(context,submitImageList));
        return convertView;
    }

    private class ViewHolder{
        private TextView tv_submit;
        private RecyclerView rv_photo_wall;
    }
}