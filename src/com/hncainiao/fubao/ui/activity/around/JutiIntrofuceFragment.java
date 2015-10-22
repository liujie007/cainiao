package com.hncainiao.fubao.ui.activity.around;

import android.content.Context;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hncainiao.fubao.R;

public class JutiIntrofuceFragment extends Fragment{
	/**
	 * 具体介绍
	 * */
	Context mContext;
	
private View view;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.jutijieshao, null);
		return view;
	}

}
