package com.like.entity;

import com.google.gson.annotations.SerializedName;

public class OrderState {
	@SerializedName("order_id")
	public String orderId;
	
	@SerializedName("state_name")
	public String stateName;
	
	@SerializedName("state_id")
	public String stateId;
	
	@SerializedName("add_time")
	public String addTime;
}
