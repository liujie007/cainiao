package com.hncainiao.fubao.ui.activity.phyexam;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.map.GoinfoActivity;
import com.hncainiao.fubao.ui.activity.personalcenter.PhyOrderActivity;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.hncainiao.fubao.utils.StringUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 项目：FuBaoHealth
 * 
 * @author liujie 日期：2015-5-16下午7:12:48 体检详细
 */
public class PhyInfoActivity extends BaseActivity {

	private String id,hospital_id,hostname;
	private TextView tvOrderSn,tvPlaceTime,tvState,tvName,tvCak,tvPhone,tvHostey,tvTaoCan,tvPrice,tvTime;
	private Context mContext;
	private double lat,lng;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phy_hosty_info);
		mContext=PhyInfoActivity.this;
		Intent intent = getIntent();
		if (intent != null) {
			//showToast("" + intent.getStringExtra("date"));
			id=intent.getStringExtra("date");
		}
		inintView();
		getNetDate();
	}
	public void leftbuttonclick(View view) {
		
		startActivity(new Intent(this,PhyOrderActivity.class));

	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		startActivity(new Intent(this,PhyOrderActivity.class));

		super.onBackPressed();
	}


	/**
	 * 控件初始化
	 */
	private void inintView() {
		setTitle("预约体检详情");
		tvOrderSn=(TextView)findViewById(R.id.tv_order_sn);
		tvPlaceTime=(TextView)findViewById(R.id.tv_place_time);
		tvState=(TextView)findViewById(R.id.tv_phy_state);
		tvName=(TextView)findViewById(R.id.tv_name);
		tvCak=(TextView)findViewById(R.id.tv_cak);
		tvPhone=(TextView)findViewById(R.id.tv_phone);
		tvHostey=(TextView)findViewById(R.id.tv_hostname);
		tvTaoCan=(TextView)findViewById(R.id.tv_taocan);
		tvPrice=(TextView)findViewById(R.id.tv_price);
		tvTime=(TextView)findViewById(R.id.exam_time);
		((Button)findViewById(R.id.btn_place)).setOnClickListener(this);
		((Button)findViewById(R.id.btn_cancel)).setOnClickListener(this);
		
		((ImageView)findViewById(R.id.iv_location)).setOnClickListener(this);
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);
		switch (arg0.getId()) {
		case R.id.btn_place:
			//继续购买
			Intent intent = new Intent(mContext,PhyMenuSelectActivity.class);
			intent.putExtra("hospital_id",hospital_id);
			startActivity(intent);
			break;
		case R.id.iv_location:
			//地图标志
			Intent intent2 = new Intent(mContext,GoinfoActivity.class);
			intent2.putExtra("hostname",hostname);
			intent2.putExtra("lng",lng);
			intent2.putExtra("lat",lat);
			startActivity(intent2);
			break;
		default:
			break;
		}
	}
	/**
	 * 得到网络数据
	 */
	private void getNetDate() {
		Intent intent=getIntent();
		if (NetworkUtil.isOnline(this)) {
			AsyncHttpClient httpClient = new AsyncHttpClient();
			RequestParams params = new RequestParams();
			params.put("exam_id", id);
			if(!intent.getStringExtra("message_id").equals("")){
				params.put("message_id", intent.getStringExtra("message_id"));
			}
			httpClient.post(Constant.URL_HOSTY_EXAM_STRING, params,
					new AsyncHttpResponseHandler() {
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
							if (!CheckJson(responseBody).equals("")) {

								// 开始解析数据
								/*
								 * {"info":[{"id":"44","people_name":"西蒙斯",
								 * "people_gender"
								 * :"1","people_age":"45","package_name"
								 * :"十一女性体检"
								 * ,"visittime":"1430740568","hospital_name"
								 * :"长沙市中心医院"}],"err":0}
								 */
								try {

									JSONObject jsonObject = new JSONObject(new String(responseBody));
									if (jsonObject.getInt("err") == 0) {
										JSONArray jsonArray = jsonObject.getJSONArray("info");
										JSONObject jObject = jsonArray.getJSONObject(0);
								        
										// 绑定数据
										hospital_id=jObject.getString("hospital_id");
										hostname=jObject.getString("hospital_name");
										lat= Double.parseDouble(jObject.getString("hospital_lat"));
									    lng=Double.parseDouble(jObject.getString("hospital_lng"));
										//"hospital_lat":"28.1473850000","hospital_lng":"113.0041390000"
										//tvOrderSn,tvPlaceTime,tvState,tvName,tvCak,tvPhone,tvHostey,tvTaoCan,tvPrice,tvTime;
										tvOrderSn.setText("订单号："+jObject.getString("order"));//订单编号
										tvPlaceTime.setText("下单时间："+StringUtil.getStrTime(jObject.getString("createtime")));
										

										switch (Integer.parseInt(jObject.getString("status")+"")) {
										case 0:
											tvState.setText("已取消");
											((Button)findViewById(R.id.btn_cancel)).setVisibility(View.GONE);
											break;
										case 1:
											tvState.setText("已预约");
											((Button)findViewById(R.id.btn_cancel)).setVisibility(View.VISIBLE);
											((Button)findViewById(R.id.btn_cancel)).setText("取消预约");
											break;
										case 2:
											((Button)findViewById(R.id.btn_cancel)).setVisibility(View.VISIBLE);
											((Button)findViewById(R.id.btn_cancel)).setText("查看报告");
											tvState.setText("已生成报告");
											break;

										}
									
										tvName.setText("姓       名："+jObject.getString("people_name"));
										tvCak.setText ("身份证号："+jObject.getString("people_idnumber"));
										tvPhone.setText("手机号码："+jObject.getString("people_phone"));
										tvHostey.setText(""+jObject.getString("hospital_name"));
										tvTaoCan.setText(""+jObject.getString("package_name"));
										tvPrice.setText(jObject.getString("package_current_price")+"元");
										tvTime.setText(""+StringUtil.getaStrTime(jObject.getString("visittime")));
										
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							} else {
								showToast("数据异常");
							}
						}

						@Override
						public void onFailure(int statusCode, Header[] headers,
								byte[] responseBody, Throwable error) {
							// TODO Auto-generated method stub
							Dissloading();
						}
					});
		} else {
			showToastNotNet();
		}
	}
}
