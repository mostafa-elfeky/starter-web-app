package com.gn4me.app.file.enums;

public enum ImageSizeEnum {

	PURE(0, 0),
	THUMBNAIL(80, 80),
	XS(24, 24),
	S(80,80),
	M(180, 180),
	L(400, 400),
	XL(400, 600),
	COVER(851, 315);
	
	private int width;
	private int heght;

	private ImageSizeEnum(int width, int heght) {
		this.width = width;
		this.heght = heght;
	}
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeght() {
		return heght;
	}
	public void setHeght(int heght) {
		this.heght = heght;
	}
	
}
