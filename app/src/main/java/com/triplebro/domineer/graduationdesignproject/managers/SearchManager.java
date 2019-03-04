package com.triplebro.domineer.graduationdesignproject.managers;

import android.content.Context;

import com.triplebro.domineer.graduationdesignproject.beans.CommodityInfo;
import com.triplebro.domineer.graduationdesignproject.sourceop.DatabaseOP;

import java.util.List;


public class SearchManager {

    private Context context;

    public SearchManager(Context context) {
        this.context = context;
    }

    public List<CommodityInfo> getCommodityRecommendInfoList() {
        DatabaseOP databaseOP = new DatabaseOP(context);
        List<CommodityInfo> commodityRecommendInfoList = databaseOP.getCommodityRecommendInfoList();
        return commodityRecommendInfoList;
    }

    public List<CommodityInfo> searchInfoList(String searchKey) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        List<CommodityInfo> searchInfoList = databaseOP.getSearchInfoList(searchKey);
        return searchInfoList;
    }
}
