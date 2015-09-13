package com.like.entity;

import com.google.gson.annotations.SerializedName;

public class MyCardEntity {

	@SerializedName("coupon_id")
	public String couponId;
	public String name;
	@SerializedName("dead_time")
	public String deadTime;
	@SerializedName("coupon_no")
	public String couponNo;
	public String uid;
	@SerializedName("use_time")
	public String useTime;
	@SerializedName("alreay_use")
	public String alreayUse;

}
