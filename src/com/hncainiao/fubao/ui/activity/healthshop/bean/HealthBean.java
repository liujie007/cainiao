package com.hncainiao.fubao.ui.activity.healthshop.bean;

import com.hncainiao.fubao.application.FuBaoApplication;

public class HealthBean
{
	public String product_id;
	public String name;
	public String img;
	public String hospital_id;
	public String crowd;
	public String price;
	public String suggested_price;
	public String stock;
	public String hospital_name;
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public String producer;
	/**
	 * @return the product_id
	 */
	public String getProduct_id() {
		return product_id;
	}
	/**
	 * @param product_id the product_id to set
	 */
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the img
	 */
	public String getImg() {
		return FuBaoApplication.appHOST+img;
	}
	/**
	 * @param img the img to set
	 */
	public void setImg(String img) {
		this.img = img;
	}
	/**
	 * @return the hospital_id
	 */
	public String getHospital_id() {
		return hospital_id;
	}
	/**
	 * @param hospital_id the hospital_id to set
	 */
	public void setHospital_id(String hospital_id) {
		this.hospital_id = hospital_id;
	}
	/**
	 * @return the crowd
	 */
	public String getCrowd() {
		return crowd;
	}
	/**
	 * @param crowd the crowd to set
	 */
	public void setCrowd(String crowd) {
		this.crowd = crowd;
	}
	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	/**
	 * @return the suggested_price
	 */
	public String getSuggested_price() {
		return suggested_price;
	}
	/**
	 * @param suggested_price the suggested_price to set
	 */
	public void setSuggested_price(String suggested_price) {
		this.suggested_price = suggested_price;
	}
	/**
	 * @return the stock
	 */
	public String getStock() {
		return stock;
	}
	/**
	 * @param stock the stock to set
	 */
	public void setStock(String stock) {
		this.stock = stock;
	}
	/**
	 * @return the hospital_name
	 */
	public String getHospital_name() {
		return hospital_name;
	}
	/**
	 * @param hospital_name the hospital_name to set
	 */
	public void setHospital_name(String hospital_name) {
		this.hospital_name = hospital_name;
	}

	
}
