package com.like.entity;

import com.google.gson.annotations.SerializedName;
import com.like.network.APIS;

public class Meishi {
	
	public String name;
	public String price;
	public String avatar;
	public String sold_cnt;
	public String star;
	@SerializedName("meishiid")
	public String meishiId;
	
	public ShoppingCartEntity toShoppingCartEntity() {
		ShoppingCartEntity entity = new ShoppingCartEntity(name, Float.valueOf(price), Integer.valueOf(sold_cnt), 0, APIS.BASE_URL + avatar);
		return entity;
	}

}
