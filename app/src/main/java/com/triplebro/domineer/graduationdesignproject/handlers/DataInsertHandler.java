package com.triplebro.domineer.graduationdesignproject.handlers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.triplebro.domineer.graduationdesignproject.activities.AdminManagerActivity;
import com.triplebro.domineer.graduationdesignproject.activities.LoginActivity;
import com.triplebro.domineer.graduationdesignproject.activities.MainActivity;
import com.triplebro.domineer.graduationdesignproject.activities.SplashActivity;
import com.triplebro.domineer.graduationdesignproject.properties.ProjectProperties;
import com.triplebro.domineer.graduationdesignproject.sourceop.DatabaseOP;

public class DataInsertHandler extends Handler {

    private Context context;

    public DataInsertHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what){
            case ProjectProperties.DATA_INSERT_SUCCESS:
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                ((Activity)context).finish();
                break;
            case ProjectProperties.DATA_INSERT_FAILED:
                DatabaseOP databaseOP = new DatabaseOP(context);
                databaseOP.deleteTable();
                Toast.makeText(context, "初始化数据失败，请重新启动本App", Toast.LENGTH_SHORT).show();
                ((Activity)context).finish();
                break;
        }
    }
}
