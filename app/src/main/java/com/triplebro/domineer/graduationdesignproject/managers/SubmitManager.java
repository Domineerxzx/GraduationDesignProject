package com.triplebro.domineer.graduationdesignproject.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.triplebro.domineer.graduationdesignproject.beans.SubmitImageInfo;
import com.triplebro.domineer.graduationdesignproject.beans.SubmitInfo;
import com.triplebro.domineer.graduationdesignproject.database.MyOpenHelper;

public class SubmitManager {

    private Context context;

    public SubmitManager(Context context) {
        this.context = context;
    }

    public long UploadSubmitInfo(SubmitInfo submitInfo){
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("submit_id",submitInfo.getSubmit_id());
        contentValues.put("phone_number",submitInfo.getPhone_number());
        contentValues.put("nickname",submitInfo.getNickname());
        contentValues.put("user_head",submitInfo.getUser_head());
        contentValues.put("submit_content",submitInfo.getSubmit_content());
        long submit_id = db.insert("submitInfo", null, contentValues);
        db.close();
        return submit_id;
    }


    public void UploadSubmitImageInfo(SubmitImageInfo submitImageInfo){

        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("submit_id",submitImageInfo.getSubmit_id());
        contentValues.put("submit_image",submitImageInfo.getSubmit_image());
        db.insert("submitImageInfo",null,contentValues);
        db.close();
    }
}
