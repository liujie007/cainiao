package com.hncainiao.fubao.ui.listener;


import com.hncainiao.fubao.utils.ToastManager;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author zhaojing
 * @version 2015年03月30日 下午1:18:36
 * 
 *          Android 防止控件被重复点击
 */
public abstract class OnClickAvoidForceListener  implements OnClickListener {
	private long lastOnClickTime = 0;

	private long lockTime = 200;
	Context context;
	

	public void onClick(View v) {

		if (isSmoothClick()) {

			onClickAvoidForce(v);

		} else {

		}
	}

	public abstract void onClickAvoidForce(View v);

	public boolean isSmoothClick() {
		boolean isSmooth = true;
		long current = System.currentTimeMillis();

		if (0 == lastOnClickTime
				|| Math.abs(current - lastOnClickTime) > lockTime) {
			lastOnClickTime = current;
			isSmooth = true;
		} else {
			isSmooth = false;
			ToastManager.getInstance(context).showToast("您操作太频频，请稍后");
		}
		return isSmooth;
	}
}
