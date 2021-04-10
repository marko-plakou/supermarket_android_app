package com.p17107.supermarkets_app;

public class Supermarkets {
    private String supermarket_id;
    private String location_en;
    private String location_gr;
    private String photo_url;
    private String product_id;
    private long product_stock;


    public long getProduct_stock() {
        return product_stock;
    }

    public String getSupermarket_id() {
        return supermarket_id;
    }

    public String getLocation_en() {
        return location_en;
    }

    public String getLocation_gr() {
        return location_gr;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setLocation_en(String location_en) {
        this.location_en = location_en;
    }

    public void setLocation_gr(String location_gr) {
        this.location_gr = location_gr;
    }

    public void setSupermarket_id(String supermarket_id) {
        this.supermarket_id = supermarket_id;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public void setProduct_stock(long product_stock) {
        this.product_stock = product_stock;
    }
}
