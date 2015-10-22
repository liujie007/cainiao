package com.hncainiao.fubao.ui.activity.doctorConsultation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.adapter.ExpertsearchAdapter;

public class HotzixunActivity extends BaseActivity {
	Context mContext;
	List<Map<String, Object>>mList;
	ListView listView;
	TextView keshi;
	ExpertsearchAdapter adapter;
	Intent intent=null;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotzixun_layout);
		Initview();
		getData();
		
	}



	private void getData() {
		intent=getIntent();
		if(intent!=null){
			keshi.setVisibility(ViewGroup.VISIBLE);
			keshi.setText(intent.getStringExtra("hotzixun_tag"));
			showData();
		}else{
			showToast("获取有误");
		}
		
		
	}



	private void showData() {
		mList=new ArrayList<Map<String,Object>>();
		Map<String, Object>map=null;
		for(int i=0;i<20;i++){
			map=new HashMap<String, Object>();
			map.put("name", "杨幂");
			map.put("zhiwei", "大姐大");
			map.put("hospital_name", "肿瘤医院");
			map.put("good_at", "擅长：治病");
			map.put("zixun_num", "2");
			mList.add(map);
			
		}
		adapter=new ExpertsearchAdapter(mContext);
		adapter.setList(mList);
		listView.setAdapter(adapter);
		
		
		
	}



	private void Initview() {
		mContext=this;
		setTitle("热门咨询");
		keshi=(TextView) findViewById(R.id.keshi_name);
		listView=(ListView) findViewById(R.id.list_hot);
		
		
	}
	
	

}
