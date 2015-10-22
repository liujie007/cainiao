package com.hncainiao.fubao.ui.activity.personalcenter;

import java.util.HashMap;

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
import com.hncainiao.fubao.net.GuaHaoApi;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.location.Location_hospital;
import com.hncainiao.fubao.ui.listener.OnClickAvoidForceListener;
import com.hncainiao.fubao.ui.views.CustomAlertDialog;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class RegisOrderMessage extends BaseActivity {
	Context mContext;
	TextView ordernum,ordertime,orderstatus,name,idcard,kanum,phone,
	 hospital,office,doctor,Visittime,mustknow;
	Button Startquee,Cannel,btnGuahao;
	ImageView iv_location;
	Intent intent=null;
	private CustomAlertDialog dialog;//弹出框
	String register_d;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reg_ordermessage);
		Initview();
		register_d=getIntent().getStringExtra("register_d");
		listen();
		GetData();
	}
	
	public void leftbuttonclick(View view) {
		
		startActivity(new Intent(this,RegisOrderActivity.class));

	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		startActivity(new Intent(this,RegisOrderActivity.class));
		super.onBackPressed();
	}


	private void listen() {
		Startquee.setOnClickListener(l);
		Cannel.setOnClickListener( l);
		iv_location.setOnClickListener( l);
		btnGuahao.setOnClickListener(l);
		}

	private void Initview() {
		// TODO Auto-generated method stub
		mContext=this;
		setTitle("预约挂号详情");
		ordernum=(TextView) findViewById(R.id.tv_order_sn);
		ordertime=(TextView) findViewById(R.id.tv_place_time);
		orderstatus=(TextView) findViewById(R.id.tv_phy_state);
		name=(TextView) findViewById(R.id.tv_name);
		idcard=(TextView) findViewById(R.id.tv_cak);
		kanum=(TextView) findViewById(R.id.kanum);
		phone=(TextView) findViewById(R.id.tv_phone);
		hospital=(TextView) findViewById(R.id.tv_hostname);
		office=(TextView) findViewById(R.id.tv_taocan);
		doctor=(TextView) findViewById(R.id.tv_price);
		Visittime=(TextView) findViewById(R.id.exam_time);
		mustknow=(TextView) findViewById(R.id.mustknow);
		Startquee=(Button) findViewById(R.id.btn_place);
		Cannel=(Button) findViewById(R.id.btn_cancel);	
		btnGuahao=(Button)findViewById(R.id.btn_guahao);
		iv_location=(ImageView) findViewById(R.id.iv_location);
	}
	private OnClickAvoidForceListener l = new OnClickAvoidForceListener() {

		@Override
		public void onClickAvoidForce(View v) {
			switch (v.getId()) {
			case R.id.iv_location:
				 intent=new Intent(mContext,Location_hospital.class);
	               startActivity(intent);
				break;
			case R.id.btn_cancel:
				dialog = new CustomAlertDialog(mContext, l);
				dialog.setTitle("提 示");
				dialog.setMessage("确认取消订单吗？");
				break;
			case R.id.btn_custom_alertdialog_confirm:
				//cannel();
				break;
			case R.id.btn_custom_alertdialog_cancel:
				 dialog.dismiss();
				 break;
			case R.id.btn_guahao:
				//继续挂号
				  if (NetworkUtil.isOnline(RegisOrderMessage.this)) {
					  Showloading();
					GuaHaoApi.RepeatGh(register_d,guahaoHandler);
				}
				  else
				  {
					  showToastNotNet();
				  }
				break;
			default:
				break;
			}
			
			
		}
		
		
	
	};
	AsyncHttpResponseHandler  guahaoHandler=new AsyncHttpResponseHandler()
	{
		public void onSuccess(String content) {
			Dissloading();
			try {
				JSONObject jsonObject = new JSONObject(content);
				if (jsonObject.getInt("err")==0) {
					GetData();
				}
				else {
					showToast(jsonObject.getString("msg"));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		};
		public void onFailure(Throwable error) {
			Dissloading();
			showToastNetTime();
		};
	};
	/*
	private void cannel() {
		
		if (NetworkUtil.isOnline(this)) {
			intent=getIntent();
			AsyncHttpClient httpClient =new AsyncHttpClient();
			String url=Constant.CannelGuahao;
			RequestParams params =new RequestParams();
			if(intent!=null){
				params.put("register_id", intent.getStringExtra("register_d"));

			}
			httpClient.post(url, params, new AsyncHttpResponseHandler(){
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
					Dissloading();
					if(!CheckJson(responseBody).equals("")){
						try {
							JSONObject object=new JSONObject(new String(responseBody));
							if(object.getInt("err")==0){
	                          showToast("取消成功");
	          				   dialog.dismiss();

	                          GetData();
							}else{
		                          showToast(object.getString("msg"));

							}
							
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}else{
						showToast("没有数据");
					}
					
					
					super.onSuccess(statusCode, headers, responseBody);
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					// TODO Auto-generated method stub
					Dissloading();
					showToast("请重试");
					
				}
				
				
			});

	}else{
		showToast("网络连接失败");
	}
}*/

	private void GetData() {
		if (NetworkUtil.isOnline(this)) {
			intent=getIntent();
			AsyncHttpClient httpClient =new AsyncHttpClient();
			String url=Constant.ResXiqng;
			RequestParams params =new RequestParams();
			params.put("register_id", register_d);
			if(!intent.getStringExtra("message_id").equals("")&&intent.getStringExtra("message_id")!=null){
				params.put("message_id", intent.getStringExtra("message_id"));
			}else{
				params.put("message_id", "");

			}
			httpClient.post(url, params, new AsyncHttpResponseHandler(){
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
					Dissloading();
					if(!CheckJson(responseBody).equals("")){
						try {
							JSONObject object=new JSONObject(new String(responseBody));
							if(object.getInt("err")==0){
								JSONArray array=object.getJSONArray("register");
							for(int i=0;i<array.length();i++){
								String status=array.getJSONObject(i).getString("status");
								
								if (status.equals("0")) {
									orderstatus.setText("已取消");
								}
								if(status.equals("1")){
									orderstatus.setText("订单已生成");
								}
								if(status.equals("2")){
									orderstatus.setText("病历已生成");
								}
								if(status.equals("20")){
									orderstatus.setText("已预约未支付");
								}
								if(status.equals("10")){
									orderstatus.setText("已预约已支付");
								}
								if(status.equals("40")){
									orderstatus.setText("未付款未挂号");
								}
								if(status.equals("30")){
									orderstatus.setText("已付款未挂号");
									btnGuahao.setVisibility(View.VISIBLE);
								}
								ordernum.setText("订单号:"+array.getJSONObject(i).getString("order"));	
								ordertime.setText("下单时间："+TimetoData(array.getJSONObject(i).getString("createtime")));
								name.setText("姓  名:"+array.getJSONObject(i).getString("patient_name"));
								idcard.setText("身份证号:"+array.getJSONObject(i).getString("patient_number"));
								kanum.setText("就诊卡号:"+array.getJSONObject(i).getString("patient_cardno"));
								phone.setText("联系电话:"+array.getJSONObject(i).getString("patient_phone"));
								hospital.setText(array.getJSONObject(i).getString("hospital_name"));
								office.setText(array.getJSONObject(i).getString("department_name"));
								doctor.setText(array.getJSONObject(i).getString("doctor_name"));

								Visittime.setText(array.getJSONObject(i).getString("clinic_date")
										//+array.getJSONObject(i).getString("clinic_dayofweek")
										+array.getJSONObject(i).getString("clinic_time"));
								//mustknow.setText(array.getJSONObject(i).getString("doctor_name"));
								HashMap<String, Object> has = new HashMap<String, Object>();
								has.put("lng", array.getJSONObject(i).getString("hospital_lng"));
								has.put("lat", array.getJSONObject(i).getString("hospital_lat"));
								has.put("adress", array.getJSONObject(i).getString("hospital_address"));
								has.put("hospital_name", array.getJSONObject(i).getString("hospital_name"));
								has.put("phone", array.getJSONObject(i).getString("hospital_telphone"));
								setMap(has, "location_hospital");
							}
								
								
								
							}
							
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}else{
						showToast("没有数据");
					}
					
					
					super.onSuccess(statusCode, headers, responseBody);
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					// TODO Auto-generated method stub
					Dissloading();
					showToast("请重试");
					super.onFailure(statusCode, headers, responseBody, error);
				}
				
				
			});

	}else{
		showToast("网络连接失败");
	}
}
	
	
	

}
