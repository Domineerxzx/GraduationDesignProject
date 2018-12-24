package com.triplebro.domineer.graduationdesignproject.managers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.triplebro.domineer.graduationdesignproject.activities.ContentActivity;
import com.triplebro.domineer.graduationdesignproject.beans.CommodityInfo;
import com.triplebro.domineer.graduationdesignproject.beans.TypeGeneralizeInfo;
import com.triplebro.domineer.graduationdesignproject.database.MyOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ContentManager {

    private Context context;

    public ContentManager(Context context) {
        this.context = context;
    }

    public List<CommodityInfo> getCommodityInfoList(int type_concrete_id) {
        List<CommodityInfo> commodityInfoList = new ArrayList<>();
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        Cursor commodityInfoCursor = db.query("commodityInfo", null, "type_concrete_id = ?", new String[]{String.valueOf(type_concrete_id)}, null, null, null);
        if(commodityInfoCursor != null && commodityInfoCursor.getCount() > 0){
            while (commodityInfoCursor.moveToNext()) {
                CommodityInfo commodityInfo = new CommodityInfo();
                commodityInfo.setCommodity_id(commodityInfoCursor.getInt(0));
                commodityInfo.setCommodity_name(commodityInfoCursor.getString(1));
                commodityInfo.setPrice(commodityInfoCursor.getInt(2));
                commodityInfo.setCommodity_image(commodityInfoCursor.getString(3));
                commodityInfo.setType_generalize_id(commodityInfoCursor.getInt(4));
                commodityInfo.setType_concrete_id(commodityInfoCursor.getInt(5));
                commodityInfo.setPhone_number(commodityInfoCursor.getString(6));
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
