package com.hncainiao.fubao.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hncainiao.fubao.R;

/**
 * @author zhaojing
 * @version 2010年8月8日 上午10:19:20
 * 
 *          医生咨询
 */
public class DoctorConsultFragment extends Fragment {
	Context mContext;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_doctor_consult, null);
		mContext=getActivity();
		
		
		
		return view;
	}
}
