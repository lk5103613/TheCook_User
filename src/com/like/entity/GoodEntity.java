package com.like.entity;

/**
 * Created by Administrator on 2015/8/19.
 */
public class GoodEntity {

    public int drawableId;
    public String name;
    public int goodMoney;
    public int baddMoney;

    public GoodEntity(){}

    public GoodEntity(int drawableId, String name, int goodMoney, int baddMoney) {
        this.drawableId = drawableId;
        this.name = name;
        this.goodMoney = goodMoney;
        this.baddMoney = baddMoney;
    }
}
