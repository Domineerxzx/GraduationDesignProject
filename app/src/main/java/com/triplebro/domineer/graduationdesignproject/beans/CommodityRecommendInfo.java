package com.triplebro.domineer.graduationdesignproject.beans;

import java.io.Serializable;

public class CommodityRecommendInfo implements Serializable {

    private int commodity_id;
    private String commodity_name;
    private int price;
    private String recommend_image;

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

    public String getRecommend_image() {
        return recommend_image;
    }

    public void setRecommend_image(String recommend_image) {
        this.recommend_image = recommend_image;
    }

    @Override
    public String toString() {
        return "CommodityRecommendInfo{" +
                "commodity_id=" + commodity_id +
                ", commodity_name='" + commodity_name + '\'' +
                ", price=" + price +
                ", recommend_image='" + recommend_image + '\'' +
                '}';
    }
}
