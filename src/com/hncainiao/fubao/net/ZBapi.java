package com.hncainiao.fubao.net;

import com.jmheart.net.ApiHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 	项目：FuBaoHealth
 * 		@author liujie
 *	日期：2015-9-22上午8:39:50 
 *
 *	周边服务接口
 *
 */
public class ZBapi {

	/**
	 * 周边服务
	 * @param lat
	 * @param lon
	 * @param keyword
	 * @param distance
	 * @param handler
	 */
	public static void getNearbyList(String company_type,String page, String lat, String lon, String keyword,String distance,
			AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("lat", lat);
		params.put("lon", lon);
		params.put("company_type", company_type);
		params.put("page", page);
		if (!keyword.equals("")) {
			params.put("keyword", keyword);
		}
		params.put("distance", distance);
		String getNearbyListturl = "getNearbyList";
		ApiHttpClient.post("/index.php/api/",getNearbyListturl, params, handler);
	}
	/**
	 * 周边服务详情
	 * @param hospital_id
	 * @param handler
	 */
	public static void getNearbyInfo( String hospital_id,
			AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("hospital_id", hospital_id);
		String getNearbyInfourl = "getNearbyInfo";
		ApiHttpClient.post("/index.php/api/",getNearbyInfourl, params, handler);
	}
}
