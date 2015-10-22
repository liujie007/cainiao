package com.hncainiao.fubao.ui.activity.personalcenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.main.MainActivity;
import com.hncainiao.fubao.ui.adapter.SuccessPayAdapter;

public class SuccessPayActivity extends BaseActivity {
	Context mContext;
	ListView listView;
	List<Map<String, Object>>mList;
	Button btn_back;
	SuccessPayAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.successpay);
		Initview();
		GetData();
	}

	private void GetData() {
		mList=new ArrayList<Map<String,Object>>();
		Map<String, Object>map=null;
		for(int i=0;i<2;i++){
			map=new HashMap<String, Object>();
			map.put("name", "媛媛");
			map.put("time", "2015-09-34");
			map.put("hospital", "长沙市第一医院");
			map.put("idcard", "430524199310237657");
			map.put("department", "眼科");
			map.put("zhencha", "诊查费>");
			map.put("juti", "眼底检查");
			mList.add(map);
		}
		adapter=new SuccessPayAdapter(mContext);
		adapter.setList(mList);
		listView.setAdapter(adapter);
		
	}

	private void Initview() {
		mContext=this;
		listView=(ListView) findViewById(R.id.list);
		btn_back=(Button) findViewById(R.id.btn_confirm_order);
		btn_back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(mContext,MainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
				//MainActivity.mJazzy.setCurrentItem(4);
				
			}
		});
		
		
	}

}
