package com.triplebro.domineer.graduationdesignproject.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.triplebro.domineer.graduationdesignproject.R;

public class AddAddressActivity extends Activity implements View.OnClickListener {

    private ImageView iv_close_add_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        initView();
        initData();
        setOnClickListener();
    }

    private void setOnClickListener() {
        iv_close_add_address.setOnClickListener(this);
    }

    private void initData() {

    }

    private void initView() {
        iv_close_add_address = (ImageView) findViewById(R.id.iv_close_add_address);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close_add_address:
                finish();
                break;
        }
    }
}
