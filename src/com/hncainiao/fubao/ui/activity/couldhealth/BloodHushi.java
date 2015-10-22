package com.hncainiao.fubao.ui.activity.couldhealth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.adapter.BloodHushiAdapter;
import com.hncainiao.fubao.utils.AChartEngineUtil;

public class BloodHushi extends BaseActivity {
	/**
	 * 血壓护士
	 * */
	 Context mContext;
	 List<String> glu_data;
	 LinearLayout layout;
	 ListView listView;
	 List<Map<String, Object>>mList;
	 BloodHushiAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bloodhushi);
		Initview();
		GetData();
		
		  AChartEngineUtil.setchart(mContext,layout,
				  new String[]{""},new int[]{Color.GREEN},glu_data,"时间","血糖值");
		
	}
	private void GetData() {
		glu_data.add("1");
		glu_data.add("10");
		glu_data.add("5");
		glu_data.add("8");
		glu_data.add("30");
		glu_data.add("25");
		
		mList=new ArrayList<Map<String,Object>>();
		Map<String, Object>map=null;
		for(int i=0;i<3;i++){
			map=new HashMap<String, Object>();
			map.put("data", "2015-08-06");
			map.put("shousuo", "收缩压：120mm/hg");
			map.put("shuzhan", "舒张压：80mm/hg");
			map.put("status", "正常");
			mList.add(map);
		}
		adapter=new BloodHushiAdapter(mContext);
		adapter.setList(mList);
		listView.setAdapter(adapter);
		
	}



	private void Initview() {
		mContext=this;
		
		listView=(ListView) findViewById(R.id.list);
		glu_data=new ArrayList<String>();
		layout=(LinearLayout) findViewById(R.id.show);
		
	} 
}
