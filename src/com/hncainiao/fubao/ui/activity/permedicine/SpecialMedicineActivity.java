package com.hncainiao.fubao.ui.activity.permedicine;

import android.os.Bundle;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;

public class SpecialMedicineActivity extends BaseActivity {
	/***
	 * 特殊药品
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spelicamedicine);
		TextView title=(TextView) findViewById(R.id.title_txt);
		title.setText("特殊药品定制");
	}
	
	

}
