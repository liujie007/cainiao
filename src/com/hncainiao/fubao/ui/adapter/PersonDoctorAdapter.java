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

public class PersonDoctorAdapter extends Adapter<Map<String, Object>>{
	 
	private Context mContext;
	private LayoutInflater inflater;
	
	
	public PersonDoctorAdapter(Context Context) {
		super(Context);
		this.mContext = Context;
		this.inflater = LayoutInflater.from(mContext);
	}
	class ViewHolder{
		ImageView img;
		TextView name;
		TextView status;
		TextView introduce;

	}  
	@Override
	public View getView( int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = (HashMap<String, Object>) mList
				.get(position);
		ViewHolder holder = null;
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.persondoctor_item, null);
			holder = new ViewHolder();
			holder.img = (ImageView) convertView
					.findViewById(R.id.yaodian_img);
			holder.status = (TextView) convertView
					.findViewById(R.id.status);
			
			holder.introduce = (TextView) convertView
					.findViewById(R.id.good);
			holder.name = (TextView) convertView
					.findViewById(R.id.name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(map.get("name") +"");
		holder.status.setText(map.get("status")+"");
		holder.introduce.setText(map.get("introduce")+"");
		//BaseActivity.imageLoader.displayImage(Constant.URL_IMAGE_HOST_STRING+map.get("img"), 
				//holder.img, BaseActivity.options);
		return convertView;
	}
	
}
