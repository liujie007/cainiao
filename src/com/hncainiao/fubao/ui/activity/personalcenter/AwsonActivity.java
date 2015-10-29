package com.hncainiao.fubao.ui.activity.personalcenter;


import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.net.PersonApi;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * 	项目：FuBaoHealth
 * 		@author liujie
 *	日期：2015-6-5上午10:16:11
 *  意见反馈
 */
public class AwsonActivity extends BaseActivity {

	EditText edcontent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.awson_activity);
		setTitle("意见反馈");
		((Button)findViewById(R.id.btn_post)).setOnClickListener(this);
		edcontent=(EditText)findViewById(R.id.ed_content);
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_post:
			 if (!edcontent.getText().toString().equals("")) {
				 pdate();
			}
			 else {
				 showToast("请填写意见内容，再提交！");
			}
			break;

		default:
			break;
		}
	}
	private void pdate()
	{
		if (NetworkUtil.isOnline(this)) {
			Showloading();
			PersonApi.feedback(edcontent.getText().toString(), handler);
		}
		else {
			showToast("无网络连接");
		}
	}
	AsyncHttpResponseHandler handler =new AsyncHttpResponseHandler()
	{
		public void onSuccess(String content) {
			Dissloading();
			try {
				JSONObject jsonObject =new JSONObject(content);
				showToast(jsonObject.getString("msg"));
				edcontent.setText("");
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
	
}
