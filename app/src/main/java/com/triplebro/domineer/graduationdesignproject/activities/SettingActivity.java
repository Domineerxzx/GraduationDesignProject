package com.triplebro.domineer.graduationdesignproject.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.triplebro.domineer.graduationdesignproject.R;

public class SettingActivity extends Activity implements View.OnClickListener {

    private Button bt_cancellation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        setOnClickListener();
    }

    private void setOnClickListener() {
        bt_cancellation.setOnClickListener(this);
    }

    private void initView() {
        bt_cancellation = (Button) findViewById(R.id.bt_cancellation);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_cancellation:
                SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
                SharedPreferences.Editor edit = userInfo.edit();
                edit.clear();
                edit.commit();
                finish();
                break;
        }
    }
}
