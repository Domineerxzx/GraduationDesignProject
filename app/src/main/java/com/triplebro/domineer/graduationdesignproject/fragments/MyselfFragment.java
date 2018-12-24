package com.triplebro.domineer.graduationdesignproject.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.triplebro.domineer.graduationdesignproject.R;
import com.triplebro.domineer.graduationdesignproject.activities.LocationActivity;
import com.triplebro.domineer.graduationdesignproject.activities.LoginActivity;
import com.triplebro.domineer.graduationdesignproject.activities.SettingActivity;
import com.triplebro.domineer.graduationdesignproject.activities.UserInfoActivity;

import org.jetbrains.annotations.Nullable;

public class MyselfFragment extends Fragment implements View.OnClickListener {

    private View fragment_myself;
    private TextView tv_nickname;
    private LinearLayout ll_user_info;
    private TextView tv_username;
    private ImageView iv_user_head;
    private RelativeLayout rl_location;
    private ImageView iv_location;
    private TextView tv_location;
    private ImageView iv_location_more;
    private RelativeLayout rl_setting;
    private ImageView iv_setting;
    private TextView tv_setting;
    private ImageView iv_setting_more;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragment_myself = inflater.inflate(R.layout.fragment_myself, null);
        initView();
        setOnClickListener();
        return fragment_myself;
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences userInfo = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String phone_number = userInfo.getString("phone_number", "暂无登录信息");
        String nickname = userInfo.getString("nickname", "点击  登录/注册");
        String userHead = userInfo.getString("userHead", "");
        if (userHead.length() != 0) {
            Glide.with(getActivity()).load(userHead).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(iv_user_head);
        }else{
            Glide.with(getActivity()).load(R.drawable.user_head_default).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(iv_user_head);
        }
        if(phone_number.equals("暂无登录信息")){
            tv_username.setText(phone_number);
        }else{
            tv_username.setText("ID:"+phone_number);
        }
        tv_nickname.setText(nickname);
    }

    private void setOnClickListener() {
        tv_nickname.setOnClickListener(this);
        ll_user_info.setOnClickListener(this);
        tv_username.setOnClickListener(this);
        iv_user_head.setOnClickListener(this);
        rl_location.setOnClickListener(this);
        iv_location.setOnClickListener(this);
        tv_location.setOnClickListener(this);
        iv_location_more.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
        iv_setting.setOnClickListener(this);
        tv_setting.setOnClickListener(this);
        iv_setting_more.setOnClickListener(this);
    }

    private void initView() {
        tv_nickname = fragment_myself.findViewById(R.id.tv_nickname);
        ll_user_info = fragment_myself.findViewById(R.id.ll_user_info);
        tv_username = fragment_myself.findViewById(R.id.tv_username);
        iv_user_head = fragment_myself.findViewById(R.id.iv_user_head);
        rl_location = fragment_myself.findViewById(R.id.rl_location);
        iv_location = fragment_myself.findViewById(R.id.iv_location);
        tv_location = fragment_myself.findViewById(R.id.tv_location);
        iv_location_more = fragment_myself.findViewById(R.id.iv_location_more);
        rl_setting = fragment_myself.findViewById(R.id.rl_setting);
        iv_setting = fragment_myself.findViewById(R.id.iv_setting);
        tv_setting = fragment_myself.findViewById(R.id.tv_setting);
        iv_setting_more = fragment_myself.findViewById(R.id.iv_setting_more);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_nickname:
            case R.id.ll_user_info:
            case R.id.tv_username:
            case R.id.iv_user_head:
                SharedPreferences userInfo = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                String phone_number = userInfo.getString("phone_number", "暂无登录信息");
                String nickname = userInfo.getString("nickname", "点击  登录/注册");
                if (phone_number.equals("暂无登录信息") && nickname.equals("点击  登录/注册")) {
                    Intent login = new Intent(getActivity(), LoginActivity.class);
                    startActivity(login);
                    break;
                } else {
                    Intent user_info = new Intent(getActivity(), UserInfoActivity.class);
                    startActivity(user_info);
                    break;
                }
            case R.id.rl_location:
            case R.id.iv_location:
            case R.id.tv_location:
            case R.id.iv_location_more:
                Intent location = new Intent(getActivity(), LocationActivity.class);
                startActivity(location);
                break;
            case R.id.rl_setting:
            case R.id.iv_setting:
            case R.id.tv_setting:
            case R.id.iv_setting_more:
                Intent setting = new Intent(getActivity(), SettingActivity.class);
                startActivity(setting);
                break;
        }
    }
}
