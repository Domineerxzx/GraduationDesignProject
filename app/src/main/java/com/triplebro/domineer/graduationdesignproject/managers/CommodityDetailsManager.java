package com.triplebro.domineer.graduationdesignproject.managers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.triplebro.domineer.graduationdesignproject.beans.CommodityInfo;
import com.triplebro.domineer.graduationdesignproject.beans.CommodityRecommendInfo;
import com.triplebro.domineer.graduationdesignproject.beans.CommoditySizeInfo;
import com.triplebro.domineer.graduationdesignproject.database.MyOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CommodityDetailsManager {

    private Context context;

    public CommodityDetailsManager(Context context) {
        this.context = context;
    }

    public List<CommodityInfo> getCommodityInfoList() {
        List<CommodityInfo> commodityInfoList = new ArrayList<>();
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        Cursor commodityRecommendInfoCursor = db.query("commodityRecommendInfo", null, null, null, null, null, null);
        if(commodityRecommendInfoCursor != null && commodityRecommendInfoCursor.getCount() > 0){
            while (commodityRecommendInfoCursor.moveToNext()) {
                CommodityInfo commodityInfo = new CommodityInfo();
                commodityInfo.setCommodity_id(commodityRecommendInfoCursor.getInt(1));
                commodityInfo.setCommodity_name(commodityRecommendInfoCursor.getString(2));
                commodityInfo.setPrice(commodityRecommendInfoCursor.getInt(3));
                commodityInfo.setCommodity_image(commodityRecommendInfoCursor.getString(4));
                commodityInfoList.add(commodityInfo);
            }
        }
        if (commodityRecommendInfoCursor != null) {
            commodityRecommendInfoCursor.close();
        }
        db.close();

        return commodityInfoList;
    }

    public List<String> getCommodityImagePathList(int commodity_id) {
        List<String> commodityImagePathList = new ArrayList<>();
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        Cursor commodityImagePathCursor = db.query("commodityImageInfo", new String[]{"commodity_image"}, "commodity_id = ?", new String[]{String.valueOf(commodity_id)}, null, null, null);
        if(commodityImagePathCursor != null && commodityImagePathCursor.getCount() > 0){
            while (commodityImagePathCursor.moveToNext()) {
                String commodityImagePath = commodityImagePathCursor.getString(0);
                commodityImagePathList.add(commodityImagePath);
            }
        }
        if (commodityImagePathCursor != null) {
            commodityImagePathCursor.close();
        }
        db.close();
        return commodityImagePathList;
    }

    public List<CommoditySizeInfo> getCommoditySizeInfoList(int commodity_id) {
        List<CommoditySizeInfo> commoditySizeInfoList = new ArrayList<>();
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        Cursor commoditySizeInfoCursor = db.query("commoditySizeInfo", null, "commodity_id = ?", new String[]{String.valueOf(commodity_id)}, null, null, null);
        if(commoditySizeInfoCursor != null && commoditySizeInfoCursor.getCount() > 0){
            while (commoditySizeInfoCursor.moveToNext()) {
                CommoditySizeInfo commoditySizeInfo = new CommoditySizeInfo();
                commoditySizeInfo.setCommodity_id(commoditySizeInfoCursor.getInt(1));
                commoditySizeInfo.setSize_name(commoditySizeInfoCursor.getString(2));
                commoditySizeInfo.setSize_count(commoditySizeInfoCursor.getInt(3));
                commoditySizeInfoList.add(commoditySizeInfo);
            }
        }
        if (commoditySizeInfoCursor != null) {
            commoditySizeInfoCursor.close();
        }
        db.close();
        return commoditySizeInfoList;
    }
}
