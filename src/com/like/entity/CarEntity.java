package com.like.entity;

public class CarEntity {
	
	private int img;
	private String name;
	private int money;
	private int count;
	
	public CarEntity(){}
	
	public CarEntity(int img, String name, int money, int count){
		this.img = img;
		this.name = name;
		this.money = money;
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

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
