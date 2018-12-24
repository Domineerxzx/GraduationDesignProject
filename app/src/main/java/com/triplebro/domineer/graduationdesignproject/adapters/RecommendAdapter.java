package com.triplebro.domineer.graduationdesignproject.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.triplebro.domineer.graduationdesignproject.R;
import com.triplebro.domineer.graduationdesignproject.beans.CommodityInfo;
import com.triplebro.domineer.graduationdesignproject.interfaces.OnItemClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {

    private Context context;
    private List<CommodityInfo> data;
    private OnItemClickListener onItemClickListener;

    public RecommendAdapter(Context context, List<CommodityInfo> data) {
        this.context = context;
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_recommend, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(inflate, onItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ViewHolder holder = viewHolder;
        holder.tv_good_name.setText(data.get(i).getCommodity_name());
        holder.tv_price.setText(String.valueOf(data.get(i).getPrice()));
        Glide.with(context).load(new File(data.get(i).getCommodity_image())).into(holder.iv_recommend);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView iv_recommend;
        private RelativeLayout rl_recommend;
        private TextView tv_good_name;
        private TextView tv_unit;
        private TextView tv_price;

        private OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
            initView(itemView);
        }

        private void initView(View itemView) {
            iv_recommend = itemView.findViewById(R.id.iv_recommend);
            rl_recommend = itemView.findViewById(R.id.rl_recommend);
            tv_good_name = itemView.findViewById(R.id.tv_good_name);
            tv_unit = itemView.findViewById(R.id.tv_unit);
            tv_price = itemView.findViewById(R.id.tv_price);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getPosition());
        }
    }
}
