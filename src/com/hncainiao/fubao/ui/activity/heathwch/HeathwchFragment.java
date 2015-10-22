package com.hncainiao.fubao.ui.activity.heathwch;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.ui.fragment.BaseFragment;

public class HeathwchFragment  extends BaseFragment{

	View view ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.heathwch_fragment, null);
		setView(view);
		return view;
	}
	
	private void setView(View view)
	{
		((TextView)view.findViewById(R.id.title_txt)).setText("健康腕表");
		((Button)view.findViewById(R.id.comeback)).setVisibility(View.GONE);
		
	}
	@Override
	protected void creatFragmentUI() {
		// TODO Auto-generated method stub
		
	}

}
