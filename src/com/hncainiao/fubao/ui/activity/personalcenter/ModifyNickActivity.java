package com.hncainiao.fubao.ui.activity.personalcenter;


import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
 *	日期：2015-5-27上午8:58:12
 *  修改昵称
 */
public class ModifyNickActivity extends BaseActivity {

	EditText edNick;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_nick_activity);
		setTitle("修改昵称");
		((Button)findViewById(R.id.btn_submit)).setOnClickListener(this);
		edNick=(EditText)findViewById(R.id.ed_newnick);
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0.getId()==R.id.btn_submit) {
			if (!CheckEditViewNull(edNick)) {
				modifyNick();
			}
			else {
				showToast("请输入新的手机号码！");
			}
			
		}
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		showLog("onstop");
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
		imm.hideSoftInputFromWindow(edNick.getWindowToken(), 0); //强制隐藏键盘  
	
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		showLog("onDestroy");
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
		imm.hideSoftInputFromWindow(edNick.getWindowToken(), 0); //强制隐藏键盘  
	}
	/**
	 * 修改昵称
	 */
	private void modifyNick()
	{
		if (NetworkUtil.isOnline(this)) {
			AsyncHttpClient httClient =new AsyncHttpClient();
			RequestParams params =new RequestParams();
			params.put("nickname", edNick.getText().toString().trim());
			params.put("member_id", SharedPreferencesConfig.getStringConfig(this, "member_id"));
		
			httClient.post(Constant.url_modify_nick,params, new AsyncHttpResponseHandler()
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
								showToast("修改昵称成功！");
								Intent intent =new Intent();
								intent.putExtra("nick", edNick.getText().toString().trim());
								SharedPreferencesConfig.saveStringConfig(ModifyNickActivity.this, "nick", edNick.getText().toString().trim());
								setResult(301, intent);
								Intent intent2 =new Intent();
								intent2.setAction(Constant.action_image);
								sendBroadcast(intent2);
								finish();
							}
							else {
								showToast("修改昵称失败！");
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
