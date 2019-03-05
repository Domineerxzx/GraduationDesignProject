package com.triplebro.domineer.graduationdesignproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.triplebro.domineer.graduationdesignproject.R;
import com.triplebro.domineer.graduationdesignproject.properties.ProjectProperties;
import com.triplebro.domineer.graduationdesignproject.utils.ossUtils.InitOssClient;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        InitOssClient.initOssClient(this,ProjectProperties.TOKEN_ADDRESS,ProjectProperties.ENDPOINT);
        RelativeLayout rv_splash = (RelativeLayout) findViewById(R.id.rv_splash);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f,1.0f);
        alphaAnimation.setDuration(2500);
        rv_splash.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
