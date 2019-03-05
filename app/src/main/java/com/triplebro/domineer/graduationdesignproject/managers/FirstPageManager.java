package com.triplebro.domineer.graduationdesignproject.managers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.triplebro.domineer.graduationdesignproject.beans.CommodityInfo;
import com.triplebro.domineer.graduationdesignproject.database.MyOpenHelper;
import com.triplebro.domineer.graduationdesignproject.sourceop.DatabaseOP;

import java.util.ArrayList;
import java.util.List;

public class FirstPageManager {

    private Context context;

    public FirstPageManager(Context context) {
        this.context = context;
    }

    public List<String> getBannerImageList() {
        List<String> bannerImageList = new ArrayList<>();
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        Cursor commodityRecommendInfo = db.query("commodityRecommendInfo",
                new String[]{"recommend_image"}, null, null,
                null, null, null);
        if (commodityRecommendInfo != null && commodityRecommendInfo.getCount() > 0) {
            while (commodityRecommendInfo.moveToNext()) {
                String recommendImage = commodityRecommendInfo.getString(0);
                bannerImageList.add(recommendImage);
                if (bannerImageList.size() == 5) {
                    break;
                }
            }
        }
        if (commodityRecommendInfo != null) {
            commodityRecommendInfo.close();
        }
        db.close();
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

    public List<CommodityInfo> getFunctionCommodityList(){

        return null;
    }
}
