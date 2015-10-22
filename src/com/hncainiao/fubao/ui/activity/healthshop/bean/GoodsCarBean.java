package com.hncainiao.fubao.ui.activity.healthshop.bean;

import java.io.Serializable;

import android.content.Context;

import com.jmheart.base.BaseApplication;

public class GoodsCarBean implements Serializable{

	public String cart_id;
	public String count;
	public String product_name;
	public String remark;
	public String price;
	public String img;

	public String getCart_id() {
		return cart_id;
	}
	public void setCart_id(String cart_id) {
		this.cart_id = cart_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String product_id;
	
	
	public GoodsCarBean(String product_id,String cart_id,String count,String product_name,String remark,String price,String img)
	{
		this.product_id=product_id;
		this.cart_id=cart_id;
		this.count=count;
		this.product_name=product_name;
		this.remark=remark;
		this.price=price;
		this.img=img;
	}
	public GoodsCarBean()
	{
		
	}
	public String getImg() {
		return BaseApplication.HOST+img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getcart_id() {
		return cart_id;
	}
	public void setcart_id(String cart_id) {
		this.cart_id = cart_id;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	
}
