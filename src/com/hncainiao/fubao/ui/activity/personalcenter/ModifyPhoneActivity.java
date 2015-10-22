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
import com.hncainiao.fubao.utils.NetworkUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 	项目：FuBaoHealth
 * 		@author liujie
 *	日期：2015-5-27上午8:58:31
 *	修改手机号码
 */
public class ModifyPhoneActivity extends BaseActivity {

	EditText edNewPhone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);//不弹出软键盘

		setContentView(R.layout.modify_phone_activity);
		setTitle("修改联系号码");
		((Button)findViewById(R.id.btn_submit)).setOnClickListener(this);
		edNewPhone=(EditText)findViewById(R.id.ed_newphone);
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0.getId()==R.id.btn_submit) {
			if (!CheckEditViewNull(edNewPhone)) {
				modifyPhone();
			}
			else {
				showToast("请输入新的手机号码！");
			}
			
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
		imm.hideSoftInputFromWindow(edNewPhone.getWindowToken(), 0); //强制隐藏键盘  
	}
	/**
	 * 修改手机号码
	 */
	private void modifyPhone()
	{
		if (NetworkUtil.isOnline(this)) {
			AsyncHttpClient httClient =new AsyncHttpClient();
			RequestParams params =new RequestParams();
			params.put("phone", edNewPhone.getText().toString().trim());
			params.put("member_id", SharedPreferencesConfig.getStringConfig(this, "member_id"));
			httClient.post(Constant.url_modify_phone,params, new AsyncHttpResponseHandler()
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
								showToast("修改手机号码成功！");
								Intent intent =new Intent();
								intent.putExtra("phone", edNewPhone.getText().toString().trim());
								SharedPreferencesConfig.saveStringConfig(ModifyPhoneActivity.this, "phone", edNewPhone.getText().toString().trim());
								setResult(101, intent);
								Intent intent2 =new Intent();
								intent2.setAction(Constant.action_image);
								sendBroadcast(intent2);
								finish();
							}
							else {
								showToast("修改手机号码失败！");
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
