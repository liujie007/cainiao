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
import android.view.ViewGroup;
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
import com.hncainiao.fubao.ui.adapter.RegisOrderAdapter;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author liujie
 * @version 2015年4月17日 上午10:22:35
 * 
 */
public class RegisOrderActivity extends BaseActivity {

	private Context mContext;
	private ListView listView;
	private RegisOrderAdapter adapter;
	List<Map<String, Object>> mList;
	ImageView nodata;
	private void setupView() {
		setTitle("我的预约挂号");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regis_order_list);
		mContext = this;
		setupView();
		listView = (ListView) findViewById(R.id.lv_regis_order_list);
		nodata = (ImageView) findViewById(R.id.imageview);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(mContext, RegisOrderMessage.class);
				intent.putExtra("register_d", mList.get(arg2).get("id") + "");
				intent.putExtra("message_id", "");
				startActivity(intent);
			}

		});
		setData();

	}

	public void leftbuttonclick(View view) {

		Intent intent = new Intent(this, MainActivity.class);
		FuBaoApplication.getInstance().setInt(1);
		startActivity(intent);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		nodata.setVisibility(View.GONE);
		super.onResume();
	}

	private void setData() {
		if (NetworkUtil.isOnline(mContext)) {
			mList = new ArrayList<Map<String, Object>>();
			AsyncHttpClient client = new AsyncHttpClient();
			String url = Constant.ResList;// 请输入接口地址
			RequestParams params = new RequestParams();
			params.put("member_id", SharedPreferencesConfig.getStringConfig(mContext, "member_id"));
			client.post(url, params, new AsyncHttpResponseHandler() {

				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					Showloading();
					super.onStart();
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					// TODO Auto-generated method stub
					nodata.setVisibility(View.VISIBLE);
					showLog(new String(responseBody));
					Dissloading();
					if (!CheckJson(responseBody).equals("")) {
						try {
							JSONObject object = new JSONObject(new String(responseBody));
							if (object.getInt("err") == 0) {
								/**
								 * 
								 * 下面拿到接口后填入数组名
								 * **/
								JSONArray array = object.getJSONArray("register");
								if (array.length() == 0) {
									listView.setVisibility(ViewGroup.GONE);
								}

								HashMap<String, Object> map = null;
								mList.clear();
								for (int i = 0; i < array.length(); i++) {
									map = new HashMap<String, Object>();
									map.put("id", array.getJSONObject(i).getString("id"));
									map.put("name", array.getJSONObject(i).getString("patient_name"));
									map.put("hospital", array.getJSONObject(i).getString("hospital_name"));
									map.put("time", array.getJSONObject(i).getString("clinic_date"));
									map.put("status", array.getJSONObject(i).getString("status"));
									map.put("office_name",array.getJSONObject(i).getString("department_name"));
									mList.add(map);
								}
								adapter = new RegisOrderAdapter(mContext);
								adapter.setList(mList);
								listView.setAdapter(adapter);
							} else if (object.getInt("err") == 1) {
								listView.setVisibility(ViewGroup.GONE);

							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block

							e.printStackTrace();
						}

					} else {

						nodata.setVisibility(ViewGroup.VISIBLE);
						listView.setVisibility(ViewGroup.GONE);
						showToast("没有数据");

					}

				}

				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					// TODO Auto-generated method stub
					Dissloading();
					nodata.setVisibility(ViewGroup.VISIBLE);
					listView.setVisibility(ViewGroup.GONE);
					showToastNetTime();
				}

			});

		} else {
			showToastNotNet();
			nodata.setVisibility(ViewGroup.VISIBLE);
			listView.setVisibility(ViewGroup.GONE);

		}
	}

}
