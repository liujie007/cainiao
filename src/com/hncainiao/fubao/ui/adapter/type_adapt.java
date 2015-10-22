package com.hncainiao.fubao.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hncainiao.fubao.R;
import com.hncainiao.fubao.model.City;

public class type_adapt extends BaseAdapter {
	
	Context context;
	List<City>types;
	
	
	

	public type_adapt(Context context, List<City> types) {
		this.context = context;
		this.types = types;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return types.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return types.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		class Viewholder{
			TextView type;
		}
		Viewholder holder;

		if(arg1==null){
			arg1=LayoutInflater.from(context).inflate(R.layout.item_spin, null);
			holder=new Viewholder();
			holder.type=(TextView) arg1.findViewById(R.id.sptext);
			arg1.setTag(holder);
		}else{
			 holder = (Viewholder) arg1.getTag();

		}
		holder.type.setText(types.get(arg0).getName());
		
		return arg1;
	}

}
