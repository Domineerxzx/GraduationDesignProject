package com.triplebro.domineer.graduationdesignproject.sourceop;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.triplebro.domineer.graduationdesignproject.activities.CollectionCommodityActivity;
import com.triplebro.domineer.graduationdesignproject.beans.CollectionCommodityInfo;
import com.triplebro.domineer.graduationdesignproject.beans.CollectionSubmitInfo;
import com.triplebro.domineer.graduationdesignproject.beans.CommodityInfo;
import com.triplebro.domineer.graduationdesignproject.beans.CommoditySizeInfo;
import com.triplebro.domineer.graduationdesignproject.beans.SubmitInfo;
import com.triplebro.domineer.graduationdesignproject.beans.TypeConcreteInfo;
import com.triplebro.domineer.graduationdesignproject.beans.TypeGeneralizeInfo;
import com.triplebro.domineer.graduationdesignproject.database.MyOpenHelper;
import com.triplebro.domineer.graduationdesignproject.handlers.OssHandler;
import com.triplebro.domineer.graduationdesignproject.properties.ProjectProperties;
import com.triplebro.domineer.graduationdesignproject.utils.ossUtils.UploadUtils;

import java.util.ArrayList;
import java.util.List;

public class DatabaseOP {

    private Context context;

    public DatabaseOP(Context context) {
        this.context = context;
    }

    public boolean getIsCollection(int commodity_id) {
        SharedPreferences userInfo = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String phone_number = userInfo.getString("phone_number", "");
        if (phone_number.length() == 0) {
            return false;
        } else {
            MyOpenHelper myOpenHelper = new MyOpenHelper(context);
            SQLiteDatabase db = myOpenHelper.getWritableDatabase();
            Cursor collectionCommodityInfo = db.query("collectionCommodityInfo", null, "commodity_id = ? and phone_number = ?", new String[]{String.valueOf(commodity_id), phone_number}, null, null, null);
            if (collectionCommodityInfo != null && collectionCommodityInfo.getCount() > 0) {
                collectionCommodityInfo.moveToNext();
                collectionCommodityInfo.close();
                db.close();
                return true;
            } else {
                collectionCommodityInfo.close();
                db.close();
                return false;
            }
        }
    }

    public boolean getIsCollectionSubmit(int submit_id) {
        SharedPreferences userInfo = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String phone_number = userInfo.getString("phone_number", "");
        if (phone_number.length() == 0) {
            return false;
        } else {
            MyOpenHelper myOpenHelper = new MyOpenHelper(context);
            SQLiteDatabase db = myOpenHelper.getWritableDatabase();
            Cursor collectionSubmitInfo = db.query("collectionSubmitInfo", null, "submit_id = ? and phone_number = ?", new String[]{String.valueOf(submit_id), phone_number}, null, null, null);
            if (collectionSubmitInfo != null && collectionSubmitInfo.getCount() > 0) {
                collectionSubmitInfo.moveToNext();
                collectionSubmitInfo.close();
                db.close();
                return true;
            } else {
                collectionSubmitInfo.close();
                db.close();
                return false;
            }
        }
    }

    public List<CollectionCommodityInfo> getCommodityCollectionInfoList(String phone_number) {
        List<CollectionCommodityInfo> collectionCommodityInfoList = new ArrayList<>();
        if (phone_number.length() == 0) {
            return collectionCommodityInfoList;
        } else {
            MyOpenHelper myOpenHelper = new MyOpenHelper(context);
            SQLiteDatabase db = myOpenHelper.getWritableDatabase();
            Cursor collectionCommodityInfoCursor = db.query("collectionCommodityInfo", null, "phone_number = ?", new String[]{phone_number}, null, null, null);
            if (collectionCommodityInfoCursor != null && collectionCommodityInfoCursor.getCount() > 0) {
                while (collectionCommodityInfoCursor.moveToNext()) {
                    CollectionCommodityInfo collectionCommodityInfo = new CollectionCommodityInfo();
                    collectionCommodityInfo.setPhone_number(phone_number);
                    collectionCommodityInfo.setCommodity_id(collectionCommodityInfoCursor.getInt(2));
                    collectionCommodityInfoList.add(collectionCommodityInfo);
                }
                collectionCommodityInfoCursor.close();
                db.close();
                return collectionCommodityInfoList;
            } else {
                if (collectionCommodityInfoCursor != null) {
                    collectionCommodityInfoCursor.close();
                }
                db.close();
                return collectionCommodityInfoList;
            }
        }
    }

