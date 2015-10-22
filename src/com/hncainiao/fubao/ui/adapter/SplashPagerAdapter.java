package com.hncainiao.fubao.ui.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.properties.Constant;
import com.hncainiao.fubao.ui.views.CustomViewPager;

/**
 * @author zhaojing
 * @version 2015-04-01 10:09:57
 * 
 *          ViewPager适配器
 */
public class SplashPagerAdapter extends PagerAdapter implements
		OnPageChangeListener {

	private Context mContext;

	private List<View> mListViews;

	private int pageNum;

	private int currentItem = 0;

	private ViewPager viewPager;

	/**
	 * 标值，0为主界面使用，1,介绍界面使用
	 */
	private int flag = 0;

	public void setTypeFlag(int f) {
		flag = f;
	}

	public SplashPagerAdapter(Context mContext, List<View> mListViews,
			int pageNum, ViewPager viewPager) {
		this.mContext = mContext;
		this.mListViews = mListViews;
		this.pageNum = pageNum;
		this.viewPager = viewPager;
		initBottomBar(); // 初始化底部的圆点
	}
 private void initBottomBar() {
		if (mListViews.size() == 0) {
			return;
		}
		LinearLayout bottomIndex = (LinearLayout) ((Activity) mContext)
				.findViewById(R.id.bottomIndex);
		bottomIndex.setVisibility(View.VISIBLE);
		bottomIndex.setBackgroundColor(android.R.color.transparent);
		bottomIndex.removeAllViews();

		if (mListViews.size() > 1) { // 屏幕欢迎页面大于1时加载底部的屏幕按钮
			for (int i = 0; i < pageNum; i++) {
				ImageView imageView;
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new GridView.LayoutParams(40, 40));
				imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);//
				if (currentItem == i) {
					imageView.setImageResource(R.drawable.home_unpoint);
				} else {
					imageView.setImageResource(R.drawable.home_selectpoint);
				}
				bottomIndex.addView(imageView, new LinearLayout.LayoutParams(
						30, 30));
			}
		}
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {

	}

	@Override
	public void finishUpdate(View arg0) {
	}

	@Override
	public int getCount() {
		return mListViews == null ? 0 : mListViews.size();
	}

	@Override
	public Object instantiateItem(View arg0, int arg1) {
		try {
			((CustomViewPager) arg0).addView(mListViews.get(arg1), 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mListViews.get(arg1);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == (arg1);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {

		currentItem = arg0; // 减去左边的空白页
		Constant.PAGENUMHOW = currentItem; // 减去左边的空白页
		viewPager.setCurrentItem(arg0);// 第一屏向左滑动的话.
		initBottomBar();
	}
}
