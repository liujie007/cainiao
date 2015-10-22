package com.hncainiao.fubao.ui.fragment.mallorder.bean;

import com.hncainiao.fubao.application.FuBaoApplication;
import com.jmheart.base.BaseApplication;

public class OrderInfoBean {

	public String getOrder_detail_id() {
		return order_detail_id;
	}
	public void setOrder_detail_id(String order_detail_id) {
		this.order_detail_id = order_detail_id;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getProduct_fee() {
		return product_fee;
	}
	public void setProduct_fee(String product_fee) {
		this.product_fee = product_fee;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getImg() {
		return BaseApplication.HOST+img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSuggested_price() {
		return suggested_price;
	}
	public void setSuggested_price(String suggested_price) {
		this.suggested_price = suggested_price;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public String getCrowd() {
		return crowd;
	}
	public void setCrowd(String crowd) {
		this.crowd = crowd;
	}
	public String getSale() {
		return sale;
	}
	public void setSale(String sale) {
		this.sale = sale;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String order_detail_id;
	public String count;
	public String price;
	public String product_fee;
	public String product_name;
	public String img;
	public String remark;
	public String suggested_price;
	public String stock;
	public String producer;
	public String crowd;
	public String sale;
	public String spec;
	public String order_detail_comment_status;
	public String getOrder_detail_comment_status() {
		return order_detail_comment_status;
	}
	public void setOrder_detail_comment_status(String order_detail_comment_status) {
		this.order_detail_comment_status = order_detail_comment_status;
	}
	
	

}
