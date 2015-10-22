package com.hncainiao.fubao.ui.activity.login;

import java.io.IOException;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.application.FuBaoApplication;
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
 *	日期：2015-5-12上午10:30:56
 *    
 *    登陆
 */
public class LoginActivity extends BaseActivity {

	private EditText edPhone,edPwd;
	private CheckBox chGetme;
	private Context mContext;
	private String mlogin="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mContext=LoginActivity.this;
		if (getIntent().getStringExtra("mlogin")!=null) {
			mlogin=getIntent().getStringExtra("mlogin");
		}
		setTitle("登录");
		inintView();
	}
	/**
	 * 控件初始化
	 */
	private void inintView()
	{
		((Button)findViewById(R.id.comeback)).setVisibility(View.INVISIBLE);
		edPhone=(EditText)findViewById(R.id.ed_phone);
		edPwd=(EditText)findViewById(R.id.ed_pwd);
		chGetme=(CheckBox)findViewById(R.id.ch_getme);
		chGetme.setChecked(SharedPreferencesConfig.getBoolConfig(mContext, "chgetme"));
		if (chGetme.isChecked()) {
			edPhone.setText(SharedPreferencesConfig.getStringConfig(mContext, "phone"));
			edPwd.setText(SharedPreferencesConfig.getStringConfig(mContext, "pwd"));
		}
		chGetme.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				SharedPreferencesConfig.saveBoolConfig(LoginActivity.this, "chgetme", arg1);
			}
		});
	
		((TextView)findViewById(R.id.tv_go_register)).setOnClickListener(this);
		((TextView)findViewById(R.id.tv_forget_pwd)).setOnClickListener(this);
		((Button)findViewById(R.id.btn_login)).setOnClickListener(this);
	}
	/**
	 * 登录判断
	 */
	private  boolean checkLogin()
	{
		if (CheckEditViewNull(edPhone)) {
			//手机号码不能为空
			showToast("手机号码不能为空！");
			
			return false;
			}
		if (!CheckPhone(edPhone)) {
			//手机号码不能为空
			showToast("请输入正确的手机号码");
			
			return false;
			}
		
		if (CheckEditViewNull(edPwd)) {
			//密码不能为空	
			showToast("密码不能为空！");
			return false;
			}
		
		return true;
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);
		switch (arg0.getId()) {
		case R.id.btn_login:
			showLog("login");
			if (checkLogin()) {
				//进行登录操作
				if (chGetme.isChecked()) {
					SharedPreferencesConfig.saveStringConfig(mContext, "phone", edPhone.getText().toString());
					SharedPreferencesConfig.saveStringConfig(mContext, "pwd", edPwd.getText().toString());
				
				}
				pLogin();
			}
			break;
		case R.id.tv_forget_pwd:
			
			Intent intent1=new Intent(mContext,FindPasswd.class);
			startActivity(intent1);
			
			//忘记密码
			break;
		case R.id.tv_go_register:
			Intent intent=new Intent(mContext,RegisterActivity.class);
			startActivity(intent);
			
			break;
			

		default:
			break;
		}
	}
	
	/**
	 * 登录操作
	 */
	private void  pLogin() {
		
		
		if (NetworkUtil.isOnline(this)) {
			AsyncHttpClient  httpClient =new AsyncHttpClient();
			RequestParams params =new  RequestParams();
			params.put("val", edPhone.getText().toString().trim());
			params.put("password", edPwd.getText().toString().trim());
		    httpClient.post( Constant.url_login, params, new AsyncHttpResponseHandler()
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
		    		super.onSuccess(statusCode, headers, responseBody);
		    			Dissloading();
		    		if (!CheckJson(responseBody).equals("")) {
		    			
		    			//开始解析数据{"err":1,"msg":"\u7528\u6237\u4e0d\u5b58\u5728\uff08\u7528\u6237\u540d+\u5bc6\u7801\uff09"}
		    			try {
							JSONObject jsonObject =new JSONObject(CheckJson(responseBody));
							  if (jsonObject.getInt("err")==0) {
								  
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
								if (mlogin.equals("self")) {
									 finish();
								}
								else {
									startActivity(new Intent(mContext,MainActivity.class));
									 FuBaoApplication.getInstance().setInt(0);
									 finish();
								}
								
								 
//								System.out.println("dddddd=ggg"+intent.getStringExtra("restologin"));
//								if(intent.getStringExtra("restologin").equals("login")){
//									Intent intent2=new Intent(mContext,RegistrationMessageActivity.class);
//									//startActivity(new Intent(mContext,RegistrationMessageActivity.class));
//									intent2.putExtra("zuozhen_num",SharedPreferencesConfig.getStringConfig(mContext, "zuozhen_num") );
//									startActivity(intent2);
//									finish();
//								}else{
//									
//								}
								
								
							}
							  else {
								showToast("登录失败！");
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    		}
		    		else {
						showToast("数据异常，不是json格式！");
					}
		    		
		    	}
		    	@Override
		    	public void onFailure(int statusCode, Header[] headers,
		    			byte[] responseBody, Throwable error) {
		    		// TODO Auto-generated method stub
		    		  Dissloading();//取消加载
		    		 showToastNetTime();//网络超时
	
		    	}
		    });
		}
		else
		{
			showToastNotNet();
		}
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (mlogin.equals("self")) {
			super.onBackPressed();
		}
		else {
			startActivity(new Intent(mContext,MainActivity.class));
		}
	  	 
		 //FuBaoApplication.getInstance().setInt(0);
		 //MainActivity.mJazzy.setCurrentItem(0);
		
	}
	
}
