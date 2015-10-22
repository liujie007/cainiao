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
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.application.FuBaoApplication;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.main.MainActivity;
import com.hncainiao.fubao.ui.activity.phyexam.PhyInfoActivity;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.hncainiao.fubao.utils.StringUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 	项目：FuBaoHealth
 * 		@author liujie
 *	日期：2015-5-16下午7:57:57
 *  我的体检预约
 */
public class PhyOrderActivity extends BaseActivity {

	private ListView listView;

	private PhyOrderAdapte adapte;
	Context mContext;
	ImageView imView;
	List<Map<String, Object>> list =new ArrayList<Map<String,Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_phyorder);
		mContext=PhyOrderActivity.this;
		inintView();
	}
	public void leftbuttonclick(View view) {
		
		Intent intent =new Intent(this,MainActivity.class);
		 FuBaoApplication.getInstance().setInt(101);
		startActivity(intent);

	}
	/**
	 * 控件初始化
	 */
	private void inintView()
	{
		setTitle("我的体检预约");
		listView=(ListView)findViewById(R.id.list);
		listView.setEmptyView(findViewById(R.id.imageview));
		imView=(ImageView)findViewById(R.id.imageview);
		adapte=new PhyOrderAdapte(mContext);
		getNetDate();
		imView.setVisibility(View.GONE);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent =new Intent(mContext,PhyInfoActivity.class);
				intent.putExtra("date", list.get(position).get("id")+"");
		    	intent.putExtra("message_id", "");
			    mContext.startActivity(intent);
			}
		});
		
	}

	/**
	 * 得到网络数据
	 */
	private void getNetDate()
	{
		if (NetworkUtil.isOnline(this)) {
			AsyncHttpClient httpClient =new AsyncHttpClient();
			RequestParams params =new RequestParams();
			params.put("member_id", SharedPreferencesConfig.getStringConfig(mContext,"member_id"));
			params.put("page", "");
			showLog(""+params.toString());
			httpClient.post(Constant.url_my_phy, params,new AsyncHttpResponseHandler()
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
					imView.setVisibility(View.VISIBLE);
					Dissloading();
					if (!CheckJson(responseBody).equals("")) {
						//开始解析数据
						JSONObject jsonObject;
						try {
							jsonObject = new JSONObject(new String(responseBody));
							if (jsonObject.getInt("err")==0) {
								JSONArray jArray =jsonObject.getJSONArray("exam");
								for (int i = 0; i < jArray.length(); i++) {
									JSONObject jObject=jArray.getJSONObject(i);
									 Map<String, Object> map =new HashMap<String, Object>();
									 map.put("tvtime", StringUtil.getaStrTime(""+jObject.getString("visittime")));
									 map.put("tvpeople", jObject.getString("people_name"));
									 map.put("tvyy", jObject.getString("status"));
									 map.put("host", jObject.getString("hospital_name"));
									 map.put("tc", jObject.getString("package_name"));
									 map.put("id", jObject.getString("id"));
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
					Dissloading();
					imView.setVisibility(View.VISIBLE);
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
