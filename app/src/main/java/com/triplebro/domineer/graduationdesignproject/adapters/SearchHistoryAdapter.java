package com.triplebro.domineer.graduationdesignproject.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.triplebro.domineer.graduationdesignproject.R;

import java.util.List;

public class SearchHistoryAdapter extends BaseAdapter {

    private Context context;
    private List<String> strings;

    public SearchHistoryAdapter(Context context, List<String> strings) {
        this.context = context;
        this.strings = strings;
    }

    @Override
    public int getCount() {
        return 16;
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
            convertView = View.inflate(context, R.layout.item_search_history,null);
            viewHolder.tv_history = convertView.findViewById(R.id.tv_history);
            viewHolder.iv_delete_history = convertView.findViewById(R.id.iv_delete_history);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    private class ViewHolder{
        private TextView tv_history;
        private ImageView iv_delete_history;
    }
}
