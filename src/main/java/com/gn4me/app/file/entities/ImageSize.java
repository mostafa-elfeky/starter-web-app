package com.gn4me.app.file.entities;

public class ImageSize {
	
	private int width;
	private int height;
	
	public ImageSize(){}
	
	public ImageSize(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	public String getCode() {
		return getWidth() + "_" + getHeight();
	}
	
	@Override
	public String toString() {
		return "ImageSize [width=" + width + ", height=" + height + "]";
	}

	@Override
	public boolean equals(Object size) {
		String sizeCode = (String) size;
		if(getCode().equals(sizeCode)) {
			return true;
		} else {
			return false;
		}
	}
	
}
