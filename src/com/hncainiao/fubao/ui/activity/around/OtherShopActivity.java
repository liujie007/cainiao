package com.hncainiao.fubao.ui.activity.around;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.adapter.OtherShopAdapter;
import com.jmheart.view.listview.RefreshListView;
import com.jmheart.view.listview.RefreshListView.OnLoadListener;
import com.jmheart.view.listview.RefreshListView.OnRefreshListener;

public class OtherShopActivity extends BaseActivity implements OnRefreshListener,OnLoadListener{
	/**
	 * 其他门面*
	 * */
	
	Context mContext;
	RefreshListView listView;
	List<Map<String, Object>>mList;
	OtherShopAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.other_shop);
		setTitle(getIntent().getStringExtra("name"));
		Initview();
		GetData();
		
	}
	private void Initview() {
		mContext=this;
		listView=(RefreshListView) findViewById(R.id.list);
		listView.setOnRefreshListener(this);
		listView.setOnLoadListener(this);
		listView.setSelector(R.drawable.cannel_liseviewback);//去除Listview點擊背景
	}
	private void GetData() {
		mList=new ArrayList<Map<String,Object>>();
		Map<String, Object>map=null;
		for(int i=0;i<10;i++){
			map=new HashMap<String, Object>();
			map.put("name", (i+1)+".湖南百姓大药房");
			map.put("distance", "距离：3320米");
			map.put("adress", "长沙市开福区湘雅路");
			mList.add(map);
			
		}
		adapter=new OtherShopAdapter(mContext);
		adapter.setList(mList);
		listView.setAdapter(adapter);
		
	}

	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		super.onLoad();
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		super.onRefresh();
	}

}
