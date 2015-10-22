package com.hncainiao.fubao.ui.activity.permedicine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.adapter.PersonDoctorAdapter;

public class PersonDoctorActivity extends BaseActivity {
	/**
	 * 高端私人醫生
	 * */
	Context mContext;
	List<Map<String, Object>>mList;
	ListView listView;
	PersonDoctorAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gaoduandoctor);
		Initview();
		GetData();
		
	}

	private void GetData() {
		mList=new ArrayList<Map<String,Object>>();
		Map<String, Object>map=null;
		for(int i=0;i<10;i++){
			map=new HashMap<String, Object>();
			map.put("name", "龚艺玲");
			map.put("status", "在线");
			map.put("introduce", "擅长各种肝病，病毒性肝炎，肝纤维化，肝衰竭等慢性病");
			mList.add(map);
			
		}
		adapter=new PersonDoctorAdapter(mContext);
		adapter.setList(mList);
		listView.setAdapter(adapter);
		listView.setSelector(R.drawable.cannel_liseviewback);//去除Listview點擊背景

	}

	private void Initview() {
		
		TextView title=(TextView) findViewById(R.id.title_txt);
		title.setText("高端私人医生");
		mContext=this;
		listView=(ListView) findViewById(R.id.list);
		
		
	}

}
