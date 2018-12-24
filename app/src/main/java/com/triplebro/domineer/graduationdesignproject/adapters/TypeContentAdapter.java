package com.triplebro.domineer.graduationdesignproject.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.triplebro.domineer.graduationdesignproject.activities.ContentActivity;
import com.triplebro.domineer.graduationdesignproject.beans.TypeConcreteInfo;
import com.triplebro.domineer.graduationdesignproject.fragments.TypeFragment;
import com.triplebro.domineer.graduationdesignproject.interfaces.OnItemClickListener;

import java.util.List;

public class TypeContentAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<TypeConcreteInfo> typeConcreteInfoList;
    private OnItemClickListener onItemClickListener;

    public TypeContentAdapter(Context context, List<TypeConcreteInfo> typeConcreteInfoList) {
        this.context = context;
        this.typeConcreteInfoList = typeConcreteInfoList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_type_content, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate,onItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).tv_type_content.setText(typeConcreteInfoList.get(position).getType_concrete_name());
        Glide.with(context).load(typeConcreteInfoList.get(position).getType_concrete_image()).into(((ViewHolder)holder).iv_type_content);
    }


    @Override
    public int getItemCount() {
        return typeConcreteInfoList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView iv_type_content;
        public TextView tv_type_content;
        public RelativeLayout rl_type_content;
        private OnItemClickListener onItemClickListener;


        public ViewHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
            initView(itemView);
        }


        private void initView(View itemView) {
            iv_type_content = itemView.findViewById(R.id.iv_type_content);
            tv_type_content = itemView.findViewById(R.id.tv_type_content);
            rl_type_content = itemView.findViewById(R.id.rl_type_content);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getPosition());
        }
    }
}
