package com.hncainiao.fubao.ui.activity.personalcenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.adapter.MyPayAdapter;

public class MyPayActivity extends BaseActivity {
	 
	Context mContext;
	ListView listView;
	MyPayAdapter adapter;
	List<Map<String, Object>>mList;
	TextView  pay;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mypay);
		InitView();
		getData();
		
		
	}


	private void getData() {
		mList=new ArrayList<Map<String,Object>>();
		Map<String, Object >map=null;
		for(int i=0;i<5;i++){
			map=new HashMap<String, Object>();
			map.put("name", "张艺馨");
			map.put("data", "03-17");
			map.put("connect", "验光检查");
			map.put("money", "30元");
			map.put("hospiatl", "长沙市第一医院");
			
			mList.add(map);
		}
		adapter=new MyPayAdapter(mContext);
		adapter.setList(mList);
		listView.setAdapter(adapter);
		
	}


	private void InitView() {
		mContext=this;
		setTitle("诊中支付");
		pay=(TextView) findViewById(R.id.hebingfukuan);
		pay.setVisibility(ViewGroup.VISIBLE);
		listView=(ListView) findViewById(R.id.list);
		
		pay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(mContext,AddAllPayActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				
				
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent=new Intent(mContext,ConfirmPayActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			}
			
		});
		

	}

}
