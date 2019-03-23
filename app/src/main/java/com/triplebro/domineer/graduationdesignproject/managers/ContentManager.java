package com.triplebro.domineer.graduationdesignproject.managers;

import android.content.Context;
import com.triplebro.domineer.graduationdesignproject.beans.CommodityInfo;
import com.triplebro.domineer.graduationdesignproject.sourceop.DatabaseOP;
import java.util.List;

public class ContentManager {

    private Context context;

    public ContentManager(Context context) {
        this.context = context;
    }

    public List<CommodityInfo> getCommodityInfoList(int type_concrete_id) {

        DatabaseOP databaseOP = new DatabaseOP(context);
        List<CommodityInfo> commodityInfoList = databaseOP.getCommodityInfoList(type_concrete_id);
        return commodityInfoList;
    }
}
