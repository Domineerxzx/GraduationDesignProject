package com.triplebro.domineer.graduationdesignproject.managers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.triplebro.domineer.graduationdesignproject.beans.CommodityInfo;
import com.triplebro.domineer.graduationdesignproject.service.NetworkConnectionService;
import com.triplebro.domineer.graduationdesignproject.sourceop.DatabaseOP;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import cc.ibooker.zcountdownviewlib.CountDownView;

public class FirstPageManager implements ServiceConnection {

    private Context context;
    private CountDownView countDownView;

    public FirstPageManager(Context context) {
        this.context = context;
    }

    public List<String> getBannerImageList() {

        List<String> bannerImageList;
        DatabaseOP databaseOP = new DatabaseOP(context);
        bannerImageList = databaseOP.getBannerImageList();
        return bannerImageList;
    }

    public List<CommodityInfo> getCommodityRecommendInfoList() {
        DatabaseOP databaseOP = new DatabaseOP(context);
        List<CommodityInfo> commodityRecommendInfoList = databaseOP.getCommodityRecommendInfoList();
        return commodityRecommendInfoList;
    }

    public boolean addCommodityCollection(int commodity_id) {

        DatabaseOP databaseOP = new DatabaseOP(context);
        boolean addCommodityCollection = databaseOP.addCommodityCollection(commodity_id);
        return addCommodityCollection;
    }


    public boolean deleteCommodityCollection(int commodity_id) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        boolean deleteCommodityCollection = databaseOP.deleteCommodityCollection(commodity_id);
        return deleteCommodityCollection;
    }

    public List<CommodityInfo> getFunctionCommodityList(List<CommodityInfo> commodityInfoList) {
        List<CommodityInfo> functionCommodityList = new ArrayList<>();
        if(commodityInfoList.size() < 9){
            return functionCommodityList;
        }
        HashSet<Integer> integerHashSet = new HashSet<Integer>();
        Random random=new Random();
        while (functionCommodityList.size() != 9) {
            int randomInt=random.nextInt(commodityInfoList.size());
            if (!integerHashSet.contains(randomInt)) {
                integerHashSet.add(randomInt);
                functionCommodityList.add(commodityInfoList.get(randomInt));
            }else {
                System.out.println("该商品已经被添加,不能重复添加");
            }
        }
        return functionCommodityList;
    }

    public void setCountDown(CountDownView countdownView) {
        if (this.countDownView == null){
            this.countDownView = countdownView;
            Intent service = new Intent(context, NetworkConnectionService.class);
            context.bindService(service, this, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        NetworkConnectionService.MyBinder myBinder = (NetworkConnectionService.MyBinder) service;
        myBinder.setCountDown(countDownView);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
