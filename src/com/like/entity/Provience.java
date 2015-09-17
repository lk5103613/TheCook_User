package com.like.entity;

import com.google.gson.annotations.SerializedName;

public class Provience {
	
	@SerializedName("ProID")
	public int id;
	public String name;
	
	public Provience() {
		
	}
	
	public Provience(int id, String name) {
		this.id = id;
		this.name = name;
	}

}
