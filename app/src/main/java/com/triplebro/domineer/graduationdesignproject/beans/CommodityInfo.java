package com.triplebro.domineer.graduationdesignproject.beans;

import java.io.Serializable;

public class CommodityInfo implements Serializable {

    private int commodity_id;

    private String commodity_name;

    private int price;

    private String commodity_image;

    private int type_generalize_id;

    private int type_concrete_id;

    private String phone_number;

    public int getCommodity_id() {
        return commodity_id;
    }

    public void setCommodity_id(int commodity_id) {
        this.commodity_id = commodity_id;
    }

    public String getCommodity_name() {
        return commodity_name;
    }

    public void setCommodity_name(String commodity_name) {
        this.commodity_name = commodity_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCommodity_image() {
        return commodity_image;
    }

    public void setCommodity_image(String commodity_image) {
        this.commodity_image = commodity_image;
    }

    public int getType_generalize_id() {
        return type_generalize_id;
    }

    public void setType_generalize_id(int type_generalize_id) {
        this.type_generalize_id = type_generalize_id;
    }

    public int getType_concrete_id() {
        return type_concrete_id;
    }

    public void setType_concrete_id(int type_concrete_id) {
        this.type_concrete_id = type_concrete_id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
