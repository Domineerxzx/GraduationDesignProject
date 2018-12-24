package com.triplebro.domineer.graduationdesignproject.utils.dialogUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.triplebro.domineer.graduationdesignproject.R;
import com.triplebro.domineer.graduationdesignproject.adapters.SizeChooseAdapter;
import com.triplebro.domineer.graduationdesignproject.beans.CommodityInfo;
import com.triplebro.domineer.graduationdesignproject.beans.CommoditySizeInfo;
import com.triplebro.domineer.graduationdesignproject.beans.ShoppingCartInfo;
import com.triplebro.domineer.graduationdesignproject.beans.TypeConcreteInfo;
import com.triplebro.domineer.graduationdesignproject.database.MyOpenHelper;
import com.triplebro.domineer.graduationdesignproject.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AddShoppingCartDialog {

    private static CommoditySizeInfo commoditySizeInfo;
    private static int size_count;

    public static void showDialog(final Context context, final CommodityInfo commodityInfo, final List<CommoditySizeInfo> commoditySizeInfoList) {
        final Dialog mShareDialog = new Dialog(context, R.style.dialog_shopping_cart);
        mShareDialog.setCanceledOnTouchOutside(true);
        mShareDialog.setCancelable(true);
        Window window = mShareDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        View view = View.inflate(context, R.layout.dialog_shopping_cart, null);
        RecyclerView rv_size_bt = view.findViewById(R.id.rv_size_bt);
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);
        TextView tv_add_shopping_cart = view.findViewById(R.id.tv_add_shopping_cart);
        final TextView tv_size = view.findViewById(R.id.tv_size);
        final TextView tv_commodity_count = view.findViewById(R.id.tv_commodity_count);
        final TextView tv_count = view.findViewById(R.id.tv_count);
        Button bt_count_down = view.findViewById(R.id.bt_count_down);
        Button bt_count_up = view.findViewById(R.id.bt_count_up);
        rv_size_bt.setLayoutManager(new GridLayoutManager(context, 5));
        SizeChooseAdapter sizeChooseAdapter = new SizeChooseAdapter(context, commoditySizeInfoList);
        rv_size_bt.setAdapter(sizeChooseAdapter);
        sizeChooseAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                commoditySizeInfo = commoditySizeInfoList.get(position);
                tv_commodity_count.setText(String.valueOf(commoditySizeInfoList.get(position).getSize_count()));
                tv_size.setText(commoditySizeInfoList.get(position).getSize_name() + "号");
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mShareDialog != null && mShareDialog.isShowing()) {
                    mShareDialog.dismiss();
                }
            }
        });
        tv_add_shopping_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mShareDialog != null && mShareDialog.isShowing()) {

                    ShoppingCartInfo shoppingCartInfo = new ShoppingCartInfo();
                    shoppingCartInfo.setCommodity_id(commodityInfo.getCommodity_id());
                    shoppingCartInfo.setCommodity_name(commodityInfo.getCommodity_name());
                    shoppingCartInfo.setCommodity_image(commodityInfo.getCommodity_image());
                    String phone_number = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString("phone_number", "");
                    if (phone_number.length() == 0) {
                        Toast.makeText(context, "东西跑不了，登录回来再买吧！！！", Toast.LENGTH_SHORT).show();
                        mShareDialog.dismiss();
                    }
                    shoppingCartInfo.setSize_name(commoditySizeInfo.getSize_name());
                    shoppingCartInfo.setCount(Integer.parseInt(tv_count.getText().toString()));
                    shoppingCartInfo.setPhone_number(phone_number);
                    shoppingCartInfo.setPrice(commodityInfo.getPrice());
                    AddShoppingCartDialog.uploadShoppingCartInfo(context, shoppingCartInfo, tv_commodity_count);
                    /*mShareDialog.dismiss();*/
                }
            }
        });
        bt_count_down.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int count = Integer.valueOf(tv_count.getText().toString());
                if (count == 1) {
                    Toast.makeText(context, "不能再少了！！！", Toast.LENGTH_SHORT).show();
                } else {
                    count = count - 1;
                    tv_count.setText(String.valueOf(count));
                }
            }
        });
        bt_count_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer integer = new Integer(tv_count.getText().toString());
                int i = integer.intValue();
                if (i < Integer.parseInt(tv_commodity_count.getText().toString())) {
                    i = i + 1;
                    tv_count.setText(String.valueOf(i));
                } else {
                    Toast.makeText(context, "不能再多了，都被你买走了！！！", Toast.LENGTH_SHORT).show();
                }

            }
        });
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
        mShareDialog.show();
    }

    private static void uploadShoppingCartInfo(Context context, ShoppingCartInfo shoppingCartInfo, TextView tv_commodity_count) {

        int count_old = 0;
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor commoditySizeInfoCursor = db.query("commoditySizeInfo", new String[]{"size_count"}, "commodity_id = ? and size_name = ?", new String[]{String.valueOf(shoppingCartInfo.getCommodity_id()), shoppingCartInfo.getSize_name()},
                null, null, null);
        if (commoditySizeInfoCursor != null && commoditySizeInfoCursor.getCount() > 0) {
            while (commoditySizeInfoCursor.moveToNext()) {
                size_count = commoditySizeInfoCursor.getInt(0);
            }
        }
        if (commoditySizeInfoCursor != null) {
            commoditySizeInfoCursor.close();
        }
        Cursor shoppingCartInfoCursor = db.query("shoppingCartInfo", null,
                "commodity_id = ? and size_name = ?",
                new String[]{String.valueOf(shoppingCartInfo.getCommodity_id()), shoppingCartInfo.getSize_name()},
                null, null, null);
        if (shoppingCartInfoCursor != null && shoppingCartInfoCursor.getCount() > 0) {
            while (shoppingCartInfoCursor.moveToNext()) {
                count_old = shoppingCartInfoCursor.getInt(3);
            }
            contentValues.put("count", shoppingCartInfo.getCount() + count_old);
            if (size_count < shoppingCartInfo.getCount()) {
                Toast.makeText(context, "不能再买了，都被你买走了！！！", Toast.LENGTH_SHORT).show();
            } else {
                int shoppingCartUpdateResult = db.update("shoppingCartInfo", contentValues, "commodity_id = ? and size_name = ?", new String[]{String.valueOf(shoppingCartInfo.getCommodity_id()), shoppingCartInfo.getSize_name()});
                if (shoppingCartUpdateResult >= 0) {
                    Toast.makeText(context, "加入购物车成功，去付款吧！！！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "出错了，加入购物车失败", Toast.LENGTH_SHORT).show();
                }
                contentValues = new ContentValues();
                size_count = size_count - shoppingCartInfo.getCount();
                contentValues.put("size_count", size_count);
                db.update("commoditySizeInfo", contentValues, "commodity_id = ? and size_name = ?", new String[]{String.valueOf(shoppingCartInfo.getCommodity_id()), shoppingCartInfo.getSize_name()});
                tv_commodity_count.setText(String.valueOf(size_count));
            }

        } else {
            contentValues.put("commodity_id", shoppingCartInfo.getCommodity_id());
            contentValues.put("size_name", shoppingCartInfo.getSize_name());
            contentValues.put("count", shoppingCartInfo.getCount());
            contentValues.put("commodity_name", shoppingCartInfo.getCommodity_name());
            contentValues.put("phone_number", shoppingCartInfo.getPhone_number());
            contentValues.put("commodity_image", shoppingCartInfo.getCommodity_image());
            contentValues.put("price", shoppingCartInfo.getPrice());
            long shoppingCartInsetResult = db.insert("shoppingCartInfo", null, contentValues);
            if (shoppingCartInsetResult >= 0) {
                Toast.makeText(context, "加入购物车成功，去付款吧！！！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "出错了，加入购物车失败", Toast.LENGTH_SHORT).show();
            }
            contentValues = new ContentValues();
            size_count = size_count - shoppingCartInfo.getCount();
            contentValues.put("size_count", size_count);
            db.update("commoditySizeInfo", contentValues, "commodity_id = ? and size_name = ?", new String[]{String.valueOf(shoppingCartInfo.getCommodity_id()), shoppingCartInfo.getSize_name()});
            tv_commodity_count.setText(String.valueOf(size_count));
        }
        if (shoppingCartInfoCursor != null) {
            shoppingCartInfoCursor.close();
        }
        db.close();
    }
}
