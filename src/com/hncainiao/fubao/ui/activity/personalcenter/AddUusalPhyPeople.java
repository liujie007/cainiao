package com.hncainiao.fubao.ui.activity.personalcenter;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.utils.IsIdCard;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.hncainiao.fubao.utils.ToastManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AddUusalPhyPeople extends BaseActivity {
	
	Context mContext;
	EditText name,pone,idcard;
	Button save;
	IsIdCard isIdCard;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addpyusually);
		InitView();
		listen();	
	}
	private void InitView() {
		mContext=this;
		setTitle("添加常用体检人");
		isIdCard=new IsIdCard();
		name=(EditText) findViewById(R.id.edit_name);
		pone=(EditText) findViewById(R.id.edit_phone);
		idcard=(EditText) findViewById(R.id.edit_idcard);
		save=(Button) findViewById(R.id.save);	
	}

	private void listen() {
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(!NUlledit()){
					getData();
					finish();
				}else {
					
					if(!Isphone(pone.getText().toString())){
						showToast("手机输入有误");
					}if(!isIdCard.validateCard(idcard.getText().toString())){
						showToast("输入身份证有误");
					}
					if(name.getText().toString().equals("")){
						showToast("姓名不能为空");
					}
				}
			}

		
		});
		
	}
	
	private boolean NUlledit(){
		if(CheckEditViewNull(name)||CheckEditViewNull(pone)||CheckEditViewNull(idcard)||
				!Isphone(pone.getText().toString())||!isIdCard.validateCard
				(idcard.getText().toString()))
	{
			return true;
			
		}
		return false;
	}
	
	

	private void getData() {
		if(NetworkUtil.isOnline(mContext)){
			AsyncHttpClient client=new AsyncHttpClient();
			String url=Constant.NEWtijian_person;
			client.setTimeout(5000);
			RequestParams params=new RequestParams();
			params.put("name",name.getText().toString().trim());
			params.put("phone",pone.getText().toString().trim());
			params.put("member_id",SharedPreferencesConfig.getStringConfig(mContext, "member_id"));
			params.put("idnumber",idcard.getText().toString().trim());
			//params.put("relate", relate);
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
					System.out.println("新建------------------"+new String(responseBody));
					if(!CheckJson(responseBody).equals("")){
						try {
							JSONObject object=new JSONObject(new String(responseBody));
							if(object.getInt("err")==0){
								ToastManager.getInstance(mContext).showToast(object.getString("msg"));
								
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
		
	


}
