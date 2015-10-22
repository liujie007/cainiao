package com.hncainiao.fubao.ui.adapter;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hncainiao.fubao.R;

public class UsuallyPhyAdapter  extends Adapter<Map<String, Object>>{
	private Context mContext;
	private LayoutInflater inflater;

	public UsuallyPhyAdapter(Context mContext) {
		super(mContext);
		this.mContext = mContext;
		this.inflater = LayoutInflater.from(mContext);
		// TODO Auto-generated constructor stub
	}
	
	class ViewHolder{
		TextView name;
		TextView phone;
		TextView idcard;
	}
	 public View getView(int position, View convertView, ViewGroup parent) {
		   
		   HashMap<String, Object> map = (HashMap<String, Object>) mList
					.get(position);
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.usuallyphyitem, null);
				holder = new ViewHolder();
			
				holder.name = (TextView) convertView
						.findViewById(R.id.name);
				holder.phone = (TextView) convertView
						.findViewById(R.id.phone);
				holder.idcard=(TextView) convertView
						.findViewById(R.id.idcard);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.name.setText(map.get("name") + "");
			holder.phone.setText(map.get("phone") + "");//医院名
			holder.idcard.setText(map.get("idcard")+"");
			
			
			return convertView; 
	 }
	

}
