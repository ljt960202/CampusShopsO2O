package com.ljt.o2o.dto;

import java.io.InputStream;

public class ImgHolder {
	private InputStream image;
	private String imageName;
	public ImgHolder(InputStream image, String imageName) {
		this.image = image;
		this.imageName = imageName;
	}
	public InputStream getImage() {
		return image;
	}
	public void setImage(InputStream image) {
		this.image = image;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	
}
