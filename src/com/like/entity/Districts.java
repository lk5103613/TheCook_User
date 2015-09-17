package com.like.entity;

import com.google.gson.annotations.SerializedName;

public class Districts {
	
	@SerializedName("Id")
	public int id;
	@SerializedName("DisName")
	public String name;
	@SerializedName("CityID")
	public int cityId;

}
