package com.triplebro.domineer.graduationdesignproject.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.triplebro.domineer.graduationdesignproject.R;
import com.triplebro.domineer.graduationdesignproject.activities.SubmitActivity;
import com.triplebro.domineer.graduationdesignproject.adapters.FoundAdapter;
import com.triplebro.domineer.graduationdesignproject.beans.SubmitInfo;
import com.triplebro.domineer.graduationdesignproject.managers.FoundManger;
import com.triplebro.domineer.graduationdesignproject.views.MyListView;

import java.util.ArrayList;
import java.util.List;

public class FoundFragment extends Fragment implements View.OnClickListener {

    private View fragment_found;
    private ListView lv_found;
    private ImageView iv_submit;
    private FoundManger foundManger;
    private List<SubmitInfo> submitInfoList;
    private FoundAdapter foundAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragment_found = inflater.inflate(R.layout.fragment_found, container, false);
        initView();
        initData();
        setOnClickListener();
        return fragment_found;
    }

    @Override
    public void onStart() {
        super.onStart();
        submitInfoList = foundManger.getSubmitInfoList();
        foundAdapter.setSubmitInfoList(submitInfoList);
    }

    private void setOnClickListener() {
        iv_submit.setOnClickListener(this);
    }

    private void initData() {
        foundManger = new FoundManger(getActivity());
        submitInfoList = foundManger.getSubmitInfoList();
        foundAdapter = new FoundAdapter(getActivity(), submitInfoList);
        lv_found.setAdapter(foundAdapter);
    }

    private void initView() {
        lv_found = (ListView) fragment_found.findViewById(R.id.lv_found);
        iv_submit = (ImageView) fragment_found.findViewById(R.id.iv_submit);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_submit:
                Intent submit = new Intent(getActivity(), SubmitActivity.class);
                startActivity(submit);
                break;
        }
    }
}
