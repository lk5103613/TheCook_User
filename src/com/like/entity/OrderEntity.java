package com.like.entity;

public class OrderEntity {
	
	private String number;
	private String name;
	private float money;
	private int date;
	
	public OrderEntity(){}
	
	public OrderEntity(String number, String name, float money, int date) {
		this.number = number;
		this.name = name;
		this.money = money;
		this.date = date;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}
	
}
