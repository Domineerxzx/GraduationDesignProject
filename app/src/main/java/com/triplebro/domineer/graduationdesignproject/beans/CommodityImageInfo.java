package com.triplebro.domineer.graduationdesignproject.beans;

import java.io.Serializable;

/**
 * @author Domineer
 * @data 2019/4/10,5:04
 * ----------为梦想启航---------
 * --Set Sell For Your Dream--
 */
public class CommodityImageInfo implements Serializable {

    private int _id;
    private int commodity_id;
    private String commodity_image;
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

    public String getCommodity_image() {
        return commodity_image;
    }

    public void setCommodity_image(String commodity_image) {
        this.commodity_image = commodity_image;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public String toString() {
        return "CommodityImageInfo{" +
                "_id=" + _id +
                ", commodity_id=" + commodity_id +
                ", commodity_image='" + commodity_image + '\'' +
                ", phone_number='" + phone_number + '\'' +
                '}';
    }
}
