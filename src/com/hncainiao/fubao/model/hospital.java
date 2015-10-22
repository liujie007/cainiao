package com.hncainiao.fubao.model;

public class hospital {
	private String id;
	private String name;
	private String img;
	private String type;
	private String distance;
	private String address;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public hospital(String id, String name, String img, String type,
			String distance,String address) {
		super();
		this.id = id;
		this.name = name;
		this.img = img;
		this.type = type;
		this.distance = distance;
		this.address=address;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	@Override
	public String toString() {
		return "hospital [id=" + id + ", name=" + name + ", img=" + img
				+ ", type=" + type + ", distance=" + distance + "]";
	}
	
	
	





}
