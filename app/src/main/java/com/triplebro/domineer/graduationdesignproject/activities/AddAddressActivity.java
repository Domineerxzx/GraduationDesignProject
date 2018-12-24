package com.triplebro.domineer.graduationdesignproject.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.triplebro.domineer.graduationdesignproject.R;
import com.triplebro.domineer.graduationdesignproject.beans.AddressInfo;
import com.triplebro.domineer.graduationdesignproject.managers.LocationManager;

public class AddAddressActivity extends Activity implements View.OnClickListener {

    private ImageView iv_close_add_address;
    private LocationManager locationManager;
    private EditText et_address_name;
    private EditText et_address_area_city;
    private EditText et_address_detailed;
    private EditText et_address_postcode;
    private EditText et_address_telephone;
    private Button bt_add_address;

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
        bt_add_address.setOnClickListener(this);
    }

    private void initData() {
        locationManager = new LocationManager(this);
        /*locationManager.uploadAddressInfo();*/
    }

    private void initView() {
        iv_close_add_address = (ImageView) findViewById(R.id.iv_close_add_address);
        et_address_name = (EditText) findViewById(R.id.et_address_name);
        et_address_area_city = (EditText) findViewById(R.id.et_address_area_city);
        et_address_detailed = (EditText) findViewById(R.id.et_address_detailed);
        et_address_postcode = (EditText) findViewById(R.id.et_address_postcode);
        et_address_telephone = (EditText) findViewById(R.id.et_address_telephone);
        bt_add_address = (Button) findViewById(R.id.bt_add_address);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close_add_address:
                finish();
                break;
            case R.id.bt_add_address:
                AddressInfo addressInfo = new AddressInfo();
                addressInfo.setPhone_number(getSharedPreferences("userInfo",MODE_PRIVATE).getString("phone_number",""));
                addressInfo.setName(et_address_name.getText().toString().trim());
                addressInfo.setCity(et_address_area_city.getText().toString().trim());
                addressInfo.setLocation(et_address_detailed.getText().toString().trim());
                addressInfo.setZip_code(Integer.parseInt(et_address_postcode.getText().toString()));
                addressInfo.setMobile(et_address_telephone.getText().toString().trim());
                locationManager.uploadAddressInfo(addressInfo);
                finish();
                break;
        }
    }
}
