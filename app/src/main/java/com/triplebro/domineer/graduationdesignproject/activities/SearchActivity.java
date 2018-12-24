package com.triplebro.domineer.graduationdesignproject.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.triplebro.domineer.graduationdesignproject.R;
import com.triplebro.domineer.graduationdesignproject.adapters.RecommendAdapter;
import com.triplebro.domineer.graduationdesignproject.adapters.SearchHistoryAdapter;
import com.triplebro.domineer.graduationdesignproject.beans.CommodityInfo;
import com.triplebro.domineer.graduationdesignproject.views.MyListView;

import java.util.ArrayList;

public class SearchActivity extends Activity implements View.OnClickListener {

    private MyListView lv_history;
    private RecyclerView rv_recommend_search;
    private RecyclerView rv_search_result;
    private ImageView iv_close_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initData();
        setOnClickListener();
    }

    private void setOnClickListener() {
        iv_close_search.setOnClickListener(this);
    }

    private void initData() {

    }

    private void initView() {
        lv_history = (MyListView) findViewById(R.id.lv_history);
        rv_recommend_search = (RecyclerView) findViewById(R.id.rv_recommend_search);
        rv_search_result = (RecyclerView) findViewById(R.id.rv_search_result);
        SearchHistoryAdapter searchHistoryAdapter = new SearchHistoryAdapter(this, new ArrayList<String>());
        lv_history.setAdapter(searchHistoryAdapter);
        iv_close_search = (ImageView) findViewById(R.id.iv_close_search);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_recommend_search.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper. HORIZONTAL);
        rv_recommend_search.setAdapter(new RecommendAdapter(this,new ArrayList<CommodityInfo>()));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_search_result.setLayoutManager(gridLayoutManager);
        rv_search_result.setAdapter(new RecommendAdapter(this,new ArrayList<CommodityInfo>()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close_search:
                finish();
                break;
        }
    }
}
