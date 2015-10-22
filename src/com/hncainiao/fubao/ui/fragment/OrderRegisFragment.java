package com.hncainiao.fubao.ui.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.application.IsLogin;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.doctor.DoctorDetailActivity;
import com.hncainiao.fubao.ui.activity.login.LoginActivity;
import com.hncainiao.fubao.ui.activity.registration.RegistrationMessageActivity;
import com.hncainiao.fubao.ui.adapter.OrderRegisDetailAdapter;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.hncainiao.fubao.utils.ToastManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @author zhaojing
 * @version 2010年8月8日 上午10:19:20
 * 
 *          预约挂号
 */
public class OrderRegisFragment extends BaseFragment {

	private static final String TAG = "OrderRegisFragment";
	private Context mContext;
	private View view;
	private OrderRegisDetailAdapter adapter;
	private ListView listView;
	RelativeLayout _order_rule;
	List<Map<String, Object>> mList;
	TextView rule;
	int tag = 0;
	ImageView nodata;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		view = inflater.inflate(R.layout.fragment_order_registertion, null);
		_order_rule = (RelativeLayout) view.findViewById(R.id.rl_bottom_order_rule);
		listView = (ListView) view.findViewById(R.id.lv_order_regis_list);
		rule = (TextView) view.findViewById(R.id.rule);
		nodata = (ImageView) view.findViewById(R.id.nodata);
		listView.setEmptyView(nodata);
		adapter = new OrderRegisDetailAdapter(mContext);
		nodata.setVisibility(View.GONE);
		setData();
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (!IsLogin.isLogin()) {
					ToastManager.getInstance(mContext).showToast("您尚未登陆，请先登陆");
					Intent intent = new Intent(getActivity(),LoginActivity.class);
					intent.putExtra("mlogin", "self");
					startActivity(intent);
				
				}
				else {
				
				String str = mList.get(position).get("order_status") + "";
				Intent intent = new Intent(mContext,RegistrationMessageActivity.class);
				intent.putExtra("zuozhen_num",mList.get(position).get("zuozhen_nums") + "");
				if (str.equals("1")&& !mList.get(position).get("zuozhen_nums").equals("")) {
					SharedPreferencesConfig.saveStringConfig(mContext,"zuozhen_num",mList.get(position).get("zuozhen_nums") + "");
					startActivity(intent);
					getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				} else {

				}
			}
				
			}
		});
		_order_rule.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (tag == 0) {
					rule.setVisibility(ViewGroup.VISIBLE);
					tag = 1;
				} else {
					rule.setVisibility(ViewGroup.GONE);
					setData();
					tag = 0;
				}
			}
		});
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub

		super.onResume();
	}

	private void setData() {
		mList = new ArrayList<Map<String, Object>>();
		if (NetworkUtil.isOnline(mContext)) {
			AsyncHttpClient client = new AsyncHttpClient();
			String url = Constant.ORDER_list;
			client.setTimeout(5000);
			RequestParams params = new RequestParams();
			params.put("doctor_id", SharedPreferencesConfig.getStringConfig(mContext, "Doctor_id"));
			client.post(url, params, new AsyncHttpResponseHandler() {
				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					super.onStart();
					Showloading();
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					  Log.i("---", "打印："+new String(responseBody));
					Dissloading();
					if (!BaseActivity.CheckJson(responseBody).equals("")) {
						try {
							Map<String, Object> map = null;
							JSONObject object = new JSONObject(new String(responseBody));
							if (object.getInt("err") == 0) {
								JSONArray array = object.getJSONArray("schedule");
								if (array.length() > 0) {
									nodata.setVisibility(View.GONE);
								} else {
									nodata.setVisibility(View.VISIBLE);
								}
								mList.clear();

								for (int i = 0; i < array.length(); i++) {
									map = new HashMap<String, Object>();
								    map.put("order_time",array.getJSONObject(i).getString("clinic_date")
													// +(array.getJSONObject(i).getString("clinic_dayofweek"))
													+ array.getJSONObject(i).getString("clinic_time"));

									map.put("zuozhen_nums", array
											.getJSONObject(i).getString("id"));
									if (array.getJSONObject(i)
											.getString("type").equals("1")) {
										map.put("order_out",
												array.getJSONObject(i).getString("department_name")	+ "普通门诊");
									} else {
										map.put("order_out",array.getJSONObject(i).getString("department_name")+ "专家门诊");
									}
									String status = array.getJSONObject(i).getString("status");
									map.put("order_status", status);
									map.put("order_money",array.getJSONObject(i).getString("register_fee")+"元");
									mList.add(map);

								}

								
								adapter.setList(mList);
								listView.setAdapter(adapter);
							}
							else {
								//ToastManager.getInstance(mContext).showToast("没有数据");
								nodata.setVisibility(ViewGroup.VISIBLE);
							}
						} catch (JSONException e) {

							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						ToastManager.getInstance(mContext).showToast("没有数据");
						nodata.setVisibility(ViewGroup.VISIBLE);
					}

					super.onSuccess(statusCode, headers, responseBody);
				}

				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					// TODO Auto-generated method stub
					nodata.setVisibility(ViewGroup.VISIBLE);
					Dissloading();
				}

			});

		} else {
			ToastManager.getInstance(mContext).showToast("当前无网络连接");
		}
	}

	// Listview排序
	public List<Map<String, Object>> paixu(List<Map<String, Object>> mList) {

		if (!mList.isEmpty()) {
			Collections.sort(mList, new Comparator<Map<String, Object>>() {
				@Override
				public int compare(Map<String, Object> object2,

				Map<String, Object> object1) {
					// 根据文本排序
					return ((String) object1.get("doctor_status")).compareTo((String) object2.get("doctor_status"));
				}
			});
		}
		return mList;
	}

	@Override
	protected void creatFragmentUI() {
		// TODO Auto-generated method stub
		
	}

}
