package com.triplebro.domineer.graduationdesignproject.managers;

import android.content.Context;
import com.triplebro.domineer.graduationdesignproject.beans.CommodityInfo;
import com.triplebro.domineer.graduationdesignproject.sourceop.DatabaseOP;
import java.util.List;

public class FirstPageManager {

    private Context context;

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

    public List<CommodityInfo> getFunctionCommodityList() {

        return null;
    }
}
