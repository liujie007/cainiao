package com.hncainiao.fubao.ui.adapter;

import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hncainiao.fubao.R;

/**
 * @author liujie
 * @version 2015年04月08日 下午5:36:56
 * 
 */
public class MainGridViewAdapter extends Adapter<HashMap<String, String>> {

	public MainGridViewAdapter(Context mContext) {
		super(mContext);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = LayoutInflater.from(mContext).inflate(
				R.layout.item_grideview_main, null);
		ImageView button = (ImageView) convertView.findViewById(R.id.btn_item_main_function);
		HashMap<String, String> map = mList.get(position);
		int draw = (mContext.getResources().getIdentifier(map.get("imageId"),
				"drawable", mContext.getPackageName()));
		button.setBackgroundResource(draw);
		
		return convertView;
	}
}
