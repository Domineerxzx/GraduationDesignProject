package com.triplebro.domineer.graduationdesignproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.triplebro.domineer.graduationdesignproject.R;
import com.triplebro.domineer.graduationdesignproject.adapters.RecommendAdapter;
import com.triplebro.domineer.graduationdesignproject.beans.CommodityInfo;
import com.triplebro.domineer.graduationdesignproject.beans.CommoditySizeInfo;
import com.triplebro.domineer.graduationdesignproject.interfaces.OnItemClickListener;
import com.triplebro.domineer.graduationdesignproject.managers.CommodityDetailsManager;
import com.triplebro.domineer.graduationdesignproject.managers.FirstPageManager;
import com.triplebro.domineer.graduationdesignproject.sourceop.DatabaseOP;
import com.triplebro.domineer.graduationdesignproject.utils.dialogUtils.AddShoppingCartDialog;
import com.triplebro.domineer.graduationdesignproject.utils.dialogUtils.ShareUtil;
import com.triplebro.domineer.graduationdesignproject.utils.imageUtils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class CommodityDetailsActivity extends Activity implements View.OnClickListener {

    private ImageView iv_close_commodity_details;
    private ImageView iv_share_commodity_details;
    private ImageView iv_collection_commodity_details;
    private boolean isCollection;
    private Banner bn_commodity;
    private RecyclerView rv_recommend_inside;
    private ArrayList<String> images;
    private CommodityInfo commodityInfo;
    private TextView tv_commodity_name;
    private TextView tv_price;
    private CommodityDetailsManager commodityDetailsManager;
    private List<CommodityInfo> commodityInfoList;
    private List<String> commodityImagePathList;
    private TextView tv_add_shopping_cart;
    private TextView tv_select_size;
    private List<CommoditySizeInfo> commoditySizeInfoList;
    private SharedPreferences userInfo;
    private String phone_number;
    private FirstPageManager firstPageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_details);
        initView();
        initData();
        setOnClickListener();
    }

    private void initData() {
        Intent intent = getIntent();
        commodityInfo = (CommodityInfo) intent.getSerializableExtra("commodityInfo");
        commodityDetailsManager = new CommodityDetailsManager(this);
        commodityInfoList = commodityDetailsManager.getCommodityInfoList();
        commodityImagePathList = commodityDetailsManager.getCommodityImagePathList(commodityInfo.getCommodity_id());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_recommend_inside.setLayoutManager(gridLayoutManager);
        RecommendAdapter recommendAdapter = new RecommendAdapter(this, commodityInfoList);
        rv_recommend_inside.setAdapter(recommendAdapter);
        recommendAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(CommodityDetailsActivity.this, CommodityDetailsActivity.class);
                intent.putExtra("commodityInfo", commodityInfoList.get(position));
                startActivity(intent);
                finish();
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
        bn_commodity.setImageLoader(new GlideImageLoader());
        bn_commodity.setImages(commodityImagePathList);
        bn_commodity.isAutoPlay(false);
        bn_commodity.setIndicatorGravity(BannerConfig.CENTER);
        bn_commodity.start();
        bn_commodity.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });
        tv_commodity_name.setText(commodityInfo.getCommodity_name());
        tv_price.setText(String.valueOf(commodityInfo.getPrice()));
        userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        phone_number = userInfo.getString("phone_number", "");
        DatabaseOP databaseOP = new DatabaseOP(this);
        isCollection = databaseOP.getIsCollection(commodityInfo.getCommodity_id());
        if(isCollection){
            iv_collection_commodity_details.setBackgroundResource(R.mipmap.collection_click);
        }
    }

    private void setOnClickListener() {
        iv_close_commodity_details.setOnClickListener(this);
        iv_collection_commodity_details.setOnClickListener(this);
        tv_add_shopping_cart.setOnClickListener(this);
        tv_select_size.setOnClickListener(this);
        iv_share_commodity_details.setOnClickListener(this);
    }

    private void initView() {
        iv_close_commodity_details = (ImageView) findViewById(R.id.iv_close_commodity_details);
        iv_share_commodity_details = (ImageView) findViewById(R.id.iv_share_commodity_details);
        iv_collection_commodity_details = (ImageView) findViewById(R.id.iv_collection_commodity_details);
        iv_close_commodity_details.bringToFront();
        iv_share_commodity_details.bringToFront();
        iv_collection_commodity_details.bringToFront();
        bn_commodity = (Banner) findViewById(R.id.bn_commodity);
        rv_recommend_inside = (RecyclerView) findViewById(R.id.rv_recommend_inside);
        tv_commodity_name = (TextView) findViewById(R.id.tv_commodity_name);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_add_shopping_cart = (TextView) findViewById(R.id.tv_add_shopping_cart);
        tv_select_size = (TextView) findViewById(R.id.tv_select_size);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close_commodity_details:
                finish();
                break;
            case R.id.iv_share_commodity_details:
                ShareUtil.shareText(this, "商品名称："+commodityInfo.getCommodity_name()+"价格："+commodityInfo.getPrice(),"分享此商品给朋友");
                break;
            case R.id.iv_collection_commodity_details:
                firstPageManager = new FirstPageManager(this);
                if (phone_number.length() > 0) {
                    if (isCollection) {
                        boolean deleteCommodityCollection = firstPageManager.deleteCommodityCollection(commodityInfo.getCommodity_id());
                        if (deleteCommodityCollection) {
                            iv_collection_commodity_details.setBackgroundResource(R.mipmap.collection);
                            isCollection = false;
                        }
                    } else {
                        boolean addCommodityCollection = firstPageManager.addCommodityCollection(commodityInfo.getCommodity_id());
                        if (addCommodityCollection) {
                            iv_collection_commodity_details.setBackgroundResource(R.mipmap.collection_click);
                            isCollection = true;
                        }
                    }
                } else {
                    Toast.makeText(this, "还没登录呢，不能收藏商品", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.tv_select_size:
            case R.id.tv_add_shopping_cart:
                commoditySizeInfoList = commodityDetailsManager.getCommoditySizeInfoList(commodityInfo.getCommodity_id());
                AddShoppingCartDialog addShoppingCartDialog = new AddShoppingCartDialog();
                addShoppingCartDialog.showDialog(this, commodityInfo, commoditySizeInfoList);
                break;
        }
    }
}
