package com.triplebro.domineer.graduationdesignproject.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.triplebro.domineer.graduationdesignproject.R;
import com.triplebro.domineer.graduationdesignproject.activities.CommodityDetailsActivity;
import com.triplebro.domineer.graduationdesignproject.activities.ContentActivity;
import com.triplebro.domineer.graduationdesignproject.activities.SearchActivity;
import com.triplebro.domineer.graduationdesignproject.activities.TypeRecommendActivity;
import com.triplebro.domineer.graduationdesignproject.adapters.RecommendAdapter;
import com.triplebro.domineer.graduationdesignproject.beans.CommodityInfo;
import com.triplebro.domineer.graduationdesignproject.interfaces.OnItemClickListener;
import com.triplebro.domineer.graduationdesignproject.interfaces.OnScrollChangedListener;
import com.triplebro.domineer.graduationdesignproject.managers.FirstPageManager;
import com.triplebro.domineer.graduationdesignproject.utils.imageUtils.GlideImageLoader;
import com.triplebro.domineer.graduationdesignproject.views.MyScrollView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import cc.ibooker.zcountdownviewlib.CountDownView;

public class FirstPageFragment extends Fragment implements OnScrollChangedListener, View.OnClickListener, OnItemClickListener {

    private View fragment_firstPage;
    private FrameLayout fl_search;
    private Banner bn_banner;
    private RecyclerView rv_recommend;
    private MyScrollView msv_first_page;
    private CountDownView countdownView;
    private TextView tv_search;
    private RelativeLayout rl_search;
    private LinearLayout ll_clothes;
    private LinearLayout ll_bags;
    private LinearLayout ll_household;
    private LinearLayout ll_technology;
    private LinearLayout ll_life;
    private LinearLayout ll_mother_and_baby;
    private LinearLayout ll_beauty_make_up;
    private LinearLayout ll_tide_card;
    private TextView tv_clothes;
    private TextView tv_bags;
    private TextView tv_household;
    private TextView tv_technology;
    private TextView tv_life;
    private TextView tv_mother_and_baby;
    private TextView tv_beauty_make_up;
    private TextView tv_tide_card;
    private ImageView iv_clothes;
    private ImageView iv_bags;
    private ImageView iv_household;
    private ImageView iv_technology;
    private ImageView iv_life;
    private ImageView iv_mother_and_baby;
    private ImageView iv_beauty_make_up;
    private ImageView iv_tide_card;
    private FirstPageManager firstPageManager;
    private List<CommodityInfo> commodityInfoList = new ArrayList<>();
    private RecommendAdapter recommendAdapter;
    private ImageView iv_scan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragment_firstPage = inflater.inflate(R.layout.fragment_first_page, null);
        initView();
        initData();
        setOnClickListener();
        return fragment_firstPage;
    }

    private void setOnClickListener() {
        tv_search.setOnClickListener(this);
        rl_search.setOnClickListener(this);

        ll_clothes.setOnClickListener(this);
        ll_bags.setOnClickListener(this);
        ll_household.setOnClickListener(this);
        ll_technology.setOnClickListener(this);
        ll_life.setOnClickListener(this);
        ll_mother_and_baby.setOnClickListener(this);
        ll_beauty_make_up.setOnClickListener(this);
        ll_tide_card.setOnClickListener(this);

        tv_clothes.setOnClickListener(this);
        tv_bags.setOnClickListener(this);
        tv_household.setOnClickListener(this);
        tv_technology.setOnClickListener(this);
        tv_life.setOnClickListener(this);
        tv_mother_and_baby.setOnClickListener(this);
        tv_beauty_make_up.setOnClickListener(this);
        tv_tide_card.setOnClickListener(this);

        iv_clothes.setOnClickListener(this);
        iv_bags.setOnClickListener(this);
        iv_household.setOnClickListener(this);
        iv_technology.setOnClickListener(this);
        iv_life.setOnClickListener(this);
        iv_mother_and_baby.setOnClickListener(this);
        iv_beauty_make_up.setOnClickListener(this);
        iv_tide_card.setOnClickListener(this);
        iv_scan.setOnClickListener(this);
    }

    private void initData() {
        firstPageManager = new FirstPageManager(getActivity());
        commodityInfoList = firstPageManager.getCommodityRecommendInfoList();
        recommendAdapter = new RecommendAdapter(getActivity(), commodityInfoList);
        rv_recommend.setAdapter(recommendAdapter);
        recommendAdapter.setOnItemClickListener(this);
        List<String> bannerImageList = firstPageManager.getBannerImageList();
        bn_banner.setImages(bannerImageList);
        bn_banner.start();
    }

    private void initView() {
        fl_search = fragment_firstPage.findViewById(R.id.fl_search);
        fl_search.bringToFront();
        bn_banner = fragment_firstPage.findViewById(R.id.bn_banner);
        rv_recommend = fragment_firstPage.findViewById(R.id.rv_recommend);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_recommend.setLayoutManager(gridLayoutManager);

        msv_first_page = fragment_firstPage.findViewById(R.id.msv_first_page);
        msv_first_page.setOnScrollChangedListener(this);

        countdownView = fragment_firstPage.findViewById(R.id.countdownView);
        setCountDown();
        tv_search = fragment_firstPage.findViewById(R.id.tv_search);
        rl_search = fragment_firstPage.findViewById(R.id.rl_search);
        ll_clothes = fragment_firstPage.findViewById(R.id.ll_clothes);
        ll_bags = fragment_firstPage.findViewById(R.id.ll_bags);
        ll_household = fragment_firstPage.findViewById(R.id.ll_household);
        ll_technology = fragment_firstPage.findViewById(R.id.ll_technology);
        ll_life = fragment_firstPage.findViewById(R.id.ll_life);
        ll_mother_and_baby = fragment_firstPage.findViewById(R.id.ll_mother_and_baby);
        ll_beauty_make_up = fragment_firstPage.findViewById(R.id.ll_beauty_make_up);
        ll_tide_card = fragment_firstPage.findViewById(R.id.ll_tide_card);
        tv_clothes = fragment_firstPage.findViewById(R.id.tv_clothes);
        tv_bags = fragment_firstPage.findViewById(R.id.tv_bags);
        tv_household = fragment_firstPage.findViewById(R.id.tv_household);
        tv_technology = fragment_firstPage.findViewById(R.id.tv_technology);
        tv_life = fragment_firstPage.findViewById(R.id.tv_life);
        tv_mother_and_baby = fragment_firstPage.findViewById(R.id.tv_mother_and_baby);
        tv_beauty_make_up = fragment_firstPage.findViewById(R.id.tv_beauty_make_up);
        tv_tide_card = fragment_firstPage.findViewById(R.id.tv_tide_card);
        iv_clothes = fragment_firstPage.findViewById(R.id.iv_clothes);
        iv_bags = fragment_firstPage.findViewById(R.id.iv_bags);
        iv_household = fragment_firstPage.findViewById(R.id.iv_household);
        iv_technology = fragment_firstPage.findViewById(R.id.iv_technology);
        iv_life = fragment_firstPage.findViewById(R.id.iv_life);
        iv_mother_and_baby = fragment_firstPage.findViewById(R.id.iv_mother_and_baby);
        iv_beauty_make_up = fragment_firstPage.findViewById(R.id.iv_beauty_make_up);
        iv_tide_card = fragment_firstPage.findViewById(R.id.iv_tide_card);
        bn_banner.setImageLoader(new GlideImageLoader());
        bn_banner.isAutoPlay(true);
        bn_banner.setDelayTime(5000);
        bn_banner.setIndicatorGravity(BannerConfig.CENTER);
        iv_scan = fragment_firstPage.findViewById(R.id.iv_scan);
    }

    private void setCountDown() {
        countdownView.setCountTime(300)
                .setHourTvBackgroundRes(R.drawable.shape_white_card)
                .setHourTvTextColorHex("#FF0000")
                .setHourTvTextSize(18)

                .setHourColonTvBackgroundColorHex("#00FFFFFF")
                .setHourColonTvSize(18, 0)
                .setHourColonTvTextColorHex("#FF7198")
                .setHourColonTvTextSize(18)

                .setMinuteTvBackgroundRes(R.drawable.shape_white_card)
                .setMinuteTvTextColorHex("#FF0000")
                .setMinuteTvTextSize(18)

                .setMinuteColonTvSize(18, 0)
                .setMinuteColonTvTextColorHex("#FF7198")
                .setMinuteColonTvTextSize(18)

                .setSecondTvBackgroundRes(R.drawable.shape_white_card)
                .setSecondTvTextColorHex("#FF0000")
                .setSecondTvTextSize(18)

                // 开启倒计时
                .startCountDown()

                // 设置倒计时结束监听
                .setCountDownEndListener(new CountDownView.CountDownEndListener() {
                    @Override
                    public void onCountDownEnd() {
                        setCountDown();
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        commodityInfoList = firstPageManager.getCommodityRecommendInfoList();
        recommendAdapter = new RecommendAdapter(getActivity(), commodityInfoList);
        rv_recommend.setAdapter(recommendAdapter);
        recommendAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (oldy <= 45) {
            fl_search.setBackgroundColor(Color.TRANSPARENT);
        } else {
            fl_search.setBackgroundColor(Color.WHITE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_scan:
                Toast.makeText(getActivity(), "此功能目前尚未完成", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_search:
            case R.id.rl_search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.ll_clothes:
            case R.id.tv_clothes:
            case R.id.iv_clothes:
                Intent clothes = new Intent(getActivity(), TypeRecommendActivity.class);
                clothes.putExtra("title", tv_clothes.getText().toString());
                clothes.putExtra("type", 0);
                getActivity().startActivity(clothes);
                break;
            case R.id.ll_bags:
            case R.id.tv_bags:
            case R.id.iv_bags:
                Intent bags = new Intent(getActivity(), TypeRecommendActivity.class);
                bags.putExtra("title", tv_bags.getText().toString());
                bags.putExtra("type", 3);
                getActivity().startActivity(bags);
                break;
            case R.id.ll_household:
            case R.id.tv_household:
            case R.id.iv_household:
                Intent household = new Intent(getActivity(), TypeRecommendActivity.class);
                household.putExtra("title", tv_household.getText().toString());
                household.putExtra("type", 5);
                getActivity().startActivity(household);
                break;
            case R.id.ll_technology:
            case R.id.tv_technology:
            case R.id.iv_technology:
                Intent technology = new Intent(getActivity(), TypeRecommendActivity.class);
                technology.putExtra("title", tv_technology.getText().toString());
                technology.putExtra("type", 6);
                getActivity().startActivity(technology);
                break;
            case R.id.ll_life:
            case R.id.tv_life:
            case R.id.iv_life:
                Intent life = new Intent(getActivity(), TypeRecommendActivity.class);
                life.putExtra("title", tv_life.getText().toString());
                life.putExtra("type",4);
                getActivity().startActivity(life);
                break;
            case R.id.ll_mother_and_baby:
            case R.id.tv_mother_and_baby:
            case R.id.iv_mother_and_baby:
                Intent mother_and_baby = new Intent(getActivity(), TypeRecommendActivity.class);
                mother_and_baby.putExtra("title", tv_mother_and_baby.getText().toString());
                mother_and_baby.putExtra("type",7);
                getActivity().startActivity(mother_and_baby);
                break;
            case R.id.ll_beauty_make_up:
            case R.id.tv_beauty_make_up:
            case R.id.iv_beauty_make_up:
                Intent beauty_make_up = new Intent(getActivity(), TypeRecommendActivity.class);
                beauty_make_up.putExtra("title", tv_beauty_make_up.getText().toString());
                beauty_make_up.putExtra("type",8);
                getActivity().startActivity(beauty_make_up);
                break;
            case R.id.ll_tide_card:
            case R.id.tv_tide_card:
            case R.id.iv_tide_card:
                Intent tide_card = new Intent(getActivity(), TypeRecommendActivity.class);
                tide_card.putExtra("title", tv_tide_card.getText().toString());
                tide_card.putExtra("type",9);
                getActivity().startActivity(tide_card);
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), CommodityDetailsActivity.class);
        intent.putExtra("commodityInfo", commodityInfoList.get(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view) {

    }

}
