package com.triplebro.domineer.graduationdesignproject.managers;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.triplebro.domineer.graduationdesignproject.beans.CommodityInfo;
import com.triplebro.domineer.graduationdesignproject.database.MyOpenHelper;

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

    public List<CommodityInfo> getCommodityInfoList() {
        List<CommodityInfo> commodityInfoList = new ArrayList<>();
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        Cursor commodityRecommendInfoCursor = db.query("commodityRecommendInfo", null, null, null, null, null, null);
        if (commodityRecommendInfoCursor != null && commodityRecommendInfoCursor.getCount() > 0) {
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

    public boolean addCommodityCollection(int commodity_id) {

        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        SharedPreferences userInfo = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String phone_number = userInfo.getString("phone_number", "");
        if (phone_number == null || phone_number.length() == 0) {
            Toast.makeText(context, "还没登录呢，不能收藏商品", Toast.LENGTH_SHORT).show();
            db.close();
            return false;
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("phone_number", phone_number);
            contentValues.put("commodity_id", commodity_id);
            long collectionCommodityInfo = db.insert("collectionCommodityInfo", null, contentValues);
            if (collectionCommodityInfo >= 0) {
                Toast.makeText(context, "添加收藏成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "添加收藏失败", Toast.LENGTH_SHORT).show();
            }
            db.close();
            return true;
        }
    }


    public boolean deleteCommodityCollection(int commodity_id) {

        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        SharedPreferences userInfo = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String phone_number = userInfo.getString("phone_number", "");
        int collectionCommodityInfo = db.delete("collectionCommodityInfo", "phone_number = ? and commodity_id = ?", new String[]{phone_number, String.valueOf(commodity_id)});
        if (collectionCommodityInfo >= 0) {
            Toast.makeText(context, "取消收藏成功", Toast.LENGTH_SHORT).show();
            db.close();
            return true;
        } else {
            Toast.makeText(context, "取消收藏失败", Toast.LENGTH_SHORT).show();
            db.close();
            return false;
        }
    }
}
