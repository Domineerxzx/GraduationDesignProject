package com.triplebro.domineer.graduationdesignproject.managers;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.triplebro.domineer.graduationdesignproject.beans.ShoppingCartInfo;
import com.triplebro.domineer.graduationdesignproject.beans.TypeConcreteInfo;
import com.triplebro.domineer.graduationdesignproject.database.MyOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartManager {

    private Context context;

    public ShoppingCartManager(Context context) {
        this.context = context;
    }

    public List<ShoppingCartInfo> getShoppingCartInfoList() {
        String phone_number = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString("phone_number", "");
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        List<ShoppingCartInfo> shoppingCartInfoList = new ArrayList<>();
        Cursor shoppingCartInfoCursor = db.query("shoppingCartInfo", null,
                "phone_number = ?", new String[]{phone_number}, null, null, null);
        if (shoppingCartInfoCursor != null && shoppingCartInfoCursor.getCount() > 0) {
            while (shoppingCartInfoCursor.moveToNext()) {
                ShoppingCartInfo shoppingCartInfo = new ShoppingCartInfo();
                shoppingCartInfo.setCommodity_id(shoppingCartInfoCursor.getInt(1));
                shoppingCartInfo.setSize_name(shoppingCartInfoCursor.getString(2));
                shoppingCartInfo.setCount(shoppingCartInfoCursor.getInt(3));
                shoppingCartInfo.setCommodity_name(shoppingCartInfoCursor.getString(4));
                shoppingCartInfo.setCommodity_image(shoppingCartInfoCursor.getString(6));
                shoppingCartInfo.setPrice(shoppingCartInfoCursor.getInt(7));
                shoppingCartInfoList.add(shoppingCartInfo);
            }
        }
        if (shoppingCartInfoCursor != null) {
            shoppingCartInfoCursor.close();
        }
        db.close();
        return shoppingCartInfoList;
    }

    public long sumShoppingCart() {
        String phone_number = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString("phone_number", "");
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        long sum = 0;
        List<ShoppingCartInfo> shoppingCartInfoList = new ArrayList<>();
        Cursor shoppingCartInfoCursor = db.query("shoppingCartInfo", new String[]{"count", "price"},
                "phone_number = ?", new String[]{phone_number}, null, null, null);
        if (shoppingCartInfoCursor != null && shoppingCartInfoCursor.getCount() > 0) {
            while (shoppingCartInfoCursor.moveToNext()) {
                sum += shoppingCartInfoCursor.getInt(0) * shoppingCartInfoCursor.getInt(1);
            }
        }
        if (shoppingCartInfoCursor != null) {
            shoppingCartInfoCursor.close();
        }
        db.close();
        return sum;
    }
}
