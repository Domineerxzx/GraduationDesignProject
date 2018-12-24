package com.triplebro.domineer.graduationdesignproject.adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.triplebro.domineer.graduationdesignproject.R;
import com.triplebro.domineer.graduationdesignproject.beans.SubmitInfo;
import com.triplebro.domineer.graduationdesignproject.database.MyOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class FoundAdapter extends BaseAdapter {

    private Context context;
    private List<SubmitInfo> submitInfoList;
    private List<String> submitImageList;

    public FoundAdapter(Context context, List<SubmitInfo> submitInfoList) {
        this.context = context;
        this.submitInfoList = submitInfoList;
    }

    public void setSubmitInfoList(List<SubmitInfo> submitInfoList) {
        this.submitInfoList = submitInfoList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return submitInfoList.size();
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
            convertView = View.inflate(context, R.layout.item_found, null);
            viewHolder.tv_nickname = convertView.findViewById(R.id.tv_nickname);
            viewHolder.tv_found_content = convertView.findViewById(R.id.tv_found_content);
            viewHolder.rv_found = convertView.findViewById(R.id.rv_found);
            viewHolder.iv_user_head = convertView.findViewById(R.id.iv_user_head);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_nickname.setText(submitInfoList.get(position).getNickname());
        viewHolder.tv_found_content.setText(submitInfoList.get(position).getSubmit_content());
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        Cursor submitImageInfoCursor = db.query("submitImageInfo", new String[]{"submit_image"}, "submit_id = ?", new String[]{String.valueOf(submitInfoList.get(position).getSubmit_id())}, null, null, null);
        submitImageList = new ArrayList<>();
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
        Glide.with(context).load(submitInfoList.get(position).getUser_head())
                .apply(RequestOptions.bitmapTransform(new CircleCrop())).into(viewHolder.iv_user_head);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        viewHolder.rv_found.setLayoutManager(gridLayoutManager);
        viewHolder.rv_found.setAdapter(new PhotoWallAdapter(context, submitImageList));
        return convertView;
    }

    private class ViewHolder{
        private TextView tv_nickname;
        private RecyclerView rv_found;
        private ImageView iv_user_head;
        private TextView tv_found_content;

    }
}
