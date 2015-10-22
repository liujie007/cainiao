package com.hncainiao.fubao.ui.activity.personalcenter;


import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
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
import com.hncainiao.fubao.utils.NetworkUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 	项目：FuBaoHealth
 * 		@author liujie
 *	日期：2015-5-27上午8:59:02
 *  修改地址
 */
public class ModifyAddressActivity extends BaseActivity {

	EditText edAddress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);//

		setContentView(R.layout.modify_address_activity);
		setTitle("修改常居住地址");
		((Button)findViewById(R.id.btn_submit)).setOnClickListener(this);
		edAddress=(EditText)findViewById(R.id.ed_newaddress);
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);
		if (arg0.getId()==R.id.btn_submit) {
			
			if (!CheckEditViewNull(edAddress)) {
				 modifyAddress();
			}
			else {
				showToast("请输入新的地址！");
			}
		}
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		showLog("onstop");
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
		imm.hideSoftInputFromWindow(edAddress.getWindowToken(), 0); //强制隐藏键盘  
	
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		showLog("destroy");
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
		imm.hideSoftInputFromWindow(edAddress.getWindowToken(), 0); //强制隐藏键盘  
	}
	/**
	 * 修改地址
	 */
	private void modifyAddress()
	{
		if (NetworkUtil.isOnline(this)) {
			AsyncHttpClient httClient =new AsyncHttpClient();
			RequestParams params =new RequestParams();
			params.put("address", edAddress.getText().toString().trim());
			params.put("member_id", SharedPreferencesConfig.getStringConfig(this, "member_id"));
		
			httClient.post(Constant.url_modify_address,params, new AsyncHttpResponseHandler()
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
								showToast("修改地址成功！");
							}
							else {
								showToast("修改地址失败！");
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
