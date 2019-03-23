package com.triplebro.domineer.graduationdesignproject.managers;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;

import com.triplebro.domineer.graduationdesignproject.beans.AdminInfo;
import com.triplebro.domineer.graduationdesignproject.beans.TypeConcreteInfo;
import com.triplebro.domineer.graduationdesignproject.beans.TypeGeneralizeInfo;
import com.triplebro.domineer.graduationdesignproject.beans.UserInfo;
import com.triplebro.domineer.graduationdesignproject.handlers.DataInsertHandler;
import com.triplebro.domineer.graduationdesignproject.properties.ProjectProperties;
import com.triplebro.domineer.graduationdesignproject.sourceop.DatabaseOP;
import com.triplebro.domineer.graduationdesignproject.utils.xml.AdminInfoParser;
import com.triplebro.domineer.graduationdesignproject.utils.xml.TypeConcreteParser;
import com.triplebro.domineer.graduationdesignproject.utils.xml.TypeGeneralizeParser;
import com.triplebro.domineer.graduationdesignproject.utils.xml.UserInfoParser;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Domineer
 * @data 2019/3/24,0:10
 * ----------为梦想启航---------
 * --Set Sell For Your Dream--
 */
public class SplashManager {

    private Context context;

    public SplashManager(Context context) {
        this.context = context;
    }

    public List<String> checkData(List<String> table_name) {
        List<String> nonentity_table_name = new ArrayList<>();
        DatabaseOP databaseOP = new DatabaseOP(context);
        for (String tableName : table_name) {
            boolean isExist = databaseOP.checkTableIsExist(tableName);
            if (!isExist) {
                nonentity_table_name.add(tableName);
            }
        }
        return nonentity_table_name;
    }

    public void uploadData(List<String> nonentity_table_name, DataInsertHandler dataInsertHandler) {

        AssetManager assets = context.getAssets();
        DatabaseOP databaseOP = new DatabaseOP(context);
        try {
            for (String nonentityTableName : nonentity_table_name) {
                InputStream inputStream = null;
                String assetsFileName;
                if (nonentityTableName.equals("adminInfo")) {
                    assetsFileName = "AdminInfoList.xml";
                    AdminInfoParser xmlParser = new AdminInfoParser();
                    inputStream = assets.open(assetsFileName);
                    List<AdminInfo> list = xmlParser.parseXML(inputStream);
                    for (AdminInfo adminInfo : list) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("phone_number", adminInfo.getPhone_number());
                        contentValues.put("password", adminInfo.getPassword());
                        contentValues.put("nickname", adminInfo.getNickname());
                        contentValues.put("user_head", adminInfo.getUser_head());
                        databaseOP.insertAdminInfo(contentValues);
                    }
                } else if (nonentityTableName.equals("userInfo")) {
                    assetsFileName = "UserInfoList.xml";
                    UserInfoParser xmlParser = new UserInfoParser();
                    inputStream = assets.open(assetsFileName);
                    List<UserInfo> list = xmlParser.parseXML(inputStream);
                    for (UserInfo userInfo : list) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("phone_number", userInfo.getPhone_number());
                        contentValues.put("password", userInfo.getPassword());
                        contentValues.put("nickname", userInfo.getNickname());
                        contentValues.put("user_head", userInfo.getUser_head());
                        databaseOP.insertUserInfo(contentValues);
                    }
                } else if (nonentityTableName.equals("typeGeneralize")) {
                    assetsFileName = "TypeGeneralizeList.xml";
                    TypeGeneralizeParser xmlParser = new TypeGeneralizeParser();
                    inputStream = assets.open(assetsFileName);
                    List<TypeGeneralizeInfo> list = xmlParser.parseXML(inputStream);
                    for (TypeGeneralizeInfo typeGeneralizeInfo : list) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("type_generalize_id", typeGeneralizeInfo.getType_generalize_id());
                        contentValues.put("type_generalize_name", typeGeneralizeInfo.getType_generalize_name());
                        databaseOP.insertTypeGeneralize(contentValues);
                    }
                } else {
                    assetsFileName = "TypeConcreteList.xml";
                    TypeConcreteParser xmlParser = new TypeConcreteParser();
                    inputStream = assets.open(assetsFileName);
                    List<TypeConcreteInfo> list = xmlParser.parseXML(inputStream);
                    for (TypeConcreteInfo typeConcreteInfo : list) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("type_concrete_id", typeConcreteInfo.getType_concrete_id());
                        contentValues.put("type_generalize_id", typeConcreteInfo.getType_generalize_id());
                        contentValues.put("type_concrete_name", typeConcreteInfo.getType_concrete_name());
                        contentValues.put("type_concrete_image", typeConcreteInfo.getType_concrete_image());
                        databaseOP.insertTypeConcrete(contentValues);
                    }
                }
            }
            dataInsertHandler.sendEmptyMessage(ProjectProperties.DATA_INSERT_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            dataInsertHandler.sendEmptyMessage(ProjectProperties.DATA_INSERT_FAILED);
        } catch (SAXException e) {
            e.printStackTrace();
            dataInsertHandler.sendEmptyMessage(ProjectProperties.DATA_INSERT_FAILED);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            dataInsertHandler.sendEmptyMessage(ProjectProperties.DATA_INSERT_FAILED);
        }
    }
}
