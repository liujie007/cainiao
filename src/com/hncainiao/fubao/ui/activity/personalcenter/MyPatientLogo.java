package com.hncainiao.fubao.ui.activity.personalcenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.adapter.MyPatientLogoAdapter;

public class MyPatientLogo extends BaseActivity {
	
	
	/**
	 * 我的病历
	 * **/
	
	Context mContext;
	ListView listView;
	List<Map<String, Object>>mList;
	MyPatientLogoAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mypatient_logo);
		InitView();
		//getData();
		getVivalData();
		
	}
	
	
	private void getVivalData() {
		  mList=new ArrayList<Map<String,Object>>();
		  Map<String, Object>map=null;
		  for(int i=0;i<5;i++){
			  map=new HashMap<String, Object>();
			  map.put("name", "张三");		
			  map.put("time", "2015-06-12");
			  map.put("hospital", "长沙市中心医院");
			  map.put("office_name", "内科");
			  mList.add(map);
			  
		  }
		  adapter=new MyPatientLogoAdapter(mContext);
		  adapter.setList(mList);
		  listView.setAdapter(adapter);
		
		
	}


	private void InitView() {
		mContext=this;
		setTitle("我的病历");
		listView=(ListView) findViewById(R.id.logolist);
		listView.setSelector(R.drawable.cannel_liseviewback);//去除Listview點擊背景

	}
	
	
	private void getData() {
		
		
	}
	
	
}
