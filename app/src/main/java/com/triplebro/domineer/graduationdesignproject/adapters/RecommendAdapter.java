package com.triplebro.domineer.graduationdesignproject.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.triplebro.domineer.graduationdesignproject.R;
import com.triplebro.domineer.graduationdesignproject.beans.CommodityInfo;
import com.triplebro.domineer.graduationdesignproject.handlers.OssHandler;
import com.triplebro.domineer.graduationdesignproject.interfaces.OnItemClickListener;
import com.triplebro.domineer.graduationdesignproject.managers.FirstPageManager;
import com.triplebro.domineer.graduationdesignproject.properties.ProjectProperties;
import com.triplebro.domineer.graduationdesignproject.sourceop.DatabaseOP;
import com.triplebro.domineer.graduationdesignproject.utils.ossUtils.DownloadUtils;

import java.io.File;
import java.util.List;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {

    private Context context;
    private List<CommodityInfo> data;
    private OnItemClickListener onItemClickListener;
    private FirstPageManager firstPageManager;

    public RecommendAdapter(Context context, List<CommodityInfo> data) {
        this.context = context;
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public List<CommodityInfo> getData() {
        return data;
    }

    public void setData(List<CommodityInfo> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_recommend, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(inflate, onItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final ViewHolder holder = viewHolder;
        holder.tv_good_name.setText(data.get(i).getCommodity_name());
        holder.tv_price.setText(String.valueOf(data.get(i).getPrice()));
        File file = new File(data.get(i).getCommodity_image());
        if (file.length() > 0) {
            Glide.with(context).load(data.get(i).getCommodity_image()).into(holder.iv_recommend);
        }else{
            OssHandler ossHandler = new OssHandler(context, holder.iv_recommend);
            DownloadUtils.downloadFileFromOss(file,ossHandler,ProjectProperties.BUCKET_NAME,"xuzhanxin/"+data.get(i).getCommodity_image());
        }
        DatabaseOP databaseOP = new DatabaseOP(context);
        boolean isCollection = databaseOP.getIsCollection(data.get(i).getCommodity_id());
        if (isCollection) {
            holder.bt_collection.setBackgroundResource(R.mipmap.collection_click);
            holder.bt_collection.setTag("isCollected");
        }

        holder.bt_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((String) holder.bt_collection.getTag()).equals("isCollected")) {
                    firstPageManager = new FirstPageManager(context);
                    boolean deleteCommodityCollection = firstPageManager.deleteCommodityCollection(data.get(i).getCommodity_id());
                    if (deleteCommodityCollection) {
                        holder.bt_collection.setBackgroundResource(R.mipmap.collection);
                        holder.bt_collection.setTag("unCollected");
                    }
                } else {
                    firstPageManager = new FirstPageManager(context);
                    boolean addCommodityCollection = firstPageManager.addCommodityCollection(data.get(i).getCommodity_id());
                    if (addCommodityCollection) {
                        holder.bt_collection.setBackgroundResource(R.mipmap.collection_click);
                        holder.bt_collection.setTag("isCollected");
                    }
                }
            }
        });
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
        private Button bt_collection;

        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
            initView(itemView);
        }

        private void initView(View itemView) {
            iv_recommend = itemView.findViewById(R.id.iv_recommend);
            iv_recommend.setScaleType(ImageView.ScaleType.CENTER_CROP);
            rl_recommend = itemView.findViewById(R.id.rl_recommend);
            tv_good_name = itemView.findViewById(R.id.tv_good_name);
            tv_unit = itemView.findViewById(R.id.tv_unit);
            tv_price = itemView.findViewById(R.id.tv_price);
            bt_collection = itemView.findViewById(R.id.bt_collection);
            bt_collection.setTag("unCollected");
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getPosition());
        }
    }
}
