package com.like.entity;

public class MenuEntity {
	private int img;
	private String name;
	private float money;
	private int count;





	public int packid;
	public String packname;
	public float price;
	public String add_time;
	public String img_path;
	public int sold_cnt;
	public String description;







	
	public MenuEntity(){}
	public MenuEntity(int img, String name,float money, int count){
		this.img = img;
		this.packname = name;
		this.price = money;
		this.count = count;
	}
	public int getImg() {
		return img;
	}
	public void setImg(int img) {
		this.img = img;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
