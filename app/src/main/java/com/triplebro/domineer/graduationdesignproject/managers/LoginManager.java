package com.triplebro.domineer.graduationdesignproject.managers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.triplebro.domineer.graduationdesignproject.service.NetworkConnectionService;

public class LoginManager implements ServiceConnection {

    private Context context;
    private String phone_number;
    private String password;

    public LoginManager(Context context) {
        this.context = context;
    }

    public void login(String phone_number, String password) {
        this.phone_number = phone_number;
        this.password = password;
        Intent service = new Intent(context, NetworkConnectionService.class);
        context.bindService(service,this,Context.BIND_AUTO_CREATE);
        System.out.println("-----------------------------------------------");
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        NetworkConnectionService.MyBinder myBinder = (NetworkConnectionService.MyBinder) service;
        myBinder.login(context, phone_number,password,this);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
