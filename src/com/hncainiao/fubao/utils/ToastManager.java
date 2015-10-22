package com.hncainiao.fubao.utils;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * Toast管理类，解决toast队列必须显示完后才会关闭的弊端
 * 
 * @data 2015-04-01
 * 
 */
public class ToastManager {

	private Toast toast;

	public static ToastManager INSTANCE;

	private Context mContext;

	public static ToastManager getInstance(Context context) {
		if (INSTANCE == null) {
			INSTANCE = new ToastManager(context);
		}
		return INSTANCE;
	}

	private ToastManager(Context context) {
		this.mContext = context;
	}

	public void showToast(String msg, int time) {

		if (Looper.myLooper() != Looper.getMainLooper()) {
			return;
		}

		if (toast == null) {
			toast = Toast.makeText(mContext, msg, time);
		} else {
			toast.setText(msg);
			toast.setDuration(time);
		}
		toast.show();
	}

	public void showToast(String msg) {

		if (Looper.myLooper() != Looper.getMainLooper()) {
			return;
		}

		if (toast == null) {
			toast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
		} else {
			toast.setText(msg);
			toast.setDuration(Toast.LENGTH_LONG);
		}
		toast.show();
	}
}
