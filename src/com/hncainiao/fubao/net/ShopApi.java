package com.hncainiao.fubao.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hncainiao.fubao.application.FuBaoApplication;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.jmheart.net.ApiHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 项目：FuBaoHealth
 * 
 * @author liujie 日期：2015-8-26下午4:15:16 商城接口
 */
public class ShopApi {

	/**
	 * 得到商品列表
	 * 
	 * @param type
	 * @param keyword
	 * @param handler
	 */
	public static void getProductList(String page, String type, String keyword,
			AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("type", type);
		params.put("keyword", keyword);
		params.put("page", page);
		String getProductListurl = "getProductList";
		ApiHttpClient.post(getProductListurl, params, handler);
	}

	/**
	 * 获取类别
	 * 
	 * @param pid
	 * @param handler
	 */
	public static void getFilterInfo(AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		String getFilterInfourl = "getFilterInfo";
		ApiHttpClient.post(getFilterInfourl, params, handler);
	}

	/**
	 * 热门搜索
	 * 
	 * @param handler
	 */
	public static void getHotWord(AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		String getSearchHoturl = "getSearchHot";
		ApiHttpClient.post(getSearchHoturl, params, handler);
	}

	/**
	 * 商品详情
	 * 
	 * @param product_id
	 * @param handler
	 */
	public static void getProductInfo(String product_id,
			AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("product_id", product_id);
		String getProductInfourl = "getProductInfo";
		ApiHttpClient.post(getProductInfourl, params, handler);
	}

	/**
	 * 加入购物车
	 * 
	 * @param product_id
	 * @param handler
	 */
	public static void addCart(String product_id, String count,
			AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("product_id", product_id);
		params.put("member_id",SharedPreferencesConfig.getStringConfig(FuBaoApplication.getInstance(), "member_id"));
		params.put("count", count);
		String addCarturl = "addCart";
		ApiHttpClient.post(addCarturl, params, handler);
	}

	/**
	 * 购物车
	 * 
	 * @param handler
	 */
	public static void getCart(AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put(
				"member_id",
				SharedPreferencesConfig.getStringConfig(
						FuBaoApplication.getInstance(), "member_id"));
		String getCartturl = "getCart";
		ApiHttpClient.post(getCartturl, params, handler);
	}

	/**
	 * 立即结算
	 * 
	 * @param cart
	 * @param handler
	 */
	public static void settle(List<HashMap<String, Object>> cart,
			AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("cart", cart);
		params.put(
				"member_id",
				SharedPreferencesConfig.getStringConfig(
						FuBaoApplication.getInstance(), "member_id"));
		String getCartturl = "settle";
		ApiHttpClient.post(getCartturl, params, handler);
	}

	/**
	 * 提交订单
	 * 
	 * @param consignee_id
	 * @param shipping_id
	 * @param paytype_id
	 * @param product
	 * @param handler
	 */
	public static void addOrder(String consignee_id, String shipping_id,
			String paytype_id, String product, AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("consignee_id", consignee_id);
		params.put("shipping_id", shipping_id);
		params.put("paytype_id", paytype_id);
		params.put("product", product);
		params.put(
				"member_id",
				SharedPreferencesConfig.getStringConfig(
						FuBaoApplication.getInstance(), "member_id"));
		String addOrderurl = "addOrder";
		ApiHttpClient.post(addOrderurl, params, handler);
	}

	/**
	 * 得到收货信息
	 * 
	 * @param handler
	 */
	public static void getConsigneeList(AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put(
				"member_id",
				SharedPreferencesConfig.getStringConfig(
						FuBaoApplication.getInstance(), "member_id"));
		String getConsigneeListurl = "getConsigneeList";
		ApiHttpClient.post(getConsigneeListurl, params, handler);
	}

	/**
	 * 添加收货人信息
	 * 
	 * @param consignee
	 * @param address
	 * @param phone
	 * @param handler
	 */
	public static void addConsignee(String consignee, String address,
			String phone, AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("consignee", consignee);
		params.put("address", address);
		params.put("phone", phone);
		params.put(
				"member_id",
				SharedPreferencesConfig.getStringConfig(
						FuBaoApplication.getInstance(), "member_id"));
		String addConsigneeurl = "addConsignee";
		ApiHttpClient.post(addConsigneeurl, params, handler);
	}

	/**
	 * 修改收货人信息
	 * 
	 * @param consignee_id
	 * @param consignee
	 * @param address
	 * @param phone
	 * @param handler
	 */
	public static void saveConsignee(String consignee_id, String consignee,
			String address, String phone, AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("consignee", consignee);
		params.put("address", address);
		params.put("phone", phone);
		params.put("consignee_id", consignee_id);
		params.put("member_id",SharedPreferencesConfig.getStringConfig(FuBaoApplication.getInstance(), "member_id"));
		String saveConsigneesturl = "saveConsignee";
		ApiHttpClient.post(saveConsigneesturl, params, handler);
	}

