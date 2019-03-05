package com.triplebro.domineer.graduationdesignproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
import com.triplebro.domineer.graduationdesignproject.managers.TypeRecommendManager;

import java.util.List;

public class TypeRecommendActivity extends Activity implements View.OnClickListener,OnItemClickListener {

    private TextView tv_title;
    private ImageView iv_back;
    private RecyclerView rv_content;
    private TypeRecommendManager typeRecommendManager;
    private List<CommodityInfo> commodityList;
    private RecommendAdapter recommendAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_recommend);

        initView();
        initData();
        setOnClickListener();
    }

    private void setOnClickListener() {
        iv_back.setOnClickListener(this);
        recommendAdapter.setOnItemClickListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        if (title != null) {
            tv_title.setText(title);
        }
        int type = intent.getIntExtra("type",-1);
        if (type != -1) {
            typeRecommendManager = new TypeRecommendManager(this);
            commodityList = typeRecommendManager.getCommodityList(type);
            rv_content.setLayoutManager(new GridLayoutManager(this,2));
            recommendAdapter = new RecommendAdapter(this, commodityList);
            rv_content.setAdapter(recommendAdapter);
        }
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
        Intent intent = new Intent(this, CommodityDetailsActivity.class);
        intent.putExtra("commodityInfo", commodityList.get(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view) {

    }
}
