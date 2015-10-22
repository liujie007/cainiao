package com.hncainiao.fubao.utils;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;


/**
 * 	项目：FuBaoHealth
 * 		@author liujie
 *	日期：2015-6-15上午11:29:53
 */
public class AndroidUtil {

	/**
	 * 获取屏幕分辨率
	 * 
	 * @param act
	 * @return
	 */
	public static DisplayMetrics getDisplayMetrics(Activity act) {
		DisplayMetrics dm = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm;
	}

	/**
	 * 获取屏幕宽ViewFlow
	 * 
	 * @param act
	 * @return
	 */
	public static int getDisplayMetricsWith(Activity act) {
		DisplayMetrics dm = getDisplayMetrics(act);
		if (dm == null) {
			return 0;
		}
		return dm.widthPixels;
	}

	/**
	 * 获取屏幕高 SoftReference
	 * 
	 * @param act
	 * @return
	 */
	public static int getDisplayMetricsHeight(Activity act) {
		DisplayMetrics dm = getDisplayMetrics(act);
		if (dm == null) {
			return 0;
		}
		return dm.heightPixels;
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}
		
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}
}
