package com.hncainiao.fubao.ui.activity.personalcenter;




import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.listener.OnClickAvoidForceListener;
import com.hncainiao.fubao.ui.views.CustomAlertDialog;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class PatientInfo extends BaseActivity {
	Context mContext;
	TextView name,sex,age,phone,idcard,ka_num;
	TextView relate;
	Intent intent=null;
	String patient_id;
	private CustomAlertDialog dialog;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.patient_info);
		InitView();
		listen();
	}
	private void listen() {
		phone.setOnClickListener(new l());
		ka_num.setOnClickListener(new l());

	}



	private void InitView() {
		mContext=this;
		setTitle("就诊人信息");
		((TextView) findViewById(R.id.delete)).setVisibility(View.VISIBLE);
		((TextView) findViewById(R.id.delete)).setOnClickListener(listener);
		
		name=(TextView) findViewById(R.id.name_x);
		sex=(TextView) findViewById(R.id.sex);
		age=(TextView) findViewById(R.id.age);
		phone=(TextView) findViewById(R.id.phone);
		idcard=(TextView) findViewById(R.id.idcard);
		ka_num=(TextView) findViewById(R.id.kanum);
		relate=(TextView) findViewById(R.id.relate);
	}
	
	private OnClickAvoidForceListener listener = new OnClickAvoidForceListener() {

		@Override
		public void onClickAvoidForce(View v) {
			if(v.getId()==R.id.delete){
				dialog = new CustomAlertDialog(mContext, listener);
				dialog.setTitle("提 示");
				dialog.setMessage("确认刪除该就诊人吗?");
			}
			
			if(v.getId()==R.id.btn_custom_alertdialog_confirm){
				if (dialog != null) {
					dialog.dismiss();
				}
			}
			if(v.getId()==R.id.btn_custom_alertdialog_confirm){
				delete();//删除就诊人
				intent=new Intent(mContext,UsuallyPatient.class);
				startActivity(intent);
			}
			
		}
		
	};
	class l implements View.OnClickListener{

		@Override
		public void onClick(View arg0) {
			if(arg0.getId()==R.id.phone){
				intent =new Intent(mContext,UpdataPhone.class);
				intent.putExtra("FLAG", "jiuzhenphone");
				intent.putExtra("patient_id", patient_id);
				startActivity(intent);
				
			}else if(arg0.getId()==R.id.kanum){
				intent =new Intent(mContext,UpdateKanum.class);
				intent.putExtra("patient_id", patient_id);
				startActivity(intent);
				
			}
		}
		
	}
	private void showData() {
		if(NetworkUtil.isOnline(mContext)){
			AsyncHttpClient client=new AsyncHttpClient();
			String url=Constant.Jiuzhenren_Juti;
			client.setTimeout(5000);
			RequestParams params=new RequestParams();
			intent=getIntent();
			params.put("patient_id", intent.getStringExtra("patient_id"));
			patient_id=intent.getStringExtra("patient_id");
			client.post(url, params, new AsyncHttpResponseHandler(){
				@Override
				public void onStart() {
					Showloading();
					super.onStart();
				}
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					Dissloading();
					String front10="";
					String lastfour="";
					
					System.out.println("就诊人信息--------------------"+new String(responseBody));
					if(!CheckJson(responseBody).equals("")){
						try {
							JSONObject object=new JSONObject(new String(responseBody));
							if(object.getInt("err")==0){
								JSONArray array=object.getJSONArray("patient");
								for(int i=0;i<array.length();i++){
									name.setText(array.getJSONObject(i).getString("name"));
									if(array.getJSONObject(i).getString("gender").equals("1")){
										sex.setText("男");	
									}else{
										sex.setText("女");
									}
									age.setText(array.getJSONObject(i).getString("age"));	
									phone.setText(array.getJSONObject(i).getString("phone"));
									
									front10=array.getJSONObject(i).getString("idnumber")
											.substring(0, 10);	
									System.out.println("前10为"+front10);
									
									
									lastfour=array.getJSONObject(i).getString("idnumber")
											.substring(14, 18);
									
									
									System.out.println("后4wei"+lastfour);

									idcard.setText(""+front10+"****"+lastfour);
					
									ka_num.setText(array.getJSONObject(i).getString("cardno"));	
									String relate1=array.getJSONObject(i).getString("relate");
									if(relate1.equals("0")){
										relate.setText("本人");
									}else if(relate1.equals("1")){
										relate.setText("家人");
									}else if(relate1.equals("2")){
										relate.setText("朋友");

									}

									
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
					Dissloading();
				}

			});
			
		}else{
			showToast("网络连接中断");
		}

	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
	
		showData();

		
		super.onResume();
	}
	
	

	private  void delete(){
		if(NetworkUtil.isOnline(mContext)){
			AsyncHttpClient client=new AsyncHttpClient();
			String url=Constant.DELETEJIUZHENREN;
			client.setTimeout(5000);
			RequestParams params=new RequestParams();
			intent=getIntent();
			params.put("patient_id", intent.getStringExtra("patient_id"));
			client.post(url, params, new AsyncHttpResponseHandler(){
				@Override
				public void onStart() {
					Showloading();
					super.onStart();
				}
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						byte[] responseBody) {
					Dissloading();
					System.out.println("就诊人列表--------------------"+new String(responseBody));
					if(!CheckJson(responseBody).equals("")){
						try {
							JSONObject object=new JSONObject(new String(responseBody));
							if(object.getInt("err")==0){
							  showToast("删除成功");
							}else{
								
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						showToast("数据异常");
					}
					super.onSuccess(statusCode, headers, responseBody);
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					// TODO Auto-generated method stub
					Dissloading();
				}

			});
		
		}else{
			showToast("无网络连接");
		}

	}

}
