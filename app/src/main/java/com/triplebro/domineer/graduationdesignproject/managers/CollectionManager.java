package com.triplebro.domineer.graduationdesignproject.managers;

import android.content.Context;

import com.triplebro.domineer.graduationdesignproject.beans.CollectionCommodityInfo;
import com.triplebro.domineer.graduationdesignproject.beans.CollectionSubmitInfo;
import com.triplebro.domineer.graduationdesignproject.beans.CommodityInfo;
import com.triplebro.domineer.graduationdesignproject.beans.SubmitInfo;
import com.triplebro.domineer.graduationdesignproject.sourceop.DatabaseOP;

import java.util.List;

public class CollectionManager {

    private Context context;

    public CollectionManager(Context context) {
        this.context = context;
    }

    public List<CommodityInfo> getCollectedCommodityInfoList(String phone_number) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        List<CollectionCommodityInfo> collectionInfoList = databaseOP.getCommodityCollectionInfoList(phone_number);
        List<CommodityInfo> collectedCommodityInfoList = databaseOP.getCollectedCommodityInfoList(collectionInfoList);
        return collectedCommodityInfoList;
    }

    public List<SubmitInfo> getCollectionSubmitInfoList(String phone_number) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        List<CollectionSubmitInfo> collectionInfoList = databaseOP.getSubmitCollectionInfoList(phone_number);
        List<SubmitInfo> collectedSubmitInfoList = databaseOP.getCollectedSubmitInfoList(collectionInfoList);
        return collectedSubmitInfoList;
    }
}
