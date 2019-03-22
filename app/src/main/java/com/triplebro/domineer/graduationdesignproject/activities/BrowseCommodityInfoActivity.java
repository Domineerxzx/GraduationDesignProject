package com.triplebro.domineer.graduationdesignproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.triplebro.domineer.graduationdesignproject.R;
import com.triplebro.domineer.graduationdesignproject.adapters.RecommendAdapter;
import com.triplebro.domineer.graduationdesignproject.beans.CommodityInfo;
import com.triplebro.domineer.graduationdesignproject.interfaces.OnItemClickListener;
import com.triplebro.domineer.graduationdesignproject.managers.BrowseCommodityManager;

import java.util.List;

public class BrowseCommodityInfoActivity extends Activity implements OnItemClickListener,View.OnClickListener {

    private ImageView iv_close_browse_and_change_commodity;
    private BrowseCommodityManager browseCommodityManager;
    private RecyclerView rv_commodity_info;
    private SharedPreferences adminInfo;
    private String phone_number;
    private RecommendAdapter recommendAdapter;
    private List<CommodityInfo> commodityInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_commodity_info);

        initView();
        initData();
        setOnClickListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        commodityInfoList = browseCommodityManager.getCommodityInfoList(phone_number);
        recommendAdapter.setData(commodityInfoList);
    }

    private void initView() {
        iv_close_browse_and_change_commodity = (ImageView) findViewById(R.id.iv_close_browse_and_change_commodity);
        rv_commodity_info = (RecyclerView) findViewById(R.id.rv_commodity_info);
    }

    private void initData() {
        browseCommodityManager = new BrowseCommodityManager(this);
        adminInfo = getSharedPreferences("adminInfo", MODE_PRIVATE);
        phone_number = adminInfo.getString("phone_number", "");
        commodityInfoList = browseCommodityManager.getCommodityInfoList(phone_number);
        rv_commodity_info.setLayoutManager(new GridLayoutManager(this,2));
        recommendAdapter = new RecommendAdapter(this, commodityInfoList);
        rv_commodity_info.setAdapter(recommendAdapter);
    }

    private void setOnClickListener() {
        iv_close_browse_and_change_commodity.setOnClickListener(this);
        recommendAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close_browse_and_change_commodity:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent commodity = new Intent(this, ChangeCommodityInfoActivity.class);
        commodity.putExtra("commodityInfo",commodityInfoList.get(position));
        startActivity(commodity);
    }

    @Override
    public void onItemLongClick(View view) {

    }
}