    public List<CollectionSubmitInfo> getSubmitCollectionInfoList(String phone_number) {
        List<CollectionSubmitInfo> collectionSubmitInfoList = new ArrayList<>();
        if (phone_number.length() == 0) {
            return collectionSubmitInfoList;
        } else {
            MyOpenHelper myOpenHelper = new MyOpenHelper(context);
            SQLiteDatabase db = myOpenHelper.getWritableDatabase();
            Cursor collectionSubmitInfoCursor = db.query("collectionSubmitInfo", null, "phone_number = ?", new String[]{phone_number}, null, null, null);
            if (collectionSubmitInfoCursor != null && collectionSubmitInfoCursor.getCount() > 0) {
                while (collectionSubmitInfoCursor.moveToNext()) {
                    CollectionSubmitInfo collectionSubmitInfo = new CollectionSubmitInfo();
                    collectionSubmitInfo.setPhone_number(phone_number);
                    collectionSubmitInfo.setSubmit_id(collectionSubmitInfoCursor.getInt(2));
                    collectionSubmitInfoList.add(collectionSubmitInfo);
                }
                collectionSubmitInfoCursor.close();
                db.close();
                return collectionSubmitInfoList;
            } else {
                if (collectionSubmitInfoCursor != null) {
                    collectionSubmitInfoCursor.close();
                }
                db.close();
                return collectionSubmitInfoList;
            }
        }
    }

    public List<CommodityInfo> getCollectedCommodityInfoList(List<CollectionCommodityInfo> collectionInfoList) {
        List<CommodityInfo> commodityInfoList = new ArrayList<>();
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        for (CollectionCommodityInfo collectionCommodityInfo : collectionInfoList) {
            int commodity_id = collectionCommodityInfo.getCommodity_id();
            Cursor commodityInfoCursor = db.query("commodityInfo", null, "commodity_id = ?", new String[]{String.valueOf(commodity_id)}, null, null, null);
            if (commodityInfoCursor != null && commodityInfoCursor.getCount() > 0) {
                commodityInfoCursor.moveToNext();
                CommodityInfo commodityInfo = new CommodityInfo();
                commodityInfo.setCommodity_id(commodityInfoCursor.getInt(0));
                commodityInfo.setCommodity_name(commodityInfoCursor.getString(1));
                commodityInfo.setPrice(commodityInfoCursor.getInt(2));
                commodityInfo.setCommodity_image(commodityInfoCursor.getString(3));
                commodityInfo.setType_generalize_id(commodityInfoCursor.getInt(4));
                commodityInfo.setType_concrete_id(commodityInfoCursor.getInt(5));
                commodityInfo.setPhone_number(commodityInfoCursor.getString(6));
                commodityInfoList.add(commodityInfo);
                commodityInfoCursor.close();
            } else {
                if (commodityInfoCursor != null) {
                    commodityInfoCursor.close();
                }
            }
        }
        db.close();
        return commodityInfoList;
    }

