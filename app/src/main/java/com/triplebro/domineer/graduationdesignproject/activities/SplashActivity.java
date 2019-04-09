package com.triplebro.domineer.graduationdesignproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.triplebro.domineer.graduationdesignproject.R;
import com.triplebro.domineer.graduationdesignproject.handlers.DataInsertHandler;
import com.triplebro.domineer.graduationdesignproject.managers.SplashManager;
import com.triplebro.domineer.graduationdesignproject.properties.ProjectProperties;
import com.triplebro.domineer.graduationdesignproject.utils.ossUtils.InitOssClient;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends Activity {

    private SplashManager splashManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        InitOssClient.initOssClient(this, ProjectProperties.TOKEN_ADDRESS, ProjectProperties.ENDPOINT);
        final DataInsertHandler dataInsertHandler = new DataInsertHandler(this);
        new Thread(){
            @Override
            public void run() {
                splashManager = new SplashManager(SplashActivity.this);
                List<String> table_name = new ArrayList<>();
                table_name.add("adminInfo");
                table_name.add("userInfo");
                table_name.add("typeGeneralize");
                table_name.add("typeConcrete");
                table_name.add("commodityInfo");
                table_name.add("commodityImageInfo");
                table_name.add("commoditySizeInfo");
                List<String> nonentity_table_name  = splashManager.checkData(table_name);
                if(nonentity_table_name.size() == 0){
                    try {
                        sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                splashManager.uploadData(nonentity_table_name,dataInsertHandler);
            }
        }.start();
    }
}
