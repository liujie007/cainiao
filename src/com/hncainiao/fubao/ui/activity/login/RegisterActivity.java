package com.hncainiao.fubao.ui.activity.login;


import java.io.IOException;
import java.net.SocketTimeoutException;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.main.MainActivity;
import com.hncainiao.fubao.utils.FileService;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 	项目：FuBaoHealth
 * 		@author liujie
 *	日期：2015-5-12上午10:20:32
 *			注册
 */
public class RegisterActivity extends BaseActivity{

	private  EditText edPhone,edPwd,edCode;
	private  Button btnCode;
	private  TimeCount time;
	private  Context mContext;
	private  CheckBox ch_fubao;
	private  TextView tv_fubao;
	String code="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		setTitle("注册");
		mContext=RegisterActivity.this;
		inintView();
	}
	/**
	 * 初始化控件
	 */
	private  void inintView()
	{
		ch_fubao=(CheckBox)findViewById(R.id.ch_sure);
		tv_fubao=(TextView)findViewById(R.id.tv_fubao_xieyi);
		edPhone=(EditText)findViewById(R.id.ed_phone);
		edPwd=(EditText)findViewById(R.id.ed_pwd);
		edCode=(EditText)findViewById(R.id.ed_code);
		((Button)findViewById(R.id.btn_register)).setOnClickListener(this);
		btnCode=(Button)findViewById(R.id.btn_yzm);
		btnCode.setOnClickListener(this);
		tv_fubao.setOnClickListener(this);
		time = new TimeCount(60000, 1000);
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);
		if (arg0.getId()==R.id.btn_register) {
			//
			if (checkRegister()&&code.equals(edCode.getText().toString())&&!edCode.getText().toString().equals("")) {
				//进行注册操作
				pRegister();
			}
			else{
				//showToast("验证码不正确");
			}
		}
		if (arg0.getId()==R.id.btn_yzm) {
			//获取验证码
			if (CheckPhone(edPhone)) {
				//手机号码不能为空
				try {
					GetCode();
				
				
				} catch (SocketTimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
				}
			else {
					showToast("手机输入有误");
			}
			
		}
		if (arg0.getId()==R.id.tv_fubao_xieyi) {
			//福保健康协议
			startActivity(new Intent(this,AgreeActvitiy.class));
		}
	}
	/**
	 * 注册判断
	 */
	private  boolean checkRegister()
	{
		if (CheckEditViewNull(edPhone)) {
			//手机号码不能为空
			showToast("手机号码不能为空！");
			return false;
			}
		if (CheckEditViewNull(edPwd)) {
			//密码不能为空	
			showToast("密码不能为空！");
			return false;
			}
		if (CheckEditViewNull(edCode)) {
			//验证码不能为空
			showToast("验证码不能为空！");
			return false;
		}
		if (!ch_fubao.isChecked()) {
			showToast("必须同意福保健康注册协议！");
			return false;
		}
		return true;
	}
	/**
	 * 注册操作
	 */
	private void  pRegister() {
		
		if (NetworkUtil.isOnline(this)) {
			AsyncHttpClient  httpClient =new AsyncHttpClient();
			RequestParams params =new  RequestParams();
			params.put("phone", edPhone.getText().toString().trim());
			params.put("password", edPwd.getText().toString().trim());
			params.put("code", edCode.getText().toString().trim());
		    httpClient.post( Constant.url_Register, params, new AsyncHttpResponseHandler()
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
		    		
						//开始解析数据
		    			try {
							JSONObject jsonObject =new JSONObject(CheckJson(responseBody));
						    if (jsonObject.getInt("err")==0) {
								//注册成功{"err":0,"member_id":18}
						    	JSONObject jObject =jsonObject.getJSONObject("member");
						    	SharedPreferencesConfig.saveStringConfig(mContext, "member_id", jObject.getString("id"));
								SharedPreferencesConfig.saveStringConfig(mContext, "phone", jObject.getString("phone"));
								//SharedPreferencesConfig.saveStringConfig(mContext, "pwd", jObject.getString("password"));
								try {
									//保存个人信息
									new FileService().saveToSDCard("member", jObject.toString());
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								startActivity(new Intent(mContext,MainActivity.class));
								finish();
							}
						    else {
								//注册失败
						    	showToast(jsonObject.getString("msg"));
							}
		    			} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
		    		
		    		
		    		super.onSuccess(statusCode, headers, responseBody);
		    	}
		    	@Override
		    	public void onFailure(int statusCode, Header[] headers,
		    			byte[] responseBody, Throwable error) {
		    		// TODO Auto-generated method stub
		    		Dissloading();
		    		showToastNetTime();
		    		
		    	}
		    });
		}
		else
		{
			showToastNotNet();
		}
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
							JSONObject object=new JSONObject(new String(responseBody));
							if(object.getInt("err")==0){
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
