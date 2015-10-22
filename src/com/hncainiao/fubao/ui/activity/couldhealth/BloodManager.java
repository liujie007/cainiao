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

 public class BloodManager extends BaseActivity {
	 
	 
	 LinearLayout layout;
	 Context mContext;
	
	 List<String> glu_data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bloodmanager);
		
		Initview();
		GetData();

		
		  AChartEngineUtil.setchart(BloodManager.this,layout,
				  new String[]{""},new int[]{Color.GREEN},glu_data,"时间","血糖值");
		
		
		
	        

	  }



	private void GetData() {
		glu_data.add("1");
		glu_data.add("10");
		glu_data.add("5");
		glu_data.add("8");
		glu_data.add("30");
		glu_data.add("25");
		
		
	}



	private void Initview() {
		mContext=this;
		glu_data=new ArrayList<String>();
		layout=(LinearLayout) findViewById(R.id.barchart);
		
	} 

}