    public List<SubmitInfo> getCollectedSubmitInfoList(List<CollectionSubmitInfo> collectionInfoList) {
        List<SubmitInfo> submitInfoList = new ArrayList<>();
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        for (CollectionSubmitInfo collectionSubmitInfo : collectionInfoList) {
            int submit_id = collectionSubmitInfo.getSubmit_id();
            Cursor submitInfoCursor = db.query("submitInfo", null, "submit_id = ?", new String[]{String.valueOf(submit_id)}, null, null, null);
            if (submitInfoCursor != null && submitInfoCursor.getCount() > 0) {
                submitInfoCursor.moveToNext();
                SubmitInfo submitInfo = new SubmitInfo();
                submitInfo.setSubmit_id(submitInfoCursor.getInt(0));
                submitInfo.setPhone_number(submitInfoCursor.getString(1));
                submitInfo.setNickname(submitInfoCursor.getString(2));
                submitInfo.setUser_head(submitInfoCursor.getString(3));
                submitInfo.setSubmit_content(submitInfoCursor.getString(4));
                submitInfoList.add(submitInfo);
                submitInfoCursor.close();
            } else {
                if (submitInfoCursor != null) {
                    submitInfoCursor.close();
                }
            }
        }
        db.close();
        return submitInfoList;
    }

    public List<CommodityInfo> getCommodityRecommendInfoList() {
        List<CommodityInfo> commodityInfoList = new ArrayList<>();
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        Cursor commodityRecommendInfoCursor = db.query("commodityInfo", null, null, null, null, null, "commodity_id desc");
        if (commodityRecommendInfoCursor != null && commodityRecommendInfoCursor.getCount() > 0) {
            while (commodityRecommendInfoCursor.moveToNext()) {
                CommodityInfo commodityInfo = new CommodityInfo();
                commodityInfo.setCommodity_id(commodityRecommendInfoCursor.getInt(0));
                commodityInfo.setCommodity_name(commodityRecommendInfoCursor.getString(1));
                commodityInfo.setPrice(commodityRecommendInfoCursor.getInt(2));
                commodityInfo.setCommodity_image(commodityRecommendInfoCursor.getString(3));
                commodityInfoList.add(commodityInfo);
            }
        }
        if (commodityRecommendInfoCursor != null) {
            commodityRecommendInfoCursor.close();
        }
        db.close();
        return commodityInfoList;
    }

    public List<CommodityInfo> getSearchInfoList(String searchKey) {
        List<CommodityInfo> commodityInfoList = new ArrayList<>();
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        Cursor commodityInfoCursor = db.query("commodityInfo", new String[]{"commodity_id", "commodity_name", "price", "commodity_image"}, "commodity_name like ?", new String[]{"%" + searchKey + "%"}, null, null, null);
        if (commodityInfoCursor != null && commodityInfoCursor.getCount() > 0) {
            while (commodityInfoCursor.moveToNext()) {
                CommodityInfo commodityInfo = new CommodityInfo();
                commodityInfo.setCommodity_id(commodityInfoCursor.getInt(0));
                commodityInfo.setCommodity_name(commodityInfoCursor.getString(1));
                commodityInfo.setPrice(commodityInfoCursor.getInt(2));
                commodityInfo.setCommodity_image(commodityInfoCursor.getString(3));
                commodityInfoList.add(commodityInfo);
            }
        }
        if (commodityInfoCursor != null) {
            commodityInfoCursor.close();
        }
        db.close();
        return commodityInfoList;
    }

