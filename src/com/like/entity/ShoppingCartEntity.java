package com.like.entity;

public class ShoppingCartEntity {

    public int id;
    public String name;
    public float price;
    public int cnt;
    public int packageId;
    public String img;

    public ShoppingCartEntity() {
    }

    public ShoppingCartEntity(String name, float price, int cnt, int packageId, String img) {
        this.name = name;
        this.price = price;
        this.cnt = cnt;
        this.packageId = packageId;
        this.img = img;
    }

}
