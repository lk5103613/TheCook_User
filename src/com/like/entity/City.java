package com.like.entity;

import com.google.gson.annotations.SerializedName;

public class City {
	
	@SerializedName("CityID")
	public int id;
	public String name;
	@SerializedName("ProID")
	public int provicenceId;

}