    public List<CommodityInfo> getTypeRecommendCommodityList(int type_id) {
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        List<CommodityInfo> commodityInfoList = new ArrayList<>();
        Cursor commodityInfoCursor;
        switch (type_id) {
            case 0:
                commodityInfoCursor = db.query("commodityInfo", new String[]{"commodity_id", "commodity_name", "price", "commodity_image"}, "type_generalize_id = ? or type_generalize_id = ?", new String[]{String.valueOf(1), String.valueOf(2)}, null, null, null);
                if (commodityInfoCursor != null && commodityInfoCursor.getCount() > 0) {
                    while (commodityInfoCursor.moveToNext()) {
                        CommodityInfo commodityInfo = new CommodityInfo();
                        commodityInfo.setCommodity_id(commodityInfoCursor.getInt(0));
                        commodityInfo.setCommodity_name(commodityInfoCursor.getString(1));
                        commodityInfo.setPrice(commodityInfoCursor.getInt(2));
                        commodityInfo.setCommodity_image(commodityInfoCursor.getString(3));
                        commodityInfoList.add(commodityInfo);
                    }
                }
                break;
            default:
                commodityInfoCursor = db.query("commodityInfo", new String[]{"commodity_id", "commodity_name", "price", "commodity_image"}, "type_generalize_id = ?", new String[]{String.valueOf(type_id)}, null, null, null);
                if (commodityInfoCursor != null && commodityInfoCursor.getCount() > 0) {
                    while (commodityInfoCursor.moveToNext()) {
                        CommodityInfo commodityInfo = new CommodityInfo();
                        commodityInfo.setCommodity_id(commodityInfoCursor.getInt(0));
                        commodityInfo.setCommodity_name(commodityInfoCursor.getString(1));
                        commodityInfo.setPrice(commodityInfoCursor.getInt(2));
                        commodityInfo.setCommodity_image(commodityInfoCursor.getString(3));
                        commodityInfoList.add(commodityInfo);
                    }
                }
                break;
        }
        if (commodityInfoCursor != null) {
            commodityInfoCursor.close();
        }
        db.close();
        return commodityInfoList;
    }

