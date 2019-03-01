package com.triplebro.domineer.graduationdesignproject.beans;

public class CollectionCommodityInfo {

    private int _id;
    private String phone_number;
    private int commodity_id;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getCommodity_id() {
        return commodity_id;
    }

    public void setCommodity_id(int commodity_id) {
        this.commodity_id = commodity_id;
    }

    @Override
    public String toString() {
        return "CollectionCommodityInfo{" +
                "_id=" + _id +
                ", phone_number='" + phone_number + '\'' +
                ", commodity_id=" + commodity_id +
                '}';
    }
}
