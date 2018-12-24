package com.triplebro.domineer.graduationdesignproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.triplebro.domineer.graduationdesignproject.R;
import com.triplebro.domineer.graduationdesignproject.adapters.AddressAdapter;
import com.triplebro.domineer.graduationdesignproject.beans.AddressInfo;
import com.triplebro.domineer.graduationdesignproject.managers.LocationManager;

import java.util.List;

public class LocationActivity extends Activity implements View.OnClickListener {

    private ImageView iv_close_address;
    private Button bt_add_address;
    private Button bt_add_address_new;
    private ListView lv_address;
    private LocationManager locationManager;
    private List<AddressInfo> addressInfoList;
    private AddressAdapter addressAdapter;
    private String phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        initView();
        initData();
        setOnClickListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        phone_number = userInfo.getString("phone_number", "");
        addressInfoList = locationManager.getAddressInfoList(phone_number);
        addressAdapter.setAddressInfoList(addressInfoList);
    }

    private void initData() {
        SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        phone_number = userInfo.getString("phone_number", "");
        locationManager = new LocationManager(this);
        addressInfoList = locationManager.getAddressInfoList(phone_number);
        addressAdapter = new AddressAdapter(this, addressInfoList);
        lv_address.setAdapter(addressAdapter);
    }

    private void setOnClickListener() {
        iv_close_address.setOnClickListener(this);
        bt_add_address_new.setOnClickListener(this);
        bt_add_address.setOnClickListener(this);
    }

    private void initView() {
        iv_close_address = (ImageView) findViewById(R.id.iv_close_address);
        bt_add_address = (Button) findViewById(R.id.bt_add_address);
        bt_add_address_new = (Button) findViewById(R.id.bt_add_address_new);
        lv_address = (ListView) findViewById(R.id.lv_address);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close_address:
                finish();
                break;
            case R.id.bt_add_address:
            case R.id.bt_add_address_new:
                Intent add_address = new Intent(this, AddAddressActivity.class);
                startActivity(add_address);
                break;
        }
    }
}
