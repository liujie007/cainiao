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
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.adapter.UsuallyPhyAdapter;
import com.hncainiao.fubao.utils.NetworkUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
 
public class UsuallyPhyPerson extends BaseActivity {
	ListView listView;
	Context  mContext;
	ImageView add1,add2,edit_xiugai;
	List<Map<String, Object>>mList;
	UsuallyPhyAdapter adapter;
	TextView nums;
	Intent intent=null;
	int current_people;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usually_phyperson);
		initView();
		addlisten();
		
	}
	
	
	
	
	private void addlisten() {
		add1.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				if(current_people<5){
					intent=new Intent(mContext,AddUusalPhyPeople.class);
					startActivity(intent);	
				}else if(current_people==5){
					showToast("您已经添加了5位体检人");
				}
				
				
			}
			
		});
	listView.setOnItemClickListener(new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			intent=new Intent(mContext,UsuallyPhyInfo.class);
			intent.putExtra("phyid", mList.get(arg2).get("id")+"");
			startActivity(intent);
			
		}
		
	});			
	}

	private void initView() {
		mContext=this;
		setTitle("常用体检人");
		listView=(ListView) findViewById(R.id.Usually_phypatientList);
		listView.setSelector(R.drawable.cannel_liseviewback);//去除Listview點擊背景
		add1=(ImageView) findViewById(R.id.add1);
		nums=(TextView) findViewById(R.id.nums);
	}
	
	@Override
	protected void onResume() {
		getdata();
		super.onResume();
	}
	private void getdata() {
		mList = new ArrayList<Map<String, Object>>();
		if(NetworkUtil.isOnline(mContext)){
			AsyncHttpClient client=new AsyncHttpClient();
			String url=Constant.Tijianperson_List;
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
					if(!CheckJson(responseBody).equals("")){
						try {
							Map<String, Object> map = null;
							JSONObject object=new JSONObject(new String(responseBody));
							if(object.getInt("err")==0){
								JSONArray array=object.getJSONArray("people");
								current_people=array.length();
								nums.setText("您还可以添加"+(5-array.length())+"位体检人");
								for(int i=0;i<array.length();i++){
									map=new HashMap<String, Object>();
									map.put("name", array.getJSONObject(i).getString("name"));
									map.put("phone", array.getJSONObject(i).getString("phone"));
									map.put("id", array.getJSONObject(i).getString("id"));
									map.put("idcard", array.getJSONObject(i).getString("idnumber"));
									mList.add(map);
								}
							}
							adapter = new UsuallyPhyAdapter(mContext);
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
					showToast("请重试");
					Dissloading();
				}

			});
		
		}else{
			showToast("无网络连接");
		}
		
	}

}
