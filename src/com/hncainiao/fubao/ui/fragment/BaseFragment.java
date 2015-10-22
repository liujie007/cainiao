package com.hncainiao.fubao.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.hncainiao.fubao.ui.views.NetLoadDialog;


/**
 * 	项目：FuBaoHealth
 * 		@author liujie
 *	日期：2015-5-28下午5:31:52
 */
public abstract class BaseFragment extends com.jmheart.base.BaseFragment {

	/**
	 * 布局解析器
	 */
	protected LayoutInflater inflater;
	NetLoadDialog hDialog;

	/**
	 * 在该函数中可以进行网络上护具的获取和UI的创建
	 */
	protected abstract void creatFragmentUI();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		inflater = LayoutInflater.from(getActivity());
		getActivity().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO 创建和fragment关联的view hierarchy.
		return new View(getActivity());
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		creatFragmentUI();
	}
	/**
	 * 展示加载动画
	 */
	public void Showloading()
	{
		hDialog =new NetLoadDialog(getActivity());
		hDialog.SetMessage("操作中...");
		hDialog.showDialog();
		
	}
	/**
	 * 取消动画
	 */
	public void Dissloading()
	{
		hDialog .dismissDialog();
	}

	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}
}