	/**
	 * 删除收货人信息
	 * 
	 * @param consignee_id
	 * @param handler
	 */
	public static void rmConsignee(String consignee_id,
			AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("consignee_id", consignee_id);
		params.put("member_id",	SharedPreferencesConfig.getStringConfig(FuBaoApplication.getInstance(), "member_id"));
		String rmConsigneeurl = "rmConsignee";
		ApiHttpClient.post(rmConsigneeurl, params, handler);
	}

	/**
	 * 得到快递方式
	 * 
	 */
	public static void getShipping(AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		String getShippingurl = "getShipping";
		ApiHttpClient.post(getShippingurl, params, handler);
	}

	/**
	 * 精准筛选
	 * 
	 * @param price
	 * @param category
	 * @param brand
	 * @param handler
	 */
	public static void filter(String page,ArrayList<String> price,
			ArrayList<String> category, ArrayList<String> brand,
			AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		String filterurl = "filter";
		params.put("page", page);
		params.put("price", price);
		params.put("category", category);
		params.put("brand", brand);

		ApiHttpClient.post(filterurl, params, handler);
	}

	/**
	 * 订单状态
	 * 
	 * @param status
	 * @param handler
	 */
	public static void getStatusOrder(String status,
			AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		String getStatusOrderurl = "getStatusOrder";
		params.put("status", status);
		params.put("member_id",SharedPreferencesConfig.getStringConfig(	FuBaoApplication.getInstance(), "member_id"));
		ApiHttpClient.post(getStatusOrderurl, params, handler);
	}
	/**
	 * @param mlist
	 * @param handler
	 * 删除购物车商品
	 */
	public static void delCart(List<String> mlist,AsyncHttpResponseHandler handler)
	{
		RequestParams params = new RequestParams();
		String delCarturl = "delCart";
		params.put("cart_id", mlist);
		params.put("member_id",SharedPreferencesConfig.getStringConfig(	FuBaoApplication.getInstance(), "member_id"));
		ApiHttpClient.post(delCarturl, params, handler);
	}
	
	/**
	 * 未付款
	 * @param orde_sn
	 * @param handler
	 */
	public static void pay(String orde_sn,AsyncHttpResponseHandler handler)
	{
		RequestParams params = new RequestParams();
		String delCarturl = "pay";
		params.put("sn", orde_sn);
		params.put("member_id",SharedPreferencesConfig.getStringConfig(	FuBaoApplication.getInstance(), "member_id"));
		ApiHttpClient.post(delCarturl, params, handler);
	}
	
	/**
	 *	确认收货
	 * @param sn
	 * @param handler
	 */
	public static void SureOrder(String sn,AsyncHttpResponseHandler handler)
	{
		RequestParams params = new RequestParams();
		String delCarturl = "takeDelivery";
		params.put("sn", sn);
		params.put("member_id",SharedPreferencesConfig.getStringConfig(	FuBaoApplication.getInstance(), "member_id"));
		ApiHttpClient.post(delCarturl, params, handler);
	}
	/**
	 * 评价
	 * @param content
	 * @param handler
	 */
	public static void OrderPj(String order_detail_id,String order_id,String content,AsyncHttpResponseHandler handler)
	{
		RequestParams params = new RequestParams();
		String delCarturl = "addOrderComment";
		params.put("order_id", order_id);
		params.put("order_detail_id", order_detail_id);
		params.put("content", content);
		params.put("member_id",SharedPreferencesConfig.getStringConfig(	FuBaoApplication.getInstance(), "member_id"));
		ApiHttpClient.post(delCarturl, params, handler);
	}
	/**
	 * 申请退款
	 * @param sn
	 * @param handler
	 */
	public static void TuiOrder(String msg,String sn,AsyncHttpResponseHandler handler)
	{
		RequestParams params = new RequestParams();
		String refundurl = "refund";
		params.put("sn", sn);
		params.put("msg", msg);
		params.put("member_id",SharedPreferencesConfig.getStringConfig(	FuBaoApplication.getInstance(), "member_id"));
		ApiHttpClient.post(refundurl, params, handler);
	}
	/**
	 * @param sn
	 * @param ramark
	 * @param numberlog
	 * @param handler
	 * 退款单
	 */
	public static void TuiOrderDan(String sn,String ramark,String numberlog, AsyncHttpResponseHandler handler)
	{
		RequestParams params = new RequestParams();
		String delCarturl = "http://jiemeidd.sinaapp.com/code/index.php";
		params.put("sn", sn);
		params.put("ramark", "ramark");
		params.put("numberlog", "numberlog");
		params.put("member_id",SharedPreferencesConfig.getStringConfig(	FuBaoApplication.getInstance(), "member_id"));
		ApiHttpClient.tpost(delCarturl, params, handler);
	}
	
}
