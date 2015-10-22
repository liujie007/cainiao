package com.hncainiao.fubao.ui.activity.message;


import android.os.Bundle;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;

/**
 * 	项目：FuBaoHealth
 * 		@author ningshunjie
 *	日期：2015-5-23上午11:13:03 
 *	消息详情
 */
public class MyInfoMessageActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_info_message_activity);
		initView();
	}
	/**
	 * 控件初始化
	 */
	private void initView()
	{
		setTitle("消息详情");
	}

}
