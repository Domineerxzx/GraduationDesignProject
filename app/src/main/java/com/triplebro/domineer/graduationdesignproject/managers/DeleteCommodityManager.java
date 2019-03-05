package com.triplebro.domineer.graduationdesignproject.managers;

import android.content.ContentValues;
import android.content.Context;

import com.triplebro.domineer.graduationdesignproject.beans.CommodityInfo;
import com.triplebro.domineer.graduationdesignproject.sourceop.DatabaseOP;

import java.util.List;

public class DeleteCommodityManager {

    private Context context;

    public DeleteCommodityManager(Context context) {
        this.context = context;
    }

    public List<CommodityInfo> getCommodityInfoList(String phone_number) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        List<CommodityInfo> commodityInfoList = databaseOP.getCommodityInfoList(phone_number);
        return commodityInfoList;
    }

    public void deleteCommodity(int commodity_id) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        databaseOP.deleteCommodity(commodity_id);
    }
}
