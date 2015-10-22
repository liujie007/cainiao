package com.hncainiao.fubao.ui.activity.couldhealth;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.utils.AChartEngineUtil;

public class MyBracelet extends BaseActivity {
	
	/********
	 * 我的手环
	 * */
	Context mContext;
	LinearLayout layout;
	List<String>data;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mybracelet);
		InitView();
		GetData();

		 AChartEngineUtil.setchart(mContext,layout,
				  new String[]{""},new int[]{Color.GREEN},data,"","");	
		
		
	}



	private void GetData() {
		data.add("1");
		data.add("1");
		data.add("5");
		data.add("5");
		data.add("8");
		data.add("7");
		data.add("7");
		data.add("20");
		
	}



	private void InitView() {
		mContext=this;
		layout=(LinearLayout) findViewById(R.id.show);
		data=new ArrayList<String>();
		
	}

}
