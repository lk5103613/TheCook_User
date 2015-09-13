package com.like.entity;

import com.google.gson.annotations.SerializedName;

public class PersonInfo {

	public String uid;
	public String mp;
	@SerializedName("all_tran_cnt")
	public String allTranCnt;
	@SerializedName("last_login_time")
	public String lastLoginTime;
	public String truename;
	public String nickname;
	@SerializedName("device_type")
	public String deviceType;
	public String lng;
	public int code;
	public String avatar;
	public String city;
	@SerializedName("last_login")
	public String lastLogin;
	public String pwd;
	@SerializedName("all_tran_money")
	public String allTranMoney;
	@SerializedName("device_no")
	public String deviceNo;
	public String province;
	public String gender;
	@SerializedName("add_time")
	public String addTime;
	public String district;
	public String lat;

}
