package com.hncainiao.fubao.ui.adapter;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hncainiao.fubao.R;

public class UsuallyPatientAdapter extends Adapter<Map<String, Object>>{
	
	private Context mContext;
	private LayoutInflater inflater;
	
	public UsuallyPatientAdapter(Context mContext) {
		super(mContext);
		this.mContext = mContext;
		this.inflater = LayoutInflater.from(mContext);
	}
	class ViewHolder{
		TextView name;
		TextView sex;
		TextView age;
		TextView phone;
		ImageView xiugai;
	}
	 public View getView(int position, View convertView, ViewGroup parent) {
		   
		   HashMap<String, Object> map = (HashMap<String, Object>) mList
					.get(position);
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.usually_patient_item, null);
				holder = new ViewHolder();
				holder.xiugai = (ImageView) convertView
						.findViewById(R.id.xiugai);
				holder.name = (TextView) convertView
						.findViewById(R.id.name);
				holder.sex = (TextView) convertView
						.findViewById(R.id.sex);
				holder.age = (TextView) convertView
						.findViewById(R.id.age);
				holder.phone = (TextView) convertView
						.findViewById(R.id.phone);
				holder.xiugai=(ImageView)convertView
						.findViewById(R.id.xiugai);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.name.setText(map.get("name") + "");
			holder.age.setText(map.get("age") + "");//医院名
			holder.phone.setText(map.get("phone")+"");
			String gender=map.get("gender")+"";
			if(gender.equals("1")){
				holder.sex.setText("男");

			}else{
				holder.sex.setText("女");

			}
					
			
			return convertView; 
	 }
	

}
