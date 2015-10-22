package com.hncainiao.fubao.ui.activity.personalcenter;



import org.apache.http.Header;
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

public class UsuallyPhyInfo extends BaseActivity {
	Context mContext;
	TextView name,sex,age,phone,idcard;
	Intent intent=null;
	String people_id;
	private CustomAlertDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usually_phyperson_info);
		InitView();
		listen();
	}

	

	private void InitView() {
		mContext=this;
		setTitle("体检人信息");
		((TextView) findViewById(R.id.delete)).setVisibility(View.VISIBLE);
		name=(TextView) findViewById(R.id.name_x);
		sex=(TextView) findViewById(R.id.sex);
		age=(TextView) findViewById(R.id.age);
		phone=(TextView) findViewById(R.id.phone);
		idcard=(TextView) findViewById(R.id.idcard);
	}
	private void listen() {
		
		((TextView) findViewById(R.id.delete)).setOnClickListener(listener);
		
		phone.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				intent=new Intent(mContext,UpdataPhone.class);
				intent.putExtra("FLAG","Phyphone" );
				intent.putExtra("people_id", people_id);
				startActivity(intent);
				
			}
		});
		
		
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
				del();//删除就诊人
				//intent=new Intent(mContext,UsuallyPatient.class);
				//startActivity(intent);
			}
			
		}
		
	};
	
	
	
	@Override
	protected void onResume() {
		ShowData();
		super.onResume();
	}
	
	private void del() {
		if(NetworkUtil.isOnline(mContext)){
			AsyncHttpClient client=new AsyncHttpClient();
			String url=Constant.DELtijian;
			client.setTimeout(5000);
			RequestParams params=new RequestParams();
			intent=getIntent();
			params.put("people_id", intent.getStringExtra("phyid"));
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
						      finish();

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
	
	

	private void ShowData() {
		if(NetworkUtil.isOnline(mContext)){
			AsyncHttpClient client=new AsyncHttpClient();
			String url=Constant.TijianXiangqing;
			client.setTimeout(5000);
			RequestParams params=new RequestParams();
			intent=getIntent();
			params.put("people_id", intent.getStringExtra("phyid"));
			people_id=intent.getStringExtra("phyid");
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
					System.out.println("体检人信息--------------------"+new String(responseBody));
					if(!CheckJson(responseBody).equals("")){
						try {
							JSONObject object=new JSONObject(new String(responseBody));
							if(object.getInt("err")==0){
								JSONObject obj2 = (JSONObject) object.get("info");
								
									name.setText(obj2.getString("name"));
									if(obj2.getString("gender").equals("1")){
										sex.setText("男");	
									}else{
										sex.setText("女");
									}
									age.setText(obj2.getString("age"));	
									phone.setText(obj2.getString("phone"));	
									idcard.setText(obj2.getString("idnumber"));
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

}
