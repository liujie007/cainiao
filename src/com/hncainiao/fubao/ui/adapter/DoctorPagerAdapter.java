package com.hncainiao.fubao.ui.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/** 
 * @author zhaojing
 * @version 2015年04月08日 下午1:25:46
 * 
 */
public class DoctorPagerAdapter extends FragmentPagerAdapter {

	ArrayList<Fragment> fragments;

	public DoctorPagerAdapter(FragmentManager fm,
			ArrayList<Fragment> list) {
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
