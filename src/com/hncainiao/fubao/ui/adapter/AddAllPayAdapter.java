package com.hncainiao.fubao.ui.adapter;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.hncainiao.fubao.R;
public class AddAllPayAdapter  extends Adapter<Map<String, Object>>{
	private Context mContext;
	private LayoutInflater inflater;
	public static Map<Integer, Boolean>ischoose=new HashMap<Integer, Boolean>();;

	public AddAllPayAdapter(Context Context) {
		super(Context);
		this.mContext = Context;
		this.inflater = LayoutInflater.from(mContext);
		
	}

	public class ViewHolder{
		TextView data;
		TextView name;
		TextView connect;
		TextView money;
		TextView hospiatl;
		ImageView img;
		public CheckBox box;
	} 
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = (HashMap<String, Object>) mList
				.get(position);
		for(int i=0;i<mList.size();i++){
			ischoose.put(i, false);	
			
		}
		ViewHolder holder = null;
		
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.addallpay_item, null);
			holder = new ViewHolder();
			holder.img = (ImageView) convertView
					.findViewById(R.id.img);
			holder.data = (TextView) convertView
					.findViewById(R.id.time);
			holder.name = (TextView) convertView
					.findViewById(R.id.name);
			holder.connect = (TextView) convertView
					.findViewById(R.id.connect);
			holder.money = (TextView) convertView
					.findViewById(R.id.money);
			holder.hospiatl = (TextView) convertView
					.findViewById(R.id.hospital);
			holder.box = (CheckBox) convertView
					.findViewById(R.id.check);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(map.get("name") + "");
		holder.data.setText(map.get("data") + "");
		holder.connect.setText(map.get("connect") + "");
		holder.money.setText(map.get("money") + "");
		holder.hospiatl.setText(map.get("hospiatl") + "");
		
		 holder.box.setChecked(getIsSelected().get(position));
		
		return convertView;
	}
	
	 public static void setIsSelected(Map<Integer,Boolean> isSelected) {
		 AddAllPayAdapter.ischoose = isSelected;
	    }
	 public static Map<Integer,Boolean> getIsSelected() {
	        return  ischoose;
	    }

	   
	
}
