package com.hncainiao.fubao.ui.activity.doctor;

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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.adapter.hostory_doctorAdapter;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class History_Doctor extends BaseActivity {
	
	Context mContext;
	ListView listView;
	List<Map<String, Object>>mList;
	hostory_doctorAdapter adapter;
	  @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.history_doctor);
		initView();
		getData();
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				Intent intent=new Intent(mContext,DoctorDetailActivity.class);
				intent.putExtra("Doctor_id",mList.get(arg2).get("id")+"");
				startActivity(intent);
			}
			
		});
		
	
	}

	private void getData() {
		if(NetworkUtil.isOnline(mContext)){
			mList=new ArrayList<Map<String,Object>>();
			AsyncHttpClient client=new AsyncHttpClient();
			String url="";//请输入接口地址
			
			RequestParams params=new RequestParams();
			
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
					// TODO Auto-generated method stub
					Dissloading();
					if(!CheckJson(responseBody).equals("")){
						try {
							HashMap<String, Object>map=null;
							JSONObject object=new JSONObject(new String(responseBody));
							if(object.getInt("err")==0){
								/**
								 * 
								 * 下面拿到接口后填入数组名
								 * **/
								JSONArray array=object.getJSONArray("");///
								for(int i=0;i<array.length();i++){
									map=new HashMap<String, Object>();
									map.put("id", array.getJSONObject(i).getString("id"));
									map.put("doctor_name", array.getJSONObject(i).getString("doctor_name"));
									map.put("patient_name", array.getJSONObject(i).getString("patient_name"));
									map.put("level", array.getJSONObject(i).getString("doctor_level"));
									map.put("img", array.getJSONObject(i).getString("img"));
									map.put("hospital", array.getJSONObject(i).getString("hospital"));
									map.put("keshi", array.getJSONObject(i).getString("keshi"));
									map.put("time", array.getJSONObject(i).getString("time"));
									map.put("states", array.getJSONObject(i).getString("states"));
									mList.add(map);
									adapter=new hostory_doctorAdapter(mContext);
									adapter.setList(mList);
									listView.setAdapter(adapter);
								}
								
							}
			
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}else{
						showToast("数据异常");
					}
					
					
					super.onSuccess(statusCode, headers, responseBody);
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					// TODO Auto-generated method stub
					Dissloading();
				//	showToast("无历史医生");;
				}
				
				
			});
			
			
			
			
		}else{
			showToastNotNet();
		}
		
		
	}

	private void initView() {
		mContext=this;
		setTitle("历史医生");	
		listView=(ListView) findViewById(R.id.history_docList);
		listView.setEmptyView(findViewById(R.id.imageview));

	}

}
