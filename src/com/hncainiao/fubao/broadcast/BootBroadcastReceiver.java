package com.hncainiao.fubao.broadcast;


import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.properties.SharedPreferencesConfig;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootBroadcastReceiver extends BroadcastReceiver {

	private static final String TAG = "BootBroadcastReceiver";

	// 重写onReceive方法
	@Override
	public void onReceive(Context context, Intent intent) {
		if (SharedPreferencesConfig
				.getBoolConfig(context, Constant.ISAUTOSTART)) {
			context.unregisterReceiver(this);//动态 销毁
			Log.v(TAG, "--开机自动服务自动启动--");
		}
	}
}