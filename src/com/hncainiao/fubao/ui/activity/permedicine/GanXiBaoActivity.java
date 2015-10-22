package com.hncainiao.fubao.ui.activity.permedicine;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;

public class GanXiBaoActivity extends BaseActivity {
	/**
	 * 干细胞
	 * */
	Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ganxibao);
		Initview();
		
	}
	private void Initview() {
		mContext=this;
		TextView title=(TextView) findViewById(R.id.title_txt);
		title.setText("干细胞");
		
	}

}
