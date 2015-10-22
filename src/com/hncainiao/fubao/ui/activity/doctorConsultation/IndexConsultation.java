package com.hncainiao.fubao.ui.activity.doctorConsultation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.activity.doctor.DoctorDetailActivity;
import com.hncainiao.fubao.ui.adapter.HorizontalListViewAdapter;
import com.hncainiao.fubao.ui.listener.OnClickAvoidForceListener;
import com.hncainiao.fubao.ui.views.HorizontalListView;

public class IndexConsultation extends BaseActivity {
	Context mContext;
	HorizontalListView listView;
	ImageView yizhen,freezixun,findzhuanjia;
	LinearLayout llchild,llwoman,llskin,llxinli;
	HorizontalListViewAdapter adapter;
	List<Map<String , Object>>mList=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.doctorzixun);
		InitView();
		addlisten();
		getData();

	}

	private void getData() {
		mList=new ArrayList<Map<String,Object>>();
		Map< String, Object>map=null;
		for(int i=0;i<5;i++){
			map=new HashMap<String, Object>();
			map.put("name", "劉德華");
			map.put("doctor_zhiwei", "教授");
			mList.add(map);
			
		}
		adapter=new HorizontalListViewAdapter(mContext);
		adapter.addList(mList);
		listView.setAdapter(adapter);
	}

	private void addlisten() {
		((TextView) findViewById(R.id.myzixun)).setOnClickListener(l);
		yizhen.setOnClickListener(l);
		freezixun.setOnClickListener(l);
		findzhuanjia.setOnClickListener(l);
		llchild.setOnClickListener(l);
		llwoman.setOnClickListener(l);
		llskin.setOnClickListener(l);
		llxinli.setOnClickListener(l);
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				 Intent intent=new Intent(mContext,DoctorDetailActivity.class);
				 startActivity(intent);
				
				
			}
			
		});
		
	}

	private void InitView() {
		mContext=this;
		setTitle("医生咨询");
		((TextView) findViewById(R.id.myzixun))
		.setVisibility(View.VISIBLE);
		listView=(HorizontalListView) findViewById(R.id.horlistview);
		yizhen=(ImageView) findViewById(R.id.yizhen);
		freezixun=(ImageView) findViewById(R.id.freezixun);
		findzhuanjia=(ImageView) findViewById(R.id.findzhunajia);
		llchild=(LinearLayout) findViewById(R.id.llchild);
		llwoman=(LinearLayout) findViewById(R.id.llwoman);
		llskin=(LinearLayout) findViewById(R.id.llskin);
		llxinli=(LinearLayout) findViewById(R.id.llxinli);

		
	}
	
	
	private OnClickAvoidForceListener l = new OnClickAvoidForceListener() {

		@Override
		public void onClickAvoidForce(View v) {
			
			Intent intent=null;
			
			switch (v.getId()) {
			case R.id.myzixun:
				showToast("我的咨询");
				break;
				
			case R.id.findzhunajia:
				intent=new Intent(mContext,Expertsearch.class);
				startActivity(intent);
				
				break;
				
			case R.id.yizhen:
				intent=new Intent(mContext,Clinicquestions.class);
				startActivity(intent);
				break;
			case R.id.freezixun:
				intent=new Intent(mContext,Freeconsultation.class);
				startActivity(intent);
				break;
			case R.id.llchild:
				intent=new Intent(mContext,Hotzixun.class);
				intent.putExtra("hotzixun_tag", "儿科");
				startActivity(intent);
				break;
			case R.id.llwoman:
				intent=new Intent(mContext,Hotzixun.class);
				intent.putExtra("hotzixun_tag", "妇科");
				startActivity(intent);
				break;
			case R.id.llskin:
				intent=new Intent(mContext,Hotzixun.class);
				intent.putExtra("hotzixun_tag", "皮肤科");
				startActivity(intent);
				break;
			case R.id.llxinli:
				intent=new Intent(mContext,Hotzixun.class);
				intent.putExtra("hotzixun_tag", "心理科");
				startActivity(intent);
				break;

			default:
				break;
			}
			
			
		}
		
	};
	
	
	

}
