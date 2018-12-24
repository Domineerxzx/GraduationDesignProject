package com.triplebro.domineer.graduationdesignproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.triplebro.domineer.graduationdesignproject.R;
import com.triplebro.domineer.graduationdesignproject.handlers.LoginHandler;
import com.triplebro.domineer.graduationdesignproject.managers.LoginManager;

public class LoginActivity extends Activity implements View.OnClickListener {

    private Button bt_create;
    private ImageView iv_close_login;
    private Button bt_login;
    private LoginManager loginManager;
    private LoginHandler loginHandler;
    private EditText et_phone_number;
    private EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
        setOnClickListener();
    }

    private void initData() {
        loginManager = new LoginManager(this);
    }

    private void setOnClickListener() {
        bt_create.setOnClickListener(this);
        iv_close_login.setOnClickListener(this);
        bt_login.setOnClickListener(this);
    }

    private void initView() {
        bt_create = findViewById(R.id.bt_create);
        iv_close_login = findViewById(R.id.iv_close_login);
        bt_login = (Button) findViewById(R.id.bt_login);
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
        et_password = (EditText) findViewById(R.id.et_password);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_create:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.iv_close_login:
                finish();
                break;
            case R.id.bt_login:
                String phone_number = et_phone_number.getText().toString();
                String password = et_password.getText().toString();
                if (phone_number.length() != 0 && password.length() != 0) {
                    loginManager.login(phone_number, password);
                } else {
                    Toast.makeText(this, "手机号或密码不能为空！！！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
