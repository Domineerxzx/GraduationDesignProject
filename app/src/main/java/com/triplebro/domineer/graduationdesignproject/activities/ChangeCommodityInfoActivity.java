package com.triplebro.domineer.graduationdesignproject.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.triplebro.domineer.graduationdesignproject.R;
import com.triplebro.domineer.graduationdesignproject.adapters.SizeInfoAdapter;
import com.triplebro.domineer.graduationdesignproject.adapters.SubmitAdapter;
import com.triplebro.domineer.graduationdesignproject.beans.CommodityInfo;
import com.triplebro.domineer.graduationdesignproject.beans.CommoditySizeInfo;
import com.triplebro.domineer.graduationdesignproject.beans.TypeConcreteInfo;
import com.triplebro.domineer.graduationdesignproject.beans.TypeGeneralizeInfo;
import com.triplebro.domineer.graduationdesignproject.handlers.OssHandler;
import com.triplebro.domineer.graduationdesignproject.interfaces.OnItemClickListener;
import com.triplebro.domineer.graduationdesignproject.managers.AddCommodityManager;
import com.triplebro.domineer.graduationdesignproject.managers.ChangeCommodityManager;
import com.triplebro.domineer.graduationdesignproject.properties.ProjectProperties;
import com.triplebro.domineer.graduationdesignproject.utils.dialogUtils.ChooseUserHeadDialogUtil;
import com.triplebro.domineer.graduationdesignproject.utils.dialogUtils.SingleChooseDialog;
import com.triplebro.domineer.graduationdesignproject.utils.imageUtils.RealPathFromUriUtils;
import com.triplebro.domineer.graduationdesignproject.utils.ossUtils.DownloadUtils;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChangeCommodityInfoActivity extends Activity implements View.OnClickListener, OnItemClickListener {

    private ImageView iv_close_change_commodity;
    private EditText et_commodity_name;
    private EditText et_commodity_price;
    private TextView tv_commodity_generalize_type_content;
    private TextView tv_commodity_concrete_type_content;
    private ImageView iv_commodity_generalize_type;
    private ImageView iv_commodity_concrete_type;
    private RecyclerView rv_commodity_image_content;
    private ImageView iv_commodity_image_show;
    private ImageView iv_delete_commodity_image_show;
    private ListView lv_commodity_size;
    private ImageView iv_add_commodity_size;
    private ImageView iv_delete_commodity_size;
    private Button bt_change_commodity;
    private long timeStamp;
    private String chooseFrom;
    private SharedPreferences adminInfo;
    private String phone_number;
    private ChangeCommodityManager changeCommodityManager;
    private String image_show;
    private SubmitAdapter submitAdapter;
    private TypeGeneralizeInfo chooseTypeGeneralizeInfo;
    private TypeConcreteInfo chooseTypeConcreteInfo;
    private List<CommoditySizeInfo> commoditySizeInfoList;
    private SizeInfoAdapter sizeInfoAdapter;
    private CommodityInfo commodityInfo;
    private TypeGeneralizeInfo commodityGeneralizeTypeInfo;
    private TypeConcreteInfo commodityConcreteTypeInfo;
    private List<String> submitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_commodity_info);
        initView();
        initData();
        setOnClickListener();
    }

    private void initView() {
        iv_close_change_commodity = findViewById(R.id.iv_close_change_commodity);
        et_commodity_name = findViewById(R.id.et_commodity_name);
        et_commodity_price = findViewById(R.id.et_commodity_price);
        tv_commodity_generalize_type_content = findViewById(R.id.tv_commodity_generalize_type_content);
        tv_commodity_concrete_type_content = findViewById(R.id.tv_commodity_concrete_type_content);
        iv_commodity_generalize_type = findViewById(R.id.iv_commodity_generalize_type);
        iv_commodity_concrete_type = findViewById(R.id.iv_commodity_concrete_type);
        rv_commodity_image_content = findViewById(R.id.rv_commodity_image_content);
        iv_commodity_image_show = findViewById(R.id.iv_commodity_image_show);
        iv_delete_commodity_image_show = findViewById(R.id.iv_delete_commodity_image_show);
        iv_delete_commodity_image_show.bringToFront();
        iv_commodity_image_show.setScaleType(ImageView.ScaleType.CENTER_CROP);
        lv_commodity_size = findViewById(R.id.lv_commodity_size);
        iv_add_commodity_size = findViewById(R.id.iv_add_commodity_size);
        iv_delete_commodity_size = findViewById(R.id.iv_delete_commodity_size);
        bt_change_commodity = findViewById(R.id.bt_change_commodity);
    }

    private void initData() {
        adminInfo = getSharedPreferences("adminInfo", MODE_PRIVATE);
        phone_number = adminInfo.getString("phone_number", "");
        Intent intent = getIntent();
        commodityInfo = (CommodityInfo) intent.getSerializableExtra("commodityInfo");
        changeCommodityManager = new ChangeCommodityManager(this);
        File file = new File(commodityInfo.getCommodity_image());
        if (file.length()>0) {
            Glide.with(this).load(file).into(iv_commodity_image_show);
        }else{
            OssHandler ossHandler = new OssHandler(this, iv_commodity_image_show);
            DownloadUtils.downloadFileFromOss(file,ossHandler,ProjectProperties.BUCKET_NAME,"xuzhanxin/"+commodityInfo.getCommodity_image());
        }
        image_show = commodityInfo.getCommodity_image();
        et_commodity_name.setText(commodityInfo.getCommodity_name());
        et_commodity_price.setText(String.valueOf(commodityInfo.getPrice()));
        rv_commodity_image_content.setLayoutManager(new GridLayoutManager(this, 3));
        commodityGeneralizeTypeInfo = changeCommodityManager.getCommodityGeneralizeTypeInfo(commodityInfo.getType_generalize_id());
        commodityConcreteTypeInfo = changeCommodityManager.getCommodityConcreteTypeInfo(commodityInfo.getType_concrete_id());
        if (commodityGeneralizeTypeInfo != null) {
            tv_commodity_generalize_type_content.setText(commodityGeneralizeTypeInfo.getType_generalize_name());
        }
        if (commodityConcreteTypeInfo != null) {
            tv_commodity_concrete_type_content.setText(commodityConcreteTypeInfo.getType_concrete_name());
        }
        submitList = changeCommodityManager.getCommodityImageList(commodityInfo.getCommodity_id());
        submitList.add("");
        submitAdapter = new SubmitAdapter(this, submitList);
        rv_commodity_image_content.setAdapter(submitAdapter);
        chooseFrom = "image_show";
        chooseTypeGeneralizeInfo = commodityGeneralizeTypeInfo;
        chooseTypeConcreteInfo = commodityConcreteTypeInfo;
        commoditySizeInfoList = changeCommodityManager.getCommoditySizeInfoList(commodityInfo.getCommodity_id());
        sizeInfoAdapter = new SizeInfoAdapter(this, commoditySizeInfoList);
        lv_commodity_size.setAdapter(sizeInfoAdapter);
    }

    private void setOnClickListener() {
        iv_close_change_commodity.setOnClickListener(this);
        tv_commodity_generalize_type_content.setOnClickListener(this);
        tv_commodity_concrete_type_content.setOnClickListener(this);
        iv_commodity_generalize_type.setOnClickListener(this);
        iv_commodity_concrete_type.setOnClickListener(this);
        iv_add_commodity_size.setOnClickListener(this);
        iv_delete_commodity_size.setOnClickListener(this);
        iv_commodity_image_show.setOnClickListener(this);
        iv_delete_commodity_image_show.setOnClickListener(this);
        bt_change_commodity.setOnClickListener(this);
        submitAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close_change_commodity:
                finish();
                break;
            case R.id.tv_commodity_generalize_type_content:
            case R.id.iv_commodity_generalize_type:
                final List<TypeGeneralizeInfo> commodityGeneralizeType = changeCommodityManager.getCommodityGeneralizeType();
                final String[] chooseGeneralize = new String[commodityGeneralizeType.size()];
                int i = 0;
                for (TypeGeneralizeInfo typeGeneralizeInfo : commodityGeneralizeType) {
                    chooseGeneralize[i] = typeGeneralizeInfo.getType_generalize_name();
                    i++;
                }
                SingleChooseDialog chooseGeneralizeDialog = new SingleChooseDialog();
                chooseGeneralizeDialog.show("请选择概括类别", chooseGeneralize, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_commodity_generalize_type_content.setText(chooseGeneralize[which]);
                        chooseTypeGeneralizeInfo = commodityGeneralizeType.get(which);
                        dialog.dismiss();
                    }
                }, getFragmentManager());
                break;
            case R.id.tv_commodity_concrete_type_content:
            case R.id.iv_commodity_concrete_type:
                if (chooseTypeGeneralizeInfo.getType_generalize_id() == 0) {
                    Toast.makeText(this, "还没有选择概括类别呢", Toast.LENGTH_SHORT).show();
                    return;
                }
                final List<TypeConcreteInfo> commodityConcreteType = changeCommodityManager.getCommodityConcreteType(chooseTypeGeneralizeInfo.getType_generalize_id());
                if (commodityConcreteType.size() == 0) {
                    Toast.makeText(this, "目前该概括类别尚无详细类别", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String[] chooseConcrete = new String[commodityConcreteType.size()];
                int j = 0;
                for (TypeConcreteInfo typeConcreteInfo : commodityConcreteType) {
                    chooseConcrete[j] = typeConcreteInfo.getType_concrete_name();
                    j++;
                }
                SingleChooseDialog chooseConcreteDialog = new SingleChooseDialog();
                chooseConcreteDialog.show("请选择详细类别", chooseConcrete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_commodity_concrete_type_content.setText(chooseConcrete[which]);
                        chooseTypeConcreteInfo = commodityConcreteType.get(which);
                        dialog.dismiss();
                    }
                }, getFragmentManager());
                break;
            case R.id.iv_add_commodity_size:
                CommoditySizeInfo commoditySizeInfo = new CommoditySizeInfo();
                commoditySizeInfoList.add(commoditySizeInfo);
                sizeInfoAdapter.setCommoditySizeInfoList(commoditySizeInfoList);
                break;
            case R.id.iv_delete_commodity_size:
                commoditySizeInfoList = sizeInfoAdapter.getCommoditySizeInfoList();
                commoditySizeInfoList.remove(commoditySizeInfoList.size() - 1);
                sizeInfoAdapter.setCommoditySizeInfoList(commoditySizeInfoList);
                break;
            case R.id.iv_commodity_image_show:
                adminInfo = getSharedPreferences("adminInfo", MODE_PRIVATE);
                phone_number = adminInfo.getString("phone_number", "");
                timeStamp = System.currentTimeMillis();
                ChooseUserHeadDialogUtil.showSelectSubmitDialog(this, phone_number, timeStamp);
                chooseFrom = "image_show";
                break;
            case R.id.iv_delete_commodity_image_show:
                Glide.with(this).load(R.drawable.submit).into(iv_commodity_image_show);
                image_show = "";
                iv_delete_commodity_image_show.setVisibility(View.GONE);
                break;
            case R.id.bt_change_commodity:
                changeCommodityManager.deleteCommodity(commodityInfo.getCommodity_id());
                String commodity_name = et_commodity_name.getText().toString().trim();
                String commodity_price = et_commodity_price.getText().toString().trim();
                int type_generalize_id = chooseTypeGeneralizeInfo.getType_generalize_id();
                int type_concrete_id = chooseTypeConcreteInfo.getType_concrete_id();
                ContentValues commodityInfo = new ContentValues();
                commodityInfo.put("commodity_name", commodity_name);
                commodityInfo.put("type_generalize_id", type_generalize_id);
                commodityInfo.put("type_concrete_id", type_concrete_id);
                commodityInfo.put("price", Integer.parseInt(commodity_price));
                commodityInfo.put("commodity_image", image_show);
                commodityInfo.put("phone_number", phone_number);
                int commodity_id = changeCommodityManager.addCommodityInfo(commodityInfo);
                if (commodity_id != -1) {
                    commoditySizeInfoList = sizeInfoAdapter.getCommoditySizeInfoList();
                    changeCommodityManager.addCommoditySizeInfo(commodity_id, commoditySizeInfoList, phone_number);
                    List<String> commodityImageList = submitAdapter.getData();
                    changeCommodityManager.addCommodityImageInfo(commodity_id, commodityImageList, phone_number);
                }
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        timeStamp = System.currentTimeMillis();
        ChooseUserHeadDialogUtil.showSelectSubmitDialog(this, phone_number, timeStamp);
        chooseFrom = "image_content";
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
                    s = RealPathFromUriUtils.getRealPathFromUri(this, data.getData());
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
            if (chooseFrom.equals("image_content")) {
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
                Glide.with(this).load(s).into(iv_commodity_image_show);
                image_show = s;
                iv_delete_commodity_image_show.setVisibility(View.VISIBLE);
            }
        } else {
            Toast.makeText(this, "取消选择", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
