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
import com.triplebro.domineer.graduationdesignproject.managers.ShoppingCartManager;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartAdapter extends BaseAdapter {

    private Context context;
    private List<ShoppingCartInfo> shoppingCartInfoList;
    private ShoppingCartManager shoppingCartManager;

    public ShoppingCartAdapter(Context context, List<ShoppingCartInfo> shoppingCartInfoList,ShoppingCartManager shoppingCartManager) {
        this.context = context;
        this.shoppingCartInfoList = shoppingCartInfoList;
        this.shoppingCartManager = shoppingCartManager;
    }

    public void setShoppingCartInfoList(List<ShoppingCartInfo> shoppingCartInfoList) {
        this.shoppingCartInfoList = shoppingCartInfoList;
        notifyDataSetChanged();
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
            viewHolder.iv_delete = convertView.findViewById(R.id.iv_delete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context).load(shoppingCartInfoList.get(position).getCommodity_image()).into(viewHolder.iv_shopping_cart);
        viewHolder.tv_shopping_cart_name.setText(shoppingCartInfoList.get(position).getCommodity_name());
        viewHolder.tv_count.setText(String.valueOf(shoppingCartInfoList.get(position).getCount()));
        viewHolder.tv_price.setText(String.valueOf(shoppingCartInfoList.get(position).getPrice()));
        viewHolder.tv_size.setText(shoppingCartInfoList.get(position).getSize_name());
        viewHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingCartInfo remove = shoppingCartInfoList.remove(position);
                shoppingCartManager.deleteCommodity(remove.getCommodity_id(),remove);
                notifyDataSetChanged();
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
        private ImageView iv_delete;
    }
}
