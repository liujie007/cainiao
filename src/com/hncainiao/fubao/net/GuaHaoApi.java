package com.hncainiao.fubao.net;

import com.jmheart.net.ApiHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class GuaHaoApi {
	
	/**
	 * 继续挂号
	 * @param register_d
	 * @param handler
	 */
	public static void RepeatGh(String register_d,
			AsyncHttpResponseHandler handler) {
		RequestParams params = new RequestParams();
		params.put("register_id", register_d);
		String RepeatGhurl = "toRegister";
		ApiHttpClient.post("/index.php/api/",RepeatGhurl, params, handler);
	}
}
