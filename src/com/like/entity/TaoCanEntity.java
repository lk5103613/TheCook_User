package com.like.entity;

public class TaoCanEntity {
	
	private int img;
	private String name;
	private String comment;
	private String description;
	
	public TaoCanEntity(){}
	
	public TaoCanEntity(int img, String name, String comment, String description){
		this.img = img;
		this.name = name;
		this.comment=comment;
		this.description = description;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
