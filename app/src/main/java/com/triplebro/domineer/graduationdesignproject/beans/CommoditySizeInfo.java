package com.triplebro.domineer.graduationdesignproject.beans;

import java.io.Serializable;

public class CommoditySizeInfo implements Serializable {

    private int _id;
    private int commodity_id;
    private String size_name;
    private int size_count;
    private String phone_number;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getCommodity_id() {
        return commodity_id;
    }

    public void setCommodity_id(int commodity_id) {
        this.commodity_id = commodity_id;
    }

    public String getSize_name() {
        return size_name;
    }

    public void setSize_name(String size_name) {
        this.size_name = size_name;
    }

    public int getSize_count() {
        return size_count;
    }

    public void setSize_count(int size_count) {
        this.size_count = size_count;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
