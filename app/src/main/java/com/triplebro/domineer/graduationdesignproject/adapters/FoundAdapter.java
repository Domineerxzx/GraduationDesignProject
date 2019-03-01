package com.triplebro.domineer.graduationdesignproject.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.triplebro.domineer.graduationdesignproject.R;
import com.triplebro.domineer.graduationdesignproject.beans.CollectionSubmitInfo;
import com.triplebro.domineer.graduationdesignproject.beans.SubmitInfo;
import com.triplebro.domineer.graduationdesignproject.database.MyOpenHelper;
import com.triplebro.domineer.graduationdesignproject.fragments.FoundFragment;
import com.triplebro.domineer.graduationdesignproject.managers.CollectionManager;
import com.triplebro.domineer.graduationdesignproject.managers.FirstPageManager;
import com.triplebro.domineer.graduationdesignproject.managers.FoundManger;
import com.triplebro.domineer.graduationdesignproject.sourceop.DatabaseOP;

import java.util.ArrayList;
import java.util.List;

public class FoundAdapter extends BaseAdapter {

    private Context context;
    private List<SubmitInfo> submitInfoList;
    private List<String> submitImageList;
    private FoundManger foundManger;
    private ListView lv_collection_submit;

    public FoundAdapter(Context context, List<SubmitInfo> submitInfoList) {
        this.context = context;
        this.submitInfoList = submitInfoList;
    }

    public FoundAdapter(Context context, List<SubmitInfo> submitInfoList, ListView lv_collection_submit) {
        this.context = context;
        this.submitInfoList = submitInfoList;
        this.lv_collection_submit = lv_collection_submit;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_found, null);
            viewHolder.tv_nickname = convertView.findViewById(R.id.tv_nickname);
            viewHolder.tv_found_content = convertView.findViewById(R.id.tv_found_content);
            viewHolder.rv_found = convertView.findViewById(R.id.rv_found);
            viewHolder.iv_user_head = convertView.findViewById(R.id.iv_user_head);
            viewHolder.iv_collection = convertView.findViewById(R.id.iv_collection);
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
        viewHolder.iv_collection.setTag("unCollected");
        DatabaseOP databaseOP = new DatabaseOP(context);
        boolean isCollection = databaseOP.getIsCollectionSubmit(submitInfoList.get(position).getSubmit_id());
        if(isCollection){
            viewHolder.iv_collection.setBackgroundResource(R.mipmap.collection_click);
            viewHolder.iv_collection.setTag("isCollected");
        }
        viewHolder.iv_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((String) viewHolder.iv_collection.getTag()).equals("isCollected")) {
                    foundManger = new FoundManger(context);
                    boolean deleteSubmitCollection = foundManger.deleteSubmitCollection(submitInfoList.get(position).getSubmit_id());
                    if (deleteSubmitCollection) {
                        viewHolder.iv_collection.setBackgroundResource(R.mipmap.collection);
                        viewHolder.iv_collection.setTag("unCollected");
                        refresh();
                    }
                } else {
                    foundManger = new FoundManger(context);
                    boolean addSubmitCollection = foundManger.addSubmitCollection(submitInfoList.get(position).getSubmit_id());
                    if (addSubmitCollection) {
                        viewHolder.iv_collection.setBackgroundResource(R.mipmap.collection_click);
                        viewHolder.iv_collection.setTag("isCollected");
                        refresh();
                    }
                }
            }
        });
        return convertView;
    }

    private void refresh(){
        if(lv_collection_submit != null){
            CollectionManager collectionManager = new CollectionManager(context);
            SharedPreferences userInfo = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            String phone_number = userInfo.getString("phone_number","");
            List<SubmitInfo> collectionSubmitInfoList = collectionManager.getCollectionSubmitInfoList(phone_number);
            submitInfoList = collectionSubmitInfoList;
            notifyDataSetChanged();
        }
    }

    private class ViewHolder{
        private TextView tv_nickname;
        private RecyclerView rv_found;
        private ImageView iv_user_head;
        private TextView tv_found_content;
        private ImageView iv_collection;
    }
}
