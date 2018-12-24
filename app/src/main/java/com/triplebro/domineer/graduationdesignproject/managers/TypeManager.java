package com.triplebro.domineer.graduationdesignproject.managers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.triplebro.domineer.graduationdesignproject.beans.TypeConcreteInfo;
import com.triplebro.domineer.graduationdesignproject.beans.TypeGeneralizeInfo;
import com.triplebro.domineer.graduationdesignproject.database.MyOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TypeManager {

    private Context context;

    public TypeManager(Context context) {
        this.context = context;
    }

    public List<TypeGeneralizeInfo> getGeneralizeTypeList() {
        List<TypeGeneralizeInfo> typeGeneralizeInfoList = new ArrayList<>();
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        Cursor typeGeneralize = db.query("typeGeneralize", null, null, null, null, null, null);
        if(typeGeneralize != null && typeGeneralize.getCount() > 0){
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

    public List<TypeConcreteInfo> getConcreteTypeList(int type_generalize_id) {
        List<TypeConcreteInfo> typeConcreteInfoList = new ArrayList<>();
        MyOpenHelper myOpenHelper = new MyOpenHelper(context);
        SQLiteDatabase db = myOpenHelper.getWritableDatabase();
        Cursor typeConcrete = db.query("typeConcrete", new String[]{"type_concrete_id","type_concrete_name","type_concrete_image"},
                "type_generalize_id = ?", new String[]{String.valueOf(type_generalize_id)}, null, null, null);
        if(typeConcrete != null && typeConcrete.getCount() > 0){
            while (typeConcrete.moveToNext()) {
                TypeConcreteInfo typeConcreteInfo = new TypeConcreteInfo();
                typeConcreteInfo.setType_concrete_id(typeConcrete.getInt(0));
                typeConcreteInfo.setType_concrete_name(typeConcrete.getString(1));
                typeConcreteInfo.setType_concrete_image(typeConcrete.getString(2));
                typeConcreteInfoList.add(typeConcreteInfo);
            }
        }
        if (typeConcrete != null) {
            typeConcrete.close();
        }
        db.close();
        return typeConcreteInfoList;
    }
}
