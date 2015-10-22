package com.hncainiao.fubao.ui.activity.personalcenter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.hncainiao.fubao.utils.StringUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class PhyTalActivity extends BaseActivity {
	private PhyTalAdapte adapte;
	Context mContext;
	ListView listView;
	ImageView imView;
	private List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_phytal);
		mContext=PhyTalActivity.this;
		inintView();
		getNetDate();
		adapte=new PhyTalAdapte(mContext);
		imView.setVisibility(View.GONE);
	}
	/**
	 * 控件初始化
	 */
	private void inintView()
	{
		setTitle("我的体检报告");
		listView=(ListView)findViewById(R.id.list);
		listView.setEmptyView(findViewById(R.id.icon_nodate));
		imView=(ImageView)findViewById(R.id.icon_nodate);
	}
	/**
	 * 得到网络数据
	 */
	private void getNetDate()
	{
		if (NetworkUtil.isOnline(this)) {
			AsyncHttpClient httpClient =new AsyncHttpClient();
			RequestParams params =new RequestParams();
			params.put("member_id", SharedPreferencesConfig.getStringConfig(mContext, "member_id"));
			httpClient.post(Constant.url_my_baogao, params,new AsyncHttpResponseHandler()
			{
				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					super.onStart();
					Showloading();
				}
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					// TODO Auto-generated method stub
					Dissloading();
					imView.setVisibility(View.VISIBLE);
					if (!CheckJson(responseBody).equals("")) {
						//开始解析数据
						try {
							JSONObject jsonObject =new JSONObject(new String(responseBody));
						  if (jsonObject.getInt("err")==0) {
							
							  JSONArray jsonArray =jsonObject.getJSONArray("report");
							  for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject jObject =jsonArray.getJSONObject(i);
								Map<String, Object> map =new HashMap<String, Object>();
								map.put("tvtime", StringUtil.getStrTime(jObject.getString("visittime")));
								map.put("tvpeople", jObject.getString("people_name"));
								map.put("host", jObject.getString("hospital_name"));
								map.put("tc", jObject.getString("package_name"));
								list.add(map);
							}
							  adapte.setList(list);
							  listView.setAdapter(adapte);
						}
						  else {
							//showToast("没有数据！");
						}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					
					}
					else {
						showToast("数据异常");
					}
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					// TODO Auto-generated method stub
					imView.setVisibility(View.VISIBLE);
					Dissloading();
					showToastNetTime();
					imView.setImageResource(R.drawable.icon_list_nonet);
				}
			});
		}
		else {
			showToastNotNet();
			imView.setVisibility(View.VISIBLE);
			imView.setImageResource(R.drawable.icon_list_nonet);
		}
	}
}
