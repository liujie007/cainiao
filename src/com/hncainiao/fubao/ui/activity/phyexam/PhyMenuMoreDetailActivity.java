package com.hncainiao.fubao.ui.activity.phyexam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.activity.BaseActivity;
import com.hncainiao.fubao.ui.views.SuspendScrollView;
import com.hncainiao.fubao.ui.views.SuspendScrollView.OnScrollListener;

/**
 * @author zhaojing
 * @version 2015年4月13日 下午5:44:09
 * 
 *          查看更多图文详情
 */
public class PhyMenuMoreDetailActivity extends BaseActivity implements
		OnScrollListener {

	private Context mContext;
	/**
	 * 自定义的具有悬浮效果的ScrollView
	 */
	private SuspendScrollView mScrollView;

	/**
	 * 在SuspendScrollView里面的购买布局
	 */
	private RelativeLayout mBuyLayout;

	/**
	 * 位于顶部的购买布局
	 */
	private RelativeLayout mTopBuyLayout;

	/**
	 * 在SuspendScrollView里面立即购买
	 */
	private Button btnBuy;
	/**
	 * 在顶部布局中的立即购买
	 */
	private Button btnTopBuy;

	private void setupView() {
		setTitle("更多详情");
		mScrollView = (SuspendScrollView) findViewById(R.id.more_suspend_scrollview);
		mBuyLayout = (RelativeLayout) findViewById(R.id.layout_more_buy_phymenu);
		mTopBuyLayout = (RelativeLayout) findViewById(R.id.top_more_buy_phymenu);
		btnBuy = (Button) ((RelativeLayout) findViewById(R.id.layout_more_buy_phymenu))
				.findViewById(R.id.btn_phymenu_buy);
		btnTopBuy = (Button) ((RelativeLayout) findViewById(R.id.top_more_buy_phymenu))
				.findViewById(R.id.btn_phymenu_buy);
	}

	private void addListener() {
		mScrollView.setOnScrollListener(this);
		btnBuy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext,
						PhyExamMessageActivity.class);
				startActivity(intent);
			}
		});
		btnTopBuy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext,
						PhyExamMessageActivity.class);
				startActivity(intent);
			}
		});
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phyexam_menu_more_detail);
		mContext = this;
		setupView();
		addListener();

		// 当布局的状态或者控件的可见性发生改变回调的接口
		findViewById(R.id.phyexam_menu_more_detail).getViewTreeObserver()
				.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						// TODO Auto-generated method stub
						onScroll(mScrollView.getScrollY());
					}
				});
	}

	// 监听滚动Y值变化，通过addView和removeView来实现悬停效果
	@Override
	public void onScroll(int scrollY) {
		// TODO Auto-generated method stub
		int mBuyLayout2ParentTop = Math.max(scrollY, mBuyLayout.getTop());
		mTopBuyLayout.layout(0, mBuyLayout2ParentTop, mTopBuyLayout.getWidth(),
				mBuyLayout2ParentTop + mTopBuyLayout.getHeight());
	}
}
