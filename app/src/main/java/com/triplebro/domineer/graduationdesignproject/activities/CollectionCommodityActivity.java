package com.triplebro.domineer.graduationdesignproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.triplebro.domineer.graduationdesignproject.R;
import com.triplebro.domineer.graduationdesignproject.adapters.RecommendAdapter;
import com.triplebro.domineer.graduationdesignproject.beans.CommodityInfo;
import com.triplebro.domineer.graduationdesignproject.interfaces.OnItemClickListener;
import com.triplebro.domineer.graduationdesignproject.managers.CollectionManager;

import java.util.List;

public class CollectionCommodityActivity extends Activity implements View.OnClickListener,OnItemClickListener {

    private ImageView iv_close_collection_commodity;
    private String phone_number;
    private RecyclerView rv_collection_commodity;
    private RecommendAdapter recommendAdapter;
    private CollectionManager collectionManager;
    private List<CommodityInfo> collectedCommodityInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_commodity);
        initView();
        initData();
        setOnClickListener();
    }

    private void setOnClickListener() {
        iv_close_collection_commodity.setOnClickListener(this);
    }

    private void initData() {
        SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        phone_number = userInfo.getString("phone_number", "");
        collectionManager = new CollectionManager(this);
        collectedCommodityInfoList = collectionManager.getCollectedCommodityInfoList(phone_number);
        if(collectedCommodityInfoList.size() == 0){
            Toast.makeText(this, "暂无收藏信息，快去添加吧", Toast.LENGTH_SHORT).show();
        }
        recommendAdapter = new RecommendAdapter(this, collectedCommodityInfoList);
        rv_collection_commodity.setAdapter(recommendAdapter);
        recommendAdapter.setOnItemClickListener(this);
    }

    private void initView() {
        iv_close_collection_commodity = (ImageView) findViewById(R.id.iv_close_collection_commodity);
        rv_collection_commodity = (RecyclerView) findViewById(R.id.rv_collection_commodity);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_collection_commodity.setLayoutManager(gridLayoutManager);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close_collection_commodity:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent collectionCommodity = new Intent(this, CommodityDetailsActivity.class);
        collectionCommodity.putExtra("commodityInfo", collectedCommodityInfoList.get(position));
        startActivity(collectionCommodity);
    }

    @Override
    public void onItemLongClick(View view) {

    }
}
