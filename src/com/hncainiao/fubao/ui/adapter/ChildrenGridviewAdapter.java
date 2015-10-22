package com.hncainiao.fubao.ui.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.model.Kind;

public class ChildrenGridviewAdapter extends BaseAdapter {
	List<Kind>kinds;
	Context context;
	
	
	

	public ChildrenGridviewAdapter(List<Kind> kinds, Context context) {
		super();
		this.kinds = kinds;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return kinds.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return kinds.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		ViewHodler viewHodler = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.expenlist_view_griditem, null);
			viewHodler = new ViewHodler();
			viewHodler.mTvType = (TextView) convertView
					.findViewById(R.id.Office_children);
			viewHodler.viewLin=(View)convertView.findViewById(R.id.lin);
			convertView.setTag(viewHodler);
		} else {
			viewHodler = (ViewHodler) convertView.getTag();
		}
		//GoodsAddressEntity addressEntity = addressList.get(position);
		Log.i("--", "--arg"+arg0+"kinds"+kinds.size());
		viewHodler.mTvType.setText(kinds.get(arg0).getName());
		if (kinds.size()%2==0) {
			if (arg0==kinds.size()-1) {
			 viewHodler.viewLin.setBackgroundDrawable(null);
			}
			if (arg0==kinds.size()-2) {
				 viewHodler.viewLin.setBackgroundDrawable(null);
			}
			
		}
		else {
			if (arg0==kinds.size()-1) {
				 viewHodler.viewLin.setBackgroundDrawable(null);
			}
		}
		
		
		
		return convertView;
	}
	   private class ViewHodler {
		private TextView mTvType;
		private View viewLin;
	}

}
