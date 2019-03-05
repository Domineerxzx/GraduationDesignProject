package com.triplebro.domineer.graduationdesignproject.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.triplebro.domineer.graduationdesignproject.R;
import com.triplebro.domineer.graduationdesignproject.beans.CommoditySizeInfo;

import java.util.List;

public class SizeInfoAdapter extends BaseAdapter {

    private Context context;

    private List<CommoditySizeInfo> commoditySizeInfoList;

    public SizeInfoAdapter(Context context, List<CommoditySizeInfo> commoditySizeInfoList) {
        this.context = context;
        this.commoditySizeInfoList = commoditySizeInfoList;
    }

    public List<CommoditySizeInfo> getCommoditySizeInfoList() {
        return commoditySizeInfoList;
    }

    public void setCommoditySizeInfoList(List<CommoditySizeInfo> commoditySizeInfoList) {
        this.commoditySizeInfoList = commoditySizeInfoList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return commoditySizeInfoList.size();
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
        final SizeInfoAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new SizeInfoAdapter.ViewHolder();
            convertView = View.inflate(context, R.layout.item_size_info, null);
            viewHolder.et_size_name = convertView.findViewById(R.id.et_size_name);
            viewHolder.et_size_count = convertView.findViewById(R.id.et_size_count);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SizeInfoAdapter.ViewHolder) convertView.getTag();
        }
        if (commoditySizeInfoList.get(position).getSize_name() != null) {
            viewHolder.et_size_name.setText(commoditySizeInfoList.get(position).getSize_name());
        }
        if (commoditySizeInfoList.get(position).getSize_count() != 0) {
            viewHolder.et_size_count.setText(String.valueOf(commoditySizeInfoList.get(position).getSize_count()));
        }
        viewHolder.et_size_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                commoditySizeInfoList.get(position).setSize_name(s.toString().trim());
            }
        });
        viewHolder.et_size_count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                commoditySizeInfoList.get(position).setSize_count(Integer.parseInt(s.toString()));
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private EditText et_size_name;
        private EditText et_size_count;
    }
}
