package com.hncainiao.fubao.ui.activity.doctorConsultation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.adapter.ClinicquestionsAdapter;

public class Clinicquestions extends BaseActivity {
	/***
	 * 义诊提问
	 * */
	Context mContext;
	GridView gridView;
	ImageView nodata;
	ClinicquestionsAdapter adapter;
	List< Map<String, Object>>mList=new ArrayList<Map<String,Object>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cinicquestions_layout);
		Initview();
		setData();
		
	}

	private void setData() {
		Map< String, Object>map=null;
		for(int i=0;i<20;i++){
			map=new HashMap<String, Object>();
			map.put("doctor_name", "赵丽颖");
			map.put("hospital_name", "妇科医院");
			map.put("zhiwei", "主任");
			mList.add(map);
		}
		adapter=new ClinicquestionsAdapter(mContext);
		adapter.setList(mList);
		gridView.setAdapter(adapter);
		
		
	}

	private void Initview() {
		mContext=this;
		setTitle("义诊提问");
		gridView=(GridView) findViewById(R.id.grid) ;
		nodata=(ImageView) findViewById(R.id.im_ondate);
		
	}
	
	
	
	

}
