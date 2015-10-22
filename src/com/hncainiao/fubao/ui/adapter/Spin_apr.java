package com.hncainiao.fubao.ui.adapter;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hncainiao.fubao.R;

public class Spin_apr extends Adapter<Map<String, Object>>  {
	
	
	
	private Context mContext;

	private LayoutInflater inflater;
	
	public Spin_apr(Context Context) {
		super(Context);
		// TODO Auto-generated constructor stub
		this.mContext = Context;
		this.inflater = LayoutInflater.from(mContext);
	}
	
	
	static class ViewHolder {
		
		TextView bankName;
		
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		HashMap<String, Object> map = (HashMap<String, Object>) mList
				.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_spin, null);
			holder = new ViewHolder();
			
			holder.bankName = (TextView) convertView
					.findViewById(R.id.sptext);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.bankName.setText(map.get("bank_name") + "");

		
		return convertView;
		
	}

	

	
	

}
