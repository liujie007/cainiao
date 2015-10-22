package com.hncainiao.fubao.ui.activity.shoppingmall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.adapter.ICCHAdpater;

public class ICCH extends BaseActivity {
	
	 Context mContext;
	 ListView listView;
	 TextView newadress;
	 ICCHAdpater adpater;
	 List<Map<String, Object>>mList;
	 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopclean_layout);
		Initview();
		getData(); 
		
	}

	private void getData() {
		mList=new ArrayList<Map<String,Object>>();
		Map<String, Object>map=null;
		for(int i=0;i<2;i++){
			map=new HashMap<String, Object>();
			map.put("name", "张三");
			map.put("adress", "湖南省长沙市岳麓区麓景路8号301室");
			map.put("phone"," 15973184230");
			mList.add(map);
			
		}
		adpater=new ICCHAdpater(mContext);
		adpater.setList(mList);
		listView.setAdapter(adpater);
		
		
		
	}

	private void Initview() {
		mContext=this;
		setTitle("商品结算");
		listView=(ListView) findViewById(R.id.adress);
		newadress=(TextView) findViewById(R.id.newadress);
		
		newadress.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(mContext,ShoppingCart.class);
				startActivity(intent);
				
			}
		});
	}

}
