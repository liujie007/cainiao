package com.hncainiao.fubao.ui.activity.login;



import java.net.SocketTimeoutException;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class FindPasswd extends BaseActivity {
	String code="";
	EditText edPhone,edNewkty,edCode,edConfirenewword;
	Button btnCode,btnConfire;
	Context mContext;
	TimeCount time;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_key);
		initView();
		initlisten();

		
	}
	private void initView() {
		// TODO Auto-generated method stub
		mContext=this;
		setTitle("找回密码");
		edPhone=(EditText) findViewById(R.id.ed_phone);
		edNewkty=(EditText) findViewById(R.id.ed_pwd);
		edCode=(EditText) findViewById(R.id.ed_code);
		btnCode=(Button) findViewById(R.id.btn_yzm);
		edConfirenewword=(EditText)findViewById(R.id.confirenew1);
		btnConfire=(Button) findViewById(R.id.btn_register);
	    time=new TimeCount(60000, 1000);

	}

	private void initlisten() {
		btnConfire.setOnClickListener(new l());
		btnCode.setOnClickListener(new l());
		
		
			
	}
	
	
	
	
	class l implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.btn_register:
				if(!NUlledit()){
					Updatakey();
				}else{
					
					if(!edConfirenewword.getText().toString().equals(edNewkty.getText().toString())){
						showToast("两次输入密码不一致");
						
					}if(edConfirenewword.getText().toString().equals("")||
							edCode.getText().toString().equals("")||
							edNewkty.getText().toString().equals("")||edPhone.getText().toString().equals("")
							){
						showToast("输入不能为空");
					}
					if(!code.equals(edCode.getText().toString())){
						showToast("验证码输入不正确");
					}
					
				}
				break;
				
			case R.id.btn_yzm:
				if(Isphone(edPhone.getText().toString())){
					try {
						GetCode();
					} catch (SocketTimeoutException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					showToast("手机号码不正确");
				}
				
				
				
				break;
				

			default:
				break;
			}
			
			
			
			
		}

		
	}
	

	private void Updatakey() {
		
		if(NetworkUtil.isOnline(mContext)){
			AsyncHttpClient client=new AsyncHttpClient();
			String url=Constant.Updatekey;
			client.setTimeout(5000);
			RequestParams params=new RequestParams();
			//params.put("member_id", SharedPreferencesConfig.getStringConfig(mContext, "member_id"));
			params.put("phone", edPhone.getText().toString());
			params.put("password", edNewkty.getText().toString());
			client.post(url, params, new AsyncHttpResponseHandler(){
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
					System.out.println("忘记密码"+new String(responseBody));
					Dissloading();
					try {
						JSONObject object=new JSONObject(new String (responseBody));
						if(object.getInt("err")==0){
							showToast("修改成功");
							Intent intent=new Intent(mContext,LoginActivity.class);
							startActivity(intent);
							
						}else{
							showToast(object.getString("msg"));
						}
	
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					
					
					
					
					super.onSuccess(statusCode, headers, responseBody);
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					// TODO Auto-generated method stub
					
					Dissloading();
					showToast("修改失败");
				}
				
				
				
				
			});
			
			
			
		}else{
			showToastNotNet();
		}
		
		
		
		
		
	}
	
	private boolean NUlledit(){
		if(CheckEditViewNull(edPhone)||CheckEditViewNull(edCode)||CheckEditViewNull(edNewkty)||
					!Isphone(edPhone.getText().toString())||CheckEditViewNull(edConfirenewword)
					||!edNewkty.getText().toString().equals(edConfirenewword.getText().toString())
					
				||!code.equals(edCode.getText().toString())
					)
		{
			return true;

		}
		return false;
	}
	
	
	
	
	

	/**
	 * 计时器
	 * 
	 * 
	 */
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {// 计时完毕时触发
			btnCode.setText("重新验证");
			btnCode.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			btnCode.setClickable(false);
			btnCode.setText("请等待" + millisUntilFinished / 1000 + "秒");
		}
	}
	
	/**
	 * 获取验证码
	 * */
	private void GetCode() throws SocketTimeoutException{
		if(NetworkUtil.isOnline(mContext)){
			AsyncHttpClient client=new AsyncHttpClient();
			String url=Constant.YZM_STRING;
			client.setTimeout(5000);
			RequestParams params=new RequestParams();
			params.put("phone",edPhone.getText().toString() );
			client.post(url, params, new AsyncHttpResponseHandler(){
				
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
					super.onSuccess(statusCode, headers, responseBody);
					System.out.println("获得验证码"+new String(responseBody));
						
						try {
							System.out.println("开始获取");
							JSONObject object=new JSONObject(new String(responseBody));
							if(object.getInt("err")==0){
								System.out.println("获取成功");
								code=object.getString("vcode");
								time.start();
								
							}else{
								showToast(object.getString("msg"));
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
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
			showToast("无网络连接");
		}
		
	
		
	}



}
