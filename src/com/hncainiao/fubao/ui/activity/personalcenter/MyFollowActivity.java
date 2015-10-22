package com.hncainiao.fubao.ui.activity.personalcenter;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.fragment.DocFollFragment;
import com.hncainiao.fubao.ui.fragment.HosFollFragment;
import com.hncainiao.fubao.ui.listener.OnClickAvoidForceListener;

/**
 * @author zhaojing
 * @version 2015年4月18日 上午9:46:16
 * 
 *          我的关注
 */
public class MyFollowActivity extends FragmentActivity {

	private Context mContext;
	private TextView tvDoctor;
	private TextView tvHospital;
	private TextView[] textViews = new TextView[2];

	private View docView;

	private View hosView;

	private ViewPager viewPager;

	private ArrayList<Fragment> fragments;

	private DocFollFragment docFollFragment;

	private HosFollFragment hosFollFragment;

	int currentPageIndex = 0;

	private void setupView() {
		((TextView)findViewById(R.id.title_txt)).setText("我的关注");
		textViews[0] = (TextView) findViewById(R.id.tv_follow_doctor);
		textViews[1] = (TextView) findViewById(R.id.tv_follow_hospital);
		docView = (View) findViewById(R.id.ll_doctor_selected);
		docView = (View) findViewById(R.id.ll_doctor_selected);
		hosView = (View) findViewById(R.id.ll_hospital_selected);
		viewPager = (ViewPager) findViewById(R.id.viewpager_follow);
	}

	private void addListener() {
		((LinearLayout) findViewById(R.id.title_btn_left))
				.setOnClickListener(listener);
		for (TextView textView : textViews) {
			textView.setOnClickListener(listener);
		}

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				currentPageIndex = arg0;
				setLine();
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_follow);
		setupView();
		addListener();

		fragments = new ArrayList<Fragment>();
		docFollFragment = new DocFollFragment();
		hosFollFragment = new HosFollFragment();
		fragments.add(docFollFragment);
		fragments.add(hosFollFragment);

		ViewPagerAdapter adapter = new ViewPagerAdapter(
				getSupportFragmentManager(), fragments);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(currentPageIndex); // 跳转到指定的某页
		setLine();
	}

	private OnClickAvoidForceListener listener = new OnClickAvoidForceListener() {

		@Override
		public void onClickAvoidForce(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.title_btn_left:
				finish();
				break;
			case R.id.tv_follow_doctor: // 医生
				currentPageIndex = 0;
				break;
			case R.id.tv_follow_hospital: // 医院
				currentPageIndex = 1;
				break;
			}
			viewPager.setCurrentItem(currentPageIndex);
			setLine();
		}
	};

	class ViewPagerAdapter extends FragmentPagerAdapter {

		ArrayList<Fragment> fragments;

		public ViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
			super(fm);
			// TODO Auto-generated constructor stub
			if (fragments == null) {
				this.fragments = list;
			} else {
				this.fragments = list;
			}
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return fragments.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragments == null ? 0 : fragments.size();
		}
	}

	private void setLine() {
		for (int i = 0; i < textViews.length; i++) {
			if (i == this.currentPageIndex) {
				hosView.setVisibility(View.VISIBLE);
				docView.setVisibility(View.INVISIBLE);
			} else {
				docView.setVisibility(View.VISIBLE);
				hosView.setVisibility(View.INVISIBLE);
			}
		}
	}
}