    public boolean addCommodityCollection(int commodity_id) {

        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        SharedPreferences userInfo = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String phone_number = userInfo.getString("phone_number", "");
        if (phone_number == null || phone_number.length() == 0) {
            Toast.makeText(context, "还没登录呢，不能收藏商品", Toast.LENGTH_SHORT).show();
            db.close();
            return false;
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("phone_number", phone_number);
            contentValues.put("commodity_id", commodity_id);
            long collectionCommodityInfo = db.insert("collectionCommodityInfo", null, contentValues);
            if (collectionCommodityInfo >= 0) {
                Toast.makeText(context, "添加收藏成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "添加收藏失败", Toast.LENGTH_SHORT).show();
            }
            db.close();
            return true;
        }
    }

    public boolean deleteCommodityCollection(int commodity_id) {

        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        SharedPreferences userInfo = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String phone_number = userInfo.getString("phone_number", "");
        int collectionCommodityInfo = db.delete("collectionCommodityInfo", "phone_number = ? and commodity_id = ?", new String[]{phone_number, String.valueOf(commodity_id)});
        if (collectionCommodityInfo >= 0) {
            Toast.makeText(context, "取消收藏成功", Toast.LENGTH_SHORT).show();
            db.close();
            return true;
        } else {
            Toast.makeText(context, "取消收藏失败", Toast.LENGTH_SHORT).show();
            db.close();
            return false;
        }
    }

    public List<TypeGeneralizeInfo> getCommodityGeneralizeType() {
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        List<TypeGeneralizeInfo> typeGeneralizeInfoList = new ArrayList<>();
        Cursor typeGeneralize = db.query("typeGeneralize", null, null, null, null, null, null);
        if (typeGeneralize != null && typeGeneralize.getCount() > 0) {
            while (typeGeneralize.moveToNext()) {
                TypeGeneralizeInfo typeGeneralizeInfo = new TypeGeneralizeInfo();
                typeGeneralizeInfo.setType_generalize_id(typeGeneralize.getInt(0));
                typeGeneralizeInfo.setType_generalize_name(typeGeneralize.getString(1));
                typeGeneralizeInfoList.add(typeGeneralizeInfo);
            }
        }
        if (typeGeneralize != null) {
            typeGeneralize.close();
        }
        db.close();
        return typeGeneralizeInfoList;
    }

    public List<TypeConcreteInfo> getCommodityConcreteType(int type_generalize_id) {
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        List<TypeConcreteInfo> typeConcreteInfoList = new ArrayList<>();
        Cursor typeConcrete = db.query("typeConcrete", null, "type_generalize_id = ?", new String[]{String.valueOf(type_generalize_id)}, null, null, null);
        if (typeConcrete != null && typeConcrete.getCount() > 0) {
            while (typeConcrete.moveToNext()) {
                TypeConcreteInfo typeConcreteInfo = new TypeConcreteInfo();
                typeConcreteInfo.setType_concrete_id(typeConcrete.getInt(0));
                typeConcreteInfo.setType_concrete_name(typeConcrete.getString(2));
                typeConcreteInfoList.add(typeConcreteInfo);
            }
        }
        if (typeConcrete != null) {
            typeConcrete.close();
        }
        db.close();
        return typeConcreteInfoList;
    }

    public TypeGeneralizeInfo getCommodityGeneralizeTypeInfo(int type_generalize_id) {
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        Cursor typeGeneralize = db.query("typeGeneralize", null, "type_generalize_id = ?", new String[]{String.valueOf(type_generalize_id)}, null, null, null);
        if (typeGeneralize != null && typeGeneralize.getCount() > 0) {
            typeGeneralize.moveToNext();
            TypeGeneralizeInfo typeGeneralizeInfo = new TypeGeneralizeInfo();
            typeGeneralizeInfo.setType_generalize_id(typeGeneralize.getInt(0));
            typeGeneralizeInfo.setType_generalize_name(typeGeneralize.getString(1));
            typeGeneralize.close();
            return typeGeneralizeInfo;
        }
        return null;
    }

    public TypeConcreteInfo getCommodityConcreteTypeInfo(int type_concrete_id) {
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        Cursor typeConcrete = db.query("typeConcrete", null, "type_concrete_id = ?", new String[]{String.valueOf(type_concrete_id)}, null, null, null);
        if (typeConcrete != null && typeConcrete.getCount() > 0) {
            typeConcrete.moveToNext();
            TypeConcreteInfo typeConcreteInfo = new TypeConcreteInfo();
            typeConcreteInfo.setType_concrete_id(typeConcrete.getInt(0));
            typeConcreteInfo.setType_concrete_name(typeConcrete.getString(2));
            typeConcrete.close();
            db.close();
            return typeConcreteInfo;
        }
        return null;
    }


    public int addCommodityInfo(ContentValues commodityInfo) {
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        long insert = db.insert("commodityInfo", null, commodityInfo);
        OssHandler ossHandler = new OssHandler(context);
        UploadUtils.uploadFileToOss(ossHandler,ProjectProperties.BUCKET_NAME,"xuzhanxin/"+commodityInfo.getAsString("commodity_image"),commodityInfo.getAsString("commodity_image"));
        if (insert != -1) {
            Toast.makeText(context, "添加商品信息成功", Toast.LENGTH_SHORT).show();
            Cursor cursor = db.query("commodityInfo", new String[]{"commodity_id"}, "phone_number = ?", new String[]{commodityInfo.getAsString("phone_number")}, null, null, "commodity_id desc");
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToNext();
                int commodity_id = cursor.getInt(0);
                cursor.close();
                db.close();
                return commodity_id;
            }
            if (cursor != null) {
                cursor.close();
            }
            db.close();
            return -1;
        } else {
            Toast.makeText(context, "添加商品信息失败", Toast.LENGTH_SHORT).show();
            db.close();
            return -1;
        }
    }

