package com.triplebro.domineer.graduationdesignproject.managers;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;
import android.widget.Toast;

import com.triplebro.domineer.graduationdesignproject.beans.ShoppingCartInfo;
import com.triplebro.domineer.graduationdesignproject.beans.TypeConcreteInfo;
import com.triplebro.domineer.graduationdesignproject.database.MyOpenHelper;
import com.triplebro.domineer.graduationdesignproject.fragments.ShoppingCartFragment;
import com.triplebro.domineer.graduationdesignproject.properties.ProjectProperties;
import com.triplebro.domineer.graduationdesignproject.sourceop.DatabaseOP;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartManager {

    private Context context;
    private TextView tv_count_price;

    public ShoppingCartManager(Context context) {
        this.context = context;
    }

    public List<ShoppingCartInfo> getShoppingCartInfoList() {
        String phone_number = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString("phone_number", "");
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        List<ShoppingCartInfo> shoppingCartInfoList = new ArrayList<>();
        Cursor shoppingCartInfoCursor = db.query("shoppingCartInfo", null,
                "phone_number = ? and isCommit = ?", new String[]{phone_number, "0"}, null, null, null);
        if (shoppingCartInfoCursor != null && shoppingCartInfoCursor.getCount() > 0) {
            while (shoppingCartInfoCursor.moveToNext()) {
                ShoppingCartInfo shoppingCartInfo = new ShoppingCartInfo();
                shoppingCartInfo.set_id(shoppingCartInfoCursor.getInt(0));
                shoppingCartInfo.setCommodity_id(shoppingCartInfoCursor.getInt(1));
                shoppingCartInfo.setSize_name(shoppingCartInfoCursor.getString(2));
                shoppingCartInfo.setCount(shoppingCartInfoCursor.getInt(3));
                shoppingCartInfo.setCommodity_name(shoppingCartInfoCursor.getString(4));
                shoppingCartInfo.setCommodity_image(shoppingCartInfoCursor.getString(6));
                shoppingCartInfo.setPrice(shoppingCartInfoCursor.getInt(7));
                shoppingCartInfo.setIsCommit(shoppingCartInfoCursor.getInt(8));
                shoppingCartInfoList.add(shoppingCartInfo);
            }
        }
        if (shoppingCartInfoCursor != null) {
            shoppingCartInfoCursor.close();
        }
        db.close();
        return shoppingCartInfoList;
    }

    public void sumShoppingCart(TextView tv_count_price) {
        if (this.tv_count_price == null) {
            this.tv_count_price = tv_count_price;
        }
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
        this.tv_count_price.setText(String.valueOf(sum));
    }

    public void clearShoppingCart(List<ShoppingCartInfo> shoppingCartInfoList) {

        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        SharedPreferences userInfo = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String phone_number = userInfo.getString("phone_number", "");
        int clearShoppingCartInfo = db.delete("shoppingCartInfo", "phone_number = ? and ", new String[]{phone_number});
        if (clearShoppingCartInfo >= 0) {
            Toast.makeText(context, "清空购物车成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "清空购物车失败", Toast.LENGTH_SHORT).show();
        }
        db.close();
        sumShoppingCart(tv_count_price);
        for (ShoppingCartInfo shoppingCartInfo : shoppingCartInfoList) {
            replaceCommoditySizeInfo(shoppingCartInfo);
        }
    }

    public void deleteCommodity(int commodity_id, ShoppingCartInfo shoppingCartInfo) {
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        SharedPreferences userInfo = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String phone_number = userInfo.getString("phone_number", "");
        int deleteShoppingCartInfo = db.delete("shoppingCartInfo", "phone_number = ? and commodity_id = ?", new String[]{phone_number, String.valueOf(commodity_id)});
        if (deleteShoppingCartInfo >= 0) {
            Toast.makeText(context, "删除购物车商品成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "删除购物车商品失败", Toast.LENGTH_SHORT).show();
        }
        db.close();
        sumShoppingCart(tv_count_price);
        replaceCommoditySizeInfo(shoppingCartInfo);
    }

    private void replaceCommoditySizeInfo(ShoppingCartInfo shoppingCartInfo) {

        long size_count = 0;
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        Cursor commoditySizeInfo = db.query("commoditySizeInfo", new String[]{"size_count"}, "commodity_id = ? and size_name = ?", new String[]{String.valueOf(shoppingCartInfo.getCommodity_id()), shoppingCartInfo.getSize_name()}, null, null, null);
        if (commoditySizeInfo != null && commoditySizeInfo.getCount() > 0) {
            commoditySizeInfo.moveToNext();
            size_count = commoditySizeInfo.getLong(0);
        }
        if (commoditySizeInfo != null) {
            commoditySizeInfo.close();
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("size_count", size_count + shoppingCartInfo.getCount());
        db.update("commoditySizeInfo", contentValues, "commodity_id = ? and size_name = ?", new String[]{String.valueOf(shoppingCartInfo.getCommodity_id()), shoppingCartInfo.getSize_name()});
        db.close();
    }

    public long commitOrder() {
        String phone_number = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString("phone_number", "");
        ContentValues contentValues = new ContentValues();
        contentValues.put("order_state", ProjectProperties.ORDER_STATE_WAIT_PAY);
        contentValues.put("phone_number", phone_number);
        DatabaseOP databaseOP = new DatabaseOP(context);
        long order_id = databaseOP.commitOrder(contentValues);
        return order_id;
    }

    public void updateShoppingCartInfo(List<ShoppingCartInfo> shoppingCartInfoList) {
        DatabaseOP databaseOP = new DatabaseOP(context);
        for (ShoppingCartInfo shoppingCartInfo :
                shoppingCartInfoList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("isCommit",1);
            databaseOP.updateShoppingCartInfo(contentValues,shoppingCartInfo.get_id());
        }
    }

    public void commitOrderContent(long order_id, List<ShoppingCartInfo> shoppingCartInfoList) {
        String phone_number = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString("phone_number", "");
        for (ShoppingCartInfo shoppingCartInfo :
                shoppingCartInfoList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("order_id",order_id);
            contentValues.put("shopping_cart_id",shoppingCartInfo.get_id());
            contentValues.put("phone_number",phone_number);
            DatabaseOP databaseOP = new DatabaseOP(context);
            databaseOP.commitOrderContent(contentValues);
        }
    }
}
