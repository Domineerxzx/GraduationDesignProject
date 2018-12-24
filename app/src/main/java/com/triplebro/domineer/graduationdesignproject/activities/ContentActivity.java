package com.triplebro.domineer.graduationdesignproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.triplebro.domineer.graduationdesignproject.R;
import com.triplebro.domineer.graduationdesignproject.adapters.RecommendAdapter;
import com.triplebro.domineer.graduationdesignproject.beans.CommodityInfo;
import com.triplebro.domineer.graduationdesignproject.beans.TypeConcreteInfo;
import com.triplebro.domineer.graduationdesignproject.interfaces.OnItemClickListener;
import com.triplebro.domineer.graduationdesignproject.managers.ContentManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContentActivity extends Activity implements View.OnClickListener, OnItemClickListener {

    private TextView tv_title;
    private ImageView iv_back;
    private RecyclerView rv_content;
    private TypeConcreteInfo typeConcreteInfo;
    private int type_concrete_id;
    private ContentManager contentManager;
    private List<CommodityInfo> commodityInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        initView();
        initData();
        setOnClickListener();
    }

    private void setOnClickListener() {
        iv_back.setOnClickListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        typeConcreteInfo = (TypeConcreteInfo) intent.getSerializableExtra("type");
        if (typeConcreteInfo != null) {
            tv_title.setText(typeConcreteInfo.getType_concrete_name());
            type_concrete_id = typeConcreteInfo.getType_concrete_id();
        }
        String title = intent.getStringExtra("title");
        if (title != null) {
            tv_title.setText(title);
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rv_content.setLayoutManager(gridLayoutManager);
        contentManager = new ContentManager(this);
        commodityInfoList = contentManager.getCommodityInfoList(type_concrete_id);
        RecommendAdapter recommendAdapter = new RecommendAdapter(this, (ArrayList<CommodityInfo>) commodityInfoList);
        rv_content.setAdapter(recommendAdapter);
        recommendAdapter.setOnItemClickListener(this);
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        rv_content = (RecyclerView) findViewById(R.id.rv_content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent commodityDetails = new Intent(this, CommodityDetailsActivity.class);
        commodityDetails.putExtra("commodityInfo",commodityInfoList.get(position));
        startActivity(commodityDetails);
    }

    @Override
    public void onItemLongClick(View view) {

    }
}
