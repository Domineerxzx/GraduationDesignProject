package com.triplebro.domineer.graduationdesignproject.managers;

import android.content.ContentValues;
import android.content.Context;

import com.triplebro.domineer.graduationdesignproject.beans.CommoditySizeInfo;
import com.triplebro.domineer.graduationdesignproject.beans.TypeConcreteInfo;
import com.triplebro.domineer.graduationdesignproject.beans.TypeGeneralizeInfo;
import com.triplebro.domineer.graduationdesignproject.sourceop.DatabaseOP;

import java.util.List;

public class ChangeCommodityManager {

    private Context context;

    public ChangeCommodityManager(Context context) {
        this.context = context;
    }

    public List<TypeGeneralizeInfo> getCommodityGeneralizeType() {
        DatabaseOP databaseOP = new DatabaseOP(context);
        List<TypeGeneralizeInfo> commodityGeneralizeType = databaseOP.getCommodityGeneralizeType();
        return commodityGeneralizeType;
    }

    public List<TypeConcreteInfo> getCommodityConcreteType(int type_generalize_id) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        List<TypeConcreteInfo> commodityConcreteType = databaseOP.getCommodityConcreteType(type_generalize_id);
        return commodityConcreteType;
    }

    public TypeGeneralizeInfo getCommodityGeneralizeTypeInfo(int type_generalize_id) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        TypeGeneralizeInfo commodityGeneralizeType = databaseOP.getCommodityGeneralizeTypeInfo(type_generalize_id);
        return commodityGeneralizeType;
    }

    public TypeConcreteInfo getCommodityConcreteTypeInfo(int type_concrete_id) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        TypeConcreteInfo commodityConcreteType = databaseOP.getCommodityConcreteTypeInfo(type_concrete_id);
        return commodityConcreteType;
    }

    public int addCommodityInfo(ContentValues commodityInfo) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        int commodity_id = databaseOP.addCommodityInfo(commodityInfo);
        return commodity_id;
    }

    public void addCommoditySizeInfo(int commodity_id, List<CommoditySizeInfo> commoditySizeInfoList, String phone_number) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        for (CommoditySizeInfo commoditySizeInfo : commoditySizeInfoList) {
            ContentValues sizeInfo = new ContentValues();
            sizeInfo.put("commodity_id",commodity_id);
            sizeInfo.put("size_name",commoditySizeInfo.getSize_name());
            sizeInfo.put("size_count",commoditySizeInfo.getSize_count());
            sizeInfo.put("phone_number",phone_number);
            databaseOP.addCommoditySizeInfo(sizeInfo);
        }
    }

    public void addCommodityImageInfo(int commodity_id, List<String> commodityImageList, String phone_number) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        if(commodityImageList.get(commodityImageList.size()-1).length() == 0){
            commodityImageList.remove(commodityImageList.size()-1);
        }
        for (String commodity_image : commodityImageList) {
            ContentValues imageInfo = new ContentValues();
            imageInfo.put("commodity_id",commodity_id);
            imageInfo.put("commodity_image",commodity_image);
            imageInfo.put("phone_number",phone_number);
            databaseOP.addCommodityImageInfo(imageInfo);
        }
    }

    public List<String> getCommodityImageList(int commodity_id) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        List<String> commodityImageList = databaseOP.getCommodityImageList(commodity_id);
        return commodityImageList;
    }

    public List<CommoditySizeInfo> getCommoditySizeInfoList(int commodity_id) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        List<CommoditySizeInfo> commoditySizeInfoList = databaseOP.getCommoditySizeInfoList(commodity_id);
        return commoditySizeInfoList;
    }

    public void deleteCommodity(int commodity_id) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        databaseOP.deleteCommodity(commodity_id);
    }
}
