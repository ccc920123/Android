package com.cn.math.bean;

public class GridViewBean {
	private int image;
    private String titMessage;
	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public String getTitMessage() {
		return titMessage;
	}

	public void setTitMessage(String titMessage) {
		this.titMessage = titMessage;
	}

	public GridViewBean(int image, String titMessage) {
		super();
		this.image = image;
		this.titMessage = titMessage;
	}
	
}
