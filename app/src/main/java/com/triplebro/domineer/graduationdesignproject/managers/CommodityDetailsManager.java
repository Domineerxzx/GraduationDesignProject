package com.triplebro.domineer.graduationdesignproject.managers;

import android.content.Context;
import com.triplebro.domineer.graduationdesignproject.beans.CommodityInfo;
import com.triplebro.domineer.graduationdesignproject.beans.CommoditySizeInfo;
import com.triplebro.domineer.graduationdesignproject.sourceop.DatabaseOP;
import java.util.List;

public class CommodityDetailsManager {

    private Context context;

    public CommodityDetailsManager(Context context) {
        this.context = context;
    }

    public List<CommodityInfo> getCommodityInfoList() {
        List<CommodityInfo> commodityInfoList;
        DatabaseOP databaseOP = new DatabaseOP(context);
        commodityInfoList = databaseOP.getCommodityInfoList();
        return commodityInfoList;
    }

    public List<String> getCommodityImagePathList(int commodity_id) {
        List<String> commodityImagePathList;
        DatabaseOP databaseOP = new DatabaseOP(context);
        commodityImagePathList = databaseOP.getCommodityImageList(commodity_id);
        return commodityImagePathList;
    }

    public List<CommoditySizeInfo> getCommoditySizeInfoList(int commodity_id) {
        List<CommoditySizeInfo> commoditySizeInfoList;
        DatabaseOP databaseOP = new DatabaseOP(context);
        commoditySizeInfoList = databaseOP.getCommoditySizeInfoList(commodity_id);
        return commoditySizeInfoList;
    }
}
