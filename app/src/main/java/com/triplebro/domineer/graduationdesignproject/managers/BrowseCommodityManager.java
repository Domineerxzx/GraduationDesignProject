package com.triplebro.domineer.graduationdesignproject.managers;

import android.content.Context;

import com.triplebro.domineer.graduationdesignproject.beans.CommodityInfo;
import com.triplebro.domineer.graduationdesignproject.sourceop.DatabaseOP;

import java.util.List;


public class BrowseCommodityManager {

    private Context context;

    public BrowseCommodityManager(Context context) {
        this.context = context;
    }

    public List<CommodityInfo> getCommodityInfoList(String phone_number) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        List<CommodityInfo> commodityInfoList = databaseOP.getCommodityInfoList(phone_number);
        return commodityInfoList;
    }
}
