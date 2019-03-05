package com.triplebro.domineer.graduationdesignproject.managers;

import android.content.Context;

import com.triplebro.domineer.graduationdesignproject.beans.CommodityInfo;
import com.triplebro.domineer.graduationdesignproject.sourceop.DatabaseOP;

import java.util.List;

public class TypeRecommendManager {

    private Context context;
    private List<CommodityInfo> typeRecommendCommodityList;

    public TypeRecommendManager(Context context) {
        this.context = context;
    }

    public List<CommodityInfo> getCommodityList(int type_id){
        DatabaseOP databaseOP = new DatabaseOP(context);
        typeRecommendCommodityList = databaseOP.getTypeRecommendCommodityList(type_id);
        return typeRecommendCommodityList;
    }
}
