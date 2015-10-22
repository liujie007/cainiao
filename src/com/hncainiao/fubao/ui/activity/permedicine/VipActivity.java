package com.hncainiao.fubao.ui.activity.permedicine;

import android.os.Bundle;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;

public class VipActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vipactivity);
		TextView title=(TextView) findViewById(R.id.title_txt);
		title.setText("vip热线服务");
	}

}
