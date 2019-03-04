package com.triplebro.domineer.graduationdesignproject.sourceop;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.triplebro.domineer.graduationdesignproject.activities.CollectionCommodityActivity;
import com.triplebro.domineer.graduationdesignproject.beans.CollectionCommodityInfo;
import com.triplebro.domineer.graduationdesignproject.beans.CollectionSubmitInfo;
import com.triplebro.domineer.graduationdesignproject.beans.CommodityInfo;
import com.triplebro.domineer.graduationdesignproject.beans.SubmitInfo;
import com.triplebro.domineer.graduationdesignproject.database.MyOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseOP {

    private Context context;

    public DatabaseOP(Context context) {
        this.context = context;
    }

    public boolean getIsCollection(int commodity_id) {
        SharedPreferences userInfo = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String phone_number = userInfo.getString("phone_number", "");
        if (phone_number.length() == 0) {
            return false;
        }else{
            MyOpenHelper myOpenHelper = new MyOpenHelper(context);
            SQLiteDatabase db = myOpenHelper.getWritableDatabase();
            Cursor collectionCommodityInfo = db.query("collectionCommodityInfo", null, "commodity_id = ? and phone_number = ?", new String[]{String.valueOf(commodity_id), phone_number}, null, null, null);
            if(collectionCommodityInfo!=null&&collectionCommodityInfo.getCount()>0){
                collectionCommodityInfo.moveToNext();
                return true;
            }else{
                return false;
            }
        }
    }

    public boolean getIsCollectionSubmit(int submit_id) {
        SharedPreferences userInfo = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String phone_number = userInfo.getString("phone_number", "");
        if (phone_number.length() == 0) {
            return false;
        }else{
            MyOpenHelper myOpenHelper = new MyOpenHelper(context);
            SQLiteDatabase db = myOpenHelper.getWritableDatabase();
            Cursor collectionSubmitInfo = db.query("collectionSubmitInfo", null, "submit_id = ? and phone_number = ?", new String[]{String.valueOf(submit_id), phone_number}, null, null, null);
            if(collectionSubmitInfo!=null&&collectionSubmitInfo.getCount()>0){
                collectionSubmitInfo.moveToNext();
                return true;
            }else{
                return false;
            }
        }
    }

    public List<CollectionCommodityInfo> getCommodityCollectionInfoList(String phone_number){
        List<CollectionCommodityInfo> collectionCommodityInfoList = new ArrayList<>();
        if (phone_number.length() == 0) {
            return collectionCommodityInfoList;
        }else{
            MyOpenHelper myOpenHelper = new MyOpenHelper(context);
            SQLiteDatabase db = myOpenHelper.getWritableDatabase();
            Cursor collectionCommodityInfoCursor = db.query("collectionCommodityInfo", null, "phone_number = ?", new String[]{phone_number}, null, null, null);
            if(collectionCommodityInfoCursor!=null&&collectionCommodityInfoCursor.getCount()>0){
                while (collectionCommodityInfoCursor.moveToNext()){
                    CollectionCommodityInfo collectionCommodityInfo = new CollectionCommodityInfo();
                    collectionCommodityInfo.setPhone_number(phone_number);
                    collectionCommodityInfo.setCommodity_id(collectionCommodityInfoCursor.getInt(2));
                    collectionCommodityInfoList.add(collectionCommodityInfo);
                }
                collectionCommodityInfoCursor.close();
                db.close();
                return collectionCommodityInfoList;
            }else{
                if (collectionCommodityInfoCursor != null) {
                    collectionCommodityInfoCursor.close();
                }
                db.close();
                return collectionCommodityInfoList;
            }
        }
    }

    public List<CollectionSubmitInfo> getSubmitCollectionInfoList(String phone_number){
        List<CollectionSubmitInfo> collectionSubmitInfoList = new ArrayList<>();
        if (phone_number.length() == 0) {
            return collectionSubmitInfoList;
        }else{
            MyOpenHelper myOpenHelper = new MyOpenHelper(context);
            SQLiteDatabase db = myOpenHelper.getWritableDatabase();
            Cursor collectionSubmitInfoCursor = db.query("collectionSubmitInfo", null, "phone_number = ?", new String[]{phone_number}, null, null, null);
            if(collectionSubmitInfoCursor!=null&&collectionSubmitInfoCursor.getCount()>0){
                while (collectionSubmitInfoCursor.moveToNext()){
                    CollectionSubmitInfo collectionSubmitInfo = new CollectionSubmitInfo();
                    collectionSubmitInfo.setPhone_number(phone_number);
                    collectionSubmitInfo.setSubmit_id(collectionSubmitInfoCursor.getInt(2));
                    collectionSubmitInfoList.add(collectionSubmitInfo);
                }
                collectionSubmitInfoCursor.close();
                db.close();
                return collectionSubmitInfoList;
            }else{
                if (collectionSubmitInfoCursor != null) {
                    collectionSubmitInfoCursor.close();
                }
                db.close();
                return collectionSubmitInfoList;
            }
        }
    }

    public List<CommodityInfo> getCollectedCommodityInfoList(List<CollectionCommodityInfo> collectionInfoList) {
        List<CommodityInfo> commodityInfoList = new ArrayList<>();
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        for (CollectionCommodityInfo collectionCommodityInfo : collectionInfoList) {
            int commodity_id = collectionCommodityInfo.getCommodity_id();
            Cursor commodityInfoCursor = db.query("commodityInfo", null, "commodity_id = ?", new String[]{String.valueOf(commodity_id)}, null, null, null);
            if(commodityInfoCursor!=null&&commodityInfoCursor.getCount()>0){
                commodityInfoCursor.moveToNext();
                CommodityInfo commodityInfo = new CommodityInfo();
                commodityInfo.setCommodity_id(commodityInfoCursor.getInt(0));
                commodityInfo.setCommodity_name(commodityInfoCursor.getString(1));
                commodityInfo.setPrice(commodityInfoCursor.getInt(2));
                commodityInfo.setCommodity_image(commodityInfoCursor.getString(3));
                commodityInfo.setType_generalize_id(commodityInfoCursor.getInt(4));
                commodityInfo.setType_concrete_id(commodityInfoCursor.getInt(5));
                commodityInfo.setPhone_number(commodityInfoCursor.getString(6));
                commodityInfoList.add(commodityInfo);
                commodityInfoCursor.close();
            }else{
                if (commodityInfoCursor != null) {
                    commodityInfoCursor.close();
                }
            }
        }
        db.close();
        return commodityInfoList;
    }

    public List<SubmitInfo> getCollectedSubmitInfoList(List<CollectionSubmitInfo> collectionInfoList) {
        List<SubmitInfo> submitInfoList = new ArrayList<>();
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        for (CollectionSubmitInfo collectionSubmitInfo : collectionInfoList) {
            int submit_id = collectionSubmitInfo.getSubmit_id();
            Cursor submitInfoCursor = db.query("submitInfo", null, "submit_id = ?", new String[]{String.valueOf(submit_id)}, null, null, null);
            if(submitInfoCursor!=null&&submitInfoCursor.getCount()>0){
                submitInfoCursor.moveToNext();
                SubmitInfo submitInfo = new SubmitInfo();
                submitInfo.setSubmit_id(submitInfoCursor.getInt(0));
                submitInfo.setPhone_number(submitInfoCursor.getString(1));
                submitInfo.setNickname(submitInfoCursor.getString(2));
                submitInfo.setUser_head(submitInfoCursor.getString(3));
                submitInfo.setSubmit_content(submitInfoCursor.getString(4));
                submitInfoList.add(submitInfo);
                submitInfoCursor.close();
            }else{
                if (submitInfoCursor != null) {
                    submitInfoCursor.close();
                }
            }
        }
        db.close();
        return submitInfoList;
    }

    public List<CommodityInfo> getCommodityRecommendInfoList() {
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

    public List<CommodityInfo> getSearchInfoList(String searchKey) {
        List<CommodityInfo> commodityInfoList = new ArrayList<>();
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        Cursor commodityInfoCursor = db.query("commodityInfo", new String[]{"commodity_id","commodity_name","price","commodity_image"}, "commodity_name like ?", new String[]{"%"+searchKey+"%"}, null, null, null);
        if (commodityInfoCursor != null && commodityInfoCursor.getCount() > 0) {
            while (commodityInfoCursor.moveToNext()) {
                CommodityInfo commodityInfo = new CommodityInfo();
                commodityInfo.setCommodity_id(commodityInfoCursor.getInt(0));
                commodityInfo.setCommodity_name(commodityInfoCursor.getString(1));
                commodityInfo.setPrice(commodityInfoCursor.getInt(2));
                commodityInfo.setCommodity_image(commodityInfoCursor.getString(3));
                commodityInfoList.add(commodityInfo);
            }
        }
        if (commodityInfoCursor != null) {
            commodityInfoCursor.close();
        }
        db.close();
        return commodityInfoList;
    }
}
