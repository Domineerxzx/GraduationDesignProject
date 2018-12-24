package com.triplebro.domineer.graduationdesignproject.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.triplebro.domineer.graduationdesignproject.R;
import com.triplebro.domineer.graduationdesignproject.beans.CommoditySizeInfo;
import com.triplebro.domineer.graduationdesignproject.interfaces.OnItemClickListener;

import java.util.List;

public class SizeChooseAdapter extends RecyclerView.Adapter<SizeChooseAdapter.ViewHolder> {

    private Context context;

    private List<CommoditySizeInfo> commoditySizeInfoList;

    private OnItemClickListener onItemClickListener;

    public SizeChooseAdapter(Context context, List<CommoditySizeInfo> commoditySizeInfoList) {
        this.context = context;
        this.commoditySizeInfoList = commoditySizeInfoList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public SizeChooseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_size, viewGroup, false);
        SizeChooseAdapter.ViewHolder viewHolder = new SizeChooseAdapter.ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SizeChooseAdapter.ViewHolder viewHolder, int i) {
        viewHolder.tv_size.setText(commoditySizeInfoList.get(i).getSize_name());
    }

    @Override
    public int getItemCount() {
        return commoditySizeInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tv_size;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
            itemView.setOnClickListener(this);
        }

        private void initView(View itemView) {
            tv_size = itemView.findViewById(R.id.tv_size);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v,getPosition());
        }
    }
}
