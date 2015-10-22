package com.hncainiao.fubao.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @author zhaojing
 * @version 2015年4月11日 上午11:02:19
 * 
 *          自定义的ListView
 */
public class CustomerListView extends ListView {

	public CustomerListView(Context context, AttributeSet arrAttributeSet) {
		super(context, arrAttributeSet);
	}

	/**
	 * Integer.MAX_VALUE >> 2,如果不设置，系统默认设置是显示两条
	 */
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
