package com.hncainiao.fubao.ui.activity.doctorConsultation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.adapter.ExpertsearchAdapter;

public class Expertsearch extends BaseActivity {
	
	
	Context mContext;
	List<Map<String , Object>>mList;
	ListView listView;
	ExpertsearchAdapter adapter;
	TextView Province;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expertsearch_layout);
		Initview();
		getData();
		
		
	}

	private void getData() {
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
		// TODO Auto-generated method stub
		mContext=this;
		Province=(TextView) findViewById(R.id.province);
		Province.setVisibility(ViewGroup.VISIBLE);
		setTitle("查找专家");
		listView=(ListView) findViewById(R.id.list);
		
	}

}
