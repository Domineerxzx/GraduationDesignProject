package com.triplebro.domineer.graduationdesignproject.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.triplebro.domineer.graduationdesignproject.R;

import java.util.List;

public class AddressAdapter extends BaseAdapter {

    private Context context;
    private List<String> strings;

    public AddressAdapter(Context context, List<String> strings) {
        this.context = context;
        this.strings = strings;
    }

    @Override
    public int getCount() {
        return 5;
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
            convertView = View.inflate(context, R.layout.item_address,null);
            viewHolder.tv_address_name = convertView.findViewById(R.id.tv_address_name);
            viewHolder.tv_address = convertView.findViewById(R.id.tv_address);
            viewHolder.tv_address_postcode = convertView.findViewById(R.id.tv_address_postcode);
            viewHolder.bt_change_address_info = convertView.findViewById(R.id.bt_change_address_info);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    private class ViewHolder{

        private TextView tv_address_name;
        private TextView tv_address;
        private TextView tv_address_postcode;
        private Button bt_change_address_info;

    }
}
