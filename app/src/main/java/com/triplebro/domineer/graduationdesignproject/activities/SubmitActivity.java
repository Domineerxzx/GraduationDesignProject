package com.triplebro.domineer.graduationdesignproject.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.triplebro.domineer.graduationdesignproject.R;
import com.triplebro.domineer.graduationdesignproject.adapters.SubmitAdapter;
import com.triplebro.domineer.graduationdesignproject.beans.SubmitImageInfo;
import com.triplebro.domineer.graduationdesignproject.beans.SubmitInfo;
import com.triplebro.domineer.graduationdesignproject.database.MyOpenHelper;
import com.triplebro.domineer.graduationdesignproject.interfaces.OnItemClickListener;
import com.triplebro.domineer.graduationdesignproject.managers.SubmitManager;
import com.triplebro.domineer.graduationdesignproject.properties.ProjectProperties;
import com.triplebro.domineer.graduationdesignproject.utils.dialogUtils.ChooseUserHeadDialogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SubmitActivity extends Activity implements View.OnClickListener, OnItemClickListener {

    private ImageView iv_close_submit;
    private RecyclerView rv_submit;
    private Button bt_submit;
    private EditText et_submit_content;
    private String phone_number;
    private long timeStamp;
    private SubmitAdapter submitAdapter;
    private SubmitManager submitManager;
    private SharedPreferences userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        initView();
        initData();
        setOnClickListener();
    }

    private void initData() {
        submitAdapter = new SubmitAdapter(this, new ArrayList<String>());
        rv_submit.setAdapter(submitAdapter);
        submitAdapter.setOnItemClickListener(this);
        submitManager = new SubmitManager(this);
    }

    private void setOnClickListener() {
        iv_close_submit.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
    }

    private void initView() {
        iv_close_submit = (ImageView) findViewById(R.id.iv_close_submit);
        rv_submit = (RecyclerView) findViewById(R.id.rv_submit);
        rv_submit.setLayoutManager(new GridLayoutManager(this, 3));
        bt_submit = (Button) findViewById(R.id.bt_submit);
        et_submit_content = (EditText) findViewById(R.id.et_submit_content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close_submit:
                finish();
                break;
            case R.id.bt_submit:
                SubmitInfo submitInfo = new SubmitInfo();
                submitInfo.setPhone_number(phone_number);
                submitInfo.setNickname(userInfo.getString("nickname", ""));
                submitInfo.setUser_head(userInfo.getString("userHead", ""));
                submitInfo.setSubmit_content(et_submit_content.getText().toString().trim());
                long submit_id = submitManager.UploadSubmitInfo(submitInfo);
                List<String> data = submitAdapter.getData();
                if (data.size() > 0 && data.size() < 9) {
                    data.remove(data.size()-1);
                }
                for (String s :data) {
                    SubmitImageInfo submitImageInfo = new SubmitImageInfo();
                    submitImageInfo.setSubmit_id((int) submit_id);
                    submitImageInfo.setSubmit_image(s);
                    submitManager.UploadSubmitImageInfo(submitImageInfo);
                }
                Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        phone_number = userInfo.getString("phone_number", "");
        timeStamp = System.currentTimeMillis();
        ChooseUserHeadDialogUtil.showSelectSubmitDialog(this, phone_number, timeStamp);
    }

    @Override
    public void onItemLongClick(View view) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        boolean isCheck = true;
        String s = "";
        switch (requestCode) {
            case ProjectProperties.FROM_GALLERY:
                if (resultCode == RESULT_OK) {
                    s = data.getData().toString();
                } else {
                    isCheck = false;
                }
                break;
            case ProjectProperties.FROM_CAMERA:
                if (resultCode == RESULT_OK) {
                    s = getFilesDir() + File.separator + "images" + File.separator + phone_number + timeStamp + ".jpg";
                } else {
                    isCheck = false;
                }
                break;
            default:
                break;
        }
        if (isCheck) {
            List<String> strings = submitAdapter.getData();
            if (strings.size() == 0) {
                strings.add(s);
            } else {
                strings.remove(strings.size() - 1);
                strings.add(s);
            }
            if (strings.size() != 9) {
                strings.add("");
            }
            submitAdapter.setData(strings);
        } else {
            Toast.makeText(this, "取消选择", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
