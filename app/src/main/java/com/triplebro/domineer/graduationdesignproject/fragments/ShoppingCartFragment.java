package com.triplebro.domineer.graduationdesignproject.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.triplebro.domineer.graduationdesignproject.R;
import com.triplebro.domineer.graduationdesignproject.adapters.ShoppingCartAdapter;
import com.triplebro.domineer.graduationdesignproject.beans.ShoppingCartInfo;
import com.triplebro.domineer.graduationdesignproject.managers.ShoppingCartManager;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartFragment extends Fragment {

    private View fragment_ShoppingCart;
    private ListView lv_shopping_cart;
    private ShoppingCartManager shoppingCartManager;
    private List<ShoppingCartInfo> shoppingCartInfoList;
    private TextView tv_tip;
    private RelativeLayout rl_pay;
    private String phone_number;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragment_ShoppingCart = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        initView();
        initData();
        return fragment_ShoppingCart;
    }

    @Override
    public void onStart() {
        super.onStart();
        shoppingCartInfoList = shoppingCartManager.getShoppingCartInfoList();
        SharedPreferences userInfo = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        phone_number = userInfo.getString("phone_number", "");
        if(phone_number.length() == 0){
            tv_tip.setVisibility(View.VISIBLE);
            tv_tip.setText("还没登录呢，查不到信息哦！！！");
            lv_shopping_cart.setVisibility(View.GONE);
            rl_pay.setVisibility(View.GONE);
        }else if(shoppingCartInfoList.size() == 0){
            tv_tip.setVisibility(View.VISIBLE);
            tv_tip.setText("购物车空空如也，快去买点东西装满它吧！！！");
            lv_shopping_cart.setVisibility(View.GONE);
            rl_pay.setVisibility(View.GONE);
        }else{
            lv_shopping_cart.setVisibility(View.VISIBLE);
            rl_pay.setVisibility(View.VISIBLE);
            tv_tip.setVisibility(View.GONE);
        }
        lv_shopping_cart.setAdapter(new ShoppingCartAdapter(getActivity(),shoppingCartInfoList));
    }

    private void initData() {
        shoppingCartManager = new ShoppingCartManager(getActivity());
    }

    private void initView() {
        lv_shopping_cart = fragment_ShoppingCart.findViewById(R.id.lv_shopping_cart);
        tv_tip = fragment_ShoppingCart.findViewById(R.id.tv_tip);
        rl_pay = fragment_ShoppingCart.findViewById(R.id.rl_pay);
    }

}
