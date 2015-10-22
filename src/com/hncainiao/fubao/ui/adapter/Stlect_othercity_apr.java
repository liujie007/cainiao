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


public class Stlect_othercity_apr extends BaseAdapter {

	List<City>cities;
	Context mContext;
	
	
	
	public Stlect_othercity_apr(List<City> cities, Context mContext) {
		super();
		this.cities = cities;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cities.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return cities.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		Viewholder holder=null;
		if(arg1==null){
			arg1=LayoutInflater.from(mContext).inflate(R.layout.item_gridview_othercity, null);
			holder=new Viewholder();
			holder.city_name=(TextView) arg1.findViewById(R.id.cityname);
			arg1.setTag(holder);
		}else{
			holder=(Viewholder) arg1.getTag();
		}
		holder.city_name.setText(cities.get(arg0).getName());
		
		return arg1;
	}
	
	static class Viewholder{
		private TextView city_name;
		
	}

}
