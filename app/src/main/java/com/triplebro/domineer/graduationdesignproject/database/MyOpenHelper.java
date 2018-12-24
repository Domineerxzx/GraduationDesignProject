package com.triplebro.domineer.graduationdesignproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class MyOpenHelper extends SQLiteOpenHelper {
    public MyOpenHelper(@Nullable Context context) {
        super(context, "HiShopping", null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //用户表
        db.execSQL("create table userInfo(phone_number varchar(20) primary key,password varchar(20),nickname varchar(20),user_head varchar(200))");
        //管理员表
        db.execSQL("create table adminInfo(phone_number varchar(20) primary key,password varchar(20),nickname varchar(20),user_head varchar(200))");

        //商品表
        db.execSQL("create table commodityInfo(commodity_id number primary key,commodity_name varchar(20),price number,commodity_image varchar(200)," +
                "type_generalize_id number,type_concrete_id number,phone_number varchar(20)," +
                "FOREIGN KEY (type_generalize_id) REFERENCES typeGeneralize(type_generalize_id)," +
                "FOREIGN KEY (type_concrete_id) REFERENCES typeConcrete(type_concrete_id)," +
                "FOREIGN KEY (phone_number) REFERENCES adminInfo(phone_number))");
        //商品图片表
        db.execSQL("create table commodityImageInfo(_id Integer primary key autoincrement,commodity_id number,commodity_image varchar(200),phone_number varchar(20)," +
                "FOREIGN KEY (commodity_id) REFERENCES commodityInfo(commodity_id)," +
                "FOREIGN KEY (phone_number) REFERENCES adminInfo(phone_number))");
        //商品尺码/库存表
        db.execSQL("create table commoditySizeInfo(_id Integer primary key autoincrement,commodity_id number,size_name varchar(20),size_count number,phone_number varchar(20)," +
                "FOREIGN KEY (commodity_id) REFERENCES commodityInfo(commodity_id)," +
                "FOREIGN KEY (phone_number) REFERENCES adminInfo(phone_number))");

        //概括分类表
        db.execSQL("create table typeGeneralize(type_generalize_id number primary key,type_generalize_name varchar(20))");
        //详细分类表
        db.execSQL("create table typeConcrete(type_concrete_id number primary key,type_generalize_id number,type_concrete_name varchar(20)," +
                "type_concrete_image varchar(200),FOREIGN KEY (type_generalize_id) REFERENCES typeGeneralize(type_generalize_id))");

        //发现表
        db.execSQL("create table submitInfo(submit_id number primary key,phone_number varchar(20),nickname varchar(20),user_head varchar(200)," +
                "submit_content varchar(500),FOREIGN KEY (phone_number) REFERENCES userInfo(phone_number))");
        //发现图片表
        db.execSQL("create table submitImageInfo(_id Integer primary key autoincrement,submit_id number,submit_image varchar(200)," +
                "FOREIGN KEY (submit_id) REFERENCES submitInfo(submit_id))");

        //购物车表
        db.execSQL("create table shoppingCartInfo(_id Integer primary key autoincrement,commodity_id number,size_name varchar(20),count number,commodity_name varchar(20)," +
                "phone_number varchar(20),commodity_image varchar(200),price number," +
                "FOREIGN KEY (commodity_id) REFERENCES commodityInfo(commodity_id)," +
                "FOREIGN KEY (phone_number) REFERENCES userInfo(phone_number))");

        //地址管理表
        db.execSQL("create table locationInfo(_id Integer primary key autoincrement,phone_number varchar(20),name varchar(20),city varchar(20)," +
                "location varcahr(100),zip_code number,mobile varchar(20),is_frist boolear," +
                "FOREIGN KEY (phone_number) REFERENCES userInfo(phone_number))");

        //收藏商品表
        db.execSQL("create table collectionCommodityInfo(_id Integer primary key autoincrement,phone_number varchar(20),commodity_id number," +
                "FOREIGN KEY (phone_number) REFERENCES userInfo(phone_number)," +
                "FOREIGN KEY (commodity_id) REFERENCES commodityInfo(commodity_id))");
        //收藏发现表
        db.execSQL("create table collectionSubmitInfo(_id Integer primary key autoincrement,phone_number varchar(20),submit_id number," +
                "FOREIGN KEY (phone_number) REFERENCES userInfo(phone_number)," +
                "FOREIGN KEY (submit_id) REFERENCES submitInfo(submit_id))");

        //商品推荐表
        db.execSQL("create table commodityRecommendInfo(_id Integer primary key autoincrement,commodity_id number,commodity_name varchar(20)," +
                "price number,recommend_image varchar(200)," +
                "FOREIGN KEY (commodity_id) REFERENCES commodityInfo(commodity_id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints 开启外键约束
            db.execSQL("PRAGMA foreign_keys = ON;");
        }
    }
}
