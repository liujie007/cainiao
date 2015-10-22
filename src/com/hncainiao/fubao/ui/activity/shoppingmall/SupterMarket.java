package com.hncainiao.fubao.ui.activity.shoppingmall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.adapter.ShopAdapter;

public class SupterMarket extends BaseActivity {
	
	/*
	 * 商城主页
	 * ***/
	
	Context mContext;
	List<Map<String, Object>>mList;
	ListView listView;
	ImageView up,down;//价格上和下
	TextView xl;//销量
	TextView newshop;//新品
	TextView shuaixuan;//筛选
	TextView price;
	ShopAdapter adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.smllshop_layout);
		Inintview();
		InitLsiten();
		getData();
	}

	private void Inintview() {
		mContext=this;
		setTitle("商城");
		((ImageView) findViewById(R.id.serach))
		.setVisibility(View.VISIBLE);
		listView=(ListView) findViewById(R.id.list);
		shuaixuan=(TextView) findViewById(R.id.shuaixuan);
		xl=(TextView) findViewById(R.id.xiaoliang);
		newshop=(TextView) findViewById(R.id.newshop);
		price=(TextView) findViewById(R.id.price);
		up=(ImageView) findViewById(R.id.up);
		down=(ImageView) findViewById(R.id.down);
		
	}
	private void InitLsiten() {
		
		xl.setOnClickListener(new l());
		newshop.setOnClickListener(new l());
		shuaixuan.setOnClickListener(new l());
		up.setOnClickListener(new l());
		down.setOnClickListener(new l());
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent=new Intent(mContext,ICCH.class);
				startActivity(intent);
				
				
			}
			
		});

	}
	class l implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.xiaoliang:
				//销量
				xl.setTextColor( Color.parseColor("#4b83e7"));
				newshop.setTextColor(Color.BLACK);
				shuaixuan.setTextColor(Color.BLACK);
				price.setTextColor(Color.BLACK);
				up.setImageResource(R.drawable.up);
				down.setImageResource(R.drawable.down);
				
				break;
			case R.id.newshop:
				//新品
				xl.setTextColor(Color.BLACK);
				newshop.setTextColor(Color.parseColor("#4b83e7"));
				shuaixuan.setTextColor(Color.BLACK);
				price.setTextColor(Color.BLACK);
				up.setImageResource(R.drawable.up);
				down.setImageResource(R.drawable.down);
				break;
			case R.id.shuaixuan:
				//筛选
				xl.setTextColor(Color.BLACK);
				newshop.setTextColor(Color.BLACK);
				shuaixuan.setTextColor(Color.parseColor("#4b83e7"));
				price.setTextColor(Color.BLACK);
				up.setImageResource(R.drawable.up);
				down.setImageResource(R.drawable.down);
				break;
			case R.id.up:
				//价格高
				xl.setTextColor(Color.BLACK);
				newshop.setTextColor(Color.BLACK);
				shuaixuan.setTextColor(Color.BLACK);
				price.setTextColor(Color.parseColor("#4b83e7"));
				up.setImageResource(R.drawable.up_bule);
				down.setImageResource(R.drawable.down);

				
				break;
			case R.id.down:
				//价格低
				xl.setTextColor(Color.BLACK);
				newshop.setTextColor(Color.BLACK);
				shuaixuan.setTextColor(Color.BLACK);
				price.setTextColor(Color.parseColor("#4b83e7"));
				up.setImageResource(R.drawable.up);
				down.setImageResource(R.drawable.down_bule);
				break;
				
			default:
				break;
			}
			
		}
		
	}

	
	
	
	private void getData() {
		mList=new ArrayList<Map<String,Object>>();
		Map< String, Object>map=null;
		for(int i=0;i<20;i++){
			map=new HashMap<String, Object>();
			map.put("shop_name", "蛋白质+脑白金");
			map.put("hospital_name", "所属医院：长沙市第一医院");
			map.put("applyperson", "适用人群：青少年");
			map.put("nowmoney", "￥69.00");
			map.put("startmoney", "￥399.00");
			mList.add(map);
			
		}
		adapter=new ShopAdapter(mContext);
		adapter.setList(mList);
		listView.setAdapter(adapter);
		
	}
	

}
