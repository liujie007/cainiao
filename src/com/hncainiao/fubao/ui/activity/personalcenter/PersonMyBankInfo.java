package com.hncainiao.fubao.ui.activity.personalcenter;

import android.os.Bundle;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;

/**
 * 	项目：FuBaoHealth
 * 		@author liujie
 *	日期：2015-5-28上午11:26:25
 *
 *  银行卡信息
 */
public class PersonMyBankInfo extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_mybank_info_activity);
		inintView();
	}
	/**
	 * 初始化view
	 */
	private void inintView()
	{
		setTitle("银行卡详情");
	}
}
