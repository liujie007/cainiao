package com.hncainiao.fubao.ui.activity.personalcenter;


import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.login.LoginActivity;
import com.hncainiao.fubao.utils.FileService;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 	项目：FuBaoHealth
 * 		@author liujie
 *	日期：2015-5-27上午8:56:57
 *
 *  修改密码
 */
public class ModifyPwdActivity extends BaseActivity {

	EditText edPwd,edNewPwd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);//不弹出软键盘

		setContentView(R.layout.modify_pwd_activity);
		setTitle("修改密码");
		((Button)findViewById(R.id.btn_submit)).setOnClickListener(this);
		edPwd=(EditText)findViewById(R.id.ed_pwd);
		edNewPwd=(EditText)findViewById(R.id.ed_newpwd);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
		imm.hideSoftInputFromWindow(edPwd.getWindowToken(), 0); //强制隐藏键盘  
		imm.hideSoftInputFromWindow(edNewPwd.getWindowToken(), 0); //强制隐藏键盘  
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);
		if (arg0.getId()==R.id.btn_submit) {
			
			if (!CheckEditViewNull(edPwd)&&!CheckEditViewNull(edNewPwd)) {
				modifyPwd();
			}
			else {
				showToast("请填写完整信息！");
			}
		}
	}
	/**
	 * 修改密码
	 */
	private void modifyPwd()
	{
		if (NetworkUtil.isOnline(this)) {
			AsyncHttpClient httClient =new AsyncHttpClient();
			RequestParams params =new RequestParams();
			params.put("old", edPwd.getText().toString().trim());
			params.put("new", edNewPwd.getText().toString().trim());
			params.put("member_id", SharedPreferencesConfig.getStringConfig(this, "member_id"));
		
			httClient.post(Constant.url_modify_pwd,params, new AsyncHttpResponseHandler()
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
					if (!CheckJson(responseBody).equals("")) {
						try {
							JSONObject jsonObject =new JSONObject(new String(responseBody));
							if (jsonObject.getInt("err")==0) {
								showToast("修改密码成功！");
								new FileService().delePhoto("imghead");
								SharedPreferencesConfig.saveStringConfig(ModifyPwdActivity.this, "member_id", "");
								SharedPreferencesConfig.clear(ModifyPwdActivity.this);
								startActivity(new Intent(ModifyPwdActivity.this,LoginActivity.class));
								finish();
							}
							else {
								showToast("修改密码失败！");
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else {
						showToast("修改参数失败！");
					}
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
		else {
			showToastNotNet();
		}
	}
}
