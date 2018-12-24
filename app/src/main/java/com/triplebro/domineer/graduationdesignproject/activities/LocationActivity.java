package com.triplebro.domineer.graduationdesignproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.triplebro.domineer.graduationdesignproject.R;
import com.triplebro.domineer.graduationdesignproject.adapters.AddressAdapter;

import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends Activity implements View.OnClickListener {

    private ImageView iv_close_address;
    private Button bt_add_address;
    private Button bt_add_address_null;
    private ListView lv_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        initView();
        initData();
        setOnClickListener();
    }

    private void initData() {
        lv_address.setAdapter(new AddressAdapter(this,new ArrayList<String>()));
    }

    private void setOnClickListener() {
        iv_close_address.setOnClickListener(this);
        bt_add_address.setOnClickListener(this);
    }

    private void initView() {
        iv_close_address = (ImageView) findViewById(R.id.iv_close_address);
        bt_add_address = (Button) findViewById(R.id.bt_add_address);
        bt_add_address_null = (Button) findViewById(R.id.bt_add_address_null);
        lv_address = (ListView) findViewById(R.id.lv_address);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close_address:
                finish();
                break;
            case R.id.bt_add_address:
            case R.id.bt_add_address_null:
                Intent add_address = new Intent(this, AddAddressActivity.class);
                startActivity(add_address);
                break;
        }
    }
}
