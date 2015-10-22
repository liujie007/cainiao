package com.hncainiao.fubao.ui.activity.registration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.hncainiao.fubao.R;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.doctor.DoctorDetailActivity;
import com.hncainiao.fubao.ui.activity.hospital.HospitalIndexActivity;
import com.hncainiao.fubao.ui.adapter.DoctorAdapter;
import com.hncainiao.fubao.ui.adapter.FollowHospitalAdapter;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Registration_search extends BaseActivity {
	Context mContext;
	LinearLayout ll_search;
	EditText search;
	Button btn_search;
	TextView tvCity;
	ListView show_search;
	List<Map<String, Object>>mList;
	FollowHospitalAdapter adapter;//医院适配器
	DoctorAdapter doctorAdapter;//医生适配器
	String tag="";//1医生   2,医院，3,科室
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);//不弹出软键盘
		setContentView(R.layout.activity_registriton_search);
		initView();
		location();// 定位
	}
	

	


	private void initView() {
		mContext=this;
		setTitle("搜索");
		ll_search=(LinearLayout) findViewById(R.id.ll_search1);
	   ((RelativeLayout) findViewById(R.id.rl_select_city)).setVisibility(View.VISIBLE);
		search=(EditText) findViewById(R.id.Res_search);
		tvCity = (TextView) findViewById(R.id.tv_city);
		btn_search=(Button) findViewById(R.id.btn_search);
		btn_search.setOnClickListener(this);
		show_search=(ListView) findViewById(R.id.Res_list);
		show_search.setEmptyView(findViewById(R.id.imageview));
		
		show_search.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent=null;
				if(tag.equals("1")){
					intent= new Intent(mContext,DoctorDetailActivity.class);
					intent.putExtra("Doctor_id", mList.get(position).get("_id")+"");
					SharedPreferencesConfig.saveStringConfig(mContext, "Doctor_id",mList.get(position).get("_id")+"");
					startActivity(intent);
					
				}
				if(tag.equals("2")){
					intent=new Intent(mContext,HospitalIndexActivity.class);
					intent.putExtra("hospital_id", mList.get(position).get("_id")+"");
					startActivity(intent);

				}
				if(tag.equals("3")){
					intent= new Intent(mContext,DoctorDetailActivity.class);
					intent.putExtra("Doctor_id", mList.get(position).get("_id")+"");
					SharedPreferencesConfig.saveStringConfig(mContext, "Doctor_id",mList.get(position).get("_id")+"");
					startActivity(intent);
				}
				
				
				
			}
		});
	}
	
	
	@Override
	public void onClick(View arg0) {
		
		switch (arg0.getId()){
		case R.id.btn_search:
		
			if(search.getText().toString().equals("")){
				showToast("请输入关键词");
			}else{
				show_search.setBackgroundDrawable(null);
				try {
					setData();
				} catch (TimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					showToast("网络连接超时");
				}
			}
			break;
		
		
		}

	}


	private void setData() throws TimeoutException{
		if(NetworkUtil.isOnline(mContext)){
			mList=new ArrayList<Map<String,Object>>();
			AsyncHttpClient client=new AsyncHttpClient();
			String url=Constant.ResSerach;//请输入接口地址
			client.setTimeout(5000);
			RequestParams params=new RequestParams();
			params.put("keyword", search.getText().toString().trim());//判断输入的搜索内容
			params.put("region", SharedPreferencesConfig.getStringConfig(mContext, "city"));
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
								 * 医院
								 * **/
								if(object.getString("type").equals("hospital")){
									JSONArray array=object.getJSONArray("data");///
									
									tag=2+"";
									for(int i=0;i<array.length();i++){
										map=new HashMap<String, Object>();
										map.put("_id", array.getJSONObject(i).getString("id"));
										map.put("name", array.getJSONObject(i).getString("name"));
										map.put("level", array.getJSONObject(i).getString("type"));
										map.put("img", array.getJSONObject(i).getString("img"));
										mList.add(map);
										adapter=new FollowHospitalAdapter(mContext);
										adapter.setList(mList);
										show_search.setAdapter(adapter);
									}//科室
								}else if(object.getString("type").equals("department")){
									tag=3+"";
									System.out.println("搜索医院");
									JSONArray array=object.getJSONArray("data");///
								
									for(int i=0;i<array.length();i++){
										map=new HashMap<String, Object>();
										map.put("name", array.getJSONObject(i).getString("name"));
										map.put("level", array.getJSONObject(i).getString("title"));
										map.put("locate", array.getJSONObject(i).getString("hospital_name"));
										map.put("img", array.getJSONObject(i).getString("avatar"));
										map.put("_id", array.getJSONObject(i).getString("doctor_id"));
										map.put("doctor_status", "4");
										mList.add(map);
										doctorAdapter=new DoctorAdapter(mContext);
										doctorAdapter.setList(mList);
										show_search.setAdapter(doctorAdapter);
										
									}
									
									
									
								}
								if(object.getString("type").equals("doctor")){
									JSONArray array=object.getJSONArray("data");///
								
                                    tag=1+"";
									for(int i=0;i<array.length();i++){
										map=new HashMap<String, Object>();
										map.put("name", array.getJSONObject(i).getString("name"));
										map.put("level", array.getJSONObject(i).getString("title"));
										map.put("locate", array.getJSONObject(i).getString("hospital_name"));
										map.put("img", array.getJSONObject(i).getString("avatar"));
										map.put("_id", array.getJSONObject(i).getString("doctor_id"));
										map.put("doctor_status", "4");
										mList.add(map);
										doctorAdapter=new DoctorAdapter(mContext);
										doctorAdapter.setList(mList);
										show_search.setAdapter(doctorAdapter);
									}
									
								}
								
							
								
							}else if(object.getInt("err")==1){
								mList.clear();
								if(adapter!=null){
									adapter.setList(mList);
									show_search.setAdapter(adapter);
									adapter.notifyDataSetChanged();
									
								}else if(doctorAdapter!=null){
									adapter.setList(mList);
									show_search.setAdapter(doctorAdapter);
									adapter.notifyDataSetChanged();
								}
								
							}
			
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}else{
						mList.clear();
						if(adapter!=null){
							adapter.setList(mList);
							show_search.setAdapter(adapter);
							adapter.notifyDataSetChanged();
							
						}else if(doctorAdapter!=null){
							if (adapter!=null) {
								
							adapter.setList(mList);
							show_search.setAdapter(doctorAdapter);
							adapter.notifyDataSetChanged();
							}
						}
						
						showToast("没有数据");
					}
					
					
				}
				@Override
				public void onFailure(int statusCode, Header[] headers,
						byte[] responseBody, Throwable error) {
					// TODO Auto-generated method stub
					if(mList!=null){
						mList.clear();
						adapter.notifyDataSetChanged();
						
					}
					showToast("请重试");
					Dissloading();
				}
				
				
			});
			
			
			
			
		}else{
			showToastNotNet();
			Dissloading();

		}
		
	}
	@Override
	public void onReceiveLocation(BDLocation arg0) {
		// TODO Auto-generated method stub
		super.onReceiveLocation(arg0);
		if (arg0 != null) {
			// 等到经度和纬度
			HashMap<String, Object> has = new HashMap<String, Object>();
			has.put("lng", arg0.getLongitude());
			has.put("lat", arg0.getLatitude());
			has.put("city", arg0.getCity());
			setMap(has, "location");
			// FuBaoApplication.getInstance().set("location", has);
		}

	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		tvCity.setText(SharedPreferencesConfig.getStringConfig(mContext, "city"));
		
		super.onResume();
	}
	

}
