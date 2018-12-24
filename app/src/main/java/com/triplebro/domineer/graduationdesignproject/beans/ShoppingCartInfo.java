package com.triplebro.domineer.graduationdesignproject.beans;

import java.io.Serializable;

public class ShoppingCartInfo implements Serializable {

    private int commodity_id;
    private String size_name;
    private int count;
    private String commodity_name;
    private String phone_number;
    private String commodity_image;
    private int price;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCommodity_name() {
        return commodity_name;
    }

    public void setCommodity_name(String commodity_name) {
        this.commodity_name = commodity_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getCommodity_image() {
        return commodity_image;
    }

    public void setCommodity_image(String commodity_image) {
        this.commodity_image = commodity_image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ShoppingCartInfo{" +
                "commodity_id=" + commodity_id +
                ", size_name='" + size_name + '\'' +
                ", count=" + count +
                ", commodity_name='" + commodity_name + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", commodity_image='" + commodity_image + '\'' +
                ", price=" + price +
                '}';
    }
}
