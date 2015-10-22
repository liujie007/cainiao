package com.hncainiao.fubao.ui.activity.personalcenter;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.hncainiao.fubao.utils.ToastManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class UpdateKanum extends BaseActivity {
	
	Context mContext;
	EditText editText;
	Button btn_queren;
	Intent intent=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.updata_kanum);
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);//不弹出软键盘

        Initview();
		
	}

	private void Initview() {
		mContext=this;
		setTitle("修改就诊卡号");
		editText=(EditText) findViewById(R.id.new_kanum);
		
		
		btn_queren=(Button) findViewById(R.id.btn_new);
		
		btn_queren.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Updatakanum();
				
			}

			
		});	
		
	}
	private void Updatakanum() {
		if(NetworkUtil.isOnline(mContext)){
			AsyncHttpClient client=new AsyncHttpClient();
			String url=Constant.UPDATEJIUZHENREN;
			client.setTimeout(5000);
			RequestParams params=new RequestParams();
			intent=getIntent();
			params.put("patient_id",intent.getStringExtra("patient_id"));	
			System.out.println("病号"+intent.getStringExtra("patient_id"));
			params.put("value",editText.getText().toString());
			params.put("field", "cardno");
			
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
					Dissloading();
					if(!CheckJson(responseBody).equals("")){
						try {
							System.out.println("修改卡号"+new String(responseBody));
							JSONObject object=new JSONObject(new String(responseBody));
							if(object.getInt("err")==0){
								
								ToastManager.getInstance(mContext).showToast(object.getString("msg"));
								//intent=new Intent(mContext,PatientInfo.class);
								//startActivity(intent);
								finish();

							}else{
								ToastManager.getInstance(mContext).showToast(object.getString("msg"));
							}
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						showToast("沒有数据");
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
			ToastManager.getInstance(mContext).showToast("无网络连接");
		}
	}
	

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
	
		super.onPause();
	}
}
