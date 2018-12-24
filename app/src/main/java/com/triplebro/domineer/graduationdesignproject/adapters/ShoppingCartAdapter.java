package com.triplebro.domineer.graduationdesignproject.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.triplebro.domineer.graduationdesignproject.R;
import com.triplebro.domineer.graduationdesignproject.beans.ShoppingCartInfo;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartAdapter extends BaseAdapter {

    private Context context;
    private List<ShoppingCartInfo> shoppingCartInfoList;

    public ShoppingCartAdapter(Context context, List<ShoppingCartInfo> shoppingCartInfoList) {
        this.context = context;
        this.shoppingCartInfoList = shoppingCartInfoList;
    }

    @Override
    public int getCount() {
        return shoppingCartInfoList.size();
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
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_shopping_cart, null);
            viewHolder.iv_shopping_cart = convertView.findViewById(R.id.iv_shopping_cart);
            viewHolder.tv_shopping_cart_name = convertView.findViewById(R.id.tv_shopping_cart_name);
            viewHolder.tv_price = convertView.findViewById(R.id.tv_price);
            viewHolder.tv_size = convertView.findViewById(R.id.tv_size);
            viewHolder.tv_count = convertView.findViewById(R.id.tv_count);
            viewHolder.bt_count_up = convertView.findViewById(R.id.bt_count_up);
            viewHolder.bt_count_down = convertView.findViewById(R.id.bt_count_down);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context).load(shoppingCartInfoList.get(position).getCommodity_image()).into(viewHolder.iv_shopping_cart);
        viewHolder.tv_shopping_cart_name.setText(shoppingCartInfoList.get(position).getCommodity_name());
        viewHolder.tv_count.setText(String.valueOf(shoppingCartInfoList.get(position).getCount()));
        viewHolder.tv_price.setText(String.valueOf(shoppingCartInfoList.get(position).getPrice()));
        viewHolder.tv_size.setText(shoppingCartInfoList.get(position).getSize_name());


        viewHolder.bt_count_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer integer = new Integer(viewHolder.tv_count.getText().toString());
                int i = integer.intValue();
                i = i + 1;
                viewHolder.tv_count.setText(String.valueOf(i));
            }
        });
        viewHolder.bt_count_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.valueOf(viewHolder.tv_count.getText().toString());
                if (count == 1) {
                    //Toast.makeText(context, "不能再少了！！！", Toast.LENGTH_SHORT).show();
                    shoppingCartInfoList.remove(position);
                    notifyDataSetChanged();
                } else {
                    count = count - 1;
                    viewHolder.tv_count.setText(String.valueOf(count));
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        private ImageView iv_shopping_cart;
        private TextView tv_shopping_cart_name;
        private TextView tv_price;
        private TextView tv_count;
        private TextView tv_size;
        private Button bt_count_down;
        private Button bt_count_up;
    }
}
