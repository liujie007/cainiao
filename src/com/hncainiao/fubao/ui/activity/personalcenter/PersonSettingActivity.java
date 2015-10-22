package com.hncainiao.fubao.ui.activity.personalcenter;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;

/**
 * 	项目：FuBaoHealth
 * 		@author liujie
 *	日期：2015-5-27上午10:23:01
 *
 *	设置
 */
public class PersonSettingActivity extends BaseActivity {

	Context mcContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preson_setting_activity);
		setTitle("设置");
		mcContext=this;
		((RelativeLayout)findViewById(R.id.rela_about)).setOnClickListener(this);
		((RelativeLayout)findViewById(R.id.rela_version)).setOnClickListener(this);
		((RelativeLayout)findViewById(R.id.rela_awson)).setOnClickListener(this);

	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		super.onClick(arg0);
		Intent intent =null;
		switch (arg0.getId()) {
		case R.id.rela_about:
			//进入到关于福报健康
			intent=new Intent(mcContext,AboutActivity.class);
			startActivity(intent);
			break;

		case R.id.rela_version:
			//进入到版本更新
			showToast("已经是最新版本了");
			//intent=new Intent(mcContext,VersionActivity.class);
			//startActivity(intent);
			break;
		case R.id.rela_awson:
			//进入到意见反馈
			intent=new Intent(mcContext,AwsonActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
