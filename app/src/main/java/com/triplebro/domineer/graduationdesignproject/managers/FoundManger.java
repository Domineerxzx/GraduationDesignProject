package com.triplebro.domineer.graduationdesignproject.managers;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.triplebro.domineer.graduationdesignproject.beans.SubmitInfo;
import com.triplebro.domineer.graduationdesignproject.database.MyOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class FoundManger {

    private Context context;

    public FoundManger(Context context) {
        this.context = context;
    }

    public List<SubmitInfo> getSubmitInfoList() {
        List<SubmitInfo> submitInfoList = new ArrayList<>();
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        Cursor submitInfoCursor = db.query("submitInfo", null, null, null, null, null, "submit_id desc");
        if (submitInfoCursor != null && submitInfoCursor.getCount() > 0) {
            while(submitInfoCursor.moveToNext()){
                SubmitInfo submitInfo = new SubmitInfo();
                submitInfo.setSubmit_id(submitInfoCursor.getInt(0));
                submitInfo.setPhone_number(submitInfoCursor.getString(1));
                submitInfo.setNickname(submitInfoCursor.getString(2));
                submitInfo.setUser_head(submitInfoCursor.getString(3));
                submitInfo.setSubmit_content(submitInfoCursor.getString(4));
                submitInfoList.add(submitInfo);
            }
        }
        if (submitInfoCursor != null) {
            submitInfoCursor.close();
        }
        db.close();
        return submitInfoList;
    }
}
