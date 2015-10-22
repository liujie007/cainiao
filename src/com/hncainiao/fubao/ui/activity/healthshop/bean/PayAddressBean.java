package com.hncainiao.fubao.ui.activity.healthshop.bean;

/**
 * 	项目：FuBaoHealth
 * 		@author liujie
 *	日期：2015-7-13下午5:06:51
 * 	地址
 */
public class PayAddressBean {

	public String id;
	public String member_id;
	public String consignee;
	public String address;
	public String phone;
	public PayAddressBean()
	{
		
	}
	public PayAddressBean(String id, String member_id,String consignee,String address,String phone)
	{
		this.id=id;
		this.member_id=member_id;
		this. consignee=consignee;
		this.address=address;
		this. phone=phone;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}


}
