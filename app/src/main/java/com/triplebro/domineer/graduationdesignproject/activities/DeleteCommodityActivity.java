package com.triplebro.domineer.graduationdesignproject.activities;

import android.app.Activity;
import android.content.DialogInterface;
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
import com.triplebro.domineer.graduationdesignproject.managers.DeleteCommodityManager;
import com.triplebro.domineer.graduationdesignproject.utils.dialogUtils.TwoButtonDialog;

import java.util.List;

public class DeleteCommodityActivity extends Activity implements OnItemClickListener,View.OnClickListener {

    private ImageView iv_close_delete_commodity;
    private DeleteCommodityManager deleteCommodityManager;
    private SharedPreferences adminInfo;
    private String phone_number;
    private List<CommodityInfo> commodityInfoList;
    private RecyclerView rv_commodity_info;
    private RecommendAdapter recommendAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_commodity);
        initView();
        initData();
        setOnClickListener();
    }

    private void initView() {
        iv_close_delete_commodity = (ImageView) findViewById(R.id.iv_close_delete_commodity);
        rv_commodity_info = (RecyclerView) findViewById(R.id.rv_commodity_info);
    }

    private void initData() {
        deleteCommodityManager = new DeleteCommodityManager(this);
        adminInfo = getSharedPreferences("adminInfo", MODE_PRIVATE);
        phone_number = adminInfo.getString("phone_number", "");
        commodityInfoList = deleteCommodityManager.getCommodityInfoList(phone_number);
        rv_commodity_info.setLayoutManager(new GridLayoutManager(this,2));
        recommendAdapter = new RecommendAdapter(this, commodityInfoList);
        rv_commodity_info.setAdapter(recommendAdapter);
    }

    private void setOnClickListener() {
        iv_close_delete_commodity.setOnClickListener(this);
        recommendAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View view, final int position) {
        TwoButtonDialog twoButtonDialog = new TwoButtonDialog();
        twoButtonDialog.show("删除商品", "是否要删除该商品？", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteCommodityManager.deleteCommodity(commodityInfoList.get(position).getCommodity_id());
                commodityInfoList.remove(position);
                recommendAdapter.setData(commodityInfoList);
                dialog.dismiss();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        },getFragmentManager());
    }

    @Override
    public void onItemLongClick(View view) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close_delete_commodity:
                finish();
                break;
        }
    }
}
