package com.triplebro.domineer.graduationdesignproject.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.triplebro.domineer.graduationdesignproject.R;
import com.triplebro.domineer.graduationdesignproject.adapters.FoundAdapter;
import com.triplebro.domineer.graduationdesignproject.beans.SubmitInfo;
import com.triplebro.domineer.graduationdesignproject.managers.CollectionManager;

import java.util.List;

public class CollectionSubmitActivity extends Activity implements View.OnClickListener {

    private ImageView iv_close_collection_submit;
    private String phone_number;
    private ListView lv_collection_submit;
    private CollectionManager collectionManager;
    private List<SubmitInfo> collectionSubmitInfoList;
    private FoundAdapter foundAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_submit);
        initView();
        initData();
        setOnClickListener();
    }

    private void setOnClickListener() {
        iv_close_collection_submit.setOnClickListener(this);
    }

    private void initData() {
        SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        phone_number = userInfo.getString("phone_number", "");
        collectionManager = new CollectionManager(this);
        collectionSubmitInfoList = collectionManager.getCollectionSubmitInfoList(phone_number);
        foundAdapter = new FoundAdapter(this, collectionSubmitInfoList,lv_collection_submit);
        lv_collection_submit.setAdapter(foundAdapter);
    }

    private void initView() {
        iv_close_collection_submit = (ImageView) findViewById(R.id.iv_close_collection_submit);
        lv_collection_submit = (ListView) findViewById(R.id.lv_collection_submit);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close_collection_submit:
                finish();
                break;
        }
    }
}
