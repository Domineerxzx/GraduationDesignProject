package com.triplebro.domineer.graduationdesignproject.managers;

import android.content.Context;

import com.triplebro.domineer.graduationdesignproject.beans.TypeConcreteInfo;
import com.triplebro.domineer.graduationdesignproject.beans.TypeGeneralizeInfo;
import com.triplebro.domineer.graduationdesignproject.sourceop.DatabaseOP;

import java.util.List;

public class TypeManager {

    private Context context;

    public TypeManager(Context context) {
        this.context = context;
    }

    public List<TypeGeneralizeInfo> getGeneralizeTypeList() {
        List<TypeGeneralizeInfo> typeGeneralizeInfoList;
        DatabaseOP databaseOP = new DatabaseOP(context);
        typeGeneralizeInfoList = databaseOP.getGeneralizeTypeList();
        return typeGeneralizeInfoList;
    }

    public List<TypeConcreteInfo> getConcreteTypeList(int type_generalize_id) {
        List<TypeConcreteInfo> typeConcreteInfoList;
        DatabaseOP databaseOP = new DatabaseOP(context);
        typeConcreteInfoList = databaseOP.getConcreteTypeList(type_generalize_id);
        return typeConcreteInfoList;
    }
}
