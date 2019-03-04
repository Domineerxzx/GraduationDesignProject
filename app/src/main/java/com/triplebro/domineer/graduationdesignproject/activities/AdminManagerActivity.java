package com.triplebro.domineer.graduationdesignproject.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.triplebro.domineer.graduationdesignproject.R;

public class AdminManagerActivity extends Activity implements View.OnClickListener {

    private TextView tv_admin;
    private LinearLayout ll_add_goods;
    private LinearLayout ll_change_goods_info;
    private LinearLayout ll_delete_goods;
    private LinearLayout ll_change_admin_info;
    private TextView tv_add_goods;
    private TextView tv_change_goods_info;
    private TextView tv_delete_goods;
    private TextView tv_change_admin_info;
    private ImageView iv_add_goods;
    private ImageView iv_change_goods_info;
    private ImageView iv_delete_goods;
    private ImageView iv_change_admin_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manager);

        initView();
        setOnClickListener();
    }

    private void setOnClickListener() {
        tv_admin.setOnClickListener(this);
        ll_add_goods.setOnClickListener(this);
        ll_change_goods_info.setOnClickListener(this);
        ll_delete_goods.setOnClickListener(this);
        ll_change_admin_info.setOnClickListener(this);
        tv_add_goods.setOnClickListener(this);
        tv_change_goods_info.setOnClickListener(this);
        tv_delete_goods.setOnClickListener(this);
        tv_change_admin_info.setOnClickListener(this);
        iv_add_goods.setOnClickListener(this);
        iv_change_goods_info.setOnClickListener(this);
        iv_delete_goods.setOnClickListener(this);
        iv_change_admin_info.setOnClickListener(this);
    }

    private void initView() {
        tv_admin = (TextView) findViewById(R.id.tv_admin);
        ll_add_goods = (LinearLayout) findViewById(R.id.ll_add_goods);
        ll_change_goods_info = (LinearLayout) findViewById(R.id.ll_change_goods_info);
        ll_delete_goods = (LinearLayout) findViewById(R.id.ll_delete_goods);
        ll_change_admin_info = (LinearLayout) findViewById(R.id.ll_change_admin_info);
        tv_add_goods = (TextView) findViewById(R.id.tv_add_goods);
        tv_change_goods_info = (TextView) findViewById(R.id.tv_change_goods_info);
        tv_delete_goods = (TextView) findViewById(R.id.tv_delete_goods);
        tv_change_admin_info = (TextView) findViewById(R.id.tv_change_admin_info);
        iv_add_goods = (ImageView) findViewById(R.id.iv_add_goods);
        iv_change_goods_info = (ImageView) findViewById(R.id.iv_change_goods_info);
        iv_delete_goods = (ImageView) findViewById(R.id.iv_delete_goods);
        iv_change_admin_info = (ImageView) findViewById(R.id.iv_change_admin_info);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_admin:
                finish();
                break;
            case R.id.ll_add_goods:
            case R.id.tv_add_goods:
            case R.id.iv_add_goods:
                break;
            case R.id.ll_change_goods_info:
            case R.id.tv_change_goods_info:
            case R.id.iv_change_goods_info:
                break;
            case R.id.ll_delete_goods:
            case R.id.tv_delete_goods:
            case R.id.iv_delete_goods:
                break;
            case R.id.ll_change_admin_info:
            case R.id.tv_change_admin_info:
            case R.id.iv_change_admin_info:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
