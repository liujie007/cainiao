package com.hncainiao.fubao.ui.activity.personalcenter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.application.FuBaoApplication;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.main.MainActivity;
import com.hncainiao.fubao.ui.adapter.UsuallyPatientAdapter;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class UsuallyPatient extends BaseActivity {
	
	ListView listView;
	ImageView add1,add2,edit_xiugai;
	TextView nums;
	Context mContext;
	List<Map<String, Object>>mList;
	UsuallyPatientAdapter adapter;
	ImageView emptyView ;
	int  current_patient;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usually_patient);
		InitView();
		listen();
	}
	private void InitView() {
		 mContext=this;
		 setTitle("常用就诊人");
		listView=(ListView) findViewById(R.id.Usually_patientList);
		listView.setSelector(R.drawable.cannel_liseviewback);//去除Listview點擊背景
		add1=(ImageView) findViewById(R.id.add1);
		nums=(TextView) findViewById(R.id.nums);
		emptyView= (ImageView)findViewById(R.id.imageview);
	}
public void leftbuttonclick(View view) {
		
		Intent intent =new Intent(this,MainActivity.class);
		 FuBaoApplication.getInstance().setInt(1);
		startActivity(intent);

	}
	
	private void listen() {
		add1.setOnClickListener(new l());
		listView.setEmptyView(emptyView);
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent =new Intent(mContext,PatientInfo.class);
				intent.putExtra("patient_id", mList.get(arg2).get("patient_id")+"");
				startActivity(intent);
				
			}
			
		});
	}
	
	
	class l implements View.OnClickListener{
		Intent intent=null;

		@Override
		public void onClick(View arg0) {
			
			if(arg0.getId()==R.id.add1){
				if(current_patient<5){
					intent=new Intent(mContext,AddUsuallyPatient.class);
					startActivity(intent);
				}
				if(current_patient==5){
					showToast("您已添加了5位就诊人");
				}
				
				
			}	
		}
		
	}

	private void getdata() {
		mList = new ArrayList<Map<String, Object>>();
		if(NetworkUtil.isOnline(mContext)){
			AsyncHttpClient client=new AsyncHttpClient();
			String url=Constant.LISTJIUZHENREN;
			client.setTimeout(5000);
			RequestParams params=new RequestParams();
			params.put("member_id", SharedPreferencesConfig.getStringConfig(mContext, "member_id"));
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
					mList.clear();
					Dissloading();
					System.out.println("就诊人列表--------------------"+new String(responseBody));
					if(!CheckJson(responseBody).equals("")){
						try {
							Map<String, Object> map = null;
							JSONObject object=new JSONObject(new String(responseBody));
							if(object.getInt("err")==0){
								JSONArray array=object.getJSONArray("patient");
								nums.setText("您还可以添加"+(5-array.length())+"位就诊人");
								current_patient=array.length();
								for(int i=0;i<array.length();i++){
									map=new HashMap<String, Object>();
									map.put("name", array.getJSONObject(i).getString("name"));
									map.put("phone", array.getJSONObject(i).getString("phone"));
									map.put("age", array.getJSONObject(i).getString("age"));
									map.put("gender", array.getJSONObject(i).getString("gender"));
									map.put("patient_id", array.getJSONObject(i).getString("id"));
									mList.add(map);
								}
							}
							adapter=new UsuallyPatientAdapter(mContext);
							adapter.setList(mList);
							listView.setAdapter(adapter);

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						showToast("没有数据");
					}

					super.onSuccess(statusCode, headers, responseBody);
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					Dissloading();
				}

			});
		
		}else{
			showToast("无网络连接");
		}

	}
	@Override
	protected void onResume() {
		getdata();

		super.onResume();
	}


}