    public void addCommoditySizeInfo(ContentValues sizeInfo) {
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        long commoditySizeInfo = db.insert("commoditySizeInfo", null, sizeInfo);
        if (commoditySizeInfo != -1) {
            Toast.makeText(context, "添加商品尺码及库存成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "添加商品尺码及库存失败", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void addCommodityImageInfo(ContentValues imageInfo) {
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        long commoditySizeInfo = db.insert("commodityImageInfo", null, imageInfo);
        OssHandler ossHandler = new OssHandler(context);
        UploadUtils.uploadFileToOss(ossHandler,ProjectProperties.BUCKET_NAME,"xuzhanxin/"+imageInfo.getAsString("commodity_image"),imageInfo.getAsString("commodity_image"));
        if (commoditySizeInfo != -1) {
            Toast.makeText(context, "添加商品图片成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "添加商品图片失败", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public List<CommodityInfo> getCommodityInfoList(String phone_number) {
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        List<CommodityInfo> commodityInfoList = new ArrayList<>();
        Cursor commodityInfoCursor = db.query("commodityInfo", null, "phone_number = ?", new String[]{phone_number}, null, null, null);
        if (commodityInfoCursor != null && commodityInfoCursor.getCount() > 0) {
            while (commodityInfoCursor.moveToNext()) {
                CommodityInfo commodityInfo = new CommodityInfo();
                commodityInfo.setCommodity_id(commodityInfoCursor.getInt(0));
                commodityInfo.setCommodity_name(commodityInfoCursor.getString(1));
                commodityInfo.setPrice(commodityInfoCursor.getInt(2));
                commodityInfo.setCommodity_image(commodityInfoCursor.getString(3));
                commodityInfo.setType_generalize_id(commodityInfoCursor.getInt(4));
                commodityInfo.setType_concrete_id(commodityInfoCursor.getInt(5));
                commodityInfo.setPhone_number(commodityInfoCursor.getString(6));
                commodityInfoList.add(commodityInfo);
            }
        }
        if (commodityInfoCursor != null) {
            commodityInfoCursor.close();
        }
        db.close();
        return commodityInfoList;
    }

    public List<String> getCommodityImageList(int commodity_id) {
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        List<String> commodityImageList = new ArrayList<>();
        Cursor commodityImageInfo = db.query("commodityImageInfo", null, "commodity_id = ?", new String[]{String.valueOf(commodity_id)}, null, null, null);
        if(commodityImageInfo != null && commodityImageInfo.getCount() > 0){
            while (commodityImageInfo.moveToNext()){
                String s = commodityImageInfo.getString(2);
                commodityImageList.add(s);
            }
        }
        if (commodityImageInfo != null) {
            commodityImageInfo.close();
        }
        db.close();
        return commodityImageList;
    }

    public List<CommoditySizeInfo> getCommoditySizeInfoList(int commodity_id) {
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        List<CommoditySizeInfo> commoditySizeInfoList = new ArrayList<>();
        Cursor commoditySizeInfoCursor = db.query("commoditySizeInfo", null, "commodity_id = ?", new String[]{String.valueOf(commodity_id)}, null, null, null);
        if(commoditySizeInfoCursor != null && commoditySizeInfoCursor.getCount() > 0){
            while (commoditySizeInfoCursor.moveToNext()){
                CommoditySizeInfo commoditySizeInfo = new CommoditySizeInfo();
                commoditySizeInfo.setSize_name(commoditySizeInfoCursor.getString(2));
                commoditySizeInfo.setSize_count(commoditySizeInfoCursor.getInt(3));
                commoditySizeInfoList.add(commoditySizeInfo);
            }
        }
        if (commoditySizeInfoCursor != null) {
            commoditySizeInfoCursor.close();
        }
        db.close();
        return commoditySizeInfoList;
    }

    public void deleteCommodity(int commodity_id) {
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        db.delete("commodityImageInfo","commodity_id = ?",new String[]{String.valueOf(commodity_id)});
        db.delete("commoditySizeInfo","commodity_id = ?",new String[]{String.valueOf(commodity_id)});
        db.delete("collectionCommodityInfo","commodity_id = ?",new String[]{String.valueOf(commodity_id)});
        db.delete("shoppingCartInfo","commodity_id = ?",new String[]{String.valueOf(commodity_id)});
        db.delete("commodityRecommendInfo","commodity_id = ?",new String[]{String.valueOf(commodity_id)});
        db.delete("commodityInfo","commodity_id = ?",new String[]{String.valueOf(commodity_id)});
    }
}
